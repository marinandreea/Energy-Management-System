package com.example.DS_Assignment1_B2_Devices.controller;

import com.example.DS_Assignment1_B2_Devices.controller.errorHandler.Error;
import com.example.DS_Assignment1_B2_Devices.dtos.DevDTO;
import com.example.DS_Assignment1_B2_Devices.dtos.DeviceDTO;
import com.example.DS_Assignment1_B2_Devices.dtos.DeviceDetailsDTO;
import com.example.DS_Assignment1_B2_Devices.dtos.builders.DeviceBuilder;
import com.example.DS_Assignment1_B2_Devices.model.Device;
import com.example.DS_Assignment1_B2_Devices.service.DeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RestController
@EnableMethodSecurity
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${rabbitmq.exchange.nameD}")
    private String exchange;
    @Value("${rabbitmq.key.nameD}")
    private String key;

    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceController.class);


    private RabbitTemplate rabbitTemplate;

    public DeviceController(RabbitTemplate rabbitTemplate){this.rabbitTemplate = rabbitTemplate;}

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/device")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<DeviceDTO> getAllDevices(){
        return deviceService.getAllDevices();
    }

    @RequestMapping("/device/{id}")
    public ResponseEntity<?> getDevice(@PathVariable int id){
        Optional<DeviceDTO> device = deviceService.getDeviceById(id);
        if(device.isPresent()){
            return new ResponseEntity<>(device.get(), HttpStatus.OK);
        }
        Error error = new Error("Could not find device with id " + id);
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }

    @CrossOrigin(origins = "http://localhost:3000")

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(method = RequestMethod.POST,value = "/device/create")
    public ResponseEntity<?> addDevice(@RequestBody DeviceDTO deviceDTO,@RequestHeader("Authorization") String authorizationHeader){

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorizationHeader);

        HttpEntity<String> entity = new HttpEntity<>(headers);


        Optional<Device> deviceToAdd = deviceService.addDevice(deviceDTO);
        if(deviceToAdd.isPresent()){

            // Notify device service
            String deviceServiceUrl = "http://localhost:8082/addDevice";
            String deviceServiceUrlD = "http://monitoring-microservice:8082/addDevice";
            HttpEntity<DevDTO> requestEntity = new HttpEntity<>(DeviceBuilder.toDevDTO(deviceDTO), headers);
            // Corrected code
            ResponseEntity<Void> deviceServiceResponse = restTemplate.postForEntity(deviceServiceUrl, requestEntity, Void.class);
           // ResponseEntity<Void> deviceServiceResponse = restTemplate.exchange(deviceServiceUrl, HttpMethod.POST, entity, Void.class,DeviceBuilder.toDevDTO(deviceDTO));


            if (deviceServiceResponse.getStatusCode().is2xxSuccessful()) {
                return new ResponseEntity<>(deviceToAdd, HttpStatus.OK);
            }

        }
        Error error = new Error("Could not add device because the user id could not be found!");
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);

    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(method = RequestMethod.PUT,value = "/device/update/{id}")
    public ResponseEntity<?> updateDevice(@RequestBody DeviceDetailsDTO deviceDetailsDTO, @PathVariable int id){
        Optional<Device> deviceOptional = deviceService.updateDevice(id, deviceDetailsDTO);


        if(deviceOptional.isEmpty()){
            Error error = new Error("Device could not be updated because the id could not be found");
            return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
        }
        DevDTO devDTO = DeviceBuilder.toDevDTO(DeviceBuilder.toDeviceDTO(deviceOptional.get()));
        sendJsonMessage(devDTO);
        return new ResponseEntity<>(deviceOptional.get(),HttpStatus.OK);
    }

    public void sendJsonMessage(DevDTO device){
        LOGGER.info(String.format("Message sent -> %s", device.toString()));
        rabbitTemplate.convertAndSend(exchange,key, device);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(method = RequestMethod.DELETE,value="/device/delete/{id}")
    public ResponseEntity<?> deleteDevice(@PathVariable int id){

        Optional<Device> deviceToBeDeleted = deviceService.deleteDevice(id);
        if(deviceToBeDeleted.isEmpty()){
            Error error = new Error("Could not find device with id " + id +" to delete");
            return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(deviceToBeDeleted.get(),HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    //@PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping("/deviceByUser/{id}")
    public ResponseEntity<?> getDevicesByUserId(@PathVariable int id){
        Optional<List<DeviceDTO>> devices = deviceService.getDevicesByUserId(id);
        if(devices.isEmpty()){
            Error error = new Error("Could not display the list of devices because the user id could not be found" +
                    " or the user has no device associated!");
            return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(devices.get(),HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.DELETE,value = "/deleteDeviceByUser/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteDevicesByUserId(@PathVariable int id){
        Optional<List<Device>> devices = deviceService.deleteDevicesByUserId(id);
        if(devices.isEmpty()){
            Error error = new Error("Could not delete devices!");
            return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(devices,HttpStatus.OK);
    }



}
