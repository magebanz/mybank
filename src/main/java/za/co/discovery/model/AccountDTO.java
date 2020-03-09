package za.co.discovery.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountDTO {
    private String clientAccountNumber;
    private Integer clientID;
    private String accountTypeCode;
    private CurrencyDTO currency;
    private BigDecimal displayBalance;
    private BigDecimal zarDisplayBalance;

    public String getClientAccountNumber() {
        return clientAccountNumber;
    }

    public void setClientAccountNumber(String clientAccountNumber) {
        this.clientAccountNumber = clientAccountNumber;
    }

    public Integer getClientID() {
        return clientID;
    }

    public void setClientID(Integer clientID) {
        this.clientID = clientID;
    }

    public String getAccountTypeCode() {
        return accountTypeCode;
    }

    public void setAccountTypeCode(String accountTypeCode) {
        this.accountTypeCode = accountTypeCode;
    }

    public CurrencyDTO getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyDTO currency) {
        this.currency = currency;
    }

    public BigDecimal getDisplayBalance() {
        return displayBalance;
    }

    public void setDisplayBalance(BigDecimal displayBalance) {
        this.displayBalance = displayBalance;
    }

    public BigDecimal getZarDisplayBalance() {
        return zarDisplayBalance;
    }

    public void setZarDisplayBalance(BigDecimal zarDisplayBalance) {
        this.zarDisplayBalance = zarDisplayBalance;
    }
}
