package za.co.discovery.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import za.co.discovery.entities.Denomination;

@Repository
public interface DenominationRepository extends CrudRepository<Denomination, Integer> {
}
