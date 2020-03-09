package za.co.discovery.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import za.co.discovery.entities.ClientAccount;
import za.co.discovery.model.AccountDTO;
import za.co.discovery.service.ClientAccountService;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/internal/api")
public class ClientAccountResource {

    @Autowired
    private ClientAccountService clientAccountService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientAccountService.class);

    @GetMapping("/retrieve/client/account")
    public ClientAccount retrieveClientAccount(String clientAccountNumber){
        return clientAccountService.retrieveClientAccounts(clientAccountNumber);
    }

    @GetMapping("/retrieve/all/client/account")
    public List<AccountDTO> retrieveAllClientAccount(Integer clientID){
        try {
            return clientAccountService.retrieveAllClientAccounts(clientID);
//            Comparator<ClientAccount> accountBalanceComparator = Comparator.comparing(ClientAccount::getDisplayBalance);
//            clientAccountList.sort(accountBalanceComparator.reversed());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
