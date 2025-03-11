package com.benjamin.onlinematatu.service.serviceImpl;

import com.benjamin.onlinematatu.DTO.GpsLocationDTO;
import com.benjamin.onlinematatu.entity.GpsLocation;
import com.benjamin.onlinematatu.repository.GpsLocationRepo;
import com.benjamin.onlinematatu.service.GpsLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GpsServiceImpl implements GpsLocationService {

    private  final GpsLocationRepo gpsLocationRepo;
    @Override
    public GpsLocationDTO convertToDTO(GpsLocation gpsLocation) {
        GpsLocationDTO dto= new GpsLocationDTO();
        dto.setLatitude(gpsLocation.getLatitude());
        dto.setLongitude(gpsLocation.getLongitude());
        dto.setTimestamp(gpsLocation.getTimestamp());
        return dto;
    }

    @Override
    public GpsLocation convertToEntity(GpsLocationDTO gpsLocationDTO) {
        GpsLocation gpsLocation = new GpsLocation();
        gpsLocation.setLatitude(gpsLocationDTO.getLatitude());
        gpsLocation.setLongitude(gpsLocationDTO.getLongitude());
        gpsLocation.setTimestamp(gpsLocationDTO.getTimestamp());
        return gpsLocation;
    }

    @Override
    public GpsLocationDTO createGpsLocation(GpsLocationDTO gpsLocationDTO) {
        GpsLocation location= convertToEntity(gpsLocationDTO);
        gpsLocationRepo.save(location);
        return convertToDTO(location);


    }

    @Override
    public List<GpsLocationDTO> getGpsLocations() {
        return gpsLocationRepo.findAll()
                .stream().map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteGpsLocation(Integer id) {
        gpsLocationRepo.deleteById(id);
    }


}
