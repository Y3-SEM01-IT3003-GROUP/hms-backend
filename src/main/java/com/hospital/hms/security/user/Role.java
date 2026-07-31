package com.hospital.hms.security.user;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import static com.hospital.hms.security.user.Permission.*;

public enum Role {

    PATIENT(Set.of(
            PATIENT_READ, PATIENT_UPDATE,
            APPOINTMENT_CREATE, APPOINTMENT_READ, APPOINTMENT_UPDATE, APPOINTMENT_DELETE,
            PRESCRIPTION_READ
    )),

    DOCTOR(Set.of(
            DOCTOR_READ, DOCTOR_UPDATE,
            PATIENT_READ,
            APPOINTMENT_READ, APPOINTMENT_UPDATE,
            PRESCRIPTION_CREATE, PRESCRIPTION_READ, PRESCRIPTION_UPDATE, PRESCRIPTION_DELETE,
            MEDICINE_READ
    )),

    PHARMACIST(Set.of(
            MEDICINE_CREATE, MEDICINE_READ, MEDICINE_UPDATE, MEDICINE_DELETE,
            PRESCRIPTION_READ
    )),

    ADMIN(Set.of(
            ADMIN_CREATE, ADMIN_READ, ADMIN_UPDATE, ADMIN_DELETE,
            PATIENT_READ, PATIENT_UPDATE,
            DOCTOR_READ, DOCTOR_UPDATE,
            APPOINTMENT_CREATE, APPOINTMENT_READ, APPOINTMENT_UPDATE, APPOINTMENT_DELETE,
            PRESCRIPTION_CREATE, PRESCRIPTION_READ, PRESCRIPTION_UPDATE, PRESCRIPTION_DELETE,
            MEDICINE_CREATE, MEDICINE_READ, MEDICINE_UPDATE, MEDICINE_DELETE
    ));

    @Getter
    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions().stream()
                .map(p -> new SimpleGrantedAuthority(p.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}

