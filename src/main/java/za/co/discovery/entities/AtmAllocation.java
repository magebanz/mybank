package za.co.discovery.entities;

import za.co.discovery.dto.AtmAllocationDTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "atm_allocation")
public class AtmAllocation {

    @Id
    @Column(name = "atm_allocation_id")
    private int atmAllocationID;
    @Column(name = "atm_id")
    private int atmID;
    @Column(name = "denomination_id")
    private int denominationID;
    private int count;

    public int getAtmAllocationID() {
        return atmAllocationID;
    }

    public void setAtmAllocationID(int atmAllocationID) {
        this.atmAllocationID = atmAllocationID;
    }

    public int getAtmID() {
        return atmID;
    }

    public void setAtmID(int atmID) {
        this.atmID = atmID;
    }

    public int getDenominationID() {
        return denominationID;
    }

    public void setDenominationID(int denominationID) {
        this.denominationID = denominationID;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public AtmAllocationDTO toAtmAllocationDTO(){
        AtmAllocationDTO dto = new AtmAllocationDTO();
        dto.setAtmAllocationID(this.atmAllocationID);
        dto.setCount(this.count);
        return dto;
    }
}
