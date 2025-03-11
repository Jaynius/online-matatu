package com.benjamin.onlinematatu.controller;


import com.benjamin.onlinematatu.DTO.GpsLocationDTO;
import com.benjamin.onlinematatu.service.GpsLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/gps-location")
@RequiredArgsConstructor
public class GpsLocationController {

    @Autowired
    private final GpsLocationService gpsLocationService;

    @PostMapping
    public ResponseEntity<GpsLocationDTO> newLocation(@RequestBody GpsLocationDTO gpsLocationDTO){
        GpsLocationDTO location=gpsLocationService.createGpsLocation(gpsLocationDTO);
        return new ResponseEntity<>(location, HttpStatus.CREATED);


    }

    @GetMapping
    public ResponseEntity<List<GpsLocationDTO>> getAllLocations(){
        List<GpsLocationDTO> locationList=gpsLocationService.getGpsLocations();
        return new ResponseEntity<>(locationList, HttpStatus.OK);

    }

    @DeleteMapping("/{location-id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Integer locationId){
        gpsLocationService.deleteGpsLocation(locationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
