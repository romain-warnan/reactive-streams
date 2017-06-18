package fr.plaisance.reactor;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

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
    void afterEach(){
        System.out.println();
    }

    @Test
    void just() {
        Flux<String> fewWords = Flux.just("Hello", "World");
        fewWords.subscribe(System.out::println);
    }

    @Test
    void fromIterable() {
        Flux<String> manyWords = Flux.fromIterable(words);
        manyWords.subscribe(System.out::println);
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

        manyLetters.subscribe(System.out::println);
    }
}
