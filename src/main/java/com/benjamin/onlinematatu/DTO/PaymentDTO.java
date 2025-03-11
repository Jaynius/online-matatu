package com.benjamin.onlinematatu.DTO;

import com.benjamin.onlinematatu.entity.Passenger;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PaymentDTO {
    private float amount;
    private LocalDate timestamp;

    private String confirmationCode;

    private Passenger passenger;

}
