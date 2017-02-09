package com.keepkoding;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class SnakeGame extends JPanel {

    // Stores screen size.
    static final int
            screenWidth = 1080,
            screenHeight = 720;
    // ASCII values for left and right.
    private final int
            LEFT = 37,
            RIGHT = 39;
    private boolean leftTurn, rightTurn;
    private static boolean gameOver;
    private Field field = new Field();
    private Snake snake = new Snake(field);


    private SnakeGame() {
        //Adding key listener
        KeyListener listener = new MyKeyListener();
        addKeyListener(listener);
        setFocusable(true);

        //Initializing everything...
        gameOver = false;
    }

    private void update() {
        //This function tells the snake to turn right or left depending on the values of the booleans
        snake.update(leftTurn, rightTurn);
        // If gameTime reaches 0, game is over.
        if(field.getGameTime() <= 0) {
            gameOver = true;
        }
    }

    @Override
    public void paint(Graphics g) {
        // Clears screen
        super.paint(g);
        // Graphics2D are much cleaner
        Graphics2D g2d = (Graphics2D) g;

        // Make all shapes drawn much more rounded and less bumpy
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        // Painting field and snake
        field.paintField(g2d);
        snake.paint(g2d);
        // Adding score and timer
        field.paintScore(g2d, snake.getScore());
        field.paintTimer(g2d);

        if(gameOver) {
            // Clear the screen
            super.paint(g);
            // Displays the score
            field.paintGameOver(g2d, snake.getScore());
        }

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Slithery Snake");
        // Creating SnakeGame
        SnakeGame game = new SnakeGame();
        // Adding game to frame
        frame.add(game);
        frame.setSize(screenWidth, screenHeight);
        frame.setVisible(true);
        //Close the frame when X is clicked
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        while (!gameOver) {
            // Re-paint the screen
            game.repaint();
            // Update the values
            game.update();
            try {
                // SLEEEEEEP. No, seriously. Pauses game for 10 ms
                Thread.sleep(10);
            }
            catch (InterruptedException e) {
                // If exception is caused, break out of the game Loop. Effectively hangs the game...
                break;
            }
        }


    }

    // My key listener class
    private class MyKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                // If left or right are pressed, make their booleans true
                case LEFT: leftTurn = true;
                    break;
                case RIGHT: rightTurn = true;
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()) {
                // If left or right are released, make their booleans false
                case LEFT: leftTurn = false;
                    break;
                case RIGHT: rightTurn = false;
                    break;
            }
        }
    }

}
