package com.hospital.hms.pharmacy.medicine;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MedicineService {

    private final MedicineRepository medicineRepository;

    public Medicine create(Medicine medicine) {
        if (medicineRepository.existsByMedicineId(medicine.getMedicineId())) {
            throw new IllegalStateException (
                   "Medicine Id " + medicine.getMedicineId() + " already exsist");
        }
        medicine.setMedicineId(null);
        Medicine saved = medicineRepository.save(medicine);
        System.out.println(saved.getMedicineId());
        saved.setMedicineId("MED-" + String.format("%04d", saved.getId()));
        return medicineRepository.save(saved);
    }

    @Transactional(readOnly = true)
    public Medicine getByMedicineId(String medicineId) {
        return medicineRepository.findByMedicineId(medicineId)
                .orElseThrow(() -> new IllegalStateException("Medicine with id " + medicineId + " not found"));
    }

    @Transactional(readOnly = true)
    public List<Medicine> getAll() {
        return medicineRepository.findAll();
    }

    public Medicine update(String medicineId, Medicine medicine) {
        Medicine existing = getByMedicineId(medicineId);
        existing.setName(medicine.getName());
        existing.setQuantity(medicine.getQuantity());
        existing.setMfd(medicine.getMfd());
        existing.setExp(medicine.getExp());
        return medicineRepository.save(existing);
    }

    public void delete(String medicineId) {
        Medicine existing = getByMedicineId(medicineId);
        medicineRepository.delete(existing);
    }

    public List<Medicine> deleteExpiredMedicines() {
        List<Medicine> expired = medicineRepository.findByExpBefore(new Date());
        medicineRepository.deleteByExpBefore(new Date());
        return expired;
    }
}



