package com.gym;

import com.gym.fe.UIInit;
import lombok.var;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;

@SpringBootApplication(scanBasePackages = "com.gym")
@EnableWebMvc
public class GymApplication extends UIInit {
  public GymApplication() {
//    Here we need to change to the main Swing application that is on Fe package
//    Comment the line bellow and add the main swing function
    initUI();
  }

  public static void main(String[] args) {

    var ctx = new SpringApplicationBuilder(GymApplication.class)
        .headless(false).run(args);

    EventQueue.invokeLater(() -> {

      var ex = ctx.getBean(GymApplication.class);
      ex.setVisible(true);
    });
  }

}
