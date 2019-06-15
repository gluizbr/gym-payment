package com.gym.be.payment;

import com.gym.be.register.RegisterModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

  private PaymentRepository paymentRepository;

  @Autowired
  public PaymentService(PaymentRepository paymentRepository) {
    this.paymentRepository = paymentRepository;
  }

  public PaymentModel save(PaymentModel payment) {
    return paymentRepository.save(payment);
  }

  public List<PaymentModel> findAll() {
    return paymentRepository.findAll();
  }

  public List<PaymentModel> findByRegister(List<RegisterModel> registerModelList) {
    return paymentRepository.findByRegisterIn(registerModelList);
  }

  public List<PaymentModel> findByPaymentInitialDate(Date initialDate) {
    return paymentRepository.findByPaymentDateGreaterThan(initialDate);
  }

  public List<PaymentModel> findByPaymentFinalDate(Date finalDate) {
    return paymentRepository.findByPaymentDateLessThan(finalDate);
  }

  public List<PaymentModel> findByPaymentDateBetween(Date initialDate, Date finalDate) {
    return paymentRepository.findByPaymentDateBetween(initialDate, finalDate);
  }

  public List<PaymentModel> findByPayed(Boolean payed) {
    return paymentRepository.findByPayed(payed);
  }

  public void generatePayment(RegisterModel student) {
    Date day = new Date();
    int actualMonth = day.getMonth();
    while (actualMonth < 12) {
      save(PaymentModel.builder()
          .register(student)
          .paymentDate(new Date(day.getYear(), actualMonth, student.getPaymentDate()))
          .payed(false)
          .build());
      actualMonth++;
    }
  }

  public PaymentModel pay(String id) {
    Optional<PaymentModel> paymentModel = paymentRepository.findById(id);
    if (paymentModel.isPresent()) {
      paymentModel.get().setPayed(true);
      return paymentRepository.save(paymentModel.get());
    }
    return null;
  }
}
