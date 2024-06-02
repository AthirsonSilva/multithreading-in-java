package org.multithreading.java.virtualthreads;

import java.util.List;
import java.util.concurrent.StructuredTaskScope;

public class StructuredConcurrency {

    record Shelter(String name) {
    }

    record Dog(String name) {
    }

    record Response(Shelter shelter, List<Dog> dogs) {
    }

    private static Shelter getShelter() {
        return new Shelter("Shelter");
    }

    private static List<Dog> getDogs() {
        return List.of(new Dog("Buddy"), new Dog("Simba"));
    }

    public static void main(String[] args) {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            var dogs = scope.fork(() -> getDogs());
            var cats = scope.fork(() -> getShelter());

            scope.join();

            var response = new Response(cats.get(), dogs.get());

            System.out.println(STR."Response: \{response}");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
