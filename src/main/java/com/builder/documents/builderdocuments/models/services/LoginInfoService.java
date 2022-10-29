package com.builder.documents.builderdocuments.models.services;

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

    public String saveUser(LoginInfoEntity info) {
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

      //TODO also get staff info
      info.setStaff(null);
      info.setRole(Role.USER);
      info.setPassword(bCryptPasswordEncoder.encode(info.getPassword()));
      loginInfos.save(info);
      return null;
    }
}