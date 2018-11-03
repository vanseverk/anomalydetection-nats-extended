package be.reactiveprogramming.anomalydetectionnats.processor;

import be.reactiveprogramming.anomalydetectionnats.processor.processor.AnomalyProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan
public class AnomalyProcessorApp {

  public static void main(String[] args) {
    SpringApplication.run(AnomalyProcessorApp.class, args);
  }

}
