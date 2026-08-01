package com.hospital.hms.doctor.doctor;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

    Optional<Doctor> findByUserId(Integer userId);

    Optional<Doctor> findByLicenseNumber(String licenseNumber);

    boolean existsByLicenseNumber(String licenseNumber);

    List<Doctor> findBySpecializationIgnoreCase(String specialization);

    List<Doctor> findByActiveTrue();

    List<Doctor> findBySpecializationIgnoreCaseAndActiveTrue(String specialization);
}
