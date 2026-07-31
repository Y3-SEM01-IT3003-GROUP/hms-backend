package com.hospital.hms.pharmacy.pharmacist;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PharmacistRepository extends JpaRepository<Pharmacist, Integer> {

    Optional<Pharmacist> findByUserId(Integer userId);

    Optional<Pharmacist> findByLicenseNumber(String licenseNumber);

    boolean existsByLicenseNumber(String licenseNumber);
}