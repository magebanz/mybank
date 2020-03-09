package za.co.discovery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.discovery.dao.CreditCardLimitRepository;
import za.co.discovery.dto.AccountDTO;
import za.co.discovery.dto.CreditCardLimitDTO;
import za.co.discovery.entities.CreditCardLimit;
import za.co.discovery.error.handling.NoRecordFoundException;

@Service
public class CreditCardLimitService {

    private final CreditCardLimitRepository cardLimitRepository;

    @Autowired
    public CreditCardLimitService(CreditCardLimitRepository cardLimitRepository) {
        this.cardLimitRepository = cardLimitRepository;
    }

    public CreditCardLimitDTO getAccountLimit(AccountDTO accountDTO) {
        CreditCardLimit cardLimit = cardLimitRepository.findById(accountDTO.getClientAccountNumber()).orElseThrow(() -> new NoRecordFoundException(String.format("No limits available for account number : %s ", accountDTO.getClientAccountNumber())));
        return cardLimit.toCreditCardLimitDTO();
    }
}
