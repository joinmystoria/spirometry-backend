package com.example.spirometrybackend.repository;

import com.example.spirometrybackend.model.ConsultationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultationRepository extends JpaRepository<ConsultationEntity, Long> {

    List<ConsultationEntity> findByUserId(Long userId);

    List<ConsultationEntity> findByDoctorId(Long doctorId);
}
