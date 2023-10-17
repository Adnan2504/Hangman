import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

class Hangman {
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
    private JTextField falseLetters;
    private int incorrectGuesses;

    public Hangman(JLabel letterToGuess, JLabel imageLabel, String[] imageFileNames, JTextField falseLetters) {
        this.letterToGuess = letterToGuess;
        this.imageLabel = imageLabel;
        this.imageFileNames = imageFileNames;
        this.falseLetters = falseLetters;
        guessedLetters = new ArrayList<>();
        resetGame();
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
        if (input.length() == 1 && Character.isLetter(input.charAt(0)) && !isLetterTried(input.charAt(0))) {
            char guessedLetter = input.charAt(0);
            guessedLetters.add(guessedLetter);
            updateDisplay();

            if (!randomWord.contains(input)) {
                addFalseLetter(guessedLetter);
                updateHangmanImage();
                incorrectGuesses++;
            }
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
        return incorrectGuesses >= imageFileNames.length;
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

    public String getRandomWord(String[] words) {
        Random random = new Random();
        int randomIndex = random.nextInt(words.length);
        return words[randomIndex];
    }

    private void updateHangmanImage() {
        if (incorrectGuesses >= 0 && incorrectGuesses < imageFileNames.length) {
            imageLabel.setIcon(new ImageIcon(imageFileNames[incorrectGuesses]));
        }
    }



}