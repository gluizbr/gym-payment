package com.gym.be.register;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RegisterRepository extends MongoRepository<RegisterModel, String> {

  RegisterModel findByName(String alias);

  List<RegisterModel> findByModalitiesContains(List<String> modalities);
}
