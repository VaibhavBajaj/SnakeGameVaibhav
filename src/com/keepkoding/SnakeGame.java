package com.keepkoding;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class SnakeGame extends JPanel {

    static int screenWidth = 1080, screenHeight = 720;
    private final int
        LEFT = 37,
        RIGHT = 39;
    private boolean leftTurn, rightTurn;
    private Field field = new Field();
    private Snake snake = new Snake(field);

    private SnakeGame() {
        KeyListener listener = new MyKeyListener();
        addKeyListener(listener);
        setFocusable(true);
    }

    private void update() {
        snake.update(leftTurn, rightTurn);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        field.paint(g2d);
        snake.paint(g2d);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Slithery Snake");
        SnakeGame game = new SnakeGame();
        frame.add(game);
        frame.setSize(screenWidth, screenHeight);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        while (true) {
            game.repaint();
            game.update();
            try {
                Thread.sleep(10);
            }
            catch (InterruptedException e) {
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
