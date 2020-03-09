package za.co.discovery.dto;

public class CurrencyDTO {
    private String currencyCode;
    private int decimalPlaces;
    private String description;
    private CurrencyConversionRateDTO conversionRateDTO;

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

    public CurrencyConversionRateDTO getConversionRateDTO() {
        return conversionRateDTO;
    }

    public void setConversionRateDTO(CurrencyConversionRateDTO conversionRateDTO) {
        this.conversionRateDTO = conversionRateDTO;
    }
}
