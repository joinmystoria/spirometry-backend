package com.example.spirometrybackend.repository;

import com.example.spirometrybackend.model.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepository extends JpaRepository<DoctorEntity, Long> {

    List<DoctorEntity> findByAvailableStatusTrue(); // Get only available doctors
}