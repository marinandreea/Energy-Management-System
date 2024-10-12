package com.example.DS_Assignment1_B2_Devices.service;

import com.example.DS_Assignment1_B2_Devices.dtos.DeviceDTO;
import com.example.DS_Assignment1_B2_Devices.dtos.DeviceDetailsDTO;
import com.example.DS_Assignment1_B2_Devices.dtos.builders.DeviceBuilder;
import com.example.DS_Assignment1_B2_Devices.model.Device;
import com.example.DS_Assignment1_B2_Devices.model.Userr;
import com.example.DS_Assignment1_B2_Devices.repository.DeviceRepository;
import com.example.DS_Assignment1_B2_Devices.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private UserRepository userRepository;

    public List<DeviceDTO> getAllDevices(){
        List<Device> devices = new ArrayList<>();
        deviceRepository.findAll().forEach(u-> devices.add(u));
        return devices.stream().map(DeviceBuilder::toDeviceDTO)
                .collect(Collectors.toList());
    }

    public Optional<DeviceDTO> getDeviceById(int id){
        Optional<Device> device = deviceRepository.findById(id);
        if(!device.isPresent()){
            return Optional.empty();
        }
        return Optional.of(DeviceBuilder.toDeviceDTO(device.get()));
    }

    public Optional<Device> addDevice(DeviceDTO deviceDTO) {
        Optional<Userr> user = userRepository.findById(deviceDTO.getUser_id().getID());
        if (!user.isPresent()) {
            return Optional.empty();
        }

        Device device = DeviceBuilder.toEntity(deviceDTO);
        device = deviceRepository.save(device);
        if (device == null) {
            // Handle the case where device addition failed
            return Optional.empty();
        }

        return Optional.of(device);
    }


    public Optional<Device> updateDevice(int id, DeviceDetailsDTO deviceDetailsDTO){
        Optional<Device> device = deviceRepository.findById(id);
        if(device.isPresent()){
            device.get().setDescription(deviceDetailsDTO.getDescription());
            device.get().setAddress(deviceDetailsDTO.getAddress());
            device.get().setMaxHourlyEnergyConsumption(deviceDetailsDTO.getMaxHourlyEnergyConsumption());

            return Optional.of(deviceRepository.save(device.get()));
        }
        return Optional.empty();
    }

    public Optional<Device> deleteDevice(int id){
        Optional<Device> device = deviceRepository.findById(id);
        if(device.isPresent()){
            deviceRepository.deleteById(id);
            return Optional.of(device.get());
        }
        return Optional.empty();
    }

    public Optional<List<DeviceDTO>> getDevicesByUserId(int id){
        Optional<Userr> user = userRepository.findById(id);
        if(user.isPresent()){
            List<Device> devices = deviceRepository.findAllByUserId(user.get());
            if(devices.isEmpty()){
                return Optional.empty();
            }
            return Optional.of(devices.stream().map(DeviceBuilder::toDeviceDTO)
                    .collect(Collectors.toList()));
        }
        return Optional.empty();
    }

    @Transactional
    public Optional<List<Device>> deleteDevicesByUserId(int id){
        Optional<Userr> user = userRepository.findById(id);
        if(user.isPresent()){
            List<Device> devices = deviceRepository.deleteAllByUserId(user.get());
            if(devices.isEmpty()){
                return Optional.empty();
            }
            return Optional.of(devices);
        }
        return Optional.empty();
    }

}
