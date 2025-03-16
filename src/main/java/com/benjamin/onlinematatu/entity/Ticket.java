package com.benjamin.onlinematatu.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ticketId;
    private int seatNumber;
    private LocalDate date;

    @OneToOne(cascade = CascadeType.ALL)
    private Passenger passenger;

    @OneToOne(cascade = CascadeType.ALL)
    private Route route;

    @ManyToOne
    @JoinColumn(name = "matatu_id", nullable = false)
    private Matatu matatu;

}
