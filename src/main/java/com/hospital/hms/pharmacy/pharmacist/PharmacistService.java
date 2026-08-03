package com.hospital.hms.pharmacy.pharmacist;

import com.hospital.hms.security.user.Role;
import com.hospital.hms.security.user.User;
import com.hospital.hms.security.user.UserRepository;
import com.hospital.hms.security.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PharmacistService {

    private final PharmacistRepository pharmacistRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Pharmacist create(Pharmacist pharmacist) {
        if (pharmacistRepository.existsByLicenseNumber(pharmacist.getLicenseNumber())) {
            throw new IllegalStateException(
                    "License number '" + pharmacist.getLicenseNumber() + "' is already in use");
        }

        User incomingUser = pharmacist.getUser();
        User user = User.builder()
                .firstname(incomingUser.getFirstname())
                .lastname(incomingUser.getLastname())
                .username(incomingUser.getUsername())
                .email(incomingUser.getEmail())
                .password(passwordEncoder.encode(incomingUser.getPassword()))
                .role(Role.PHARMACIST)
                .build();
        user = userRepository.save(user);

        pharmacist.setUser(user);
        return pharmacistRepository.save(pharmacist);
    }

    @Transactional(readOnly = true)
    public Pharmacist getById(Integer id) {
        return pharmacistRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Pharmacist with id " + id + " not found"));
    }

    @Transactional(readOnly = true)
    public List<Pharmacist> getAll() {
        return pharmacistRepository.findAll();
    }

    public Pharmacist update(Integer id, Pharmacist pharmacist) {
        Pharmacist existing = getById(id);

        User incomingUser = pharmacist.getUser();

        existing.getUser().setFirstname(incomingUser.getFirstname());
        existing.getUser().setLastname(incomingUser.getLastname());
        existing.getUser().setEmail(incomingUser.getEmail());
        existing.getUser().setPassword(passwordEncoder.encode(incomingUser.getPassword()));
        existing.getUser().setRole(Role.PHARMACIST);
        existing.setStatus(pharmacist.getStatus());
        return pharmacistRepository.save(existing);
    }

    public void delete(Integer id) {
        Pharmacist existing = getById(id);
        pharmacistRepository.delete(existing);
    }
}