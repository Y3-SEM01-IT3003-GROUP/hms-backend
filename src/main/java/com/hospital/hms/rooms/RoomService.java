package com.hospital.hms.rooms;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomService {
    private final RoomRepository roomRepository;


    public Room create(Room room) {
        if (roomRepository.existsByRoomNumber(room.getRoomNumber())) {
            throw new IllegalStateException(
                    "Room number '" + room.getRoomNumber() + "' is already allocated");
        }

        room.setRoomId(null);
        room.setAvailableBeds(room.getCapacity());
        room.setStatus(RoomStatus.AVAILABLE);

        Room saved = roomRepository.save(room);
        saved.setRoomId("ROOM-" + String.format("%04d", saved.getId()));
        return roomRepository.save(saved);
    }


    @Transactional(readOnly = true)
    public Room getByRoomId(String roomId) {
        return roomRepository.findByRoomId(roomId)
                .orElseThrow(() -> new IllegalStateException("Room '" + roomId + "' not found"));
    }

    @Transactional(readOnly = true)
    public List<Room> getAll() {
        return roomRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public List<Room> getAvailableRooms() {
        return roomRepository.findByAvailableBedsGreaterThan(0);
    }


    public Room update(String roomId, Room room) {
        Room existing = getByRoomId(roomId);
        existing.setDepartment(room.getDepartment());
        existing.setCapacity(room.getCapacity());
        existing.setAvailableBeds(room.getAvailableBeds());
        return roomRepository.save(existing);
    }


    public Room decommission(String roomId) {
        Room existing = getByRoomId(roomId);
        existing.setStatus(RoomStatus.DECOMMISSIONED);
        existing.setAvailableBeds(0);
        return roomRepository.save(existing);
    }
}
