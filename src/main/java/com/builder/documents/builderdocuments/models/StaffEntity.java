package com.builder.documents.builderdocuments.models;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "staff")
public class StaffEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idstaff")
    private long idStaff; 

    @Column(name = "name")
    private String name;

    @Column(name = "lastname")
    private String lastname;

    @ManyToOne
    @JoinColumn(name = "position", referencedColumnName = "idpositions")
    private PositionEntity position;

    @Column(name = "salary")
    private BigDecimal salary;

    @OneToOne
    @JoinColumn(name = "logininfo", referencedColumnName = "idlogininfo")
    private LoginInfoEntity logininfo;

    public StaffEntity() {}

    public StaffEntity(long idStaff, String name, String lastname, PositionEntity position, BigDecimal salary, LoginInfoEntity loginInfo) {
        this.idStaff = idStaff;
        this.name = name;
        this.lastname = lastname;
        this.position = position;
        this.salary = salary;
        this.logininfo = loginInfo;
    }

    public long getIdStaff() {
        return idStaff;
    }

    public void setIdStaff(long idStaff) {
        this.idStaff = idStaff;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public PositionEntity getPosition() {
        return position;
    }

    public void setPosition(PositionEntity position) {
        this.position = position;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public LoginInfoEntity getLoginInfo() {
        return logininfo;
    }

    public void setLoginInfo(LoginInfoEntity loginInfo) {
        this.logininfo = loginInfo;
    }
}
