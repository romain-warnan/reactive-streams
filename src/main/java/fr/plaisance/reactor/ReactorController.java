package fr.plaisance.reactor;

import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * https://github.com/sdeleuze/spring-reactive-playground/blob/master/src/main/java/playground/SseController.java
 * */
@RestController
public class ReactorController {

    private ReactiveLibrary reactiveLibrary;

    ReactorController(ReactiveLibrary reactiveLibrary) {
        this.reactiveLibrary = reactiveLibrary;
    }

    @GetMapping("hello/{name}")
    Mono<String> hello(@PathVariable String name) {
        return Mono
                .just(name)
                .map(string -> String.format("Hello %s!", string));
    }

    @GetMapping("helloDelay/{name}")
    Mono<String> helloDelay(@PathVariable String name) {
        return Mono
                .just(name)
                .delaySubscription(Duration.ofSeconds(5))
                .map(string -> String.format("Hello %s!", string));
    }


    @GetMapping(value = "logs/{size}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<ServerSentEvent<String>> logs(@PathVariable Integer size) {
        return reactiveLibrary.logs(size);
    }

    @GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<ServerSentEvent<String>> events() {
        return Flux
                .interval(Duration.ofSeconds(1))
                .map(millis -> ServerSentEvent
                        .builder("foo\nbar")
                        .comment("bar\nbaz")
                        .id(Long.toString(millis))
                        .build());
    }
}
