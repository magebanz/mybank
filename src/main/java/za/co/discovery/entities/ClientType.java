package za.co.discovery.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "client_type")
public class ClientType {

    @Id
    private String clientTypeCode;
    private String description;

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
}
