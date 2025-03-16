package com.benjamin.onlinematatu.controller;


import com.benjamin.onlinematatu.DTO.PaymentDTO;
import com.benjamin.onlinematatu.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
        List<PaymentDTO> paymentList=paymentService.getPayments();
        return ResponseEntity.ok(paymentList);
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
