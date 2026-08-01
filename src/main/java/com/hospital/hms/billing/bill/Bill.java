package com.hospital.hms.billing.bill;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table(name = "bill")
public class Bill {

    @Id
    @GeneratedValue
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BillType type;

    /** Linked appointment or pharmacy order id */
    private Integer referenceId;

    private Integer patientId;

    private String description;

    @Column(precision = 12, scale = 2)
    private BigDecimal consultationFee;

    @Column(precision = 12, scale = 2)
    private BigDecimal pharmacyFee;

    @Column(precision = 12, scale = 2)
    private BigDecimal otherFee;

    /** Calculated on the frontend before submit */
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BillStatus status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime paidAt;

    @PrePersist
    void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (status == null) {
            status = BillStatus.PENDING;
        }
        if (consultationFee == null) {
            consultationFee = BigDecimal.ZERO;
        }
        if (pharmacyFee == null) {
            pharmacyFee = BigDecimal.ZERO;
        }
        if (otherFee == null) {
            otherFee = BigDecimal.ZERO;
        }
    }
}
