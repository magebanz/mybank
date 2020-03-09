package za.co.discovery.entities;

import za.co.discovery.model.AtmDTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "atm")
public class Atm {

    @Id
    @Column(name = "atm_id")
    private int atmID;
    private String name;
    private String location;

    public int getAtmID() {
        return atmID;
    }

    public void setAtmID(int atmID) {
        this.atmID = atmID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public AtmDTO toAtmDTO(){
        AtmDTO dto = new AtmDTO();
        dto.setAtmID(this.atmID);
        dto.setLocation(this.location);
        dto.setName(this.name);
        return dto;
    }
}
