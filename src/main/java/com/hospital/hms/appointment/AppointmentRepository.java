package com.hospital.hms.appointment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    List<Appointment> findByPatientId(Integer patientId);

    List<Appointment> findByDoctorId(Integer doctorId);

    boolean existsByDoctorIdAndTimeSlotAndStatus(Integer doctorId, String timeSlot, AppointmentStatus status);
}
