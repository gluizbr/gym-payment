package com.gym.be.payment;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;

public interface PaymentRepository extends MongoRepository<PaymentModel, String> {

  PaymentModel findByDate(Date date);
}
