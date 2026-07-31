package com.hospital.hms.Prescription;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "prescriptions")
@Data
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String patientName;
    private String doctorName;

    @Column(columnDefinition = "TEXT")
    private String medicinesAndDosage;
    private String notes;
}