package com.StockAlarms.StockAlarmsApplication.domain;


import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Turian Ovidiu.
 * This class represent User Entity.
 */

@Entity
public class User implements DomainObject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Version
    private Integer version;


    @NotEmpty
    @Size(min = 2, max = 50, message = "Please insert a valid first name")
    private String firstName;


    @NotEmpty
    @Size(min = 2, max = 50, message = "Please insert a valid last name")
    private String lastName;

    @NotEmpty
    @Email
    private String email;

    @Transient
    private String password;

    private String encryptedPassword;

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "user", orphanRemoval = true)
    private List<Alarm> alarmList = new ArrayList<>();

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Alarm> getAlarmList() {
        if (alarmList == null){
            alarmList = new ArrayList<>();
        }
        return alarmList;
    }

    public void setAlarmList(List<Alarm> alarmList) {
        this.alarmList = alarmList;
    }
}
