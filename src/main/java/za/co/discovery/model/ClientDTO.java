package za.co.discovery.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.sql.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientDTO {
    private  Integer clientId;
    private String title;
    private String name;
    private String surname;
    private Date dob;
    private ClientSubTypeDTO clientSubTypeDTO;
    private List<AccountDTO> accounts;

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

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

    public ClientSubTypeDTO getClientSubTypeDTO() {
        return clientSubTypeDTO;
    }

    public void setClientSubTypeDTO(ClientSubTypeDTO clientSubTypeDTO) {
        this.clientSubTypeDTO = clientSubTypeDTO;
    }

    public List<AccountDTO> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountDTO> accounts) {
        this.accounts = accounts;
    }
}
