package za.co.discovery.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import za.co.discovery.entities.CurrencyConversionRate;
import za.co.discovery.service.CurrencyConversionRateService;

@RestController
@RequestMapping("/internal/api")
public class CurrencyConversionRateResource {

    @Autowired
    private CurrencyConversionRateService conversionRateService;

    @GetMapping("currency/conversion")
    public CurrencyConversionRate getCurrencyConversionRate(String currencyCode){
        return conversionRateService.getCurrencyConversionRate(currencyCode);
    }
}
