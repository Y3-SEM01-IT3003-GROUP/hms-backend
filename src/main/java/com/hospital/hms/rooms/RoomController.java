package com.hospital.hms.rooms;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Room create(@RequestBody Room room) {
        return roomService.create(room);
    }

    @GetMapping("/{roomId}")
    public Room getByRoomId(@PathVariable String roomId) {
        return roomService.getByRoomId(roomId);
    }

    @GetMapping
    public List<Room> getAll() {
        return roomService.getAll();
    }

    @GetMapping("/available")
    public List<Room> getAvailableRooms() {
        return roomService.getAvailableRooms();
    }

    @PutMapping("/{roomId}")
    public Room update(@PathVariable String roomId, @RequestBody Room room) {
        return roomService.update(roomId, room);
    }

    @DeleteMapping("/{roomId}")
    public Room decommission(@PathVariable String roomId) {
        return roomService.decommission(roomId);
    }
}
