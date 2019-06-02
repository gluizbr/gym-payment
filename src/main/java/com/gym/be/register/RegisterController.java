package com.gym.be.register;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegisterController {

  private RegisterService registerService;

  public RegisterController(RegisterService registerService) {
    this.registerService = registerService;
  }

  @GetMapping
  public ResponseEntity paymentGet() {
    return ResponseEntity.ok(registerService.findAll());
  }

  @PostMapping
  public ResponseEntity paymentPost() {
    return ResponseEntity.ok(registerService.save(
        RegisterModel.builder().name("Leo").build()
    ));
  }
}
