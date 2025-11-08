package fr.univ_amu.m1info.Sudoku;



public class SudokuSatEncoder {
    // Variable index mapping: v(r,c,d) in [1..size], 0-based r,c,d given
    private static int v(int size, int r, int c, int d) {
        // 1 + r*size*size + c*size + d
        return 1 + r*size*size + c*size + d;
    }

    public static Cnf encode(Sudoku sdk, boolean forbidHorizontalConsecutive) {
        int size = sdk.getSize();      // = n^2
        int n = sdk.getN();
        int numVars = size*size*size;  // n^6
        Cnf cnf = new Cnf(numVars);

        // 1) Cell "at least one"
        for (int r=0;r<size;r++) for (int c=0;c<size;c++) {
            int[] clause = new int[size];
            for (int d=0; d<size; d++) clause[d] = v(size,r,c,d);
            cnf.addClause(clause);
        }

        // 2) Cell "at most one"
        for (int r=0;r<size;r++) for (int c=0;c<size;c++) {
            for (int d=0; d<size; d++) for (int e=d+1; e<size; e++) {
                cnf.addClause(-v(size,r,c,d), -v(size,r,c,e));
            }
        }

        // 3) Row constraints
        // 3a) at least one per symbol per row
        for (int r=0;r<size;r++) for (int d=0; d<size; d++) {
            int[] clause = new int[size];
            for (int c=0;c<size;c++) clause[c] = v(size,r,c,d);
            cnf.addClause(clause);
        }
        // 3b) at most one per symbol per row
        for (int r=0;r<size;r++) for (int d=0; d<size; d++) {
            for (int c1=0;c1<size;c1++) for (int c2=c1+1;c2<size;c2++) {
                cnf.addClause(-v(size,r,c1,d), -v(size,r,c2,d));
            }
        }

        // 4) Column constraints
        // 4a) at least one
        for (int c=0;c<size;c++) for (int d=0; d<size; d++) {
            int[] clause = new int[size];
            for (int r=0;r<size;r++) clause[r] = v(size,r,c,d);
            cnf.addClause(clause);
        }
        // 4b) at most one
        for (int c=0;c<size;c++) for (int d=0; d<size; d++) {
            for (int r1=0;r1<size;r1++) for (int r2=r1+1;r2<size;r2++) {
                cnf.addClause(-v(size,r1,c,d), -v(size,r2,c,d));
            }
        }

        // 5) Block constraints (n x n blocks)
        for (int br=0; br<n; br++) for (int bc=0; bc<n; bc++) {
            for (int d=0; d<size; d++) {
                // at least one in block
                int[] clause = new int[size];
                int k=0;
                for (int dr=0; dr<n; dr++) for (int dc=0; dc<n; dc++) {
                    int r = br*n + dr, c = bc*n + dc;
                    clause[k++] = v(size,r,c,d);
                }
                cnf.addClause(clause);

                // at most one in block
                for (int i=0;i<size;i++) for (int j=i+1;j<size;j++) {
                    int r1 = br*n + (i / n), c1 = bc*n + (i % n);
                    int r2 = br*n + (j / n), c2 = bc*n + (j % n);
                    cnf.addClause(-v(size,r1,c1,d), -v(size,r2,c2,d));
                }
            }
        }

        // 6) Clues (unit clauses)
        int[][] g = sdk.getGrid();
        for (int r=0;r<size;r++) for (int c=0;c<size;c++) {
            int val = g[r][c];
            if (val != 0) {
                int d = val - 1;
                cnf.addClause(v(size,r,c,d));
            }
        }

        // 7) Optional: forbid horizontally-adjacent consecutive numbers
        if (forbidHorizontalConsecutive) {
            for (int r=0;r<size;r++) for (int c=0;c<size-1;c++) {
                // forbid (d, d+1)
                for (int d=0; d<size-1; d++) {
                    cnf.addClause(-v(size,r,c,d), -v(size,r,c+1,d+1));
                }
                // forbid (d, d-1)
                for (int d=1; d<size; d++) {
                    cnf.addClause(-v(size,r,c,d), -v(size,r,c+1,d-1));
                }
            }
        }


        return cnf;
    }

    public static int varIndex(int size, int r, int c, int d) {
        return v(size,r,c,d);
    }
}

