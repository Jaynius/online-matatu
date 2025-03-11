package com.benjamin.onlinematatu.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GpsLocationDTO {
    private Double latitude;
    private Double longitude;
    private LocalDateTime timestamp;
}
