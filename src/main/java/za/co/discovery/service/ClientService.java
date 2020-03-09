package za.co.discovery.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.discovery.dao.ClientRepository;
import za.co.discovery.error.handling.NoRecordFoundException;
import za.co.discovery.entities.Client;
import za.co.discovery.dto.AccountDTO;
import za.co.discovery.dto.ClientDTO;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientAccountService clientAccountService;
    private final CurrencyConversionRateService currencyConversionRateService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientService.class);

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
        LOGGER.info(String.format("Retrieving client with ID %d", clientID));
        Optional<Client> client = Optional.ofNullable(clientRepository.findById(clientID).orElseThrow(() -> new NoRecordFoundException(String.format("No record found client ID %d", clientID))));
        return client.get().toClientDto();
    }

    public ClientDTO getSortedTransactionalAccounts(Integer clientID){
        LOGGER.info(String.format("Retrieving transactional accounts for client ID: %d", clientID));
        ClientDTO clientDTO = retrieveClient(clientID);
        // get all ZAR Accounts
        List<AccountDTO> currencyAccountDTOs = clientAccountService.retrieveAllClientAccounts(clientID);
        currencyAccountDTOs = currencyAccountDTOs.stream()
                .filter(account -> account.getAccountTypeDTO().getTransactional()).collect(Collectors.toList());
        
        Comparator<AccountDTO> accountBalanceComparator = Comparator.comparing(AccountDTO::getZarDisplayBalance);
        currencyAccountDTOs.sort(accountBalanceComparator.reversed());
        clientDTO.setAccounts(currencyAccountDTOs);
        LOGGER.info(String.format("Client with client ID: %d has %d transactional accounts", clientID, currencyAccountDTOs.size()));
        return clientDTO;
    }

    public ClientDTO getAllClientsAccounts(Integer clientID){
        LOGGER.info(String.format("Retrieving all accounts for client ID: %d", clientID));
        ClientDTO clientDTO = retrieveClient(clientID);
        // filter transactional accounts
        List<AccountDTO> accountDTOs = clientAccountService.retrieveAllClientAccounts(clientID);
        Comparator<AccountDTO> accountBalanceComparator = Comparator.comparing(AccountDTO::getZarDisplayBalance);

        accountDTOs.sort(accountBalanceComparator.reversed());
        clientDTO.setAccounts(accountDTOs);
        return clientDTO;
    }

    public ClientDTO getSortedCurrencyAccounts(Integer clientID){
        LOGGER.info(String.format("Retrieving currency accounts for client ID: %d", clientID));
        ClientDTO clientDTO = retrieveClient(clientID);

        // get all client Accounts
        List<AccountDTO> accountDTOs = clientAccountService.retrieveAllClientAccounts(clientID);
        // filter ZAR and HLOAN & PLOAN Accounts
        accountDTOs = accountDTOs.stream().filter(account -> account.getAccountTypeDTO().getAccountTypeCode().equalsIgnoreCase("CFCA")).collect(Collectors.toList());
        // Comparator for sorting accounts on zarDisplayBalance
        Comparator<AccountDTO> accountBalanceComparator = Comparator.comparing(AccountDTO::getZarDisplayBalance);
        
        accountDTOs.sort(accountBalanceComparator.reversed());
        clientDTO.setAccounts(accountDTOs);
        LOGGER.info(String.format("Client with client ID: %d has %d currency accounts", clientID, accountDTOs.size()));
        return clientDTO;
    }
}
