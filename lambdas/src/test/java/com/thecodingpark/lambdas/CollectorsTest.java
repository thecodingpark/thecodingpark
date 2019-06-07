package com.thecodingpark.lambdas;

import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Set;
import java.util.Optional;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CollectorsTest {

    @Test
    public void joining() {
        Stream<String> vehicles = Stream.of("car", "boat", "plane");
        String result = vehicles.collect(Collectors.joining(", "));

        assertEquals("car, boat, plane", result);
    }

    @Test
    public void averagingInt() {
        Stream<String> vehicles = Stream.of("car", "boat", "plane");
        Double result = vehicles.collect(Collectors.averagingInt(String::length));

        assertEquals(4.0, result.doubleValue(), 0.101);
    }

    @Test
    public void collectingInMaps() {
        Stream<String> vehicles = Stream.of("car", "boat", "plane");
        Map<String, Integer> map = vehicles.collect(Collectors.toMap(string -> string, String::length));

        assertTrue(map.containsKey("car"));
        assertTrue(map.containsKey("boat"));
        assertTrue(map.containsKey("plane"));

        assertEquals(3, map.get("car").longValue());
        assertEquals(4, map.get("boat").longValue());
        assertEquals(5, map.get("plane").longValue());
    }

    @Test
    public void collectingInMergedMaps() {
        Stream<String> vehicles = Stream.of("car", "boat", "plane", "bike");
        Map<Integer, String> map = vehicles.collect(Collectors.toMap(
                String::length, k -> k, (s1, s2) -> s1 + "," + s2
        ));

        assertEquals("{3=car, 4=boat,bike, 5=plane}", map.toString());
        assertEquals("class java.util.HashMap", map.getClass().toString());
    }

    @Test
    public void collectingInTreeMap() {
        Stream<String> vehicles = Stream.of("car", "boat", "plane", "bike");
        TreeMap<Integer, String> map = vehicles.collect(Collectors.toMap(
                String::length, k -> k, (s1, s2) -> s1 + "," + s2, TreeMap::new));

        assertEquals("{3=car, 4=boat,bike, 5=plane}", map.toString());
        assertEquals("class java.util.TreeMap", map.getClass().toString());
    }

    @Test
    public void collectingByGroupingInList() {
        Stream<String> vehicles = Stream.of("car", "boat", "plane", "bike");
        Map<Integer, List<String>> map = vehicles.collect(Collectors.groupingBy(String::length));

        assertEquals("{3=[car], 4=[boat, bike], 5=[plane]}", map.toString());
    }

    @Test
    public void collectingByGroupingInSet() {
        Stream<String> vehicles = Stream.of("car", "boat", "plane", "bike");
        Map<Integer, Set<String>> map = vehicles.collect(Collectors.groupingBy(String::length, Collectors.toSet()));

        assertEquals("{3=[car], 4=[boat, bike], 5=[plane]}", map.toString());
    }

    @Test
    public void collectingByGroupingInTreeMap() {
        Stream<String> vehicles = Stream.of("car", "boat", "plane", "bike");
        TreeMap<Integer, Set<String>> map = vehicles.collect(Collectors.groupingBy(String::length, TreeMap::new, Collectors.toSet()));

        assertEquals("{3=[car], 4=[boat, bike], 5=[plane]}", map.toString());
    }

    @Test
    public void collectingByGroupingInTreeMapAndList() {
        Stream<String> vehicles = Stream.of("car", "boat", "plane", "bike");
        TreeMap<Integer, List<String>> map = vehicles.collect(Collectors.groupingBy(String::length, TreeMap::new, Collectors.toList()));

        assertEquals("{3=[car], 4=[boat, bike], 5=[plane]}", map.toString());
    }

    @Test
    public void collectingByPartitioning() {
        Stream<String> vehicles = Stream.of("car", "boat", "plane", "bike");
        Map<Boolean, List<String>> map = vehicles.collect(Collectors.partitioningBy(s -> s.length() <= 3));

        assertEquals("{false=[boat, plane, bike], true=[car]}", map.toString());
    }

    @Test
    public void collectingByPartitioningToSet() {
        Stream<String> vehicles = Stream.of("car", "boat", "plane", "bike");
        Map<Boolean, Set<String>> map = vehicles.collect(Collectors.partitioningBy(s -> s.length() <= 10, Collectors.toSet()));

        assertEquals("{false=[], true=[plane, car, boat, bike]}", map.toString());
    }

    @Test
    public void collectingByCounting() {
        Stream<String> vehicles = Stream.of("car", "boat", "plane", "bike");
        Map<Integer, Long> map = vehicles.collect(Collectors.groupingBy(String::length, Collectors.counting()));

        assertEquals("{3=1, 4=2, 5=1}", map.toString());
    }

    @Test
    public void collectingByGroupingAndMapping() {
        Stream<String> vehicles = Stream.of("car", "boat", "plane", "bike");

        Map<Integer, Optional<Character>> map = vehicles.collect(
                Collectors.groupingBy(
                        String::length,
                        Collectors.mapping(s -> s.charAt(0),
                                Collectors.minBy((Comparator<Character>)Comparator.naturalOrder())
                        )
                )
        );

        assertEquals("{3=Optional[c], 4=Optional[b], 5=Optional[p]}", map.toString());
    }

    @Test
    public void mapping() {
        Stream<String> vehicles = Stream.of("car", "boat", "plane");

//        Optional<Integer> maxLength = ohMy
//                .collect(
//                      Collectors.mapping(
//                          s -> s.length(),
//                          Collectors.maxBy(Integer::compareTo)
//                      )
//                );

//        Optional<Integer> maxLength = ohMy
//                .map(String::length).collect(Collectors.maxBy(Integer::compareTo));

        Optional<Integer> maxLength = vehicles
                .map(String::length)
                .max(Integer::compareTo);

        assertEquals(5, maxLength.get().longValue());
    }


}
