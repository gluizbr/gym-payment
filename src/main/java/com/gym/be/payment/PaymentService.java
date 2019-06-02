package com.gym.be.payment;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

  private PaymentRepository paymentRepository;

  public PaymentService(PaymentRepository paymentRepository){
    this.paymentRepository = paymentRepository;
  }

  public PaymentModel save(PaymentModel payment) {
    return paymentRepository.save(payment);
  }

  public List<PaymentModel> findAll() {
    return paymentRepository.findAll();
  }
}
