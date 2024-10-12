package com.example.DS_Assignment1_B2_Devices.dtos;

public class DeviceDetailsDTO {

    private String description;
    private String address;
    private double maxHourlyEnergyConsumption;

    public DeviceDetailsDTO(){}

    public DeviceDetailsDTO(String description, String address, double maxHourlyEnergyConsumption) {
        this.description = description;
        this.address = address;
        this.maxHourlyEnergyConsumption = maxHourlyEnergyConsumption;
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
}
