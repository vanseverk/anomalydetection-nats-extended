package be.reactiveprogramming.anomalydetectionnats.client.controller;

import be.reactiveprogramming.anomalydetectionnats.client.service.AnomalyService;
import be.reactiveprogramming.anomalydetectionnats.common.event.AnomalyEvent;
import java.util.Optional;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class AnomalyTrackerController {

  private final AnomalyService anomalyService;

  public AnomalyTrackerController(AnomalyService anomalyService) {
    this.anomalyService = anomalyService;
  }

  @GetMapping(value = "/anomalytracker/anomalies", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  @ResponseBody
  public Flux<AnomalyEvent> eventStream() {
    return anomalyService.streamAnomalies(Optional.empty());
  }

  @GetMapping(value = "/anomalytracker/{deviceId}/anomalies", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  @ResponseBody
  public Flux<AnomalyEvent> eventStream(@PathVariable("deviceId") String deviceId) {
    return anomalyService.streamAnomalies(Optional.of(deviceId));
  }

}