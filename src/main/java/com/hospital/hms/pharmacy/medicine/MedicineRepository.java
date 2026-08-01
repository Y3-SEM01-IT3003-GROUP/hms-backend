package com.hospital.hms.pharmacy.medicine;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MedicineRepository extends JpaRepository<Medicine, Integer> {

    Optional<Medicine> findByMedicineId(String medicineId);

    boolean existsByMedicineId(String medicineId);

    List<Medicine> findByExpBefore(Date date);

    void deleteByExpBefore(Date date);

}
