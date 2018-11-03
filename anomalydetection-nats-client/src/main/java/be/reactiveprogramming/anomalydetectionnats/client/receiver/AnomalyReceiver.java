package be.reactiveprogramming.anomalydetectionnats.client.receiver;

import be.reactiveprogramming.anomalydetectionnats.common.event.AnomalyEvent;
import reactor.core.publisher.Flux;

public interface AnomalyReceiver {

  Flux<AnomalyEvent> streamAnomalies();

}
