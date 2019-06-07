package com.thecodingpark.lambdas;

import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.function.Consumer;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;
import java.util.function.BinaryOperator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class LambdaTest {

    @Test
    public void supplierTest() {
        Supplier<LocalDate> localDate1 = LocalDate::now;
        Supplier<LocalDate> localDate2 = () -> LocalDate.now();

        assertNotNull(localDate1.get());
        assertNotNull(localDate2.get());

        Supplier<ArrayList<String>> supplier = ArrayList<String>::new;
        assertEquals(0, supplier.get().size());
    }

    @Test
    public void consumerTest() {
        Consumer<String> c1 = System.out::println;
        Consumer<String> c2 = x -> System.out.println(x);

        c1.accept("Markos");
        c2.accept("Markos");
    }

    @Test
    public void biconsumerTest() {
        Map<String, Integer> map = new HashMap<>();
        BiConsumer<String, Integer> b1 = map::put;
        BiConsumer<String, Integer> b2 = (k, v) -> map.put(k, v);

        b1.accept("chicken", 7);
        b2.accept("chick", 1);

        assertEquals(new Integer(7), map.get("chicken"));
        assertEquals(new Integer(1), map.get("chick"));
    }

    @Test
    public void predicateTest() {
        Predicate<String> p1 = String::isEmpty;
        Predicate<String> p2 = x -> x.isEmpty();

        assertTrue(p1.test(""));
        assertTrue(p2.test(""));
    }

    @Test
    public void bipredicateTsest() {
        BiPredicate<String, String> b1 = String::startsWith;
        BiPredicate<String, String> b2 = (string, prefix) -> string.startsWith(prefix);

        assertTrue(b1.test("chicken", "chick"));
        assertTrue(b2.test("chicken", "chick"));
    }

    @Test
    public void function() {
        Function<String, Integer> f1 = String::length;
        Function<String, Integer> f2 = x -> x.length();

        assertEquals(new Integer(5), f1.apply("hello"));
        assertEquals(new Integer(5), f1.apply("hello"));
    }

    @Test
    public void bifunction() {
        BiFunction<String, String, String> b1 = String::concat;
        BiFunction<String, String, String> b2 = (string, toAdd) -> string.concat(toAdd);

        assertEquals("hello world", b1.apply("hello ", "world"));
        assertEquals("hello world", b2.apply("hello ", "world"));
    }

    @Test
    public void unaryOperator() {
        UnaryOperator<String> u1 = String::toUpperCase;
        UnaryOperator<String> u2 = x -> x.toUpperCase();

        assertEquals("HELLO", u1.apply("hello"));
        assertEquals("HELLO", u2.apply("hello"));
    }

    @Test
    public void binaryOperator() {
        BinaryOperator<String> b1 = String::concat;
        BinaryOperator<String> b2 = (string, toAdd) -> string.concat(toAdd);

        assertEquals("hello world", b1.apply("hello ", "world"));
        assertEquals("hello world", b2.apply("hello ", "world"));
    }
}
