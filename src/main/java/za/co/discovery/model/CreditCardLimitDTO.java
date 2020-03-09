package za.co.discovery.model;

import java.math.BigDecimal;

public class CreditCardLimitDTO {
    private String clientAccountNumber;
    private BigDecimal accountLimit;

    public String getClientAccountNumber() {
        return clientAccountNumber;
    }

    public void setClientAccountNumber(String clientAccountNumber) {
        this.clientAccountNumber = clientAccountNumber;
    }

    public BigDecimal getAccountLimit() {
        return accountLimit;
    }

    public void setAccountLimit(BigDecimal accountLimit) {
        this.accountLimit = accountLimit;
    }
}
