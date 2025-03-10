package com.benjamin.onlinematatu.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Matatu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int matatuId;
    private String driverName;
    private String licenceNumber;
    private int capacity;
    private GpsLocation currentGpsLocation;
    private Route route;
}
