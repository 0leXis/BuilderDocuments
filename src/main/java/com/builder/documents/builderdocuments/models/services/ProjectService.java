package com.builder.documents.builderdocuments.models.services;

import java.io.StringWriter;
import java.util.List;
import java.util.Optional;

import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.*;

import com.builder.documents.builderdocuments.models.DocumentEntity;
import com.builder.documents.builderdocuments.models.ProjectEntity;
import com.builder.documents.builderdocuments.models.ProjectMaterialEntity;
import com.builder.documents.builderdocuments.models.ProjectsStateEntity;
import com.builder.documents.builderdocuments.models.interfaces.IProjectService;
import com.builder.documents.builderdocuments.models.repositories.DocumentsRepository;
import com.builder.documents.builderdocuments.models.repositories.ProjectMaterialRepository;
import com.builder.documents.builderdocuments.models.repositories.ProjectsRepository;
import com.builder.documents.builderdocuments.models.repositories.ProjectsStatesRepository;

@Service
public class ProjectService implements IProjectService{

    @Autowired
    ProjectsRepository projectRepo;
    @Autowired
    DocumentsService documentsService;
    @Autowired
    StaffService staffService;
    @Autowired
    ProjectsStatesRepository projectsStatesRepo;
    @Autowired
    ProjectMaterialRepository projectMaterialsRepo;

    @Override
    public String addProject(ProjectEntity project) {
        Optional<ProjectEntity> projectEntity = projectRepo.findById(project.getIdProjects());
        if(projectEntity.isPresent())
            return "Project exists";

        if(project.getName() == null || project.getName().length() < 1)
            return "Enter name";

        projectRepo.save(project);
        return null;
    }

    @Override
    public String editProject(ProjectEntity project) {
        Optional<ProjectEntity> projectEntity = projectRepo.findById(project.getIdProjects());
        if(!projectEntity.isPresent())
            return "Staff doesn't exists";
        if(project.getName() == null || project.getName().length() < 1)
            return "Enter name";

        projectRepo.save(project);
        return null;
    }

    @Override
    public String deleteProject(ProjectEntity project) {
        Optional<ProjectEntity> projectEntity = projectRepo.findById(project.getIdProjects());
        if(projectEntity == null){
            return "Project doesn't exists";
        }

        projectRepo.delete(project);
        return null;
    }

    @Override
    public String createDocument(ProjectEntity project, String secretKey) {
        Optional<ProjectEntity> projectEntity = projectRepo.findById(project.getIdProjects());
        if(projectEntity == null){
            return "Project doesn't exists";
        }

        StringWriter documentTextBuffer = new StringWriter();

        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        
            Document doc = docBuilder.newDocument();
        
            Element rootElement = doc.createElement("project");
            doc.appendChild(rootElement);
        
            Element name = doc.createElement("name");
            name.appendChild(doc.createTextNode(projectEntity.get().getName()));
            rootElement.appendChild(name);

            Element client = doc.createElement("client");
            client.appendChild(doc.createTextNode(projectEntity.get().getOrder().getClient().getName()));
            rootElement.appendChild(client);
        
            Element startDate = doc.createElement("startDate");
            startDate.appendChild(doc.createTextNode(projectEntity.get().getStartDate().toString()));
            rootElement.appendChild(startDate);
        
            Element estimateEndDate = doc.createElement("estimateEndDate");
            estimateEndDate.appendChild(doc.createTextNode(projectEntity.get().getEstimateEndDate().toString()));
            rootElement.appendChild(estimateEndDate);

            if(projectEntity.get().getEndDate() != null){
                Element endDate = doc.createElement("endDate");
                endDate.appendChild(doc.createTextNode(projectEntity.get().getEndDate().toString()));
                rootElement.appendChild(endDate);
            }

            Element cost = doc.createElement("cost");
            cost.appendChild(doc.createTextNode(projectEntity.get().getCost().toString()));
            rootElement.appendChild(cost);
        
            Element states = doc.createElement("states");
            rootElement.appendChild(states);

            List<ProjectsStateEntity> projectStates = projectsStatesRepo.findByIdProject(projectEntity.get());
            for (ProjectsStateEntity state : projectStates) {
                Element stateElement = doc.createElement("state");
                states.appendChild(stateElement);

                name = doc.createElement("name");
                name.appendChild(doc.createTextNode(state.getIdState().getName()));
                stateElement.appendChild(name);

                startDate = doc.createElement("startDate");
                startDate.appendChild(doc.createTextNode(state.getStartDate().toString()));
                stateElement.appendChild(startDate);
            
                estimateEndDate = doc.createElement("estimateEndDate");
                estimateEndDate.appendChild(doc.createTextNode(state.getEstimateEndDate().toString()));
                stateElement.appendChild(estimateEndDate);
    
                if(projectEntity.get().getEndDate() != null){
                    Element endDate = doc.createElement("endDate");
                    endDate.appendChild(doc.createTextNode(state.getEndDate().toString()));
                    stateElement.appendChild(endDate);
                }
            }

            Element materials = doc.createElement("materials");
            rootElement.appendChild(materials);

            List<ProjectMaterialEntity> projectMaterials = projectMaterialsRepo.findByIdProject(projectEntity.get());
            for (ProjectMaterialEntity material : projectMaterials) {
                Element materialElement = doc.createElement("material");
                materials.appendChild(materialElement);

                name = doc.createElement("name");
                name.appendChild(doc.createTextNode(material.getIdMaterial().getName()));
                materialElement.appendChild(name);

                Element estimateCount = doc.createElement("estimateCount");
                estimateCount.appendChild(doc.createTextNode(String.valueOf(material.getEstimateCount())));
                materialElement.appendChild(estimateCount);

                Element usedCount= doc.createElement("usedCount");
                usedCount.appendChild(doc.createTextNode(String.valueOf(material.getUsedCount())));
                materialElement.appendChild(usedCount);
            }
        
            //write the content into xml file
            TransformerFactory transformerFactory =  TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(doc);
        
            StreamResult result =  new StreamResult(documentTextBuffer);
            transformer.transform(source, result);
        
        }catch(ParserConfigurationException e){
            return e.getMessage();
        }catch(TransformerException e){
            return e.getMessage();
        }

        DocumentEntity document = new DocumentEntity(0, "Отчет: " + projectEntity.get().getName(), staffService.getCurrentStaff().get(), null, null, null, null, null, null, null, true);
        String errorMessage = documentsService.addDocument(document, documentTextBuffer.getBuffer().toString(), secretKey);
        
        if(errorMessage != null)
            return errorMessage;
        return "redirect:/document?item="+document.getIdDocument();
    }
}
