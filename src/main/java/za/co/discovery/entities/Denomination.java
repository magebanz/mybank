package za.co.discovery.entities;

import za.co.discovery.model.DenominationDTO;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity(name = "denomination")
public class Denomination {

    @Id
    private int denominationID;
    private BigDecimal value;
    private String denominationTypeCode;

    public int getDenominationID() {
        return denominationID;
    }

    public void setDenominationID(int denominationID) {
        this.denominationID = denominationID;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getDenominationTypeCode() {
        return denominationTypeCode;
    }

    public void setDenominationTypeCode(String denominationTypeCode) {
        this.denominationTypeCode = denominationTypeCode;
    }

    public DenominationDTO toDTO(){
        DenominationDTO dto = new DenominationDTO();
        dto.setDenominationID(this.denominationID);
        dto.setValue(this.value);
        dto.setDenominationTypeCode(this.denominationTypeCode);
        return dto;
    }
}
