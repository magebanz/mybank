package za.co.discovery.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import za.co.discovery.entities.CreditCardLimit;

@Repository
public interface CreditCardLimitRepository extends CrudRepository<CreditCardLimit, String> {
}
