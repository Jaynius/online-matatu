package com.benjamin.onlinematatu.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GpsLocationDTO {
    private String latitude;
    private String longitude;
    private LocalDateTime timestamp;
}
