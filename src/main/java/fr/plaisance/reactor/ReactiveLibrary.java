package fr.plaisance.reactor;

import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class ReactiveLibrary {

    public Flux<ServerSentEvent<String>> logs(int size) {
        return Flux.range(0, size)
                .map(index -> ServerSentEvent
                        .builder(handleOne(index))
                        .id(Long.toString(index))
                        .build());
    }

    private String handleOne(int n) {
        double duration = 500 * Math.random();
        try {
            Thread.sleep((long) duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return String.format("Traitement de la requête [%3d] en %3.0f ms.", n, duration);
    }
}
