package com.google.card;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class DataTest {


    @ParameterizedTest
    //C   ON    TASK                                    D
    @ValueSource(strings = {"T=A", "A=S", "S=K", "S=-", "S=3", "O=N", "C=D", "1=2",})
    void positiveNextSteps(String testPair) {
        String[] param = testPair.split("=");
        long a = Arrays.stream(Data.nextSteps(param[0].charAt(0)))
                .filter(s -> s != null && s.contains(param[1]))
                .count();
        long e = 1;
        assertEquals(e, a);
    }

    @ParameterizedTest
    //C   ON    TASK                                    D
    @ValueSource(strings = {"T!=T", "9!=7", "8!=6", "7!=5", "6!=4", "5!=3", "S!=4"})
    void negativeNextSteps(String testPair) {
        String[] param = testPair.split("!=");
        long a = Arrays.stream(Data.nextSteps(param[0].charAt(0)))
                .filter(s -> s != null && s.contains(param[1]))
                .count();
        long e = 0;
        assertEquals(e, a);
    }

    @Test
    void rndStartLine() {
        char[] chars = Data.alphabet().toCharArray();
        for (int i = 0; i < 10; i++) {
            for (char c : chars) {
                int line = Data.rndStartLine(c);
                assertTrue(line > 0);
            }
        }
    }

    @Test
    void rndNextLine() {
        char[] chars = Data.alphabet().toCharArray();
        for (int i = 0; i < 10; i++) {
            for (char c : chars) {
                int line = Data.rndStartLine(c);
                String next = Data.oneStep(c, line);
                int j = Data.rnd.nextInt(next.length());
                char c2 = next.charAt(j);
                int line2 = Data.rndNextLine(c2, line);
                assertTrue(line2 == line + 1 || line2 == line - 1);
            }
        }
    }
}