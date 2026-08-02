package com.hospital.hms.patient;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends  JpaRepository<Patient, Integer> {

    Optional<Patient> findByUserId(Integer userId);
}
