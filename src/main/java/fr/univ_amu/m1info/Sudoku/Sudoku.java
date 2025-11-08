package fr.univ_amu.m1info.Sudoku;
import java.util.Arrays;

public class Sudoku {
    private final int n;          // racine de la taille des blocs (n=3 pour 9x9)
    private final int size;       // n^2 (9 pour 9x9)
    private final int[][] grid;   // 0 = vide, 1..size sinon

    public Sudoku(int n, int[][] grid) {
        this.n = n;
        this.size = n * n;
        this.grid = grid;
    }

    public static Sudoku fromLinearString(int n, String s81) {
        int size = n * n;
        if (s81.length() != size * size)
            throw new IllegalArgumentException("Expected " + (size * size) + " chars.");
        int[][] g = new int[size][size];
        int k = 0;
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                char ch = s81.charAt(k++);
                if (ch == '.') g[r][c] = 0;
                else {
                    int d = ch - '0';
                    if (d < 1 || d > size) throw new IllegalArgumentException("Bad digit: " + ch);
                    g[r][c] = d;
                }
            }
        }
        return new Sudoku(n, g);
    }

    public int getN() { return n; }
    public int getSize() { return size; }
    public int[][] getGrid() { return grid; }
    public int get(int r, int c) { return grid[r][c]; }
    public void set(int r, int c, int v) { grid[r][c] = v; }

    public String toLinearString() {
        StringBuilder sb = new StringBuilder(size * size);
        for (int[] row : grid)
            for (int v : row) sb.append(v == 0 ? '.' : (char)('0' + v));
        return sb.toString();
    }

    public void print() {
        int s = size;
        String sep = "-".repeat(2*s + n + 1);
        for (int r = 0; r < s; r++) {
            if (r % n == 0) System.out.println(sep);
            for (int c = 0; c < s; c++) {
                if (c % n == 0) System.out.print("|");
                int v = grid[r][c];
                System.out.print(v == 0 ? "." : v);
                System.out.print(' ');
            }
            System.out.println("|");
        }
        System.out.println(sep);
    }
}

