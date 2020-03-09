package za.co.discovery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Service;
import za.co.discovery.dao.ClientAccountRepository;
import za.co.discovery.entities.ClientAccount;
import za.co.discovery.error.handling.NoRecordFoundException;
import za.co.discovery.model.AccountDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientAccountService {

    @Autowired
    private ClientAccountRepository clientAccountRepository;
    @Autowired
    private CurrencyService currencyService;
    @Autowired
    private CurrencyConversionRateService currencyConversionRateService;

    private static BigDecimal compare(ClientAccount account1, ClientAccount account2) {
        return account1.getDisplayBalance().subtract(account2.getDisplayBalance());
    }

    public ClientAccount retrieveClientAccounts(String clientAccountNumber){
        Optional<ClientAccount> optClientAccount = clientAccountRepository.findById(clientAccountNumber);
        return optClientAccount.isPresent() ? optClientAccount.get() : null;
    }

    public List<AccountDTO> retrieveAllClientAccounts(Integer clientID) throws NoRecordFoundException {
        List<ClientAccount> clientAccountList = clientAccountRepository.findAllByClientID(clientID);
        List<AccountDTO> accounts = new ArrayList<>();
        if(clientAccountList == null || clientAccountList.isEmpty()){
            throw new NoRecordFoundException("No accounts to display.") ;
        }

        clientAccountList.stream().forEach(account -> {
            AccountDTO dto = account.toAccountDTO();
            dto.setCurrency(currencyService.getAllCurrencies().stream()
                    .filter(currencyDTO -> currencyDTO.getCurrencyCode().equalsIgnoreCase(account.getCurrencyCode())).collect(Collectors.toList()).get(0));
            accounts.add(dto);
        });

        accounts.stream().forEach(accountDTO ->
                accountDTO.getCurrency().setConversionRateDTO(currencyConversionRateService.getCurrencyConversionRate(accountDTO.getCurrency().getCurrencyCode()).toConversionRateDTO()));

        // convert balance to ZAR
        accounts.stream().forEach(accountDTO -> {
            SpelExpressionParser parser =  new SpelExpressionParser();
            Expression expression = parser.parseExpression(accountDTO.getDisplayBalance() + " " +  accountDTO.getCurrency().getConversionRateDTO().getConversionIndicator() + " " + accountDTO.getCurrency().getConversionRateDTO().getRate());
            BigDecimal convertedBal = BigDecimal.valueOf((Double) expression.getValue());
            accountDTO.setZarDisplayBalance(convertedBal);
        });
        return accounts;
    }

    public AccountDTO withdrawAmount(AccountDTO account, BigDecimal withdrawalAmount) throws Exception {
        if(account.getDisplayBalance().compareTo(withdrawalAmount) < 0){
            throw new Exception("Insufficient funds.");
        }

        ClientAccount entity = new ClientAccount();
        entity.setAccountTypeCode(account.getAccountTypeCode());
        entity.setClientID(account.getClientID());
        entity.setClientAccountNumber(account.getClientAccountNumber());
        entity.setCurrencyCode(account.getCurrency().getCurrencyCode());
        entity.setDisplayBalance(account.getDisplayBalance().subtract(withdrawalAmount));
        clientAccountRepository.save(entity);

        return entity.toAccountDTO();

    }


}
