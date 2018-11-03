package be.reactiveprogramming.anomalydetectionnats.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan
public class AnomalyGatewayApp {

  public static void main(String[] args) throws Exception {
    SpringApplication.run(AnomalyGatewayApp.class, args);
  }

}
