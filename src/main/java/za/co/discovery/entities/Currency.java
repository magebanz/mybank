package za.co.discovery.entities;

import za.co.discovery.dto.CurrencyDTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "currency")
public class Currency {

    @Id
    @Column (name = "currency_code")
    private String currencyCode;
    private int decimalPlaces;
    private String description;

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public int getDecimalPlaces() {
        return decimalPlaces;
    }

    public void setDecimalPlaces(int decimalPlaces) {
        this.decimalPlaces = decimalPlaces;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CurrencyDTO toCurrencyDTO(){
        CurrencyDTO dto = new CurrencyDTO();
        dto.setCurrencyCode(this.currencyCode);
        dto.setDecimalPlaces(this.decimalPlaces);
        dto.setDescription(this.description);
        return dto;
    }
}
