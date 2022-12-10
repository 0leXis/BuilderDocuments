package com.builder.documents.builderdocuments.models.services;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.builder.documents.builderdocuments.models.DocumentEntity;
import com.builder.documents.builderdocuments.models.DocumentTemplateEntity;
import com.builder.documents.builderdocuments.models.StaffEntity;
import com.builder.documents.builderdocuments.models.documentSavers.MultipartDocumentSaver;
import com.builder.documents.builderdocuments.models.documentSavers.StringDocumentSaver;
import com.builder.documents.builderdocuments.models.interfaces.IDocumentSaver;
import com.builder.documents.builderdocuments.models.interfaces.IDocumentsService;
import com.builder.documents.builderdocuments.models.interfaces.IStaffService;
import com.builder.documents.builderdocuments.models.interfaces.IXMLValidationService;
import com.builder.documents.builderdocuments.models.repositories.DocumentApproversRepository;
import com.builder.documents.builderdocuments.models.repositories.DocumentsRepository;
import com.builder.documents.builderdocuments.models.repositories.LoginInfoRepository;
import com.builder.documents.builderdocuments.models.repositories.StaffRepository;
import org.apache.commons.io.FilenameUtils;
import org.apache.xerces.impl.dv.util.Base64;

@Service
public class DocumentsService implements IDocumentsService {
    public static String documentsPath = "E:\\Java\\BuilderDocuments\\builderdocuments\\src\\main\\resources\\storage\\"; //TODO To config

    @Autowired
    IXMLValidationService xmlService;
    @Autowired
    IStaffService staffService;
    @Autowired
    LoginInfoRepository loginRepo;
    @Autowired
    DocumentsRepository documentsRepo;
    @Autowired
    DocumentApproversRepository approversRepo;

    public String addDocument(DocumentEntity info, IDocumentSaver saver, String secretKey) {
        //TODO Template check
        //TODO Localization
        if(info.getName().length() < 1)
            return "Enter name";
        if(info.getDescription() == null)
            info.setDescription("Нет описания");

        String validationError = saver.Validate();
        if(validationError != null)
            return "Error uploading document";

        info.setDateCreated(new Date());
        info.setDateModified(new Date());

        Optional<StaffEntity> creator = staffService.getCurrentStaff();
        if(!creator.isPresent())
            return "Creator does not exists";
        info.setCreator(creator.get());

        String documentFile;
        try{
            documentFile = saver.Save(documentsPath);
        }
        catch(IOException e){
            return "Error uploading document";
        }

        info.setPath(documentFile);

        try{
            setDocumentHash(info, secretKey);
            if(compareHashes(info, creator.get().getOpenKey())){
                try{
                    Files.deleteIfExists(Paths.get(info.getPath()));
                    //TODO WHY EXCEPTOION??????
                }
                catch (Exception e) {}
                return "Invalid sign";
            }
        }
        catch(Exception e){
            try{
                Files.deleteIfExists(Paths.get(info.getPath()));
                //TODO WHY EXCEPTOION??????
            }
            catch (Exception ex) {}
            return "Error comparing hashes";
        }

        documentsRepo.save(info);
        return null;
    }

    @Override
    public String addDocument(DocumentEntity info, MultipartFile file, String secretKey)
    {
        info.setFormalized(false);
        return addDocument(info, new MultipartDocumentSaver(file), secretKey);
    }

    @Override
    public String addDocument(DocumentEntity info, String documentText, String secretKey)
    {
        info.setFormalized(true);
        DocumentTemplateEntity template = info.getTemplate();
        if(template == null)
            return addDocument(info, new StringDocumentSaver(documentText, null, xmlService), secretKey);
        else
            return addDocument(info, new StringDocumentSaver(documentText, new File(template.getPath()), xmlService), secretKey);
    }

    @Override
    public String editDocument(DocumentEntity info, MultipartFile file, String secretKey) {
        info.setDateModified(new Date());
        return null;
    }

    @Override
    public String deleteDocument(DocumentEntity info) {
        Optional<DocumentEntity> documentEntity = documentsRepo.findById(info.getIdDocument());
        if(documentEntity == null){
            return "Document doesn't exists";
        }

        try{
            Files.deleteIfExists(Paths.get(info.getPath()));
            //TODO WHY EXCEPTOION??????
        }
        catch (Exception e) {}

        approversRepo.deleteByDocument(documentEntity.get());
        documentsRepo.delete(documentEntity.get());

        return null;
    }
    
    public void setDocumentHash(DocumentEntity info, String secretKeyString) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException{
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(Base64.decode(secretKeyString));
        PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

        Cipher encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE, privateKey);

        byte[] fileBytes = Files.readAllBytes(Paths.get(info.getPath()));
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(fileBytes);

        info.setHash(Base64.encode(encryptCipher.doFinal(hash)));
    }

    public boolean compareHashes(DocumentEntity info, String publicKeyString) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException{
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(Base64.decode(publicKeyString));
        PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

        Cipher encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] fileBytes = Files.readAllBytes(Paths.get(info.getPath()));
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(fileBytes);


        byte[] decoded = Base64.decode(info.getHash());
        decoded = Arrays.copyOf(decoded, 245);
        return Base64.encode(encryptCipher.doFinal(decoded)).equals(Base64.encode(hash));
    }
}
