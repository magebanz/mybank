package za.co.discovery.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import za.co.discovery.model.*;
import za.co.discovery.service.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@RestController
@RequestMapping("/mybank")
public class MyBankResource {

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

        ClientDTO client = getSortedTransactionalAccounts(request.getClientID());

        AccountDTO account = client.getAccounts().stream().filter(accountDTO -> accountDTO.getClientAccountNumber().equalsIgnoreCase(request.getAccountNumber())).collect(Collectors.toList()).get(0);
        // find ATM
        AtmAllocationDTO atm = allocationService.findByAtmID(request.getAtmID()) ;
        // check if ATM has funds
        if(atm.getAtmTotal().compareTo(request.getAmount()) < 0){
            throw new Exception(String.format("Amount not available, would you like to withdraw %s",atm.getAtmTotal().toString()));
        }
        // check if account balance is enough for withdrawal
        if(account.getDisplayBalance().compareTo(request.getAmount()) < 0){
            throw new Exception("Insufficient funds.");
        }

        // check if request can be fulfilled using notes only
        Map<Integer, List<DenominationDTO>> usedNotes = allocationService.getNotesToBeUsed(atm, request.getAmount());
        // prints out notes used for withdrawal
        usedNotes.forEach((key,value) -> System.out.println(String.format("Used %d x R %s ", value.size(), value.get(0).getValue().toString())));
        // withdraw fund;
        System.out.println(String.format("Account balance before withdrawal is %s for account number %s", account.getDisplayBalance().toString(), account.getClientAccountNumber()));
        account  = clientAccountService.withdrawAmount(account,request.getAmount());
        System.out.println(String.format("Account balance before withdrawal is %s for account number %s", account.getDisplayBalance().toString(), account.getClientAccountNumber()));

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