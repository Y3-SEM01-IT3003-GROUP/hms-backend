package com.hospital.hms.billing.bill;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bills")
@RequiredArgsConstructor
public class BillController {

    private final BillService billService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Bill create(@RequestBody Bill bill) {
        return billService.create(bill);
    }

    @GetMapping("/{id}")
    public Bill getById(@PathVariable Integer id) {
        return billService.getById(id);
    }

    @GetMapping
    public List<Bill> getAll() {
        return billService.getAll();
    }

    @PutMapping("/{id}")
    public Bill update(@PathVariable Integer id, @RequestBody Bill bill) {
        return billService.update(id, bill);
    }

    @DeleteMapping("/{id}")
    public Bill delete(@PathVariable Integer id) {
        return billService.delete(id);
    }
}
