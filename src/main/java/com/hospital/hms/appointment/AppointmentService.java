package com.hospital.hms.appointment;

import com.hospital.hms.security.user.Role;
import com.hospital.hms.security.user.User;
import com.hospital.hms.security.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;

    public Appointment create(Appointment appointment) {
        User patient = getUserWithRole(appointment.getPatient().getId(), Role.PATIENT);
        User doctor = getUserWithRole(appointment.getDoctor().getId(), Role.DOCTOR);

        if (appointmentRepository.existsByDoctorIdAndTimeSlotAndStatus(
                doctor.getId(), appointment.getTimeSlot(), AppointmentStatus.BOOKED)) {
            throw new IllegalStateException(
                    "Doctor is already booked for slot " + appointment.getTimeSlot());
        }

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setStatus(AppointmentStatus.BOOKED);
        return appointmentRepository.save(appointment);
    }

    @Transactional(readOnly = true)
    public Appointment getById(Integer id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Appointment with id " + id + " not found"));
    }

    @Transactional(readOnly = true)
    public List<Appointment> getAll() {
        return appointmentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Appointment> getByPatientId(Integer patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    @Transactional(readOnly = true)
    public List<Appointment> getByDoctorId(Integer doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }

    // Reschedule (new timeSlot) or mark completed — status toggle dropdown in the UI
    public Appointment update(Integer id, Appointment incoming) {
        Appointment existing = getById(id);
        if (incoming.getTimeSlot() != null) {
            existing.setTimeSlot(incoming.getTimeSlot());
        }
        if (incoming.getStatus() != null) {
            existing.setStatus(incoming.getStatus());
        }
        return appointmentRepository.save(existing);
    }

    // Cancel button — soft delete: status becomes CANCELLED rather than removing the row
    public void cancel(Integer id) {
        Appointment existing = getById(id);
        existing.setStatus(AppointmentStatus.CANCELLED);
        appointmentRepository.save(existing);
    }

    private User getUserWithRole(Integer userId, Role expectedRole) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User with id " + userId + " not found"));
        if (user.getRole() != expectedRole) {
            throw new IllegalStateException(
                    "User " + userId + " does not have role " + expectedRole);
        }
        return user;
    }
}
