package com.keepkoding;

import java.awt.Color;
import java.awt.Graphics2D;

class Snake {

    private int x, y, ctr;
    private double
            xVel,
            yVel,
            turnSpeed,
            velVector;
    private int screenWidth, screenHeight, foodDiameter, beadCount, prevFoodX, prevFoodY, maxVel;
    private final int beadDiameter = 30;
    private double score;
    private int[][] board;
    private Field field;

    Snake(Field field) {

        this.field = field;

        screenWidth = SnakeGame.screenWidth;
        screenHeight = SnakeGame.screenHeight;
        foodDiameter = Field.foodDiameter;

        score = 0;
        beadCount = 1;

        maxVel = 6;

        ctr = 1;

        board = new int[screenWidth][screenHeight];
        board[x][y] = ctr;
        ctr++;

        x = (int)(Math.random() * (screenWidth - 2 * beadDiameter)) + beadDiameter;
        y = (int)(Math.random() * (screenHeight - 2 * beadDiameter)) + beadDiameter;

        turnSpeed = (double)maxVel / 20;
        xVel = Math.random() * maxVel;
        yVel = maxVel - xVel;

        prevFoodX = x;
        prevFoodY = y;
    }

    private int distBetween (int x1, int y1, int x2, int y2) {
        return (int)(Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2)));
    }

    private void checkFoodConsumed() {
        int foodX = field.getFoodX();
        int foodY = field.getFoodY();

        if (distBetween(x, y, foodX, foodY) <= (beadDiameter + foodDiameter) / 2) {
            beadCount++;
            maxVel += 1;
            turnSpeed = (double)maxVel / 20;

            score += distBetween(prevFoodX, prevFoodY, foodX, foodY);
            score += maxVel * 50;

            field.setGameTime(field.getGameTime() + 1);
            prevFoodX = foodX;
            prevFoodY = foodY;
            field.spawnFood();
        }
    }

    private int findDirectionQuad() {
        if (xVel >= 0 && yVel >= 0) {
            return 1;
        }
        else if ((xVel <= 0 && yVel >= 0)) {
            return 2;
        }
        else if ((xVel <= 0 && yVel <= 0)) {
            return 3;
        }
        else {
            return 4;
        }
    }

    void update(boolean leftTurn, boolean rightTurn) {

        int dirQuad = findDirectionQuad();

        if (dirQuad == 1) {
            if (rightTurn) {
                xVel -= turnSpeed;
                yVel += turnSpeed;
            }
            else if (leftTurn) {
                xVel += turnSpeed;
                yVel -= turnSpeed;
            }
        }
        else if (dirQuad == 2) {
            if (rightTurn) {
                xVel -= turnSpeed;
                yVel -= turnSpeed;
            }
            else if (leftTurn) {
                xVel += turnSpeed;
                yVel += turnSpeed;
            }
        }
        else if (dirQuad == 3) {
            if (rightTurn) {
                xVel += turnSpeed;
                yVel -= turnSpeed;
            }
            else if (leftTurn) {
                xVel -= turnSpeed;
                yVel += turnSpeed;
            }
        }
        else if (dirQuad == 4) {
            if (rightTurn) {
                xVel += turnSpeed;
                yVel += turnSpeed;
            }
            else if (leftTurn) {
                xVel -= turnSpeed;
                yVel -= turnSpeed;
            }
        }

        double marginalError = Math.abs(xVel) + Math.abs(yVel) - maxVel;
        if (marginalError > 0) {
            if (xVel > marginalError) {
                xVel -= marginalError;
            }
            else if (xVel < -1 * marginalError) {
                xVel += marginalError;
            }
            else if (yVel > marginalError) {
                yVel -= marginalError;
            }
            else {
                yVel += marginalError;
            }
        }

        if (x + xVel < 0 || x + xVel > screenWidth - beadDiameter) {
            xVel = -1 * xVel;
        }
        if (y + yVel < 0 || y + yVel > screenHeight - beadDiameter) {
            yVel = -1 * yVel;
        }

        board[x][y] = ctr;
        ctr++;

        x = x + (int)xVel;
        y = y + (int)yVel;
        velVector = Math.sqrt((xVel*xVel) + (yVel*yVel));

        checkFoodConsumed();

    }

    double getScore() {
        return score / 100;
    }

    void paint(Graphics2D g) {
        g.setColor(Color.GREEN);

        for (int count = 1; count <= beadCount; count++) {
            for (int i = 0; i < screenWidth; i++) {
                for (int j = 0; j < screenHeight; j++) {
                    if (ctr - board[i][j] == (int)(beadDiameter * count / velVector)) {
                        g.fillOval(i, j, beadDiameter, beadDiameter);
                    }
                }
            }
        }
    }

}
