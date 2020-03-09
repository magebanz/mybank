package za.co.discovery.entities;

import za.co.discovery.dto.CurrencyConversionRateDTO;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity(name = "currency_conversion_rate")
public class CurrencyConversionRate {

    @Id
    private String currencyCode;
    private String conversionIndicator;
    private BigDecimal rate;

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getConversionIndicator() {
        return conversionIndicator;
    }

    public void setConversionIndicator(String conversionIndicator) {
        this.conversionIndicator = conversionIndicator;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public CurrencyConversionRateDTO toConversionRateDTO(){
        CurrencyConversionRateDTO dto = new CurrencyConversionRateDTO();
        dto.setConversionIndicator(this.conversionIndicator);
        dto.setCurrencyCode(this.currencyCode);
        dto.setRate(this.rate);
        return dto;
    }
}
