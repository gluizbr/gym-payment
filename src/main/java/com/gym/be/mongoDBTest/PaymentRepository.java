package com.gym.be.mongoDBTest;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaymentRepository extends MongoRepository<PaymentModel, String> {

  PaymentModel findByName(String alias);
}
