package com.hospital.hms.doctor.doctor;

import com.hospital.hms.security.user.Role;
import com.hospital.hms.security.user.User;
import com.hospital.hms.security.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Doctor create(Doctor doctor) {
        if (doctor.getSpecialization() == null || doctor.getSpecialization().isBlank()) {
            throw new IllegalArgumentException("specialization is required");
        }
        if (doctor.getLicenseNumber() == null || doctor.getLicenseNumber().isBlank()) {
            throw new IllegalArgumentException("licenseNumber is required");
        }
        if (doctorRepository.existsByLicenseNumber(doctor.getLicenseNumber())) {
            throw new IllegalStateException(
                    "License number '" + doctor.getLicenseNumber() + "' is already in use");
        }

        User incomingUser = doctor.getUser();
        if (incomingUser == null) {
            throw new IllegalArgumentException("user details are required");
        }

        User user = User.builder()
                .firstname(incomingUser.getFirstname())
                .lastname(incomingUser.getLastname())
                .username(incomingUser.getUsername())
                .email(incomingUser.getEmail())
                .password(passwordEncoder.encode(incomingUser.getPassword()))
                .role(Role.DOCTOR)
                .build();
        user = userRepository.save(user);

        doctor.setId(null);
        doctor.setUser(user);
        doctor.setActive(true);
        if (doctor.getStatus() == null) {
            doctor.setStatus(DoctorStatus.ACTIVE);
        }
        if (doctor.getConsultationFee() == null) {
            doctor.setConsultationFee(BigDecimal.ZERO);
        }

        return doctorRepository.save(doctor);
    }

    @Transactional(readOnly = true)
    public Doctor getById(Integer id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Doctor with id " + id + " not found"));
    }

    @Transactional(readOnly = true)
    public List<Doctor> getAll(String specialization) {
        if (specialization != null && !specialization.isBlank()) {
            return doctorRepository.findBySpecializationIgnoreCase(specialization.trim());
        }
        return doctorRepository.findAll();
    }

    /** Updates working hours, consultation fee, status, or specialization. */
    public Doctor update(Integer id, Doctor doctor) {
        Doctor existing = getById(id);

        if (!Boolean.TRUE.equals(existing.getActive())) {
            throw new IllegalStateException("Cannot update a deactivated doctor");
        }

        if (doctor.getWorkingHours() != null) {
            existing.setWorkingHours(doctor.getWorkingHours());
        }
        if (doctor.getConsultationFee() != null) {
            existing.setConsultationFee(doctor.getConsultationFee());
        }
        if (doctor.getStatus() != null) {
            existing.setStatus(doctor.getStatus());
        }
        if (doctor.getSpecialization() != null && !doctor.getSpecialization().isBlank()) {
            existing.setSpecialization(doctor.getSpecialization());
        }

        return doctorRepository.save(existing);
    }

    /** Soft-deactivates the doctor by setting active = false. */
    public Doctor delete(Integer id) {
        Doctor existing = getById(id);

        if (!Boolean.TRUE.equals(existing.getActive())) {
            throw new IllegalStateException("Doctor is already deactivated");
        }

        existing.setActive(false);
        existing.setStatus(DoctorStatus.TERMINATED);
        return doctorRepository.save(existing);
    }
}
