package com.hospital.hms.Prescription;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/prescriptions")
@CrossOrigin(origins = "*")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @PostMapping
    public Prescription createPrescription(@RequestBody Prescription prescription) {
        return prescriptionService.createPrescription(prescription);
    }

    @GetMapping
    public List<Prescription> getPrescriptions(@RequestParam(required = false) String search) {
        return prescriptionService.getPrescriptions(search);
    }

    @PutMapping("/{id}")
    public Prescription updatePrescription(@PathVariable int id, @RequestBody Prescription details) {
        return prescriptionService.updatePrescription(id, details);
    }

    @DeleteMapping("/{id}")
    public void deletePrescription(@PathVariable int id) {
        prescriptionService.deletePrescription(id);
    }
}