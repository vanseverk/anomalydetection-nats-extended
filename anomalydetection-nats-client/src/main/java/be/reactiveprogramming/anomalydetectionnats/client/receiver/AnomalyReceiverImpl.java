package be.reactiveprogramming.anomalydetectionnats.client.receiver;

import be.reactiveprogramming.anomalydetectionnats.common.event.AnomalyEvent;
import be.vanseverk.reactornatsstreaming.ReactiveNatsStreaming;
import be.vanseverk.reactornatsstreaming.Receiver;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.nats.streaming.Message;
import io.nats.streaming.StreamingConnection;
import io.nats.streaming.StreamingConnectionFactory;
import java.io.IOException;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class AnomalyReceiverImpl implements AnomalyReceiver {

  private Receiver receiver;
  private ObjectMapper objectMapper = new ObjectMapper();

  private static final Logger LOGGER = LoggerFactory.getLogger(AnomalyReceiverImpl.class);

  public AnomalyReceiverImpl() throws IOException, InterruptedException {
    StreamingConnection sc = new StreamingConnectionFactory("test-cluster", "client-" + new Random().nextInt()).createConnection();
    receiver = ReactiveNatsStreaming.createReceiver(sc);
  }

  @Override
  public Flux<AnomalyEvent> streamAnomalies() {
    return receiver.receiveAutoAck("anomalies")
        .map(m -> fromBinary(m));
  }

  private AnomalyEvent fromBinary(Message m) {
    try {
      return objectMapper.readValue(m.getData(), AnomalyEvent.class);
    } catch (Exception e) {
      throw new IllegalArgumentException(e);
    }
  }
}
