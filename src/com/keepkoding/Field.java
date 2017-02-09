package com.keepkoding;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.text.DecimalFormat;
import javax.swing.*;

class Field {

    static int foodDiameter = 20;
    private int screenWidth, screenHeight;
    private int foodX, foodY, textSize;
    // Used for time left in game
    private double gameTime;

    Field() {
        screenWidth = SnakeGame.screenWidth;
        screenHeight = SnakeGame.screenHeight;

        textSize = screenWidth / 45;

        //Approx 30 seconds
        gameTime = 30;

        // This reduced the gameTime by 0.02 after every 10 ms. Due to lag, it slows down in game.
        Timer timer = new Timer(10, e -> {
            gameTime -= 0.02;
        });
        // Start timer
        timer.start();

        spawnFood();
    }

    void spawnFood() {
        foodX = (int)(Math.random() * (screenWidth - (2 * foodDiameter))) + foodDiameter;
        foodY = (int)(Math.random() * (screenHeight - (2 * foodDiameter))) + foodDiameter;
    }

    void paintField(Graphics2D g) {
        g.setColor(Color.BLUE);
        g.fillOval(foodX, foodY, foodDiameter, foodDiameter);
    }

    void paintTimer(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesRoman", Font.BOLD, textSize));
        g.drawString("Time Left: " + new DecimalFormat("##.##").format(gameTime),
                screenWidth / 20, screenHeight / 10);
    }

    void paintScore(Graphics2D g, double score) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesRoman", Font.BOLD, textSize));
        g.drawString("Score: " + new DecimalFormat("##.##").format(score),
                screenWidth * 4 / 5, screenHeight / 10);
    }

    void paintGameOver(Graphics2D g, double score) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesRoman", Font.BOLD, textSize * 2));
        g.drawString("Game Over. Your score was " + new DecimalFormat("##.##").format(score),
                screenWidth / 8, screenHeight / 3);
    }

    int getFoodX() {
        return foodX;
    }

    int getFoodY() {
        return foodY;
    }

    double getGameTime() {
        return gameTime;
    }

    void setGameTime(double time) {
        gameTime = time;
    }
}
