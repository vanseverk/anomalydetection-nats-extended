package be.reactiveprogramming.anomalydetectionnats.gateway.gateway;

import be.reactiveprogramming.anomalydetectionnats.common.event.MeasurementEvent;
import be.vanseverk.reactornatsstreaming.ReactiveNatsStreaming;
import be.vanseverk.reactornatsstreaming.Sender;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.nats.streaming.StreamingConnection;
import io.nats.streaming.StreamingConnectionFactory;
import java.io.IOException;
import java.util.Random;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class MeasurementGatewayImpl implements MeasurementGateway {

  private Sender sender;
  private ObjectMapper objectMapper = new ObjectMapper();

  public MeasurementGatewayImpl() throws IOException, InterruptedException {
    StreamingConnection sc = new StreamingConnectionFactory("test-cluster", "client-" + new Random().nextInt()).createConnection();
    sender = ReactiveNatsStreaming.createSender(sc);
  }

  private byte[] toBinary(MeasurementEvent body) {
    try {
      return objectMapper.writeValueAsBytes(body);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException(e);
    }
  }

  @Override
  public Mono<Void> sendMeasurement(MeasurementEvent measurementEvent) {
    return sender.publish("measurements", toBinary(measurementEvent));
  }
}
