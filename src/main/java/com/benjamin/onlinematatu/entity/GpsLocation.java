package com.benjamin.onlinematatu.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GpsLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer gpsId;
    private Double latitude;
    private Double  longitude;
    private LocalDateTime timestamp;
}
