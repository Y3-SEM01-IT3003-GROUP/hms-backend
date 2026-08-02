package com.hospital.hms.Prescription;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PrescriptionRepository extends JpaRepository<Prescription, Integer> {
    List<Prescription> findByPatientNameContainingIgnoreCase(String patientName);
}