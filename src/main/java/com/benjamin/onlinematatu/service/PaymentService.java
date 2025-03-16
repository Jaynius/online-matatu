package com.benjamin.onlinematatu.service;

import com.benjamin.onlinematatu.DTO.PaymentDTO;
import com.benjamin.onlinematatu.entity.Payment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PaymentService {
    public PaymentDTO convertToDTO(Payment payment);
    public Payment convertToEntity(PaymentDTO paymentDTO);
    public PaymentDTO createPayment(PaymentDTO paymentDTO);
    public List<PaymentDTO> getPayments();
    public void deletePayment(Integer id);
    public PaymentDTO updatePaymentById(Integer id, PaymentDTO paymentDTO);
    public PaymentDTO getPaymentById(Integer paymentId);
}
