package za.co.discovery.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import za.co.discovery.model.CurrencyDTO;
import za.co.discovery.service.CurrencyService;

import java.util.List;

//@RestController
//@RequestMapping("/internal/api")
public class CurrencyResource {

    @Autowired
    private CurrencyService currencyService;

   // @GetMapping("/currencies")
    public List<CurrencyDTO> loadAllCurrencies(){
        return currencyService.getAllCurrencies();
    }

    //@GetMapping("/currency")
    public CurrencyDTO getCurrency(String currencyCode){
        return currencyService.getCurrency(currencyCode).toCurrencyDTO();
    }
}
