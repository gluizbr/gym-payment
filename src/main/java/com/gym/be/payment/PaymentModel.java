package com.gym.be.payment;

import com.gym.be.register.RegisterModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentModel {
  @Id
  private String id;
  private RegisterModel register;
  private Date paymentDate;
}
