package za.co.discovery.model;

import java.math.BigDecimal;

public class DenominationDTO {
    private int denominationID;
    private BigDecimal value;
    private String denominationTypeCode;
    private int atmDenominationCount;
    private BigDecimal total;

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

    public int getAtmDenominationCount() {
        return atmDenominationCount;
    }

    public void setAtmDenominationCount(int atmDenominationCount) {
        this.atmDenominationCount = atmDenominationCount;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
