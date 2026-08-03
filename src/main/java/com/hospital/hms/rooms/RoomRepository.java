package com.hospital.hms.rooms;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Integer> {

    Optional<Room> findByRoomId(String roomId);

    boolean existsByRoomNumber(String roomNumber);

    List<Room> findByAvailableBedsGreaterThan(Integer minBeds);
}

