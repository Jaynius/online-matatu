package com.benjamin.onlinematatu.DTO;

import lombok.Data;

@Data
public class RegisterRequestDTO {
    private String username;
    private String password;
    private String passengerName;
    private String passengerEmail;
    private String passengerPhone;
} 