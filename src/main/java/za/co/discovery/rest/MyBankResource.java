package za.co.discovery.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import za.co.discovery.dto.*;
import za.co.discovery.service.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/mybank")
public class MyBankResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyBankResource.class);

    @Autowired
    private ClientService clientService;
    @Autowired
    private ClientAccountService clientAccountService;
    @Autowired
    private CurrencyService currencyService;
    @Autowired
    private CurrencyConversionRateService conversionRateService;
    @Autowired
    private AtmAllocationService allocationService;

    @GetMapping("/client/transactional")
    public ClientDTO getSortedTransactionalAccounts(Integer clientID){
       return clientService.getSortedTransactionalAccounts(clientID);
    }

    @GetMapping("/client/currency/accounts")
    public ClientDTO getSortedCurrencyAccounts(Integer clientID){
        return clientService.getSortedCurrencyAccounts(clientID);
    }

    @PostMapping("/withdraw")
    public List<DenominationDTO> withdrawFunds(WithdrawalRequest request) throws Exception {

        ClientDTO client = clientService.getAllClientsAccounts(request.getClientID());

        AccountDTO account = client.getAccounts().stream().filter(accountDTO -> accountDTO.getClientAccountNumber().equalsIgnoreCase(request.getAccountNumber())).collect(Collectors.toList()).get(0);
        // find ATM
        AtmAllocationDTO atm = allocationService.findByAtmID(request.getAtmID()) ;
        // check if ATM has funds
        if(atm.getAtmTotal().compareTo(request.getAmount()) < 0){
            throw new Exception(String.format("Amount not available, would you like to withdraw %s",atm.getAtmTotal().toString()));
        }
        // check if account balance is enough for withdrawal
        if(account.getZarDisplayBalance().compareTo(request.getAmount()) < 0){
            throw new Exception("Insufficient funds.");
        }
        if(account.getAccountLimitDTO() != null && account.getAccountLimitDTO().getAccountLimit().compareTo(request.getAmount()) < 0) {
            throw new Exception("Account limit exceeded");
        }

        // get notes to be used to fulfill withdrawal request
        Map<Integer, List<DenominationDTO>> usedNotes = allocationService.getNotesToBeUsed(atm, request.getAmount());
        // prints out notes used for withdrawal
        usedNotes.forEach((key,value) -> LOGGER.info(String.format("Used %d x R %s ", value.size(), value.get(0).getValue().toString())));
        // withdraw fund;
        LOGGER.info(String.format("Account balance before withdrawal is %s for account number %s", account.getDisplayBalance().toString(), account.getClientAccountNumber()));
        account  = clientAccountService.withdrawAmount(account,request.getAmount());
        LOGGER.info(String.format("Account balance before withdrawal is %s for account number %s", account.getDisplayBalance().toString(), account.getClientAccountNumber()));

        List<DenominationDTO> responseDTOList = new ArrayList<>();
        usedNotes.forEach((key,denominationDTOList) -> {
            DenominationDTO responseDTO = denominationDTOList.get(0);
            responseDTO.setAtmDenominationCount(denominationDTOList.size());
            responseDTO.setTotal(responseDTO.getValue().multiply(BigDecimal.valueOf(denominationDTOList.size())));
            responseDTOList.add(responseDTO);
        });

        return responseDTOList;
    }
}
