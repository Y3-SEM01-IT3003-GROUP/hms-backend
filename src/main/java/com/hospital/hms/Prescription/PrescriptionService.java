package com.hospital.hms.Prescription;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    public Prescription createPrescription(Prescription prescription) {
        return prescriptionRepository.save(prescription);
    }

    public List<Prescription> getPrescriptions(String search) {
        if (search != null && !search.trim().isEmpty()) {
            return prescriptionRepository.findByPatientNameContainingIgnoreCase(search);
        }
        return prescriptionRepository.findAll();
    }

    public Prescription updatePrescription(int id, Prescription details) {
        Prescription p = prescriptionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Prescription not found with id: " + id
                ));

        if (details.getPatientName() != null) {
            p.setPatientName(details.getPatientName());
        }
        if (details.getDoctorName() != null) {
            p.setDoctorName(details.getDoctorName());
        }
        p.setMedicinesAndDosage(details.getMedicinesAndDosage());
        p.setNotes(details.getNotes());

        return prescriptionRepository.save(p);
    }

    public void deletePrescription(int id) {
        if (!prescriptionRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Prescription not found with id: " + id
            );
        }
        prescriptionRepository.deleteById(id);
    }
}