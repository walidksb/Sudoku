package fr.univ_amu.m1info.Sudoku;
import java.io.*;
import java.util.List;

public class DimacsWriter {
    public static void write(File out, Cnf cnf) throws IOException {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(out))) {
            List<int[]> cls = cnf.getClauses();
            w.write("p cnf " + cnf.getNumVars() + " " + cls.size());
            w.newLine();
            for (int[] clause : cls) {
                for (int lit : clause) {
                    w.write(Integer.toString(lit));
                    w.write(' ');
                }
                w.write("0");
                w.newLine();
            }
        }
    }
}

