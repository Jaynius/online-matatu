package com.benjamin.onlinematatu.DTO;

import com.benjamin.onlinematatu.entity.Matatu;
import lombok.Data;

import java.util.List;

@Data
public class PassengerDTO {
    private String passengerName;
    private String passengerEmail;
    private String passengerPhone;

    private List<Matatu> matatus;
}
