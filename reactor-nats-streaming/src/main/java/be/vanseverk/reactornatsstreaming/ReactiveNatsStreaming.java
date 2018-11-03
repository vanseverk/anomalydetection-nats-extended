package be.vanseverk.reactornatsstreaming;

import io.nats.streaming.StreamingConnection;

public class ReactiveNatsStreaming {

  public static Receiver createReceiver(StreamingConnection sc) {
    return new Receiver(sc);
  }

  public static Sender createSender(StreamingConnection sc) {
    return new Sender(sc);
  }
}
