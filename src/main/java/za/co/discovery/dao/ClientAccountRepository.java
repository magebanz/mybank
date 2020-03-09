package za.co.discovery.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.co.discovery.entities.ClientAccount;
import za.co.discovery.error.handling.NoRecordFoundException;

import java.util.List;

@Repository
public interface ClientAccountRepository extends CrudRepository<ClientAccount, String> {

    String FIND_BY_CLIENT_ID = "select p from client_account p where p.clientID= :clientID";

    @Query(FIND_BY_CLIENT_ID)
    public List<ClientAccount> findAllByClientID(@Param("clientID") Integer clientID) throws NoRecordFoundException;
}
