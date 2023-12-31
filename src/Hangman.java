import javax.swing.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Hangman {
    private List<String> hangmanWords;
    private ArrayList<Character> guessedLetters;
    private String randomWord;
    private JLabel letterToGuess;
    private JLabel imageLabel;
    private String[] imageFileNames;
    private JTextField falseLetters;
    private int incorrectGuesses;
    private List<Character> history;
    private int currentLevel = 0;

    public Hangman(JLabel letterToGuess, JLabel imageLabel, String[] imageFileNames, JTextField falseLetters, List<String> words) {
        this.letterToGuess = letterToGuess;
        this.imageLabel = imageLabel;
        this.imageFileNames = imageFileNames;
        this.falseLetters = falseLetters;
        hangmanWords = words;
        guessedLetters = new ArrayList<>();
        history = new ArrayList<>();
        resetGame();
    }

    public void updateDisplay() {
        StringBuilder display = new StringBuilder();
        for (int i = 0; i < randomWord.length(); i++) {
            char letter = randomWord.charAt(i);
            if (guessedLetters.contains(letter)) {
                display.append(letter);
            } else {
                display.append("_ ");
            }
        }
        letterToGuess.setText(display.toString());
    }

    public void checkLetter(String input) {
        if (input.length() == 1 && Character.isLetter(input.charAt(0)) && !isLetterTried(input.charAt(0))) {
            char guessedLetter = input.charAt(0);
            guessedLetters.add(guessedLetter);
            updateDisplay();

            if (!randomWord.contains(input)) {
                addFalseLetter(guessedLetter);
                incorrectGuesses++;
                currentLevel++;
                updateHangmanImage();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Input can't be empty", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean hasWon() {
        for (int i = 0; i < randomWord.length(); i++) {
            if (!guessedLetters.contains(randomWord.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public boolean isGameLost() {
        return incorrectGuesses >= 9;
    }

    public boolean isLetterTried(char letter) {
        return guessedLetters.contains(letter);
    }

    public String getRandomWord() {
        return randomWord;
    }

    public void resetGame() {
        randomWord = getRandomWord(hangmanWords);
        guessedLetters.clear();
        incorrectGuesses = 0;
        imageLabel.setIcon(new ImageIcon(imageFileNames[incorrectGuesses]));
        updateDisplay();
        falseLetters.setText("");
    }

    private void addFalseLetter(char letter) {
        if (falseLetters.getText().isEmpty()) {
            falseLetters.setText(String.valueOf(letter));
        } else {
            falseLetters.setText(falseLetters.getText() + ", " + letter);
        }
    }

    public String getRandomWord(List<String> words) {
        Random random = new Random();
        int randomIndex = random.nextInt(words.size());
        return words.get(randomIndex);
    }

    public void updateHangmanImage() {
        if (incorrectGuesses >= 0 && incorrectGuesses < imageFileNames.length) {
            imageLabel.setIcon(new ImageIcon(imageFileNames[incorrectGuesses]));
        }
    }

    public void showHistory() {
        StringBuilder historyStr = new StringBuilder("Guessed letters history: ");
        for (Character letter : history) {
            historyStr.append(letter).append(", ");
        }
        JOptionPane.showMessageDialog(null, historyStr.toString(), "Guessed Letters History", JOptionPane.INFORMATION_MESSAGE);
    }

    public void setCurrentLevel(int level) {
        if (level >= 0 && level < imageFileNames.length) {
            currentLevel = level;
            incorrectGuesses = level;
        }
    }


    public void clearHistory() {
        history.clear();
    }
}