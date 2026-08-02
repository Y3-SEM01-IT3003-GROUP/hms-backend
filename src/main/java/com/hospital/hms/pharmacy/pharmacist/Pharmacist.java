package com.hospital.hms.pharmacy.pharmacist;

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
@Table(name = "pharmacist")
public class Pharmacist {

    @Id
    @GeneratedValue
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;

    @Column(unique = true, nullable = false)
    private String licenseNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PharmacistStatus status;
}