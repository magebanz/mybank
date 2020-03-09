package za.co.discovery.entities;

import za.co.discovery.dto.AccountDTO;
import za.co.discovery.dto.AccountTypeDTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "account_type")
public class AccountType {

    @Id
    @Column(name = "account_type_code")
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

    public AccountTypeDTO toAccountTypeDTO(){
        AccountTypeDTO dto = new AccountTypeDTO();
        dto.setTransactional(this.transactional);
        dto.setAccountTypeCode(this.accountTypeCode);
        dto.setDescription(this.description);
        return dto;
    }
}
