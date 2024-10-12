package com.example.DS_Assignment1_B2_Devices.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@Builder
@NoArgsConstructor
public class Device {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int ID;
    @Column
    private String description;
    @Column
    private String address;
    @Column
    private double maxHourlyEnergyConsumption;
    @ManyToOne
    @JoinColumn(referencedColumnName = "ID")
    private Userr userId;

    public Device(int ID, String description,String address, double maxHourlyEnergyConsumption, Userr userId) {
        this.ID = ID;
        this.description = description;
        this.address = address;
        this.maxHourlyEnergyConsumption = maxHourlyEnergyConsumption;
        this.userId = userId;
    }

    public Device(String description,String address, double maxHourlyEnergyConsumption, Userr userid) {
        this.description = description;
        this.address = address;
        this.maxHourlyEnergyConsumption = maxHourlyEnergyConsumption;
        this.userId = userid;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getMaxHourlyEnergyConsumption() {
        return maxHourlyEnergyConsumption;
    }

    public void setMaxHourlyEnergyConsumption(double maxHourlyEnergyConsumption) {
        this.maxHourlyEnergyConsumption = maxHourlyEnergyConsumption;
    }

    public Userr getUser_id() {
        return userId;
    }

    public void setUser_id(Userr user_id) {
        this.userId = user_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
