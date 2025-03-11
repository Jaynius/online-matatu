package com.benjamin.onlinematatu.service;

import com.benjamin.onlinematatu.DTO.GpsLocationDTO;
import com.benjamin.onlinematatu.entity.GpsLocation;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GpsLocationService {

    public GpsLocationDTO convertToDTO(GpsLocation gpsLocation);
    public GpsLocation convertToEntity(GpsLocationDTO gpsLocationDTO);
    public GpsLocationDTO createGpsLocation(GpsLocationDTO gpsLocationDTO);
    public List<GpsLocationDTO> getGpsLocations();
    public void deleteGpsLocation(Integer id);

}
