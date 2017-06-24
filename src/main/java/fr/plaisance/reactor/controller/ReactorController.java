package fr.plaisance.reactor.controller;

import fr.plaisance.reactor.converter.FluxConverter;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import fr.plaisance.reactor.service.LogService;

import java.time.Duration;

@Controller
public class ReactorController {

    private LogService logService;

    ReactorController(LogService logService) {
        this.logService = logService;
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
        return FluxConverter.sseFlux(logService.logs(size));
    }
}
