package com.gym.be.controller;

import com.gym.be.payment.PaymentFilterModel;
import com.gym.be.payment.PaymentService;
import com.gym.be.register.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {
  PaymentService paymentService;
  RegisterService registerService;

  @Autowired
  public PaymentController(PaymentService paymentService,
      RegisterService registerService) {
    this.paymentService = paymentService;
    this.registerService = registerService;
  }

  @GetMapping()
  public ResponseEntity paymentGet(@RequestParam(value = "name", required = false) String name,
      @RequestParam(value = "initial_date", required = false)
      @DateTimeFormat(pattern = "yyyy-MM-dd") Date initialDate,
      @RequestParam(value = "final_date", required = false)
      @DateTimeFormat(pattern = "yyyy-MM-dd")
          Date finalDate,
      @RequestParam(value = "payed", required = false) Boolean payed,
      @RequestParam(value = "modalities", required = false) List<String> modalities) {

    return ResponseEntity.ok(paymentService.findByFilter(PaymentFilterModel.builder()
        .name(name)
        .initialDate(initialDate)
        .finalDate(finalDate)
        .payed(payed)
        .modalities(modalities)
        .build()));
  }

  @PostMapping("/pay")
  public ResponseEntity paymentPost(@RequestParam String id) {
    return ResponseEntity.ok(paymentService.pay(id));
  }
}
