package com.hospital.hms.doctor.doctor;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Doctor create(@RequestBody Doctor doctor) {
        return doctorService.create(doctor);
    }

    @GetMapping("/{id}")
    public Doctor getById(@PathVariable Integer id) {
        return doctorService.getById(id);
    }

    @GetMapping
    public List<Doctor> getAll(@RequestParam(required = false) String specialization) {
        return doctorService.getAll(specialization);
    }

    @PutMapping("/{id}")
    public Doctor update(@PathVariable Integer id, @RequestBody Doctor doctor) {
        return doctorService.update(id, doctor);
    }

    @DeleteMapping("/{id}")
    public Doctor delete(@PathVariable Integer id) {
        return doctorService.delete(id);
    }
}

