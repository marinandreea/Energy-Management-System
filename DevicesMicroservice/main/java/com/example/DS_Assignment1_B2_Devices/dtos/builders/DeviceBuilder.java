package com.example.DS_Assignment1_B2_Devices.dtos.builders;

import com.example.DS_Assignment1_B2_Devices.dtos.DevDTO;
import com.example.DS_Assignment1_B2_Devices.dtos.DeviceDTO;
import com.example.DS_Assignment1_B2_Devices.dtos.DeviceDetailsDTO;
import com.example.DS_Assignment1_B2_Devices.model.Device;

public class DeviceBuilder {

    public DeviceBuilder(){}

    public static DeviceDTO toDeviceDTO(Device device){
        return new DeviceDTO(device.getID(), device.getDescription(), device.getAddress(),
                device.getMaxHourlyEnergyConsumption(), UserBuilder.toUserDTO(device.getUser_id()));
    }

    public static DeviceDetailsDTO toDeviceDetailsDTO(Device device){
        return new DeviceDetailsDTO(device.getDescription(), device.getAddress(), device.getMaxHourlyEnergyConsumption());
    }

    public static Device toEntity(DeviceDTO deviceDTO){
        return new Device(deviceDTO.getDescription(),
                deviceDTO.getAddress(),
                deviceDTO.getMaxHourlyEnergyConsumption(),
                UserBuilder.toEntity(deviceDTO.getUser_id()));
    }

    public static DevDTO toDevDTO(DeviceDTO deviceDTO){
        return new DevDTO(deviceDTO.getID(), deviceDTO.getDescription(), deviceDTO.getAddress(),
                deviceDTO.getMaxHourlyEnergyConsumption(), deviceDTO.getUser_id().getID());
    }
}
