package be.reactiveprogramming.anomalydetectionnats.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan
public class AnomalyClientApp {

  public static void main(String[] args) throws Exception {
    SpringApplication.run(AnomalyClientApp.class, args);
  }

}
