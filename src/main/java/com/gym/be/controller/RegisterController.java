package com.gym.be.controller;

import com.gym.be.register.RegisterModel;
import com.gym.be.register.RegisterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity paymentPost(@RequestParam String id,
                                      @RequestParam String name,
                                      @RequestParam List<String> modalities,
                                      @RequestParam Integer paymentDate,
                                      @RequestParam Float value) {
        return ResponseEntity.ok(registerService.save(
                RegisterModel.builder()
                        .id(id)
                        .name(name)
                        .modalities(modalities)
                        .paymentDate(paymentDate)
                        .value(value)
                        .build()
        ));
    }
}
