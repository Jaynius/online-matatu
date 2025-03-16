package com.benjamin.onlinematatu.controller;

import com.benjamin.onlinematatu.DTO.PassengerDTO;
import com.benjamin.onlinematatu.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/passenger")
@RequiredArgsConstructor
public class PassengerController {

    private final PassengerService passengerService;

    @PostMapping
    public ResponseEntity<PassengerDTO> newPassenger(@RequestBody PassengerDTO passengerDTO){
        PassengerDTO passenger=passengerService.createPassenger(passengerDTO);
        return new ResponseEntity<>(passenger, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PassengerDTO> getPassenger(@RequestParam Integer id){
        PassengerDTO passenger=passengerService.getPassenger(id);
        return new ResponseEntity<>(passenger, HttpStatus.OK);
    }

    @GetMapping
    public  ResponseEntity<List<PassengerDTO>> getAllPassenger(){
        return new ResponseEntity<>(passengerService.getAllPassenger(), HttpStatus.OK);
    }
}
