package com.thecodingpark.lambdas;

import org.junit.Test;

import java.util.Optional;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import static org.hamcrest.CoreMatchers.hasItems;

public class StreamsTerminalOperations {
    @Test
    public void count() {
        Stream<String> vehicles = Stream.of("car", "boat", "plane");
        assertEquals(3, vehicles.count());
    }

    @Test
    public void mixMax() {
        Stream<String> vehicles = Stream.of("car", "boat", "plane");
        Optional<String> min = vehicles.min((s1, s2) -> s1.length() - s2.length());
        assertEquals("car", min.get());
    }

    @Test
    public void findAny() {
        Stream<String> vehicles = Stream.of("car", "boat", "plane");
        Stream<String> infinite = Stream.generate(() -> "chimp");

        assertEquals("car", vehicles.findAny().get());
        assertEquals("chimp", infinite.findAny().get());
    }

    @Test
    public void allAnyNoneMatch() {
        List<String> list = Arrays.asList("monkey", "2", "chimp");
        Stream<String> infinite = Stream.generate(() -> "chimp");

        Predicate<String> predicate = string -> Character.isLetter(string.charAt(0));

        assertTrue(list.stream().anyMatch(predicate));
        assertFalse(list.stream().allMatch(predicate));
        assertFalse(list.stream().noneMatch(predicate));
        assertTrue(infinite.anyMatch(predicate));
    }

    @Test
    public void forEach() {
        Stream<String> vehicles = Stream.of("car", "boat", "plane");
        List<String> list = new ArrayList<>();

        vehicles.forEach(str -> list.add(str));

        assertEquals("car", list.get(0));
        assertEquals("boat", list.get(1));
        assertEquals("plane", list.get(2));
    }

    @Test
    public void reduce() {
//        1st Approach -----------------------------------------------
//        String[] array = new String[] { "w", "o", "l", "f" };
//        String result = "";
//
//        for (String s: array)
//            result = result + s;
//
//        System.out.println(result);

//        2nd Approach -----------------------------------------------
//        Stream<String> stream = Stream.of("w", "o", "l", "f");
//        String word = stream.reduce("", (stringFormed, character) -> stringFormed + character);

//      3rd Approach -----------------------------------------------
        Stream<String> stream = Stream.of("w", "o", "l", "f");
        String word = stream.reduce("", String::concat);

        assertEquals("wolf", word);
    }

    @Test
    public void collect() {
        Stream<String> stream = Stream.of("w", "o", "l", "f");
        StringBuilder word = stream.collect(StringBuilder::new,
                StringBuilder::append, StringBuilder::append);

        assertEquals(new StringBuilder("wolf").toString(), word.toString());
    }

    @Test
    public void collectCollection() {
        Stream<String> stream = Stream.of("w", "o", "l", "f");
        //TreeSet<String> set = stream.collect(CollectorsTest.toCollection(TreeSet::new));
        Set<String> set = stream.collect(Collectors.toSet());

        assertThat(set, hasItems("f", "l", "o", "w"));
    }

}
