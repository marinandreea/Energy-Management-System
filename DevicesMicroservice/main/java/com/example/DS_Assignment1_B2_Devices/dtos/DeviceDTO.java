package com.example.DS_Assignment1_B2_Devices.dtos;

import com.example.DS_Assignment1_B2_Devices.model.Userr;

public class DeviceDTO {

    private int ID;
    private String description;
    private String address;
    private double maxHourlyEnergyConsumption;
    private UserDTO user_id;

    public DeviceDTO(){}

    public DeviceDTO(int ID,String description, String address, double maxHourlyEnergyConsumption, UserDTO user_id) {
        this.ID = ID;
        this.description = description;
        this.address = address;
        this.maxHourlyEnergyConsumption = maxHourlyEnergyConsumption;
        this.user_id = user_id;
    }

    public DeviceDTO(int ID,String description, String address, double maxHourlyEnergyConsumption) {
        this.ID = ID;
        this.description = description;
        this.address = address;
        this.maxHourlyEnergyConsumption = maxHourlyEnergyConsumption;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getMaxHourlyEnergyConsumption() {
        return maxHourlyEnergyConsumption;
    }

    public void setMaxHourlyEnergyConsumption(double maxHourlyEnergyConsumption) {
        this.maxHourlyEnergyConsumption = maxHourlyEnergyConsumption;
    }

    public UserDTO getUser_id() {
        return user_id;
    }

    public void setUser_id(UserDTO user_id) {
        this.user_id = user_id;
    }
}
