package com.builder.documents.builderdocuments.models.services;

import java.util.Optional;
import java.util.regex.Pattern;

import javax.persistence.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.*;

import com.builder.documents.builderdocuments.models.LoginInfoEntity;
import com.builder.documents.builderdocuments.models.Role;
import com.builder.documents.builderdocuments.models.interfaces.ILoginInfoService;
import com.builder.documents.builderdocuments.models.repositories.LoginInfoRepository;

@Service
public class LoginInfoService implements ILoginInfoService {

    private Pattern isMatchSecurityRegex = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$");
    
    @PersistenceContext
    private EntityManager em;
    @Autowired
    LoginInfoRepository loginInfos;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public String addUser(LoginInfoEntity info) {
        LoginInfoEntity user = loginInfos.findByLogin(info.getLogin());
        if(user != null){
            return "User already exists";
        }
        if(info.getLogin() == null || info.getPassword() == null){
            return "Enter login and password";
        }
        if(!isMatchSecurityRegex.matcher(info.getPassword()).matches()){
            return "Password length must be 8 or higher, should include at least one uppercase letter, lowercase letter and digit";
        }

      info.setRole(Role.USER);
      info.setPassword(bCryptPasswordEncoder.encode(info.getPassword()));
      loginInfos.save(info);
      return null;
    }

    @Override
    public String editUser(LoginInfoEntity info) {
        Optional<LoginInfoEntity> user = loginInfos.findById(info.getIdLoginInfo());
        if(user == null){
            return "User doesn't exists";
        }
        LoginInfoEntity userByLogin = loginInfos.findByLogin(info.getLogin());
        if(userByLogin != null && userByLogin != user.get()){
            return "There is another user with same login";
        }
        if(info.getLogin() == null || info.getPassword() == null){
            return "Enter login and password";
        }
        if(!isMatchSecurityRegex.matcher(info.getPassword()).matches()){
            return "Password length must be 8 or higher, should include at least one uppercase letter, lowercase letter and digit";
        }

      info.setRole(Role.USER);
      info.setPassword(bCryptPasswordEncoder.encode(info.getPassword()));
      loginInfos.save(info);
      return null;
    }

    @Override
    public String deleteUser(LoginInfoEntity info) {
        Optional<LoginInfoEntity> user = loginInfos.findById(info.getIdLoginInfo());
        if(user == null){
            return "User doesn't exists";
        }

        loginInfos.delete(info);
        return null;
    }
}