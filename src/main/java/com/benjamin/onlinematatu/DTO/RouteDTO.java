package com.benjamin.onlinematatu.DTO;

import com.benjamin.onlinematatu.entity.Matatu;
import lombok.Data;

import java.util.List;

@Data
public class RouteDTO {
    private String routeName;
    private String origin;
    private String destination;
    private double fare;
    private List<Matatu> matatuList;
}
