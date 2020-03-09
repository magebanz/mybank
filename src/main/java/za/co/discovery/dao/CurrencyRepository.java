package za.co.discovery.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import za.co.discovery.entities.Currency;

@Repository
public interface CurrencyRepository extends CrudRepository<Currency, String> {
}
