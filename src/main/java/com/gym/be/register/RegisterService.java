package com.gym.be.register;

import com.gym.be.payment.PaymentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegisterService {

  private RegisterRepository registerRepository;
  private PaymentService paymentService;

  public RegisterService(RegisterRepository registerRepository,
      PaymentService paymentService) {
    this.registerRepository = registerRepository;
    this.paymentService = paymentService;
  }

  public RegisterModel save(RegisterModel register) {
    RegisterModel saved = registerRepository.save(register);
    paymentService.generatePayment(saved);
    return saved;
  }

  public List<RegisterModel> findAll() {
    return registerRepository.findAll();
  }
}
