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

    @Column(name = "staff")
    private Long staff; //TODO link

    @Column(name = "role")
    private Role role;

    public LoginInfoEntity() {}

    public LoginInfoEntity(long idLoginInfo, String login, String password, String salt, Long staff) { //TODO link
        this.idLoginInfo = idLoginInfo;
        this.login = login;
        this.password = password;
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

    public Long getStaff() {
        return staff;
    }

    public void setStaff(Long staff) {
        this.staff = staff;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}