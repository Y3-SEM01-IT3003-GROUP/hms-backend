package com.hospital.hms.patient;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hospital.hms.security.user.Role;
import com.hospital.hms.security.user.User;
import com.hospital.hms.security.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PatientService {

    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public Patient create(Patient patient) {

        User incomingUser = patient.getUser();
        User user = User.builder()
                .firstname(incomingUser.getFirstname())
                .lastname(incomingUser.getLastname())
                .username(incomingUser.getUsername())
                .email(incomingUser.getEmail())
                .password(passwordEncoder.encode(incomingUser.getPassword()))
                .role(Role.PATIENT)
                .build();
        user = userRepository.save(user);

        patient.setUser(user);
        patientRepository.save(patient);
        user.setPassword(null);

        patient.setUser(user); // Remove the user reference before returning the patient object
        return patient;
    }


    @Transactional(readOnly = true)
    public Patient getById(Integer id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Patient with id " + id + " not found"));
    }

    @Transactional(readOnly = true)
    public List<Patient> getAll() {
        return patientRepository.findAll();
    }

    public Patient update(Integer id, Patient patient) {
        Patient existing = getById(id);
        existing.setStatus(patient.getStatus());
        return patientRepository.save(existing);
    }

    public void delete(Integer id) {
        Patient existing = getById(id);
        patientRepository.delete(existing);
    }
}
