package com.hospital.hms.rooms;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String roomId;

    @Column(nullable = false)
    private String department;
    @Column(unique = true, nullable = false)
    private String roomNumber;
    @Column(nullable = false)
    private Integer capacity;
    @Column(nullable = false)
    private Integer availableBeds;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoomStatus status;
}
