package fr.univ_amu.m1info.Sudoku;
import java.util.ArrayList;
import java.util.List;

public class Cnf {
    private final int numVars;
    private final List<int[]> clauses = new ArrayList<>();

    public Cnf(int numVars) {
        this.numVars = numVars;
    }

    public void addClause(int... lits) {
        clauses.add(lits);
    }

    public int getNumVars() { return numVars; }
    public List<int[]> getClauses() { return clauses; }
}

