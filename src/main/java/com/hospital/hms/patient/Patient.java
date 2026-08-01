package com.hospital.hms.patient;

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
@Table(name = "patients")
public class Patient {
    @Id
    @GeneratedValue
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;
    
    private PatientStatus status;
}
