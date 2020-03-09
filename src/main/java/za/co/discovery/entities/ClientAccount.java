package za.co.discovery.entities;

import za.co.discovery.dto.AccountDTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity(name = "client_account")
public class ClientAccount {
    @Id
    @Column(name="client_account_number")
    private String clientAccountNumber;
    @Column(name = "client_id")
    private Integer clientID;
    private String accountTypeCode;
    private String currencyCode;
    private BigDecimal displayBalance;

    public String getClientAccountNumber() {
        return clientAccountNumber;
    }

    public void setClientAccountNumber(String clientAccountNumber) {
        this.clientAccountNumber = clientAccountNumber;
    }

    public Integer getClientID() {
        return clientID;
    }

    public void setClientID(Integer client_id) {
        this.clientID = client_id;
    }

    public String getAccountTypeCode() {
        return accountTypeCode;
    }

    public void setAccountTypeCode(String account_type_code) {
        this.accountTypeCode = account_type_code;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currency_code) {
        this.currencyCode = currency_code;
    }

    public BigDecimal getDisplayBalance() {
        return displayBalance;
    }

    public void setDisplayBalance(BigDecimal display_balance) {
        this.displayBalance = display_balance;
    }

    public AccountDTO toAccountDTO() {
        AccountDTO dto = new AccountDTO();
        dto.setAccountTypeCode(this.accountTypeCode);
        dto.setClientAccountNumber(this.clientAccountNumber);
        dto.setClientID(this.clientID);
        dto.setDisplayBalance(this.displayBalance);
        return dto;
    }

}
