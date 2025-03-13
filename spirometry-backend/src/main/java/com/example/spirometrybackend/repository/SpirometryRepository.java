package com.example.spirometrybackend.repository;

import com.example.spirometrybackend.model.SpirometryTestEntity;
import com.example.spirometrybackend.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpirometryRepository extends JpaRepository<SpirometryTestEntity, Long> {

    List<SpirometryTestEntity> findByUser(UserEntity userEntity);
}