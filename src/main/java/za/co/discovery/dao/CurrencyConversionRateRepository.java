package za.co.discovery.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import za.co.discovery.entities.CurrencyConversionRate;

@Repository
public interface CurrencyConversionRateRepository extends CrudRepository<CurrencyConversionRate, String> {
}
