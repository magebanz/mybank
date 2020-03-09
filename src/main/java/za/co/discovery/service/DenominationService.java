package za.co.discovery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.discovery.dao.DenominationRepository;
import za.co.discovery.entities.Denomination;
import za.co.discovery.error.handling.NoRecordFoundException;
import za.co.discovery.model.DenominationDTO;

@Service
public class DenominationService {
    @Autowired
    private DenominationRepository denominationRepository;

    public DenominationDTO getAtmDenominationByID(Integer atmDenomination){
        Denomination denomination = denominationRepository.findById(atmDenomination).orElseThrow(() -> new NoRecordFoundException("No denomination for ATM"));
        return denomination.toDTO();
    }
}
