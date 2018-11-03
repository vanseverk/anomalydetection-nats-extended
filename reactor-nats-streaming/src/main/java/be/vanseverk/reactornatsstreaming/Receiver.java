package be.vanseverk.reactornatsstreaming;

import io.nats.streaming.Message;
import io.nats.streaming.StreamingConnection;
import io.nats.streaming.SubscriptionOptions;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class Receiver {

  private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

  private final StreamingConnection sc;

  Receiver(StreamingConnection sc) {
    this.sc = sc;
  }

  public Flux<Message> receiveAutoAck(String name) {
    return Flux.create(emitter -> {
      try {
        sc.subscribe(name, message -> emitter.next(message));

        AtomicBoolean cancelled = new AtomicBoolean(false);

        emitter.onDispose(() -> {
          if (cancelled.compareAndSet(false, true)) {
            try {
              sc.close();
            } catch (IOException | TimeoutException | InterruptedException e) {
              LOGGER.warn("Error while closing channel: " + e.getMessage());
            }
          }
        });
      } catch (IOException | InterruptedException | TimeoutException e) {
        LOGGER.error("Received an exception", e);
        emitter.error(e);
      }
    });
  }

  public Flux<Message> replayAutoAck(String name, long startAtSequence) {
    return Flux.create(emitter -> {
      try {
        sc.subscribe(name, message -> emitter.next(message), new SubscriptionOptions.Builder().startAtSequence(startAtSequence).build());

        AtomicBoolean cancelled = new AtomicBoolean(false);

        emitter.onDispose(() -> {
          if (cancelled.compareAndSet(false, true)) {
            try {
              sc.close();
            } catch (IOException | TimeoutException | InterruptedException e) {
              LOGGER.warn("Error while closing channel: " + e.getMessage());
            }
          }
        });
      } catch (IOException | InterruptedException | TimeoutException e) {
        emitter.error(e);
      }
    });
  }

  public Flux<Message> queueRead(String name, String queueGroupName) {
    return Flux.create(emitter -> {
      try {
        sc.subscribe(name, queueGroupName, message -> emitter.next(message));

        AtomicBoolean cancelled = new AtomicBoolean(false);

        emitter.onDispose(() -> {
          if (cancelled.compareAndSet(false, true)) {
            try {
              sc.close();
            } catch (IOException | TimeoutException | InterruptedException e) {
              LOGGER.warn("Error while closing channel: " + e.getMessage());
            }
          }
        });
      } catch (IOException | InterruptedException | TimeoutException e) {
        emitter.error(e);
      }
    });
  }

  public Flux<Message> receiveAutoAck(String foo, Replay replay) {
    return null;
  }

}
