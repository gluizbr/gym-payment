package com.gym.be.payment;

import com.gym.be.register.RegisterModel;
import com.gym.be.register.RegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

  private PaymentRepository paymentRepository;

  private MongoTemplate mongoTemplate;

  private RegisterRepository registerRepository;

  @Autowired
  public PaymentService(PaymentRepository paymentRepository, MongoTemplate mongoTemplate,
      RegisterRepository registerRepository) {
    this.registerRepository = registerRepository;
    this.paymentRepository = paymentRepository;
    this.mongoTemplate = mongoTemplate;
  }

  public PaymentModel save(PaymentModel payment) {
    return paymentRepository.save(payment);
  }

  public List<PaymentModel> findByFilter(PaymentFilterModel filterModel) {
    List<PaymentModel> paymentModels;
    Query dynamicQuery = new Query();
    Criteria register = null;
    if (filterModel.getName() != null) {
      register =
          Criteria.where("register").in(registerRepository.findByName(filterModel.getName()));
    }
    if (filterModel.getModalities() != null) {
      register = register == null ? Criteria.where("register")
          .in(registerRepository.findByModalitiesContains(filterModel.getModalities())) :
          register.andOperator(Criteria.where("register")
              .in(registerRepository.findByModalitiesContains(filterModel.getModalities())));
    }
    if (register != null) {
      dynamicQuery.addCriteria(register);
    }

    Criteria date = null;
    if (filterModel.getInitialDate() != null) {
      date = Criteria.where("paymentDate").gte(filterModel.getInitialDate());
    }
    if (filterModel.getFinalDate() != null) {
      date = date == null ? Criteria.where("paymentDate").lte(filterModel.getFinalDate()) :
          date.andOperator(Criteria.where("paymentDate").lte(filterModel.getFinalDate()));
    }

    if (date != null) {
      dynamicQuery.addCriteria(date);
    }

    if (filterModel.getPayed() != null) {
      Criteria payed = Criteria.where("payed").is(filterModel.getPayed());
      dynamicQuery.addCriteria(payed);
    }

    return mongoTemplate.find(dynamicQuery, PaymentModel.class, "paymentModel");
  }

  public void generatePayment(RegisterModel student) {
    Date day = new Date();
    int actualMonth = day.getMonth();
    while (actualMonth < 12) {
      save(PaymentModel.builder()
          .register(student)
          .paymentDate(new Date(day.getYear(), actualMonth, student.getPaymentDate()))
          .payed(false)
          .build());
      actualMonth++;
    }
  }

  public PaymentModel pay(String id) {
    Optional<PaymentModel> paymentModel = paymentRepository.findById(id);
    if (paymentModel.isPresent()) {
      paymentModel.get().setPayed(true);
      return paymentRepository.save(paymentModel.get());
    }
    return null;
  }
}
