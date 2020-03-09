package za.co.discovery.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import za.co.discovery.service.ClientAccountService;
import za.co.discovery.service.ClientService;
import za.co.discovery.service.CurrencyConversionRateService;
import za.co.discovery.service.CurrencyService;

@RestController
@RequestMapping("/internal/api")
public class GetConvertedClientAccountsResource {
    @Autowired
    private ClientService clientService;
    @Autowired
    private ClientAccountService clientAccountService;
    @Autowired
    private CurrencyService currencyService;
    @Autowired
    private CurrencyConversionRateService currencyConversionRateService;

    public void getConvertedClientAccouts(Integer clientID) throws Exception {
//        Response response = clientService.retrieveClient(clientID);

//        Listt<ClientAccount> clientAccount = clientAccountService.retrieveAllClientAccounts(clientID);

        
    }
}
