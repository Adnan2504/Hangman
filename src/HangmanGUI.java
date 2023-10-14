import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;


public class HangmanGUI {
    public JPanel playingField;
    public JLabel imageLabel;
    public JTextField letterInput;
    public JButton startButton;
    public JButton exitButton;
    public JTextField falseLetters;
    public JButton submitButton;
    public JLabel letterToGuess;

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
    private int currentImageIndex = 0;

    public HangmanGUI() {
        imageLabel.setIcon(new ImageIcon(imageFileNames[0]));

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hangman = new Hangman(letterToGuess, imageLabel, imageFileNames);
                hangman.updateDisplay();
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
                hangman.checkLetter(letterInput.getText().toLowerCase());
                letterInput.setText(""); // Clear the input field
            }
        });

        // Increase the font size of the letterToGuess label
        Font biggerFont = letterToGuess.getFont().deriveFont(Font.PLAIN, 24);
        letterToGuess.setFont(biggerFont);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Hangman Game");
            HangmanGUI hangmanGUI = new HangmanGUI();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(hangmanGUI.playingField);
            frame.pack();
            frame.setVisible(true);
        });
    }
}