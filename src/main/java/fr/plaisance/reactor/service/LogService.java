package fr.plaisance.reactor.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class LogService {

    public Flux<String> logs(int size) {
        return Flux.range(0, size).map(this::handleOne);
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
