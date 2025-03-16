package com.benjamin.onlinematatu.service.serviceImpl;

import com.benjamin.onlinematatu.DTO.TicketDTO;
import com.benjamin.onlinematatu.entity.Ticket;
import com.benjamin.onlinematatu.repository.TicketRepo;
import com.benjamin.onlinematatu.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    public final TicketRepo ticketRepo;

    @Override
    public TicketDTO convertToDTO(Ticket ticket) {
        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setPassenger(ticket.getPassenger());
        ticketDTO.setMatatu(ticket.getMatatu());
        ticketDTO.setRoute(ticket.getRoute());
        ticketDTO.setSeatNumber(ticket.getSeatNumber());
        ticketDTO.setDate(ticket.getDate());
        return ticketDTO;
    }

    @Override
    public Ticket convertToEntity(TicketDTO ticketDTO) {
        Ticket ticket = new Ticket();
        ticket.setPassenger(ticketDTO.getPassenger());
        ticket.setMatatu(ticketDTO.getMatatu());
        ticket.setRoute(ticketDTO.getRoute());
        ticket.setSeatNumber(ticketDTO.getSeatNumber());
        ticket.setDate(ticketDTO.getDate());
        return ticket;
    }

    @Override
    public TicketDTO createTicket(TicketDTO ticketDTO) {
        Ticket ticket = convertToEntity(ticketDTO);
        ticketRepo.save(ticket);
        return convertToDTO(ticket);
    }

    @Override
    public List<TicketDTO> getTickets() {
      return ticketRepo.findAll()
              .stream()
              .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteTicket(Integer id) {
        ticketRepo.deleteById(id);

    }

    @Override
    public TicketDTO updateTicketById(Integer id, TicketDTO ticketDTO) {
        Ticket ticket = ticketRepo.findById(id)
                .orElseThrow(()-> new RuntimeException("Ticket not found"));
        ticket.setPassenger(ticketDTO.getPassenger());
        ticket.setMatatu(ticketDTO.getMatatu());
        ticket.setRoute(ticketDTO.getRoute());
        ticket.setSeatNumber(ticketDTO.getSeatNumber());
        ticket.setDate(ticketDTO.getDate());
        return convertToDTO(ticket);
    }

    @Override
    public TicketDTO getTicketById(Integer ticketId){
       Ticket ticket = ticketRepo.findById(ticketId)
               .orElseThrow(()-> new RuntimeException("Ticket not found"));
         return convertToDTO(ticket);
    }

    @Override
    public List<TicketDTO> getTicketByPassengerID(Integer passengerId) {
        List<Ticket> ticketList=ticketRepo.findAll();
        if(ticketList.isEmpty()){
            throw new RuntimeException("Ticket list not found");
        }
        for(Ticket ticket:ticketList){
            if(ticket.getPassenger().getPassengerId()==passengerId){
                return ticketList.stream()
                        .map(this::convertToDTO)
                        .collect(Collectors.toList());
            }
        }

        throw new RuntimeException("Passenger not found");
    }


}
