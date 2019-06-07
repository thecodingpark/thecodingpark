package com.thecodingpark.lambdas;

import org.junit.Test;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class OptionalTest {

    @Test
    public void optionalEmpty() {
        Optional<Integer> optional = Optional.empty();

        assertFalse(optional.isPresent());
        assertEquals(new Integer(15), optional.orElse(15));
    }

    @Test
    public void optionalOf() {
        Optional<String> optional = Optional.of("hello");

        assertTrue(optional.isPresent());
        assertEquals("hello", optional.get());
    }

    @Test
    public void ifPresent() {
        Optional<String> optional = Optional.of("hello");

        optional.ifPresent(s -> {
            assertTrue(optional.isPresent());
            assertEquals("hello", optional.get());
        });
    }

    @Test(expected = NoSuchElementException.class)
    public void ofNullable() {
        Optional<String> optional = Optional.ofNullable(null);

        assertFalse(optional.isPresent());
        optional.get();
    }
}
