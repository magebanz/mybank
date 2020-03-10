package za.co.discovery.entities;

import za.co.discovery.dto.ClientSubTypeDTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "client_sub_type")
public class ClientSubType {

    @Id
    @Column(name = "client_sub_type_code")
    private String clientSubTypeCode;
    private String clientTypeCode;
    private String description;

    public String getClientSubTypeCode() {
        return clientSubTypeCode;
    }

    public void setClientSubTypeCode(String clientSubTypeCode) {
        this.clientSubTypeCode = clientSubTypeCode;
    }

    public String getClientTypeCode() {
        return clientTypeCode;
    }

    public void setClientTypeCode(String clientTypeCode) {
        this.clientTypeCode = clientTypeCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ClientSubTypeDTO toClientSubTypeDTO(){
        ClientSubTypeDTO dto = new ClientSubTypeDTO();
        dto.setClientSubTypeCode(this.clientSubTypeCode);
        dto.setClientTypeCode(this.clientTypeCode);
        dto.setDescription(this.description);
        return dto;
    }
    
}
