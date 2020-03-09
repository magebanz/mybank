package za.co.discovery.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import za.co.discovery.entities.Client;

@Repository
public interface ClientRepository extends CrudRepository<Client, Integer> {
}
