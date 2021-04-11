package com.google.card;

import java.util.HashMap;
import java.util.Random;

public class Data {

    public static final String[] mapData = {
            "&-0123456789ABCDEFGHIJKLMNOPQR/STUVWXYZ:#@'=\"[.<(+|]$*);^\\,%_>? ",
            //вверх смещен поэтому см. комментарий (но первый символ & а не /)
            //-0123456789ABCDEFGHIJKLMNOPQR/STUVWXYZ:#@'="[.<(+|]$*);^\,%_>?
            "X           XXXXXXXXX                        XXXXXX             ",//12
            " X                   XXXXXXXXX                     XXXXXX       ",//11
            "  X                           XXXXXXXXX                  XXXXXX ",//0
            "   X        X        X        X                                 ",//1
            "    X        X        X        X       X     X     X     X      ",//2
            "     X        X        X        X       X     X     X     X     ",//3
            "      X        X        X        X       X     X     X     X    ",//4
            "       X        X        X        X       X     X     X     X   ",//5
            "        X        X        X        X       X     X     X     X  ",//6
            "         X        X        X        X       X     X     X     X ",//7
            "          X        X        X        X XXXXXXXXXXXXXXXXXXXXXXXX ",//8
            "           X        X        X        X                         ",//9

    };
    static Random rnd = new Random();

    private static final String[][] next = new String[mapData[0].length()][mapData.length];
    private static final HashMap<Character, Integer> map = new HashMap<>();

    static {
        String chars = mapData[0];
        for (int i = 0; i < chars.length(); i++) {
            map.put(chars.charAt(i), i); //это буква и ее индекс в mapData
        }
        StringBuilder sb = new StringBuilder();
        for (int col = 0; col < chars.length(); col++) {
            for (int line = 1; line <= 12; line++) {
                if (mapData[line].charAt(col) == 'X') {
                    sb.setLength(0);
                    for (int col2 = 0; col2 < chars.length(); col2++) {
                        if (col2 == col) continue;
                        if ((line > 1 && mapData[line - 1].charAt(col2) == 'X')
                                ||
                                (line < 12 && mapData[line + 1].charAt(col2) == 'X'))
                            sb.append(mapData[0].charAt(col2));
                    }
                    next[col][line] = sb.toString();
                }
            }
        }

    }

    static int indexChar(char c) {
        return map.get(c);
    }

    static char ch(char symbol, int line) {
        Integer pos = map.get(symbol);
        if (pos != null) {
            char ch = mapData[line].charAt(pos);
            if (ch == ' ' && line > 0) {
                ch = '.';
            }
            return ch;
        }
        throw new RuntimeException("Incorrect char: " + symbol);
    }

    static String alphabet() {
        return mapData[0].replace(" ", "");
    }

    static String[] nextSteps(char symbol) {
        String[] nextSteps = new String[mapData.length];
        int index = map.get(symbol);
        //12
        System.arraycopy(next[index], 1, nextSteps, 1, mapData.length - 1);
        return nextSteps;
    }

    static String oneStep(char symbol, int line) {
        int index = map.get(symbol);
        return next[index][line];
    }

    static int rndStartLine(char symbol) {
        int index = map.get(symbol);
        while (true) {
            int line = rnd.nextInt(mapData.length - 1) + 1;
            if (mapData[line].charAt(index) == 'X') {
                return line;
            }
        }
    }

    static int rndNextLine(char symbol, int currentLine) {
        int index = map.get(symbol);
        int up = currentLine > 1 && mapData[currentLine - 1].charAt(index) == 'X'
                ? currentLine - 1
                : -1;
        int down = currentLine < mapData.length - 1 && mapData[currentLine + 1].charAt(index) == 'X'
                ? currentLine + 1
                : -1;
        //if (up==-1 && down==-1) return currentLine; - некорректно (just hide many problems)
        if (up == -1) return down;
        if (down == -1) return up;
        return rnd.nextInt(2) > 0 ? up : down;
    }
}
