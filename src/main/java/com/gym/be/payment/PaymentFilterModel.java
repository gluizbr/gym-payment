package com.gym.be.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentFilterModel {
  String name;
  Date initialDate;
  Date finalDate;
  Boolean payed;
  List<String> modalities;
}
