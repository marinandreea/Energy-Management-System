package com.example.DS_Assignment1_B1_User.repository;

import com.example.DS_Assignment1_B1_User.model.Userr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<Userr,Integer> {

    Optional<Userr> findByUsername(String username);

}
