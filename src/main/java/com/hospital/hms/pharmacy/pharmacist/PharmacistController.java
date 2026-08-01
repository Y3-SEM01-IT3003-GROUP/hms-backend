package com.hospital.hms.pharmacy.pharmacist;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pharmacists")
@RequiredArgsConstructor
public class PharmacistController {

    private final PharmacistService pharmacistService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pharmacist create(@RequestBody Pharmacist pharmacist) {
        return pharmacistService.create(pharmacist);
    }

    @GetMapping("/{id}")
    public Pharmacist getById(@PathVariable Integer id) {
        return pharmacistService.getById(id);
    }

    @GetMapping
    public List<Pharmacist> getAll() {
        return pharmacistService.getAll();
    }

    @PutMapping("/{id}")
    public Pharmacist update(@PathVariable Integer id, @RequestBody Pharmacist pharmacist) {
        return pharmacistService.update(id, pharmacist);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        pharmacistService.delete(id);
    }
}