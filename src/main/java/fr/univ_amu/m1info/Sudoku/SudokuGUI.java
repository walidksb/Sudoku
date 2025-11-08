package fr.univ_amu.m1info.Sudoku;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokuGUI extends JFrame {
    private final JTextField[][] cells = new JTextField[9][9];
    private final JButton solveButton = new JButton("Résoudre");
    private final JButton clearButton = new JButton("Effacer");

    public SudokuGUI() {
        super("Sudoku Solver (via Minisat)");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(500, 600);

        JPanel gridPanel = new JPanel(new GridLayout(9, 9));
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                JTextField tf = new JTextField(1);
                tf.setHorizontalAlignment(JTextField.CENTER);
                tf.setFont(new Font("SansSerif", Font.BOLD, 20));
                if ((r / 3 + c / 3) % 2 == 0)
                    tf.setBackground(new Color(230, 230, 230)); // alternance couleur bloc
                cells[r][c] = tf;
                gridPanel.add(tf);
            }
        }
        add(gridPanel, BorderLayout.CENTER);

        JPanel buttons = new JPanel();
        buttons.add(solveButton);
        buttons.add(clearButton);
        add(buttons, BorderLayout.SOUTH);

        solveButton.addActionListener(new SolveListener());
        clearButton.addActionListener(e -> clearGrid());
    }

    private void clearGrid() {
        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++)
                cells[r][c].setText("");
    }

    private String readGrid() {
        StringBuilder sb = new StringBuilder(81);
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                String text = cells[r][c].getText().trim();
                if (text.isEmpty()) sb.append('.');
                else sb.append(text.charAt(0));
            }
        }
        return sb.toString();
    }

    private void displaySolution(Sudoku solution) {
        int[][] g = solution.getGrid();
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                cells[r][c].setText(Integer.toString(g[r][c]));
                cells[r][c].setForeground(Color.BLUE);
            }
        }
    }

    private class SolveListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String grid81 = readGrid();
                Sudoku in = Sudoku.fromLinearString(3, grid81);
                Cnf cnf = SudokuSatEncoder.encode(in, false);
                MinisatRunner.Result res = MinisatRunner.solve(cnf);
                if (!res.sat) {
                    JOptionPane.showMessageDialog(SudokuGUI.this,
                            "Pas de solution (UNSAT)", "Résultat", JOptionPane.ERROR_MESSAGE);
                } else {
                    Sudoku solved = SudokuDecoder.fromModel(in.getN(), res.model);
                    displaySolution(solved);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(SudokuGUI.this,
                        "Erreur : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SudokuGUI().setVisible(true));
    }
}
