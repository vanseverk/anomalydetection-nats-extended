package be.reactiveprogramming.anomalydetectionnats.gateway.controller;

import be.reactiveprogramming.anomalydetectionnats.common.event.MeasurementEvent;
import be.reactiveprogramming.anomalydetectionnats.gateway.gateway.MeasurementGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class MeasurementGatewayController {

  private MeasurementGateway measurementGateway;

  @Autowired
  public MeasurementGatewayController(MeasurementGateway measurementGateway) {
    this.measurementGateway = measurementGateway;
  }

  @RequestMapping(method = RequestMethod.POST, path = "measurements")
  public Mono<Void> receiveMeasurem1ent(@RequestBody MeasurementEvent measurementEvent) {
    return measurementGateway.sendMeasurement(measurementEvent);
  }

}
