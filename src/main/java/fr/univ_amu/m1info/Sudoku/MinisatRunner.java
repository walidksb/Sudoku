package fr.univ_amu.m1info.Sudoku;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class MinisatRunner {
    public static class Result {
        public final boolean sat;
        public final int[] model; // 1..numVars map to {+var | -var} presence, or empty if UNSAT
        public Result(boolean sat, int[] model) { this.sat = sat; this.model = model; }
    }

    public static Result solve(Cnf cnf) throws IOException, InterruptedException {
        File in = Files.createTempFile("sudoku", ".cnf").toFile();
        File out = Files.createTempFile("sudoku", ".sat").toFile();
        DimacsWriter.write(in, cnf);

        ProcessBuilder pb = new ProcessBuilder("minisat", in.getAbsolutePath(), out.getAbsolutePath());
        pb.redirectErrorStream(true);
        System.out.println("Executing: minisat " + in.getAbsolutePath() + " " + out.getAbsolutePath());
        Process p = pb.start();

        // consume stdout of minisat (not strictly needed, but nice for logs)
        try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            while (br.readLine() != null) { /* drain */ }
        }
        int code = p.waitFor();

        // Minisat output file format:
        // line1: SAT or UNSAT
        // line2: model literals terminated by 0 (if SAT)
        try (BufferedReader br = new BufferedReader(new FileReader(out))) {
            String status = br.readLine();
            if (status == null || status.startsWith("UNSAT")) {
                in.delete(); out.delete();
                return new Result(false, new int[0]);
            }
            String line;
            List<Integer> lits = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                String[] toks = line.trim().split("\\s+");
                for (String t : toks) {
                    if (t.equals("0") || t.isEmpty()) continue;
                    lits.add(Integer.parseInt(t));
                }
            }
            int[] arr = lits.stream().mapToInt(Integer::intValue).toArray();
            in.delete(); out.delete();
            return new Result(true, arr);
        }
    }
}

