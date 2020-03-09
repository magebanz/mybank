package za.co.discovery.model;

import za.co.discovery.entities.Denomination;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AtmAllocationDTO {
    private int atmAllocationID;
    private AtmDTO atm;
    private List<DenominationDTO> denominations = new ArrayList<>();
    private BigDecimal atmTotal;
    private int count;

    public int getAtmAllocationID() {
        return atmAllocationID;
    }

    public void setAtmAllocationID(int atmAllocationID) {
        this.atmAllocationID = atmAllocationID;
    }

    public AtmDTO getAtm() {
        return atm;
    }

    public void setAtm(AtmDTO atm) {
        this.atm = atm;
    }

    public List<DenominationDTO> getDenominations() {
        return denominations;
    }

    public void setDenominations(List<DenominationDTO> denominations) {
        this.denominations = denominations;
    }

    public BigDecimal getAtmTotal() {
        return atmTotal;
    }

    public void setAtmTotal(BigDecimal atmTotal) {
        this.atmTotal = atmTotal;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
