package be.reactiveprogramming.anomalydetectionnats.processor.processor;

import be.reactiveprogramming.anomalydetectionnats.common.event.AnomalyEvent;
import be.reactiveprogramming.anomalydetectionnats.common.event.MeasurementEvent;
import be.vanseverk.reactornatsstreaming.ReactiveNatsStreaming;
import be.vanseverk.reactornatsstreaming.Receiver;
import be.vanseverk.reactornatsstreaming.Sender;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.nats.streaming.Message;
import io.nats.streaming.StreamingConnection;
import io.nats.streaming.StreamingConnectionFactory;
import java.io.IOException;
import java.util.Random;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AnomalyProcessor {

  private ObjectMapper objectMapper = new ObjectMapper();

  public AnomalyProcessor() throws IOException, InterruptedException {
    StreamingConnection sc = new StreamingConnectionFactory("test-cluster", "client-" + new Random().nextInt()).createConnection();
    Sender sender = ReactiveNatsStreaming.createSender(sc);
    Receiver receiver = ReactiveNatsStreaming.createReceiver(sc);

    receiver.receiveAutoAck("measurements")
        .flatMap(msg -> handleMeasurement(fromBinary(msg)))
        .flatMap(ae -> sender.publish("anomalies", toBinary(ae)))
        .subscribe();

  }

  private Mono<AnomalyEvent> handleMeasurement(MeasurementEvent me) {
    if (me.getMeasurementValue().longValue() > 20) {
      return Mono.empty();
    } else {
      System.out.println("Measurement is anomaly for device " + me.getDeviceId());
      return Mono.just(new AnomalyEvent(me.getTimestamp(), me.getDeviceId(), "Measurement of " + me.getMeasurementValue().longValue()));
    }
  }

  private byte[] toBinary(AnomalyEvent body) {
    try {
      return objectMapper.writeValueAsBytes(body);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException(e);
    }
  }

  private MeasurementEvent fromBinary(Message m) {
    try {
      return objectMapper.readValue(m.getData(), MeasurementEvent.class);
    } catch (Exception e) {
      throw new IllegalArgumentException(e);
    }
  }
}
