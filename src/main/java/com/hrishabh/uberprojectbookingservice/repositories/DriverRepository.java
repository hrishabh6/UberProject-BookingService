package com.hrishabh.uberprojectbookingservice.repositories;

import com.example.uberprojectentityservice.models.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver, Long> {
}
