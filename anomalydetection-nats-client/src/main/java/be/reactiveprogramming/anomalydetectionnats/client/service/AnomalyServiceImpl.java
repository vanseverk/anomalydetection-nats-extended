package be.reactiveprogramming.anomalydetectionnats.client.service;

import be.reactiveprogramming.anomalydetectionnats.common.event.AnomalyEvent;
import be.reactiveprogramming.anomalydetectionnats.client.receiver.AnomalyReceiver;
import java.time.Duration;
import java.util.Optional;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class AnomalyServiceImpl implements AnomalyService {

  private final Flux<AnomalyEvent> anomalyFlux;

  public AnomalyServiceImpl(AnomalyReceiver anomalyReceiver) {
    anomalyFlux = anomalyReceiver.streamAnomalies().cache(Duration.ofMinutes(30));
    anomalyFlux.subscribe();
  }

  public Flux<AnomalyEvent> streamAnomalies(Optional<String> deviceId) {
    if(deviceId.isPresent()) {
      return anomalyFlux.share().filter(e -> e.getDeviceId().equals(deviceId.get()));
    } else {
      return anomalyFlux.share();
    }
  }
}
