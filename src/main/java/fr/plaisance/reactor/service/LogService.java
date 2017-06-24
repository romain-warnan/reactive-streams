package fr.plaisance.reactor.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class LogService {

    public Flux<String> logs(int size) {
        return Flux
                .range(0, size)
                .map(this::handleOne);
    }

    private String handleOne(int n) {
        double duration = 2_000 * Math.random();
        try {
            Thread.sleep((long) duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(duration > 1_950) {
            return String.format("[ERROR] Erreur lors du traitement de l’enregistrement [%3d].", n + 1);
        }
        return String.format("[INFO]  Traitement de l’enregistrement [%3d] en %3.0f ms.", n + 1, duration);
    }
}
