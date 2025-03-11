package com.benjamin.onlinematatu.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MpesaPayment{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mpesaPaymentId;
    private float amount;
    private LocalDate timestamp;

    private String confirmationCode;

    @OneToOne(cascade = CascadeType.ALL)
    private Passenger passenger;


}
