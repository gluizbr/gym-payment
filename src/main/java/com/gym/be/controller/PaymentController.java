package com.gym.be.controller;

import com.gym.be.payment.PaymentService;
import com.gym.be.register.RegisterModel;
import com.gym.be.register.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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

    if (initialDate != null && finalDate != null) {
      return ResponseEntity.ok(paymentService.findByPaymentDateBetween(initialDate, finalDate));
    }

    if (initialDate != null) {
      return ResponseEntity.ok(paymentService.findByPaymentInitialDate(initialDate));
    }

    if (finalDate != null) {
      return ResponseEntity.ok(paymentService.findByPaymentFinalDate(finalDate));
    }

    if (name != null) {
      List<RegisterModel> registerModelList = new ArrayList<>();
      registerModelList.add(registerService.findByName(name));
      return ResponseEntity.ok(paymentService.findByRegister(registerModelList));
    }

    if (modalities != null) {
      return ResponseEntity.ok(
          paymentService.findByRegister(registerService.findByModalities(modalities)));
    }

    if (payed != null) {
      return ResponseEntity.ok(paymentService.findByPayed(payed));
    }

    return ResponseEntity.ok(paymentService.findAll());
  }

  @PostMapping("/pay")
  public ResponseEntity paymentPost(@RequestParam String id) {
    return ResponseEntity.ok(paymentService.pay(id));
  }
}
