package com.benjamin.onlinematatu.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "passenger_id")
    private int passengerId;
    private String passengerName;
    private String passengerEmail;
    private String passengerPhone;

    @ManyToMany
    @JoinTable(
            name = "passenger_matatu",
            joinColumns = @JoinColumn(name = "passenger_id"),
            inverseJoinColumns = @JoinColumn(name = "matatu_id")
    )
    private List<Matatu> matatus;

}
