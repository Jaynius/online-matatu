package com.benjamin.onlinematatu.DTO;


import com.benjamin.onlinematatu.entity.GpsLocation;
import com.benjamin.onlinematatu.entity.Route;
import lombok.Data;

import java.util.List;

@Data
public class MatatuDTO {
    private String driverName;
    private String licenceNumber;
    private int capacity;
    private List<GpsLocation> currentGpsLocation;
    private Route route;

}
