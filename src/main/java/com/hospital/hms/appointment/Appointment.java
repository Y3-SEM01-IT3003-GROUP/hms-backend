package com.hospital.hms.appointment;

import com.hospital.hms.security.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table(name = "appointment")
public class Appointment {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private User patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private User doctor;

    @Column(nullable = false)
    private String timeSlot;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppointmentStatus status;
}
