package za.co.discovery.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import za.co.discovery.entities.Atm;

@Repository
public interface AtmRepository extends CrudRepository<Atm, Integer> {
}
