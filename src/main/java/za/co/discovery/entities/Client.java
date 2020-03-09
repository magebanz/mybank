package za.co.discovery.entities;

import za.co.discovery.model.ClientDTO;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class Client {
    @Id
    @Column(name = "client_id")
    private  Integer clientID;
    private String title;
    private String name;
    private String surname;
    private Date dob;
    private String clientSubTypeCode;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Integer getClientID() {
        return clientID;
    }

    public void setClientID(Integer client_id) {
        this.clientID = client_id;
    }

    public String getClientSubTypeCode() {
        return clientSubTypeCode;
    }

    public void setClientSubTypeCode(String clientSubTypeCode) {
        this.clientSubTypeCode = clientSubTypeCode;
    }

    public ClientDTO toClientDto(){
        ClientDTO dto = new ClientDTO();
        dto.setClientId(this.clientID);
        dto.setDob(this.dob);
        dto.setName(this.name);
        dto.setSurname(this.surname);
        dto.setTitle(this.title);
        return dto;
    }
}
