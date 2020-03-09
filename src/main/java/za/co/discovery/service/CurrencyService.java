package za.co.discovery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import za.co.discovery.dao.CurrencyRepository;
import za.co.discovery.entities.Currency;
import za.co.discovery.error.handling.NoRecordFoundException;
import za.co.discovery.dto.CurrencyDTO;

import java.util.ArrayList;
import java.util.List;

@Service
public class CurrencyService {

    private final CurrencyRepository currencyRepository;

    @Autowired
    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public Currency getCurrency(String currencyCode) {
       return currencyRepository.findById(currencyCode).orElseThrow(() -> new NoRecordFoundException("No currency available"));
    }

    @Cacheable(value = "currency", key = "#root.methodName")
    public List<CurrencyDTO> getAllCurrencies(){
        Iterable<Currency> currencies = currencyRepository.findAll();
        List<CurrencyDTO> currencyDTOList = new ArrayList<>();
        currencies.forEach(currency -> currencyDTOList.add(currency.toCurrencyDTO()));
        return currencyDTOList;
    }

}
