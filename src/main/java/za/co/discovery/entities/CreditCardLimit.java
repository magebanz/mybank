package za.co.discovery.entities;

import za.co.discovery.dto.CreditCardLimitDTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity(name = "credit_card_limit")
public class CreditCardLimit {

    @Id
    @Column(name = "client_account_number")
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

    public CreditCardLimitDTO toCreditCardLimitDTO(){
        CreditCardLimitDTO dto = new CreditCardLimitDTO();
        dto.setAccountLimit(this.accountLimit);
        dto.setClientAccountNumber(this.clientAccountNumber);
        return dto;
    }
}
