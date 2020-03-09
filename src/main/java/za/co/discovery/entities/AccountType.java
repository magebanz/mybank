package za.co.discovery.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "account_type")
public class AccountType {

    @Id
    private String accountTypeCode;
    private String description;
    private Boolean transactional;

    public String getAccountTypeCode() {
        return accountTypeCode;
    }

    public void setAccountTypeCode(String accountTypeCode) {
        this.accountTypeCode = accountTypeCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getTransactional() {
        return transactional;
    }

    public void setTransactional(Boolean transactional) {
        this.transactional = transactional;
    }
}
