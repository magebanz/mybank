package za.co.discovery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Service;
import za.co.discovery.dao.ClientRepository;
import za.co.discovery.error.handling.NoRecordFoundException;
import za.co.discovery.entities.Client;
import za.co.discovery.model.AccountDTO;
import za.co.discovery.model.ClientDTO;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientAccountService clientAccountService;
    private final CurrencyConversionRateService currencyConversionRateService;

    @Autowired
    public ClientService(ClientRepository clientRepository, ClientAccountService clientAccountService, CurrencyConversionRateService rateService) {
        this.clientRepository = clientRepository;
        this.clientAccountService = clientAccountService;
        this.currencyConversionRateService = rateService;
    }

    /**
     *
     * @param clientID
     * @return Client
     *
     */
    public ClientDTO retrieveClient(Integer clientID) {
        Optional<Client> client = Optional.ofNullable(clientRepository.findById(clientID).orElseThrow(() -> new NoRecordFoundException(String.format("No record found client ID %d", clientID))));
        return client.get().toClientDto();
    }

    public ClientDTO getSortedTransactionalAccounts(Integer clientID){
        ClientDTO clientDTO = retrieveClient(clientID);
        // get all ZAR Accounts
        List<AccountDTO> currencyDTOs = clientAccountService.retrieveAllClientAccounts(clientID).stream()
                .filter(account -> account.getCurrency().getCurrencyCode().equalsIgnoreCase("ZAR")).collect(Collectors.toList());
        Comparator<AccountDTO> accountBalanceComparator = Comparator.comparing(AccountDTO::getDisplayBalance);
        currencyDTOs.sort(accountBalanceComparator.reversed());
        clientDTO.setAccounts(currencyDTOs);
        return clientDTO;
    }

    public ClientDTO getSortedCurrencyAccounts(Integer clientID){
        ClientDTO clientDTO = retrieveClient(clientID);

        // get all ZAR Accounts
        List<AccountDTO> accountDTOs = clientAccountService.retrieveAllClientAccounts(clientID).stream()
                .filter(account -> !account.getCurrency().getCurrencyCode().equalsIgnoreCase("ZAR")).collect(Collectors.toList());
        Comparator<AccountDTO> accountBalanceComparator = Comparator.comparing(AccountDTO::getZarDisplayBalance);

        accountDTOs.stream().forEach(accountDTO ->
                accountDTO.getCurrency().setConversionRateDTO(currencyConversionRateService.getCurrencyConversionRate(accountDTO.getCurrency().getCurrencyCode()).toConversionRateDTO()));

        // convert balance to ZAR
        accountDTOs.stream().forEach(accountDTO -> {
            SpelExpressionParser parser =  new SpelExpressionParser();
            Expression expression = parser.parseExpression(accountDTO.getDisplayBalance() + " " +  accountDTO.getCurrency().getConversionRateDTO().getConversionIndicator() + " " + accountDTO.getCurrency().getConversionRateDTO().getRate());
            BigDecimal convertedBal = BigDecimal.valueOf((Double) expression.getValue());
            accountDTO.setZarDisplayBalance(convertedBal);
        });
        accountDTOs.sort(accountBalanceComparator.reversed());
        clientDTO.setAccounts(accountDTOs);
        return clientDTO;
    }
}
