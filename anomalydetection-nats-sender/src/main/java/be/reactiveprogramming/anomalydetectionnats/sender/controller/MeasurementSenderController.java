package be.reactiveprogramming.anomalydetectionnats.sender.controller;

import be.reactiveprogramming.anomalydetectionnats.sender.sender.MeasurementSender;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class MeasurementSenderController {

  private final MeasurementSender measurementSender;

  public MeasurementSenderController(MeasurementSender measurementSender) {
    this.measurementSender = measurementSender;
  }

  @GetMapping(value = "/sendMeasurements", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  @ResponseBody
  public Flux<Integer> eventStream() {
    return measurementSender.sendMeasurement();
  }

}