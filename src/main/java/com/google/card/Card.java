package com.google.card;

import java.nio.charset.StandardCharsets;

public class Card {

    private final String init;

    public Card(String init) {
        this.init = init;
        byte[] bytes = init.getBytes(StandardCharsets.UTF_8);
        if (init.length() != bytes.length) {
            throw new RuntimeException("Incorrect init string: " + init);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        //card
        for (int line = 0; line < 13; line++) {
            sb.append(line >= 3 ? line - 3 : " ").append(' ');
            for (int j = 0; j < init.length(); j++) {
                int okLine = MainSolver.map.get(init.charAt(j)).line;
                char ch = Data.ch(init.charAt(j), line);
                ch = ch == 'X' & okLine == line ? '■' : ch;
                sb.append(ch);
            }
            sb.append('\n');
        }
        //numbers of cols
        sb.append("--");
        for (int j = 1; j <= init.length(); j++) {
            sb.append(j % 10);
        }
        sb.append("\n\n");
        //helper
        for (int j = 0; j < init.length(); j++)
            sb.append(Data.ch(init.charAt(j), 0)).append("  ");
        sb.append("\n");
        for (int j = 0; j < init.length(); j++) {
            StringBuilder code = new StringBuilder();
            for (int line = 1; line < 13; line++) {
                if (Data.ch(init.charAt(j), line) == 'X') {
                    if (line == 1) code.append('.');
                    if (line == 2) code.append('-');
                    if (line > 2) code.append(line - 3);
                }
            }
            sb.append(String.format("%-3s", code));
        }
        sb.append("\n");

        return sb.toString();
    }
}
