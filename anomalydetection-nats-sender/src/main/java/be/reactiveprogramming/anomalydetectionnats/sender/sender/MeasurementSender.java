package be.reactiveprogramming.anomalydetectionnats.sender.sender;

import be.reactiveprogramming.anomalydetectionnats.common.event.MeasurementEvent;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MeasurementSender {

  private final WebClient wc = WebClient.create("http://localhost:8081");

  private final Random r = new Random();

  public Flux<Integer> sendMeasurement() {
    return Flux.range(1, 10000).flatMap(number -> sendMeasurementForNumber(number));
  }

  private Mono<Integer> sendMeasurementForNumber(int number) {
    final MeasurementEvent measurement = new MeasurementEvent(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME), "" + r.nextInt(10), BigDecimal.valueOf(r.nextDouble() * 100));
    return wc.method(HttpMethod.POST).uri("measurements")
        .body(BodyInserters.fromPublisher(Mono.just(measurement), MeasurementEvent.class))
        .exchange().then(Mono.just(number));
  }
}

