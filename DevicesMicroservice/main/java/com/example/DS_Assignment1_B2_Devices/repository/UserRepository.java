package com.example.DS_Assignment1_B2_Devices.repository;

import com.example.DS_Assignment1_B2_Devices.model.Userr;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<Userr,Integer> {
}
