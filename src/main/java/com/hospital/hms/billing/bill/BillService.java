package com.hospital.hms.billing.bill;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BillService {

    private final BillRepository billRepository;

    public Bill create(Bill bill) {
        if (bill.getTotalAmount() == null) {
            throw new IllegalArgumentException("totalAmount is required");
        }
        if (bill.getType() == null) {
            throw new IllegalArgumentException("type is required (APPOINTMENT or PHARMACY)");
        }
        bill.setId(null);
        bill.setStatus(BillStatus.PENDING);
        bill.setPaidAt(null);
        return billRepository.save(bill);
    }

    @Transactional(readOnly = true)
    public Bill getById(Integer id) {
        return billRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Bill with id " + id + " not found"));
    }

    @Transactional(readOnly = true)
    public List<Bill> getAll() {
        return billRepository.findAll();
    }

    /** Updates payment status (e.g. PENDING → PAID). */
    public Bill update(Integer id, Bill bill) {
        Bill existing = getById(id);

        if (existing.getStatus() == BillStatus.VOIDED) {
            throw new IllegalStateException("Cannot update a voided bill");
        }

        BillStatus newStatus = bill.getStatus();
        if (newStatus == null) {
            throw new IllegalArgumentException("status is required");
        }
        if (newStatus == BillStatus.VOIDED) {
            throw new IllegalArgumentException("Use DELETE to void a bill");
        }

        existing.setStatus(newStatus);
        if (newStatus == BillStatus.PAID) {
            existing.setPaidAt(LocalDateTime.now());
        } else if (newStatus == BillStatus.PENDING) {
            existing.setPaidAt(null);
        }

        return billRepository.save(existing);
    }

    /** Voids the invoice by setting status to VOIDED (soft cancel). */
    public Bill delete(Integer id) {
        Bill existing = getById(id);

        if (existing.getStatus() == BillStatus.VOIDED) {
            throw new IllegalStateException("Bill is already voided");
        }
        if (existing.getStatus() == BillStatus.PAID) {
            throw new IllegalStateException("Cannot void a paid bill");
        }

        existing.setStatus(BillStatus.VOIDED);
        return billRepository.save(existing);
    }
}
