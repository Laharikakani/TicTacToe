import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class TicTacToe {
    private JFrame mainFrame;
    private JButton[][] buttons;
    private char[][] board;
    private char currentPlayer;
    private Random random;
    private boolean isComputerPlayer;

    public TicTacToe() {
        mainFrame = new JFrame("Tic Tac Toe");
        mainFrame.setSize(300, 350);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 3));

        buttons = new JButton[3][3];
        board = new char[3][3];
        currentPlayer = 'X';
        random = new Random();
        isComputerPlayer = true;

        initializeBoard();
        initializeButtons(buttonPanel);

        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> resetGame());

        JButton gameModeButton = new JButton("Switch to Dual Player");
        gameModeButton.addActionListener(e -> {
            isComputerPlayer = !isComputerPlayer;
            gameModeButton.setText(isComputerPlayer ? "Switch to Dual Player" : "Switch to Computer Player");
            resetGame();
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 2));
        bottomPanel.add(gameModeButton);
        bottomPanel.add(resetButton);

        mainFrame.add(buttonPanel, BorderLayout.CENTER);
        mainFrame.add(bottomPanel, BorderLayout.SOUTH);
    }

    private void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '-';
            }
        }
    }

    private void initializeButtons(JPanel buttonPanel) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("-");
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 40));

                int finalI = i;
                int finalJ = j;
                buttons[i][j].addActionListener(e -> makeMove(finalI, finalJ));
                buttonPanel.add(buttons[i][j]);
            }
        }
    }

    private void makeMove(int row, int col) {
        if (board[row][col] == '-' && !checkWin()) {
            board[row][col] = currentPlayer;
            buttons[row][col].setText(String.valueOf(currentPlayer));
            if (checkWin()) {
                JOptionPane.showMessageDialog(mainFrame, "Player " + currentPlayer + " wins!");
            } else if (isBoardFull()) {
                JOptionPane.showMessageDialog(mainFrame, "It's a tie!");
            } else {
                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                if (isComputerPlayer && currentPlayer == 'O') {
                    computerMove();
                    currentPlayer = 'X';
                }
            }
        }
    }

    private void computerMove() {
        int row, col;
        do {
            row = random.nextInt(3);
            col = random.nextInt(3);
        } while (board[row][col] != '-');

        makeMove(row, col);
    }

    private boolean checkWin() {
        // Check rows, columns, and diagonals for a win
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != '-') {
                return true; // Row win
            }
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != '-') {
                return true; // Column win
            }
        }
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != '-') {
            return true; // Diagonal win (top-left to bottom-right)
        }
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != '-') {
            return true; // Diagonal win (top-right to bottom-left)
        }
        return false;
    }

    private boolean isBoardFull() {
        // Check if the board is full
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') {
                    return false; // Board is not full
                }
            }
        }
        return true; // Board is full
    }

    private void resetGame() {
        initializeBoard();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("-");
            }
        }
        currentPlayer = 'X';
        if (!isComputerPlayer) {
            JOptionPane.showMessageDialog(mainFrame, "Dual player mode selected. Player 1 starts with X.");
        }
    }

    public void play() {
        mainFrame.setVisible(true);
        if (isComputerPlayer && currentPlayer == 'O') {
            computerMove();
            currentPlayer = 'X';
        } else if (!isComputerPlayer) {
            JOptionPane.showMessageDialog(mainFrame, "Dual player mode selected. Player 1 starts with X.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TicTacToe game = new TicTacToe();
            game.play();
        });
    }
}
