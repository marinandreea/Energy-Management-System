package com.example.DS_Assignment1_B2_Devices.repository;

import com.example.DS_Assignment1_B2_Devices.model.Device;
import com.example.DS_Assignment1_B2_Devices.model.Userr;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends CrudRepository<Device,Integer> {

    List<Device> findAllByUserId(Userr user);
    List<Device> deleteAllByUserId(Userr user);
}
