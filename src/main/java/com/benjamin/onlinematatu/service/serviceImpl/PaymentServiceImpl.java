package com.benjamin.onlinematatu.service.serviceImpl;

import com.benjamin.onlinematatu.DTO.PaymentDTO;
import com.benjamin.onlinematatu.entity.Payment;
import com.benjamin.onlinematatu.repository.PaymentRepo;
import com.benjamin.onlinematatu.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepo paymentRepo;

    @Override
    public PaymentDTO convertToDTO(Payment payment) {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setPassenger(payment.getPassenger());
        paymentDTO.setAmount(payment.getAmount());
        paymentDTO.setConfirmationCode(payment.getConfirmationCode());
        paymentDTO.setTimestamp(payment.getTimestamp());
        return paymentDTO;
    }

    @Override
    public Payment convertToEntity(PaymentDTO paymentDTO) {
        Payment payment = new Payment();
        payment.setPassenger(paymentDTO.getPassenger());
        payment.setAmount(paymentDTO.getAmount());
        payment.setConfirmationCode(paymentDTO.getConfirmationCode());
        payment.setTimestamp(paymentDTO.getTimestamp());
        return payment;
    }

    @Override
    public PaymentDTO createPayment(PaymentDTO paymentDTO) {
        Payment payment = convertToEntity(paymentDTO);
        paymentRepo.save(payment);
        return convertToDTO(payment);
    }

    @Override
    public List<PaymentDTO> getPayments() {
        return paymentRepo.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deletePayment(Integer id) {
        paymentRepo.deleteById(id);

    }

    @Override
    public PaymentDTO updatePaymentById(Integer id, PaymentDTO paymentDTO) {
        Payment payment = paymentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        payment.setPassenger(paymentDTO.getPassenger());
        payment.setAmount(paymentDTO.getAmount());
        payment.setConfirmationCode(paymentDTO.getConfirmationCode());
        payment.setTimestamp(paymentDTO.getTimestamp());
        paymentRepo.save(payment);
        return convertToDTO(payment);
    }

    @Override
    public PaymentDTO getPaymentById(Integer paymentId) {
        Payment payment = paymentRepo.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        return convertToDTO(payment);
    }

    @Override
    public List<PaymentDTO> getPaymentsByPassenger(Integer passengerId) {
        return paymentRepo.findAll()
                .stream()
                .filter(payment -> payment.getPassenger().getPassengerId() == passengerId)
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

}
