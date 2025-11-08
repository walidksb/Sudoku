# 🧩 Sudoku Solver via SAT (Java + Minisat)

Projet universitaire (M1 Informatique — Complexité)  
Université d’Aix-Marseille

---

## 🎯 Objectif

Ce projet implémente un **résolveur de Sudoku** basé sur une **réduction en formule SAT**.  
Chaque grille 9×9 est transformée en une formule propositionnelle au format **DIMACS CNF**,  
résolue ensuite à l’aide du solveur **Minisat**.

Le programme prend en charge :
- ✅ Exécution **par ligne de commande** (grille en argument ou benchmark) via `Main.java`
- ✅ Exécution **avec interface graphique (Swing)** via la classe `SudokuGUI` qui contient une classe Main
- ✅ Encodage SAT complet pour Sudoku 9×9 (et généralisé à n²×n²)
- ✅ Option de contrainte additionnelle : *pas de valeurs consécutives sur la même ligne* via le flag `--noconsec`
- ✅ Décodage et affichage lisible de la solution
- ✅ Vérification et affichage "UNSAT" si la grille est impossible

---

## 🧱 Structure du projet
Sudoku/
├── src/
│ └── main/
│ └── java/
│ └── fr/univ_amu/m1info/Sudoku/
│ ├── Main.java # Exécution par ligne de commande
│ ├── Sudoku.java # Représentation et affichage d'une grille
│ ├── Cnf.java # Classe CNF pour stocker les variables et clauses
│ ├── SudokuSatEncoder.java # Encodage du Sudoku en CNF (réduction SAT)
│ ├── SudokuDecoder.java # Décodage du modèle SAT vers une grille
│ ├── MinisatRunner.java # Interface d'appel à Minisat (ProcessBuilder)
│ ├── SudokuGUI.java # Interface graphique Swing
│ ├── Solutions.java # Gestion des solutions multiples (optionnel)
│ ├── benchmark_sudoku_1.txt 
│ ├── benchmark_sudoku_2.txt 
│ └── ...
└── README.md

---

## ⚙️ Installation

### 1. Prérequis
- **Java 17** ou supérieur  
- **Minisat** installé et accessible dans le PATH

### 2. Installation de Minisat

#### 🔹 Sous Ubuntu / WSL
```bash
sudo apt update
sudo apt install minisat
```
#### 🔹 Sous Windows
- via le siteweb de minisat `http://minisat.se/`

---

## ▶️ Exécution en ligne de commande
### Compilation
- Depuis la racine du projet :

```bash
javac src/main/java/fr/univ_amu/m1info/Sudoku/*.java -d out
```
- Lancement (résolution d'une grille)

```bash
cd out
java fr.univ_amu.m1info.Sudoku.Main solve "53..7....6..195....98....6.8...6...34..8.3..17...2...6....6....28....419..5....8..79"
```
- 🧩 Exemple de sortie :
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
- Lancement sur un fichier de benchmark:
```bash
java fr.univ_amu.m1info.Sudoku.Main benchmark benchmark_sudoku_1.txt
```
Affiche le nombre de grilles résolues en 1 seconde.

## 🪟 Exécution avec interface graphique (Swing)
### Lancer la GUI :
```bash
java fr.univ_amu.m1info.Sudoku.SudokuGUI
```
### Fonctionnalités :
✅ Saisie manuelle de la grille 9×9
✅ Bouton Résoudre → génération CNF + appel Minisat + affichage automatique
✅ Bouton Effacer → réinitialisation
✅ Les chiffres trouvés sont affichés en bleu

## 🧰 Fichiers temporaires
Lors de l’exécution, le programme crée :

- un fichier CNF : /tmp/sudokuXXXX.cnf
- un fichier de sortie Minisat : /tmp/sudokuXXXX.sat

Ils sont supprimés automatiquement à la fin de l’exécution.

## 🧠 Fonctionnalités implémentées
-----------------------------------------------------------------
| Fonctionnalité                      | Fichier principal       |
| ----------------------------------- | ----------------------- |
| Lecture et affichage d'une grille   | `Sudoku.java`           |
| Génération des clauses SAT          | `SudokuSatEncoder.java` |
| Appel de Minisat via ProcessBuilder | `MinisatRunner.java`    |
| Décodage du modèle en Sudoku        | `SudokuDecoder.java`    |
| Interface terminale                 | `Main.java`             |
| Interface graphique Swing           | `SudokuGUI.java`        |
| Benchmark sur plusieurs grilles     | `Main.java`             |
| Option “no consecutive numbers”     | `SudokuSatEncoder.java` |
-----------------------------------------------------------------

### 🧾 Exemple d’utilisation du flag --noconsec
```bash
java fr.univ_amu.m1info.Sudoku.Main solve "53..7....6..195....98....6.8...6...34..8.3..17...2...6....6....28....419..5....8..79" --noconsec
```
➡️ Ajoute la contrainte “pas de chiffres consécutifs sur la même ligne”.


## 🧠 Auteurs et crédits
* Groupe 18 — Université d’Aix-Marseille
    Master 1 Informatique
    Module : Complexité
    Encadrant : Kevin Perrot

Utilise :
* 🧠 Java 17
* ⚙️ Minisat pour la résolution SAT
* 🪟 Swing pour l’interface graphique

## 📄 Licence
Projet académique — usage libre à but pédagogique.
Créditer l’auteur en cas de réutilisation.