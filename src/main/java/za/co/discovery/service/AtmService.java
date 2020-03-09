package za.co.discovery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.discovery.dao.AtmRepository;
import za.co.discovery.entities.Atm;
import za.co.discovery.error.handling.NoRecordFoundException;
import za.co.discovery.dto.AtmDTO;

@Service
public class AtmService {

    @Autowired
    private AtmRepository atmRepository;

    public AtmDTO findAtmByID(int atmID){
        Atm atm =  atmRepository.findById(atmID).orElseThrow(() -> new NoRecordFoundException("Atm ID not registered"));
        return atm.toAtmDTO();
    }
}
