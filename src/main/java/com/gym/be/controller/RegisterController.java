package com.gym.be.controller;

import com.gym.be.register.RegisterModel;
import com.gym.be.register.RegisterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/register")
public class RegisterController {

  private RegisterService registerService;

  public RegisterController(RegisterService registerService) {
    this.registerService = registerService;
  }

  @GetMapping()
  public ResponseEntity registerGet() {
    return ResponseEntity.ok(registerService.findAll());
  }

  @PostMapping
  public ResponseEntity registerPost(@RequestParam String name,
      @RequestParam List<String> modalities,
      @RequestParam Integer paymentDate,
      @RequestParam Float value) {
    return ResponseEntity.ok(registerService.save(
        RegisterModel.builder()
            .name(name)
            .modalities(modalities)
            .paymentDate(paymentDate)
            .value(value)
            .build()
    ));
  }
}
