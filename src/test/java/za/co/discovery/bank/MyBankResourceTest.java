package za.co.discovery.bank;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import za.co.discovery.dto.AccountDTO;
import za.co.discovery.dto.ClientDTO;
import za.co.discovery.dto.DenominationDTO;
import za.co.discovery.dto.WithdrawalRequest;
import za.co.discovery.rest.MyBankResource;
import za.co.discovery.service.ClientService;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MyBankResourceTest {

    @Autowired
    private ClientService clientService;
    @Autowired
    private MyBankResource resource;

    @Test
    public void getCurrencyAccounts(){
        ClientDTO clientDTO =  resource.getSortedCurrencyAccounts(1);
        assert(clientDTO != null);
        List<AccountDTO> accounts = clientDTO.getAccounts();
        assert(accounts.size() > 0);
    }

    @Test
    public void getTransactionalAccount(){
            
           ClientDTO clientDTO = resource.getSortedCurrencyAccounts(1);
           assert(clientDTO != null);
           List<AccountDTO> accounts = clientDTO.getAccounts();
           assert(accounts.size() > 0);
    }

    @Test
    public void withdraw() throws Exception {
        WithdrawalRequest request = new WithdrawalRequest();
        request.setAmount(BigDecimal.valueOf(300));
        request.setAtmID(1);
        request.setClientID(1);
        request.setAccountNumber("4067342946");

        List<DenominationDTO> notesToBeUsed = resource.withdrawFunds(request);
        assert(notesToBeUsed.size() > 0);

    }
}
