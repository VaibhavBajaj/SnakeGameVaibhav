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

    static final int
            screenWidth = 1080,
            screenHeight = 720;
    private final int
            LEFT = 37,
            RIGHT = 39;
    private double gameTime;
    private boolean leftTurn, rightTurn;
    private static boolean gameOver;
    private Field field = new Field();
    private Snake snake = new Snake(field);


    private SnakeGame() {
        KeyListener listener = new MyKeyListener();
        addKeyListener(listener);
        setFocusable(true);

        gameTime = 30;

        Timer timer = new Timer(10, e -> {
            gameTime -= 0.02;
        });

        timer.start();

        gameOver = false;
    }

    private void update() {
        snake.update(leftTurn, rightTurn);
        // 0.01 seconds lee-way causes display to paint timeLeft as 0.
        if(gameTime < 0.01) {
            gameOver = true;
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        field.paintField(g2d);
        snake.paint(g2d);

        field.paintTimer(g2d, gameTime);

        if(gameOver) {
            super.paint(g);
            field.paintGameOver(g2d, snake.getScore());
        }

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Slithery Snake");
        SnakeGame game = new SnakeGame();
        frame.add(game);
        frame.setSize(screenWidth, screenHeight);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        while (!gameOver) {
            game.repaint();
            game.update();
            try {
                Thread.sleep(10);
            }
            catch (InterruptedException e) {
                break;
            }
        }


    }

    private class MyKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case LEFT: leftTurn = true;
                    break;
                case RIGHT: rightTurn = true;
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()) {
                case LEFT: leftTurn = false;
                    break;
                case RIGHT: rightTurn = false;
                    break;
            }
        }
    }

}
