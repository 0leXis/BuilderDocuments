package com.builder.documents.builderdocuments.models.interfaces;

import com.builder.documents.builderdocuments.models.LoginInfoEntity;

public interface ILoginInfoService {
    public String addUser(LoginInfoEntity info);
    public String editUser(LoginInfoEntity info);
    public String deleteUser(LoginInfoEntity info);
}
