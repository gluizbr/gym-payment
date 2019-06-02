package com.gym.be.register;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RegisterRepository extends MongoRepository<RegisterModel, String> {

  RegisterModel findByName(String alias);
}
