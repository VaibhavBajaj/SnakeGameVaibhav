package com.keepkoding;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.text.DecimalFormat;

class Field {

    static int foodDiameter = 20;
    private int screenWidth, screenHeight;
    private int foodX, foodY;

    Field() {
        screenWidth = SnakeGame.screenWidth;
        screenHeight = SnakeGame.screenHeight;

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

    void paintTimer(Graphics2D g, double gameTime) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesRoman", Font.BOLD, 25));
        g.drawString("Time Left: " + new DecimalFormat("##.##").format(gameTime),
                screenWidth / 20, screenHeight / 10);
    }

    void paintGameOver(Graphics2D g, double score) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesRoman", Font.BOLD, 48));
        g.drawString("Game Over. Your score was " + new DecimalFormat("##.##").format(score),
                screenWidth / 8, screenHeight / 3);
    }

    int getFoodX() {
        return foodX;
    }

    int getFoodY() {
        return foodY;
    }

}
