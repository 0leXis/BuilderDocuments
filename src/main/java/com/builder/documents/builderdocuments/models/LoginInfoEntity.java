package com.builder.documents.builderdocuments.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "logininfo")
public class LoginInfoEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idlogininfo")
    private long idLoginInfo; 

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private Role role;

    public LoginInfoEntity() {}

    public LoginInfoEntity(long idLoginInfo, String login, String password, String salt) {
        this.idLoginInfo = idLoginInfo;
        this.login = login;
        this.password = password;
    }

    public long getIdLoginInfo() {
        return idLoginInfo;
    }

    public void setIdLoginInfo(long idLoginInfo) {
        this.idLoginInfo = idLoginInfo;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
