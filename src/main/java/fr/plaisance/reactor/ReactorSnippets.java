package fr.plaisance.reactor;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static java.lang.System.out;

public class ReactorSnippets {

    private static final List<String> words = Arrays.asList(
            "the",
            "quick",
            "brown",
            "fox",
            "jumped",
            "over",
            "the",
            "lazy",
            "dog"
    );

    @AfterEach
    void afterEach() {
        System.out.println();
    }

    @Test
    void just() {
        Flux<String> fewWords = Flux.just("Hello", "World");
        fewWords.subscribe(out::println);
    }

    @Test
    void fromIterable() {
        Flux<String> manyWords = Flux.fromIterable(words);
        manyWords.subscribe(out::println);
    }

    @Test
    void findingMissingLetter() {
        Flux<String> manyLetters = Flux
                .fromIterable(words)
                .flatMap(word -> Flux.fromArray(word.split("")))
                .distinct()
                .sort()
                .zipWith(
                        Flux.range(1, 100),
                        (string, count) -> String.format("%2d. %s", count, string));

        manyLetters.subscribe(out::println);
    }

    @Test
    void restoringMissingLetters() {
        Mono<String> missing = Mono.just("s");
        Flux<String> allLetters = Flux
                .fromIterable(words)
                .flatMap(word -> Flux.fromArray(word.split("")))
                .concatWith(missing)
                .distinct()
                .sort()
                .zipWith(
                        Flux.range(1, 100),
                        (letter, index) -> String.format("%2d. %s", index, letter));
        allLetters.subscribe(out::println);
    }

    @Test
    void shortCircuit() {
        Flux<String> helloPausedWorld = Mono.just("Hello")
                .concatWith(Mono.just("World")
                                .delaySubscription(Duration.ofSeconds(1)));
        helloPausedWorld.subscribe(out::println);
    }

    @Test
    void blocks() {
        Flux<String> helloPausedWorld = Mono.just("Hello")
                .concatWith(Mono.just("World")
                                .delaySubscription(Duration.ofSeconds(1)));
        helloPausedWorld.toStream().forEach(out::println);
    }

    @Test
    void firstEmitting() {
        Mono<String> mono = Mono.just("Ooops I'm late")
                                .delaySubscription(Duration.ofMillis(100));
        Flux<String> flux = Flux.just("Let's get", "the party", "started !")
                                .delayElements(Duration.ofMillis(50));

        Flux.firstEmitting(mono, flux)
            .toIterable()
            .forEach(out::println);
    }
}
