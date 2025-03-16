package com.benjamin.onlinematatu.service;

import com.benjamin.onlinematatu.DTO.TicketDTO;
import com.benjamin.onlinematatu.entity.Ticket;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TicketService {
    public TicketDTO convertToDTO(Ticket ticket);
    public Ticket convertToEntity(TicketDTO ticketDTO);
    public TicketDTO createTicket(TicketDTO ticketDTO);
    public List<TicketDTO> getTickets();
    public void deleteTicket(Integer id);
    public TicketDTO updateTicketById(Integer id, TicketDTO ticketDTO);

   public TicketDTO getTicketById(Integer ticketId);
   public  TicketDTO getTicketByPassengerID(Integer passengerIdd);
}
