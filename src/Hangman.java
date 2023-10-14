import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

class Hangman extends HangmanGUI {
    private String[] hangmanWords = {
            "computer",
            "programming",
            "java",
            "hangman",
            "developer",
            "algorithm",
            "debugging",
            "variable",
            "interface",
            "exception",
            "application",
            "framework",
            "iteration",
            "database",
            "authentication",
            "encryption",
            "repository",
            "polymorphism",
            "inheritance"
    };
    private ArrayList<Character> guessedLetters;
    private String randomWord;
    private JLabel letterToGuess;
    private JLabel imageLabel;
    private String[] imageFileNames;
    private int incorrectGuesses;
    private JTextField falseLetters;

    public Hangman(JLabel letterToGuess, JLabel imageLabel, String[] imageFileNames, JTextField falseLetters) {
        this.letterToGuess = letterToGuess;
        this.imageLabel = imageLabel;
        this.imageFileNames = imageFileNames;
        this.falseLetters = falseLetters;
        guessedLetters = new ArrayList<>();
        randomWord = getRandomWord(hangmanWords);
        incorrectGuesses = 0;
    }

    public void updateDisplay() {
        StringBuilder display = new StringBuilder();
        for (int i = 0; i < randomWord.length(); i++) {
            char letter = randomWord.charAt(i);
            if (guessedLetters.contains(letter)) {
                display.append(letter);
            } else {
                display.append("_");
            }
        }
        letterToGuess.setText(display.toString());
    }

    public void checkLetter(String input) {
        if (input.length() == 1 && Character.isLetter(input.charAt(0))) {
            char guessedLetter = input.charAt(0);
            if (guessedLetters.contains(guessedLetter)) {
                // Letter was already guessed
                JOptionPane.showMessageDialog(null, "Letter '" + guessedLetter + "' was already entered.", "Letter Already Guessed", JOptionPane.INFORMATION_MESSAGE);
            } else {
                guessedLetters.add(guessedLetter);
                updateDisplay();

                if (!randomWord.contains(input)) {
                    incorrectGuesses++;
                    updateHangmanImage();
                    addFalseLetter(guessedLetter);
                }

                if (incorrectGuesses == imageFileNames.length - 1) {
                    // Game lost, handle game over logic here
                    JOptionPane.showMessageDialog(null, "You lost. The word was: " + randomWord, "Game Over", JOptionPane.INFORMATION_MESSAGE);
                    resetGame();
                }
            }
        }
    }

    private boolean hasWon() {
        for (int i = 0; i < randomWord.length(); i++) {
            if (!guessedLetters.contains(randomWord.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static String getRandomWord(String[] words) {
        Random random = new Random();
        int randomIndex = random.nextInt(words.length);
        return words[randomIndex];
    }

    private void updateHangmanImage() {
        if (incorrectGuesses < imageFileNames.length) {
            imageLabel.setIcon(new ImageIcon(imageFileNames[incorrectGuesses]));
        }
    }

    private void resetGame() {
        randomWord = getRandomWord(hangmanWords);
        guessedLetters.clear();
        incorrectGuesses = 0;
        updateDisplay();
        imageLabel.setIcon(new ImageIcon(imageFileNames[0]));
        falseLetters.setText("");
    }

    private void addFalseLetter(char letter) {
        if (falseLetters.getText().isEmpty()) {
            falseLetters.setText(String.valueOf(letter));
        } else {
            falseLetters.setText(falseLetters.getText() + ", " + letter);
        }
    }
}