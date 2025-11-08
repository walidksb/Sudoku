package fr.univ_amu.m1info.Sudoku;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    private static void usage() {
        System.out.println("""
            Usage:
              java Main solve <grid81> [--noconsec]
              java Main solve-file <file_with_one_grid_per_line> [--noconsec]
              java Main benchmark <file_with_grids> [--noconsec]
            """);
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 2) { usage(); return; }
        String cmd = args[0];
        boolean noConsec = false;
        for (String a : args) if ("--noconsec".equals(a)) noConsec = true;

        switch (cmd) {
            case "solve" -> {
                String grid = args[1];
                runSolve(grid, noConsec);
            }
            case "solve-file" -> {
                String path = args[1];
                List<String> lines = Files.readAllLines(Paths.get(path));
                for (String g : lines) {
                    if (g.isBlank()) continue;
                    System.out.println("Input:");
                    Sudoku in = Sudoku.fromLinearString(3, g.trim());
                    in.print();
                    solveAndPrint(in, noConsec);
                }
            }
            case "benchmark" -> {
                String path = args[1];
                runBenchmark(path, noConsec);
            }
            default -> usage();
        }
    }

    private static void runSolve(String grid81, boolean noConsec) throws Exception {
        Sudoku in = Sudoku.fromLinearString(3, grid81.trim());
        System.out.println("Input:");
        in.print();
        solveAndPrint(in, noConsec);
    }

    private static void solveAndPrint(Sudoku in, boolean noConsec) throws Exception {
        Cnf cnf = SudokuSatEncoder.encode(in, noConsec);
        MinisatRunner.Result res = MinisatRunner.solve(cnf);
        if (!res.sat) {
            System.out.println("UNSAT (no solution).");
            return;
        }
        Sudoku out = SudokuDecoder.fromModel(in.getN(), res.model);
        System.out.println("Solution:");
        out.print();
    }

    // Very simple 1-second budget: count how many solved before deadline.
    private static void runBenchmark(String path, boolean noConsec) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(path));
        long deadline = System.nanoTime() + 1_000_000_000L; // 1 second
        int solved = 0, attempted = 0;

        for (String g : lines) {
            if (g.isBlank()) continue;
            attempted++;
            Sudoku in = Sudoku.fromLinearString(3, g.trim());
            Cnf cnf = SudokuSatEncoder.encode(in, noConsec);
            MinisatRunner.Result res = MinisatRunner.solve(cnf);
            if (res.sat) solved++;

            if (System.nanoTime() > deadline) break;
        }
        System.out.printf("Attempted: %d, Solved: %d in ~1s%n", attempted, solved);
    }
}
