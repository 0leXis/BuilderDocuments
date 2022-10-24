package com.builder.documents.builderdocuments.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "logininfo")
public class LoginInfoEntity implements Serializable {

    @Id
    @Column(name = "idlogininfo")
    private long idLoginInfo; 

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "salt")
    private String salt;

    @Column(name = "staff")
    private long staff; //TODO link

    public LoginInfoEntity() {}

    public LoginInfoEntity(long idLoginInfo, String login, String password, String salt, long staff) { //TODO link
        this.idLoginInfo = idLoginInfo;
        this.login = login;
        this.password = password;
        this.salt = salt;
        this.staff = staff;
    }

    public long getId() {
        return idLoginInfo;
    }

    public void setId(long idLoginInfo) {
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

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public long getStaff() {
        return staff;
    }

    public void setStaff(long staff) {
        this.staff = staff;
    }
}
