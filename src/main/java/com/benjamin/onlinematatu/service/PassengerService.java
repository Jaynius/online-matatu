package com.benjamin.onlinematatu.service;

import com.benjamin.onlinematatu.DTO.PassengerDTO;
import com.benjamin.onlinematatu.entity.Passenger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PassengerService {
    public PassengerDTO convertToDTO(Passenger passenger);
    public Passenger convertToEntity(PassengerDTO passengerDTO);
    public PassengerDTO createPassenger(PassengerDTO passengerDTO);
    public PassengerDTO getPassenger(Integer id);
    public void deletePassenger(Integer id);
    public PassengerDTO updatePassengerById(Integer id, PassengerDTO passengerDTO);
    public List<PassengerDTO> getAllPassenger();


}
