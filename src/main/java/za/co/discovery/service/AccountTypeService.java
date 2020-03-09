package za.co.discovery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.discovery.dao.AccountTypeRepository;
import za.co.discovery.dto.AccountDTO;
import za.co.discovery.dto.AccountTypeDTO;
import za.co.discovery.entities.AccountType;
import za.co.discovery.error.handling.NoRecordFoundException;

@Service
public class AccountTypeService {

    private final AccountTypeRepository accountTypeRepository;

    @Autowired
    public AccountTypeService(AccountTypeRepository accountTypeRepository) {
        this.accountTypeRepository = accountTypeRepository;
    }

    public AccountTypeDTO getAccountType(AccountDTO accountDTO) {
        AccountType accountType = accountTypeRepository.findById(accountDTO.getAccountTypeCode()).orElseThrow(()
                -> new NoRecordFoundException(String.format("No account type for account number %s", accountDTO.getClientAccountNumber())));
        return accountType.toAccountTypeDTO();
    }
}
