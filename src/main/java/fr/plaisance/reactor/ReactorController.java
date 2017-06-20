package fr.plaisance.reactor;

import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * https://github.com/sdeleuze/spring-reactive-playground/blob/master/src/main/java/playground/SseController.java
 * */
@Controller
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


    @GetMapping(value = "logs/{size}", produces = "text/event-stream;charset=UTF-8")
    @ResponseBody
    Flux<ServerSentEvent<String>> logs(@PathVariable Integer size) {
        return reactiveLibrary.logs(size);
    }
}
