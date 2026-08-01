package com.hospital.hms.billing.bill;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Integer> {

    List<Bill> findByPatientId(Integer patientId);

    List<Bill> findByStatus(BillStatus status);

    List<Bill> findByType(BillType type);
}
