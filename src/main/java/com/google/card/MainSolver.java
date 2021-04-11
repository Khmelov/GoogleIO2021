package com.google.card;

import java.util.*;
import java.util.stream.IntStream;

public class MainSolver {

    private static final String[] mapData = Data.mapData;


    static final Map<Character, Vertex> map = new HashMap<>();

    public static void main(String[] args) {
        String word = "C  ON    TASK                                    D";
        if (args.length > 0) {
            StringJoiner restorer = new StringJoiner(" ", "", "");
            for (String arg : args) {
                restorer.add(arg);
            }
            word = restorer.toString();
        }
        System.out.printf("%2$s\n  The string for card (you can set it in args):\n \"%s\"\n%2$s\n",
                word, "-".repeat(2 + word.length())
        );
        for (int i = 0; i < 10000000; i++) {
            String s = tryFind(word);
            if (word.length() == s.length()) {
                Card card = new Card(s);
                System.out.println("\n" + card);
                break;
            }
        }
    }

    public static String tryFind(String word) {
        StringBuilder out = new StringBuilder();
        Map<Character, Vertex> map = build();
        Queue<Vertex> q = new ArrayDeque<>();
        Vertex current = map.get(word.charAt(0));
        current.dist = 0;
        q.add(current);
        int line = 6;//Data.rndStartLine(current.character);
        while (q.size() > 0 && current.dist < word.length() - 1) {
            current = q.poll();
            current.visited = true;
            ArrayList<Vertex> vertices = new ArrayList<>(current.edges);
            //тут не все подряд, а только те что по диагонали от текущей line - ее добавить надо
            char nextLetter = current.dist + 1 < word.length() ? word.charAt(current.dist + 1) : ' ';
            char currentLetter = current.dist > 0 ? word.charAt(current.dist) : '~';
            if (nextLetter != ' ') {
                Vertex next = map.get(nextLetter);
                if (currentLetter == ' ' && rndLine(next, line) < 0) {
                    break; //текущая вершина не попала в хвост
                }
                int nextLine = Data.rndStartLine(nextLetter);
                if (Math.abs(line - nextLine) > 1) {
                    q.add(current);
                    continue;
                }
                line = newLine(out, q, current, next, nextLine);
                continue;
            }
            //ищем продолжение
            Collections.shuffle(vertices);
            for (Vertex next : vertices) {
                if (word.indexOf(next.character) < 0 && next.dist < current.dist && !next.visited) {
                    int nextLine = rndLine(next, line);
                    if (nextLine >= 0) {
                        line = newLine(out, q, current, next, nextLine);
                        break;
                    }
                }
            }
        }
        //System.out.println(out);
        if (out.length() > max) {
            System.out.println(out);
            max = out.length();
        }
        return out.toString();
    }

    private static int max = 0;

    static Map<Character, Vertex> build() {
        if (map.size() > 0) { //уже есть, просто сбросим visited и все
            for (Vertex vertex : map.values()) {
                vertex.visited = false;
                vertex.dist = -1;
                vertex.line = -1;
            }
            return map;
        }
        String chars = mapData[0];
        //иначе заполняем карту
        IntStream.range(0, chars.length()).forEach(i -> {
            Vertex vertex = Vertex.of(chars.charAt(i));
            map.put(chars.charAt(i), vertex); //это буква и ее индекс в mapData
        });
        for (int col = 0; col < chars.length(); col++) { //перебор всех символов
            Vertex current = map.get(mapData[0].charAt(col));
            for (int col2 = 0; col2 < chars.length(); col2++) {//поиск ребер
                //if (col2 == col) continue;
                boolean collision = false;
                for (int line = 1; line <= 12; line++) {
                    if (mapData[line].charAt(col) == 'X' && mapData[line].charAt(col2) == 'X') {
                        collision = true;
                        break;
                    }
                }
                if (collision) continue;
                for (int line = 1; line <= 12; line++) {
                    if (mapData[line].charAt(col) == 'X') {
                        if ((line > 1 && mapData[line - 1].charAt(col2) == 'X')
                                ||
                                (line < 12 && mapData[line + 1].charAt(col2) == 'X'))
                            current.edges.add(map.get(mapData[0].charAt(col2)));
                    }
                }
            }
        }
        return map;
    }

    private static int newLine(StringBuilder out, Queue<Vertex> q, Vertex current, Vertex next, int nextLine) {
        next.dist = current.dist + 1;
        next.line = nextLine;
        q.add(next);
        out.append(current.character);
        return nextLine;
    }

    static int rndLine(Vertex next, int line) {
        return Data.rndNextLine(next.character, line);
    }
}
