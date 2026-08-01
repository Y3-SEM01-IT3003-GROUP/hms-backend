package com.hospital.hms.security.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public enum Permission {

    PATIENT_READ("patient:read"),
    PATIENT_UPDATE("patient:update"),

    DOCTOR_READ("doctor:read"),
    DOCTOR_UPDATE("doctor:update"),

    APPOINTMENT_CREATE("appointment:create"),
    APPOINTMENT_READ("appointment:read"),
    APPOINTMENT_UPDATE("appointment:update"),
    APPOINTMENT_DELETE("appointment:delete"),

    PRESCRIPTION_CREATE("prescription:create"),
    PRESCRIPTION_READ("prescription:read"),
    PRESCRIPTION_UPDATE("prescription:update"),
    PRESCRIPTION_DELETE("prescription:delete"),

    MEDICINE_CREATE("medicine:create"),
    MEDICINE_READ("medicine:read"),
    MEDICINE_UPDATE("medicine:update"),
    MEDICINE_DELETE("medicine:delete"),

    BILL_CREATE("bill:create"),
    BILL_READ("bill:read"),
    BILL_UPDATE("bill:update"),
    BILL_DELETE("bill:delete"),

    ADMIN_CREATE("admin:create"),
    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_DELETE("admin:delete");

    @Getter
    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }
}






