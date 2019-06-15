package com.gym.be.payment;

import com.gym.be.register.RegisterModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface PaymentRepository extends MongoRepository<PaymentModel, String> {

  List<PaymentModel> findByRegisterIn(List<RegisterModel> registerModel);

  List<PaymentModel> findByPaymentDateGreaterThan(Date paymentDate);

  List<PaymentModel> findByPaymentDateLessThan(Date paymentDate);

  List<PaymentModel> findByPaymentDateBetween(Date initialPaymentDate, Date finalPaymentDate);

  List<PaymentModel> findByPayed(Boolean payed);
}
