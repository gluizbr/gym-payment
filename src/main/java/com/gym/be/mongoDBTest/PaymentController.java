package com.gym.be.mongoDBTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {

  private PaymentService paymentService;

  public PaymentController(PaymentService paymentService) {
    this.paymentService = paymentService;
  }

  @GetMapping
  public ResponseEntity paymentGet() {
    return ResponseEntity.ok(paymentService.findAll());
  }

  @PostMapping
  public ResponseEntity paymentPost() {
    return ResponseEntity.ok(paymentService.save(
        PaymentModel.builder().name("Leo").build()
    ));
  }
}
