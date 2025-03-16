package com.benjamin.onlinematatu.controller;

import com.benjamin.onlinematatu.DTO.TicketDTO;
import com.benjamin.onlinematatu.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ticket")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping
    public ResponseEntity<TicketDTO> newTicket(@RequestBody TicketDTO ticketDTO){
        TicketDTO ticket=ticketService.createTicket(ticketDTO);
        return new ResponseEntity<>(ticket, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TicketDTO>> getAllTicket(){
        List<TicketDTO> ticketList=ticketService.getTickets();
        return new ResponseEntity<>(ticketList, HttpStatus.OK);
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<TicketDTO> getTicketById(@PathVariable Integer ticketId){
        TicketDTO ticket=ticketService.getTicketById(ticketId);
        return new ResponseEntity<>(ticket, HttpStatus.OK);
    }



    @DeleteMapping("/{ticketId}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Integer ticketId){
        ticketService.deleteTicket(ticketId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{ticketId}")
    public ResponseEntity<TicketDTO> updateTicket(@PathVariable Integer ticketId, @RequestBody TicketDTO ticketDTO){
        TicketDTO ticket=ticketService.updateTicketById(ticketId, ticketDTO);
        return new ResponseEntity<>(ticket, HttpStatus.OK);
    }

    @GetMapping("/{passengerId}")
    public ResponseEntity<List<TicketDTO>> getTicketByPassengerID(@PathVariable Integer passengerId){
        List<TicketDTO> ticketList=ticketService.getTicketByPassengerID(passengerId);
        return new ResponseEntity<>(ticketList, HttpStatus.OK);
    }
}
