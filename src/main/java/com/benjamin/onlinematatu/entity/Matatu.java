package com.benjamin.onlinematatu.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Matatu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="matatu_id")
    private int matatuId;
    private String driverName;
    private String licenceNumber;
    private int capacity;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "gps_locations",referencedColumnName ="matatu_id" )
    private List<GpsLocation> currentGpsLocation;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="route")
    private Route route;
}
