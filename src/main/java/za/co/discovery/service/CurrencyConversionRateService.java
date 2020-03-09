package za.co.discovery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.discovery.dao.CurrencyConversionRateRepository;
import za.co.discovery.entities.CurrencyConversionRate;
import za.co.discovery.error.handling.NoRecordFoundException;

import java.util.Optional;

@Service
public class CurrencyConversionRateService {

    @Autowired
    private CurrencyConversionRateRepository conversionRateRepository;

    public CurrencyConversionRate getCurrencyConversionRate(String currencyCode){
        return conversionRateRepository.findById(currencyCode).orElseThrow(()-> new NoRecordFoundException("Conversion rate not found"));
    }
}
