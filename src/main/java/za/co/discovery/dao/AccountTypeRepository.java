package za.co.discovery.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import za.co.discovery.entities.AccountType;

@Repository
public interface AccountTypeRepository extends CrudRepository<AccountType,String> {
}
