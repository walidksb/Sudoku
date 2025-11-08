package fr.univ_amu.m1info.Sudoku;

public class SudokuDecoder {
    public static Sudoku fromModel(int n, int[] model) {
        int size = n*n;
        int[][] grid = new int[size][size];
        // Convert model list of signed ints into a boolean assignment for positives
        // Positive literal k => variable k is true
        // Variables start at 1 up to size^3
        boolean[] isTrue = new boolean[size*size*size + 1];
        for (int lit : model) if (lit > 0) isTrue[Math.abs(lit)] = true;

        for (int r=0;r<size;r++) for (int c=0;c<size;c++) {
            for (int d=0; d<size; d++) {
                int var = SudokuSatEncoder.varIndex(size, r, c, d);
                if (isTrue[var]) {
                    grid[r][c] = d+1;
                    break;
                }
            }
        }
        return new Sudoku(n, grid);
    }
}
