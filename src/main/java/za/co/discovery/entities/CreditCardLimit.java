package za.co.discovery.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity(name = "credit_card_limit")
public class CreditCardLimit {

    @Id
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
