package com.thecodingpark.lambdas;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class StreamsIntermediateOperations {

    @Test
    public void filter() {
        Stream<String> stream = Stream.of("car", "plane", "boat");
        String result = stream.filter(x -> x.startsWith("p"))
                .findFirst().get();

        assertEquals("plane", result);
    }

    @Test
    public void distinct() {
        Stream<String> stream = Stream.of("car", "plane", "car", "boat");
        String result = stream
                .distinct()
                .reduce("", (stringFormed, character) -> stringFormed + character);

        assertEquals("carplaneboat", result);
    }

    @Test
    public void skipLimit() {
        Stream<Integer> stream = Stream.iterate(1, n -> n + 1);
        List<Integer> result = stream.skip(5).limit(2).collect(Collectors.toList());

        assertEquals(2, result.size());
        assertEquals(6, result.get(0).intValue());
        assertEquals(7, result.get(1).intValue());
    }

    @Test
    public void map() {
        Stream<String> stream = Stream.of("car", "boat", "plane");
        List<Integer> result = stream.map(String::length).collect(Collectors.toList());

        assertEquals(3, result.size());
        assertEquals(3, result.get(0).intValue());
        assertEquals(4, result.get(1).intValue());
        assertEquals(5, result.get(2).intValue());
    }

    @Test
    public void flatmap() {
        List<String> zero = Arrays.asList();
        List<String> one = Arrays.asList("car");
        List<String> two = Arrays.asList("boat", "plane");
        Stream<List<String>> vehicles = Stream.of(zero, one, two);

        List<String> response = vehicles.flatMap(Collection::stream).collect(Collectors.toList());

        assertEquals(3, response.size());
        assertEquals("car", response.get(0));
        assertEquals("boat", response.get(1));
        assertEquals("plane", response.get(2));
    }

    @Test
    public void sorted() {
        Stream<String> stream = Stream.of("car ", "boat ");
        String result = stream.sorted().reduce("", (res, string) -> res + string);

        assertEquals("boat car ", result);
    }

    @Test
    public void sortedComparator() {
        Stream<String> stream = Stream.of("kia ", "mercedez ");
        String result = stream.sorted(Comparator.reverseOrder()).reduce("", (res, string) -> res + string);

        assertEquals("mercedez kia ", result);
    }

    @Test
    public void peek() {
        Stream<String> stream = Stream.of("black bear", "brown bear", "grizzly");
        long count = stream.filter(s -> s.startsWith("g"))
                .peek(System.out::println).count(); // grizzly
        System.out.println(count); // 1
    }

}
