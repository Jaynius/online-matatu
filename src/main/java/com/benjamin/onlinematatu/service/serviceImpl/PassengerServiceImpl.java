package com.benjamin.onlinematatu.service.serviceImpl;

import com.benjamin.onlinematatu.DTO.PassengerDTO;
import com.benjamin.onlinematatu.entity.Passenger;
import com.benjamin.onlinematatu.repository.PassengerRepo;
import com.benjamin.onlinematatu.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepo passengerRepo;

    @Override
    public PassengerDTO convertToDTO(Passenger passenger) {
        PassengerDTO passengerDTO=new PassengerDTO();
        passengerDTO.setPassengerName(passenger.getPassengerName());
        passengerDTO.setPassengerPhone(passenger.getPassengerPhone());
        passengerDTO.setPassengerEmail(passenger.getPassengerEmail());
        passengerDTO.setMatatus(passenger.getMatatus());
        return passengerDTO;
    }

    @Override
    public Passenger convertToEntity(PassengerDTO passengerDTO) {
        Passenger passenger=new Passenger();
        passenger.setPassengerName(passengerDTO.getPassengerName());
        passenger.setPassengerPhone(passengerDTO.getPassengerPhone());
        passenger.setPassengerEmail(passengerDTO.getPassengerEmail());
        passenger.setMatatus(passengerDTO.getMatatus());
        return passenger;
    }

    @Override
    public PassengerDTO createPassenger(PassengerDTO passengerDTO) {
        Passenger passenger=convertToEntity(passengerDTO);
        passengerRepo.save(passenger);
        return convertToDTO(passenger);
    }

    @Override
    public PassengerDTO getPassenger(Integer id) {
        Passenger passenger=passengerRepo.findById(id)
                .orElseThrow(()-> new RuntimeException("Passenger not found"));
        return convertToDTO(passenger);
    }

    @Override
    public void deletePassenger(Integer id) {
        passengerRepo.deleteById(id);

    }

    @Override
    public PassengerDTO updatePassengerById(Integer id, PassengerDTO passengerDTO) {
        Passenger passenger=passengerRepo.findById(id)
                .orElseThrow(()-> new RuntimeException("Passenger not found"));
        passenger.setPassengerName(passengerDTO.getPassengerName());
        passenger.setPassengerPhone(passengerDTO.getPassengerPhone());
        passenger.setPassengerEmail(passengerDTO.getPassengerEmail());
        passenger.setMatatus(passengerDTO.getMatatus());
        passengerRepo.save(passenger);
        return convertToDTO(passenger);
    }

    @Override
    public List<PassengerDTO> getAllPassenger() {
        return passengerRepo.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
