package za.co.discovery.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.discovery.dao.AtmAllocationRepository;
import za.co.discovery.entities.AtmAllocation;
import za.co.discovery.error.handling.NoRecordFoundException;
import za.co.discovery.dto.AtmAllocationDTO;
import za.co.discovery.dto.DenominationDTO;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class AtmAllocationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AtmAllocationService.class);
    @Autowired
    private AtmAllocationRepository atmAllocationRepository;
    @Autowired
    private AtmService atmService;
    @Autowired
    private DenominationService denominationService;

    public AtmAllocationDTO findByAtmID(Integer atmID){
        List<AtmAllocation> atmAllocation =  atmAllocationRepository.findByAtmID(atmID);
        if(atmAllocation == null || atmAllocation.isEmpty()){
            throw new NoRecordFoundException("Atm not found");
        }
        AtmAllocationDTO dto = atmAllocation.get(0).toAtmAllocationDTO();
        dto.setAtm(atmService.findAtmByID(atmID));
        atmAllocation.stream().forEach(allocation -> {
            DenominationDTO denominationDTO = denominationService.getAtmDenominationByID(allocation.getDenominationID());
            denominationDTO.setAtmDenominationCount(allocation.getCount());
            denominationDTO.setTotal(denominationDTO.getValue().multiply(BigDecimal.valueOf(denominationDTO.getAtmDenominationCount())));
            dto.getDenominations().add(denominationDTO);
        });
        dto.setAtmTotal(dto.getDenominations().stream().map(DenominationDTO::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add));
        return dto;
    }
    

    public Map<Integer, List<DenominationDTO>> getNotesToBeUsed(AtmAllocationDTO atmAllocationDTO, BigDecimal withdrawalAmount){
        List<DenominationDTO> notes = atmAllocationDTO.getDenominations().stream().filter(denominationDTO -> denominationDTO.getDenominationTypeCode().equalsIgnoreCase("N")).collect(Collectors.toList());
        //notes total
        BigDecimal totalNotes = notes.stream().map(DenominationDTO::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
        notes.sort(Comparator.comparing(DenominationDTO::getValue).reversed());
        BigDecimal amountNeeded = withdrawalAmount;

        // determine notes used
        Map<Integer, List<DenominationDTO>> usedNotes = new HashMap<>();
        for(DenominationDTO note: notes ){

            List<DenominationDTO> dtoList = new ArrayList<>();
            if(amountNeeded.subtract(note.getTotal()).compareTo(BigDecimal.ZERO) > 0){
                amountNeeded = amountNeeded.subtract(note.getTotal());
                IntStream.range(0,note.getAtmDenominationCount()).forEach(count -> dtoList.add(note));
            } else {
                int notesNeeded = amountNeeded.divide(note.getValue()).intValue();
                amountNeeded = amountNeeded.subtract(note.getValue().multiply(BigDecimal.valueOf(notesNeeded)));
                IntStream.range(0,notesNeeded).forEach(count -> dtoList.add(note));
            }

            if(usedNotes.get(note.getDenominationID()) == null){
                if(!dtoList.isEmpty()){
                    usedNotes.put(note.getDenominationID(), dtoList);
                }
            } else {
                usedNotes.get(note.getDenominationID()).add(note);
            }

            if(amountNeeded.compareTo(BigDecimal.ZERO) == 0){
                break;
            }
        }
        return usedNotes;
    }
}
