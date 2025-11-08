# 🧩 Sudoku Solver via SAT (Java + Minisat)

University Project (M1 Computer Science — Complexity)  
Aix-Marseille University

---

## 🎯 Objective

This project implements a **Sudoku solver** based on a **reduction to SAT**.  
Each 9×9 grid is transformed into a propositional formula in **DIMACS CNF** format,  
which is then solved using the **Minisat** solver.

The program supports:
- ✅ **Command-line execution** (grid or benchmark input) via `Main.java`
- ✅ **Graphical interface (Swing)** via the `SudokuGUI` class, which includes a `main` method
- ✅ Complete SAT encoding for 9×9 Sudoku (generalized to n²×n²)
- ✅ Optional constraint: *no consecutive numbers on the same row* using the flag `--noconsec`
- ✅ Decoding and readable display of the solution
- ✅ Detection and display of "UNSAT" when the grid is unsolvable

---

## ⚙️ Installation

### 1. Requirements
- **Java 17** or higher  
- **Minisat** installed and accessible in the system PATH

### 2. Installing Minisat

#### 🔹 On Ubuntu / WSL
```bash
sudo apt update
sudo apt install minisat
```

#### 🔹 On Windows
Download from the official Minisat website:  
👉 http://minisat.se/

---

## ▶️ Command-Line Execution

### Compilation
From the project root:
```bash
javac src/main/java/fr/univ_amu/m1info/Sudoku/*.java -d out
```

### Running a Sudoku Grid
```bash
cd out
java fr.univ_amu.m1info.Sudoku.Main solve "53..7....6..195....98....6.8...6...34..8.3..17...2...6....6....28....419..5....8..79"
```

### 🧩 Example Output
```
Input:
----------------------
|5 3 . |. 7 . |. . . |
|6 . . |1 9 5 |. . . |
|. 9 8 |. . . |. 6 . |
----------------------
Solution:
----------------------
|5 3 4 |6 7 8 |9 1 2 |
|6 7 2 |1 9 5 |3 4 8 |
|1 9 8 |3 4 2 |5 6 7 |
...
```

### Running a Benchmark File
```bash
java fr.univ_amu.m1info.Sudoku.Main benchmark benchmark_sudoku_1.txt
```
Displays the number of grids solved within 1 second.

---

## 🪟 Graphical User Interface (Swing)

### Launch the GUI
```bash
java fr.univ_amu.m1info.Sudoku.SudokuGUI
```

### Features
✅ Manual 9×9 grid input  
✅ "Solve" button → CNF generation + Minisat call + automatic display  
✅ "Clear" button → reset the grid  
✅ Computed digits are displayed in **blue**

---

## 🧰 Temporary Files
During execution, the program creates:

- CNF file: `/tmp/sudokuXXXX.cnf`  
- Minisat output file: `/tmp/sudokuXXXX.sat`  

These files are automatically deleted after execution.

---

## 🧠 Implemented Features
| Feature                               | Main File                |
| ------------------------------------- | ------------------------ |
| Read and display a Sudoku grid        | `Sudoku.java`            |
| Generate SAT clauses                  | `SudokuSatEncoder.java`  |
| Run Minisat via ProcessBuilder        | `MinisatRunner.java`     |
| Decode SAT model into Sudoku grid     | `SudokuDecoder.java`     |
| Command-line interface                | `Main.java`              |
| Swing graphical interface             | `SudokuGUI.java`         |
| Benchmark multiple grids              | `Main.java`              |
| "No consecutive numbers" constraint   | `SudokuSatEncoder.java`  |

---

### 🧾 Example Using the `--noconsec` Flag
```bash
java fr.univ_amu.m1info.Sudoku.Main solve "53..7....6..195....98....6.8...6...34..8.3..17...2...6....6....28....419..5....8..79" --noconsec
```
➡️ Adds the constraint: *no consecutive numbers on the same row*.

---

## 🧑‍💻 Authors and Credits
* Group 18 — Aix-Marseille University  
  Master 1 Computer Science  
  Course: Complexity  
  Supervisor: **Kevin Perrot**

### Technologies Used
* 🧠 Java 25  
* ⚙️ Minisat (SAT Solver)  
* 🪟 Swing (Graphical Interface)

---

## 📄 License
Academic project — free educational use.  
Please credit the authors if reused.
