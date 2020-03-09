package za.co.discovery.model;

import java.math.BigDecimal;

public class WithdrawalRequest {
    private Integer atmID;
    private Integer clientID;
    private String accountNumber;
    private BigDecimal amount;

    public Integer getAtmID() {
        return atmID;
    }

    public void setAtmID(Integer atmID) {
        this.atmID = atmID;
    }

    public Integer getClientID() {
        return clientID;
    }

    public void setClientID(Integer clientID) {
        this.clientID = clientID;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
