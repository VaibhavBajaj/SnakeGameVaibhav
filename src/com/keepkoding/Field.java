package com.keepkoding;

import java.awt.Color;
import java.awt.Graphics2D;

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
        foodX = (int)(Math.random() * screenWidth);
        foodY = (int)(Math.random() * screenHeight);
    }

    void paint(Graphics2D g) {
        g.setColor(Color.BLUE);
        g.fillOval(foodX, foodY, foodDiameter, foodDiameter);
    }

    int getFoodX() {
        return foodX;
    }

    int getFoodY() {
        return foodY;
    }

}
