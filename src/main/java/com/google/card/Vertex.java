package com.google.card;

import java.util.HashSet;
import java.util.Set;

class Vertex {

    private static final String[] mapData = Data.mapData;

    final Character character;
    final int col;
    final Set<Vertex> edges;
    final int mask;

    // это для обхода нужно
    int dist = -1;
    boolean visited;
    public int line;

    private Vertex(Character character, int col) {
        this.character = character;
        this.col = col;
        edges = new HashSet<>();
        //битовое представление, чем ниже тем старше бит.
        int bit = 1;
        int mask = 0;
        for (int i = 1; i <= 12; i++, bit = bit * 2) {
            if (mapData[i].charAt(col) == 'X') {
                mask = mask | bit;
            }
        }
        this.mask = mask;
    }

    public static Vertex of(char c) {
        int indexChar = Data.indexChar(c);
        return new Vertex(c, indexChar);
    }

    boolean containBites(Vertex lessBitesVertex) {
        return (this.mask & lessBitesVertex.mask) == lessBitesVertex.mask;
    }


    @Override
    public String toString() {
        return character +
                ":" + col +
                "=" +
                edges
                        .stream()
                        .map(vertex -> vertex.character + "")
                        .reduce((v1, v2) -> "" + v1 + v2)
                        .orElse("");
    }
}
