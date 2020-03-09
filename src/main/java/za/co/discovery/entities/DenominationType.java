package za.co.discovery.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "denomination_type")
public class DenominationType {

    @Id
    private String denominationTypeCode;
    private String description;

    public String getDenominationTypeCode() {
        return denominationTypeCode;
    }

    public void setDenominationTypeCode(String denominationTypeCode) {
        this.denominationTypeCode = denominationTypeCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
