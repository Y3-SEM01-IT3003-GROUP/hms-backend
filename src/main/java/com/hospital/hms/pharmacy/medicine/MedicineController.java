package com.hospital.hms.pharmacy.medicine;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/medicine")
@RequiredArgsConstructor
public class MedicineController {

    private final MedicineService medicineService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Medicine create(@RequestBody Medicine medicine) {
        return medicineService.create(medicine);
    }

    @GetMapping("/{medicineId}")
    public Medicine getByMedicineId(@PathVariable String medicineId) {
        return medicineService.getByMedicineId(medicineId);
    }

    @GetMapping
    public List<Medicine> getAll() {
        return medicineService.getAll();
    }

    @PutMapping("/{medicineId}")
    public Medicine update(@PathVariable String medicineId, @RequestBody Medicine medicine) {
        return medicineService.update(medicineId, medicine);
    }

    @DeleteMapping("/{medicineId}")
    public void delete(@PathVariable String medicineId) {
        medicineService.delete(medicineId);
    }

    @DeleteMapping("/expired")
    public List<Medicine> deleteExpired() {
        return medicineService.deleteExpiredMedicines();
    }

}
