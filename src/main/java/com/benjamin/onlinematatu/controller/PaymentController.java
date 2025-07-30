package com.benjamin.onlinematatu.controller;


import com.benjamin.onlinematatu.DTO.PaymentDTO;
import com.benjamin.onlinematatu.entity.User;
import com.benjamin.onlinematatu.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {

    
    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentDTO> createPayment(PaymentDTO paymentDTO){
        PaymentDTO payment=paymentService.createPayment(paymentDTO);
        return ResponseEntity.ok(payment);
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<PaymentDTO> getPaymentByTicketId(@PathVariable Integer ticketId){
        PaymentDTO payment=paymentService.getPaymentById(ticketId);
        return ResponseEntity.ok(payment);
    }

    @GetMapping
    public ResponseEntity<List<PaymentDTO>> getAllPayments(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        
        if (currentUser.getRole() == User.Role.ADMIN) {
            List<PaymentDTO> paymentList = paymentService.getPayments();
            return ResponseEntity.ok(paymentList);
        } else {
            // For passengers, return only their payments
            List<PaymentDTO> paymentList = paymentService.getPaymentsByPassenger(currentUser.getPassenger().getPassengerId());
            return ResponseEntity.ok(paymentList);
        }
    }

    @PatchMapping("/{ticketId}")
    public ResponseEntity<PaymentDTO> updatePayment(@PathVariable Integer ticketId, @RequestBody PaymentDTO paymentDTO){
        PaymentDTO payment=paymentService.updatePaymentById(ticketId, paymentDTO);
        return ResponseEntity.ok(payment);
    }

    @DeleteMapping("/{ticketId}")
    public ResponseEntity<Void> deletePayment(@PathVariable Integer ticketId){
        paymentService.deletePayment(ticketId);
        return ResponseEntity.noContent().build();
    }


}
