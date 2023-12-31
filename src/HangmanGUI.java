import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HangmanGUI {
    public JPanel playingField;
    public JLabel imageLabel;
    public JTextField letterInput;
    public JButton startButton;
    public JButton exitButton;
    public JTextField falseLetters;
    public JButton submitButton;
    public JLabel letterToGuess;
    public JCheckBoxMenuItem showHistoryMenuItem;
    public JMenuItem startingLevel;
    private Hangman hangman;

    private final String[] imageFileNames = {
            "HangmanPic/hangman1.png",
            "HangmanPic/hangman2.png",
            "HangmanPic/hangman3.png",
            "HangmanPic/hangman4.png",
            "HangmanPic/hangman5.png",
            "HangmanPic/hangman6.png",
            "HangmanPic/hangman7.png",
            "HangmanPic/hangman8.png",
            "HangmanPic/hangman9.png",
            "HangmanPic/hangman10.png"
    };

    public HangmanGUI() {
        imageLabel.setIcon(new ImageIcon(imageFileNames[0]));

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> words = readWordsFromFile("Words/wordsForHangman.txt");
                hangman = new Hangman(letterToGuess, imageLabel, imageFileNames, falseLetters, words);
                hangman.resetGame();
                hangman.updateDisplay();
                imageLabel.setIcon(new ImageIcon(imageFileNames[0]));
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(playingField);
                frame.dispose();
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSubmission();
            }
        });

        letterInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSubmission();
            }
        });

        showHistoryMenuItem = new JCheckBoxMenuItem("Show History");
        showHistoryMenuItem.setSelected(true);

        showHistoryMenuItem.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    falseLetters.setVisible(true);
                } else {
                    falseLetters.setVisible(false);
                }
            }
        });

        startingLevel = new JMenuItem("Starting Level");
        startingLevel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String startlvl = JOptionPane.showInputDialog(playingField, "Enter starting level:");
                try {
                    int level = Integer.parseInt(startlvl);
                    hangman.setCurrentLevel(level);
                    hangman.updateHangmanImage();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(playingField, "Invalid input for starting level.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        Font biggerFont = letterToGuess.getFont().deriveFont(Font.PLAIN, 24);
        letterToGuess.setFont(biggerFont);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Game");
        menu.add(showHistoryMenuItem);
        menu.add(startingLevel);
        menuBar.add(menu);

        JFrame frame = new JFrame("Hangman Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(playingField);
        frame.setJMenuBar(menuBar);
        frame.pack();
        frame.setVisible(true);
    }

    public void handleSubmission() {
        if (hangman != null) {
            String input = letterInput.getText().toLowerCase();
            if (input.length() == 1 && Character.isLetter(input.charAt(0)) && !hangman.isLetterTried(input.charAt(0))) {
                hangman.checkLetter(input);

                if (hangman.hasWon()) {
                    int option = JOptionPane.showConfirmDialog(null, "Congratulations! You've guessed the word: " + hangman.getRandomWord() + "\nDo you want to restart?", "You Win!", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                    if (option == JOptionPane.YES_OPTION) {
                        hangman.resetGame();
                        letterInput.setText("");
                        imageLabel.setIcon(new ImageIcon(imageFileNames[0]));
                    }
                }

                if (hangman.isGameLost()) {
                    int option = JOptionPane.showConfirmDialog(null, "You lost. The word was: " + hangman.getRandomWord() + "\nDo you want to restart?", "Game Over", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                    if (option == JOptionPane.YES_OPTION) {
                        hangman.resetGame();
                        letterInput.setText("");
                        imageLabel.setIcon(new ImageIcon(imageFileNames[0]));
                    }
                }

                letterInput.setText("");
            } else {
                if (input.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Input can't be null or empty", "Invalid Input", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                ;
                if (!hangman.isLetterTried(input.charAt(0))) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid letter.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    letterInput.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "Letter '" + input + "' was already entered.", "Letter Already Guessed", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Press the 'Start' button to begin the game.", "Game Not Started", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static List<String> readWordsFromFile(String fileName) {
        List<String> words = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                words.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return words;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new HangmanGUI();
        });
    }
}
