package com.gym.be.register;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegisterService {

  private RegisterRepository registerRepository;

  public RegisterService(RegisterRepository registerRepository){
    this.registerRepository = registerRepository;
  }

  public RegisterModel save(RegisterModel register) {
    return registerRepository.save(register);
  }

  public List<RegisterModel> findAll() {
    return registerRepository.findAll();
  }
}
