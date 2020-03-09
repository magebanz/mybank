package za.co.discovery.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import za.co.discovery.entities.AtmAllocation;
import za.co.discovery.error.handling.NoRecordFoundException;
import za.co.discovery.service.AtmAllocationService;

import java.util.List;

@Repository
public interface AtmAllocationRepository extends CrudRepository<AtmAllocation, Integer> {
    String FIND_BY_ATM_ID = "select p from atm_allocation p where p.atmID= :atmID";

    @Query(FIND_BY_ATM_ID)
    List<AtmAllocation> findByAtmID(@Param("atmID") Integer atmID) throws NoRecordFoundException;
}
