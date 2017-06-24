package fr.plaisance.reactor.converter;

import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;

public abstract class FluxConverter {

    public static <T> Flux<ServerSentEvent<T>> sseFlux(Flux<T> flux){
        return flux.map(s -> ServerSentEvent.builder(s).build());
    }
}
