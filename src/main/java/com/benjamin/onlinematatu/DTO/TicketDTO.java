package com.benjamin.onlinematatu.DTO;

import com.benjamin.onlinematatu.entity.Matatu;
import com.benjamin.onlinematatu.entity.Passenger;
import com.benjamin.onlinematatu.entity.Route;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TicketDTO {
    private int seatNumber;
    private LocalDate date;

    private Passenger passenger;

    private Route route;

    private Matatu matatu;
}
