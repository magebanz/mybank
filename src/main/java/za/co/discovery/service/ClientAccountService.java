package za.co.discovery.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Service;
import za.co.discovery.dao.ClientAccountRepository;
import za.co.discovery.dto.AccountTypeDTO;
import za.co.discovery.entities.ClientAccount;
import za.co.discovery.error.handling.NoRecordFoundException;
import za.co.discovery.dto.AccountDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientAccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientAccountService.class);

    private final ClientAccountRepository clientAccountRepository;
    private final CurrencyService currencyService;
    private final CurrencyConversionRateService currencyConversionRateService;
    private final CreditCardLimitService creditCardLimitService;

    @Autowired
    private AccountTypeService accountTypeService;

    @Autowired
    public ClientAccountService(ClientAccountRepository clientAccountRepository, CurrencyService currencyService, CurrencyConversionRateService currencyConversionRateService, CreditCardLimitService creditCardLimitService) {
        this.clientAccountRepository = clientAccountRepository;
        this.currencyService = currencyService;
        this.currencyConversionRateService = currencyConversionRateService;
        this.creditCardLimitService = creditCardLimitService;
    }

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

        // add currency conversion rate
        accounts.stream().forEach(accountDTO ->
                accountDTO.getCurrency().setConversionRateDTO(currencyConversionRateService.getCurrencyConversionRate(accountDTO.getCurrency().getCurrencyCode()).toConversionRateDTO()));

        // add account limits
        accounts.stream().forEach(accountDTO -> {
            try{
                accountDTO.setAccountLimitDTO(creditCardLimitService.getAccountLimit(accountDTO));
            } catch (NoRecordFoundException nrfe) {
                LOGGER.error(String.format("No limits set for account number %s", accountDTO.getClientAccountNumber()));
            }
        });

        // add account type
        accounts.stream().forEach(accountDTO -> {
            try{
                accountDTO.setAccountTypeDTO(accountTypeService.getAccountType(accountDTO));
            } catch(NoRecordFoundException nrfe){
                LOGGER.error(String.format("No account type for account number %s", accountDTO.getClientAccountNumber()));
                accountDTO.setAccountTypeDTO(new AccountTypeDTO());
            }
        });
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
        // just validate again incase there were other transactions against the account, i.e debit order, send money etc..
        if(account.getZarDisplayBalance().compareTo(withdrawalAmount) < 0){
            throw new Exception("Insufficient funds.");
        }
        if(account.getAccountLimitDTO() != null && account.getAccountLimitDTO().getAccountLimit().compareTo(withdrawalAmount) < 0) {
            throw new Exception("Account limit exceeded");
        }

        ClientAccount entity = new ClientAccount();
        entity.setAccountTypeCode(account.getAccountTypeDTO().getAccountTypeCode());
        entity.setClientID(account.getClientID());
        entity.setClientAccountNumber(account.getClientAccountNumber());
        entity.setCurrencyCode(account.getCurrency().getCurrencyCode());
        if(!account.getCurrency().getCurrencyCode().equalsIgnoreCase("ZAR")){
            SpelExpressionParser parser =  new SpelExpressionParser();
            String conversionIndicator = (account.getCurrency().getConversionRateDTO().getConversionIndicator().equalsIgnoreCase("/"))? "*":"/";
            Expression expression = parser.parseExpression(withdrawalAmount + " " +  conversionIndicator + " " + account.getCurrency().getConversionRateDTO().getRate());
            BigDecimal convertedBal = BigDecimal.valueOf((Double) expression.getValue());
            // convert withdrawal amount to Account's currency
            LOGGER.info(String.format("Converted R %s to %s %s", withdrawalAmount.toString(),account.getCurrency().getCurrencyCode(),convertedBal.toString()));
            withdrawalAmount =  convertedBal;

        }
        entity.setDisplayBalance(account.getDisplayBalance().subtract(withdrawalAmount));

        clientAccountRepository.save(entity);

        return entity.toAccountDTO();

    }


}
