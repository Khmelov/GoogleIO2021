package com.google.card;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VertexTest {

    @Test
    void of() {
        String alphabet = Data.alphabet();
        System.out.println(alphabet);
        int eMaskZ = 0b100000000100;
        Vertex z = Vertex.of('Z');
        int aMaskZ = z.mask;
        assertEquals(eMaskZ, aMaskZ);
    }

    @ParameterizedTest
    @ValueSource(strings = {"A&"})
    void containBites(String string) {
        Vertex first = Vertex.of(string.charAt(0));
        Vertex second = Vertex.of(string.charAt(1));
        assertTrue(first.containBites(second));
    }
}