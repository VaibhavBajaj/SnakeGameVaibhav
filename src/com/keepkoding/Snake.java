package com.keepkoding;

import java.awt.Color;
import java.awt.Graphics2D;

// Wherever I have ignored 'private' or 'public', it is package-private
// This class will create an instance of our snake
class Snake {

    // x and y store coordinates of snake
    private int x, y;
    // ctr is used to mark the positions visited by the snake-head in the 2D array board[][]
    private int ctr;
    private double
            // Velocities in x and y direction
            xVel,
            yVel,
            // Speed at which snake turns
            turnSpeed,
            // Vector with total velocity value
            velVector,
            // Maximum velocity of the snake
            maxVel,
            score;

    private int screenWidth,
            screenHeight,
            // Diameter of globules of food
            foodDiameter,
            // beadCount stores number of beads
            beadCount,
            // Coordinates of previously spawned food.
            prevFoodX,
            prevFoodY;
    // Diameter of snake's beads
    private final int beadDiameter = 30;
    // 2-D array board which stores ctr in positions previously visited by the snake-head.
    private long[][] board;
    private Field field;

    Snake(Field field) {

        this.field = field;

        // Initializing variables
        screenWidth = SnakeGame.screenWidth;
        screenHeight = SnakeGame.screenHeight;
        foodDiameter = Field.foodDiameter;

        score = 0;
        beadCount = 2;

        x = (int)(Math.random() * (screenWidth - 2 * beadDiameter)) + beadDiameter;
        y = (int)(Math.random() * (screenHeight - 2 * beadDiameter)) + beadDiameter;

        ctr = 1;

        // Initializing board with first ctr value on spawn position of snake-head
        board = new long[screenWidth][screenHeight];
        board[x][y] = ctr;
        ctr++;

        // Initializing velocities
        maxVel = 6;
        turnSpeed = calcTurnSpeed(maxVel);
        xVel = Math.random() * maxVel;
        yVel = maxVel - xVel;

        // The first values of prevFoodX and prevFoodY are the spawn co-ordinates of the snake
        prevFoodX = x;
        prevFoodY = y;
    }

    private double calcTurnSpeed(double maxVel) {
        return maxVel / 20;
    }

    // Calculates distance between two co-ordinates
    private int distBetween (int x1, int y1, int x2, int y2) {
        return (int)(Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2)));
    }

    // Checks if food has been consumed
    private void checkFoodConsumed() {
        int foodX = field.getFoodX();
        int foodY = field.getFoodY();

        // If the bead is touching the food
        if (distBetween(x, y, foodX, foodY) <= (beadDiameter + foodDiameter) / 2) {
            // Increase snake length
            beadCount++;
            // Increase maximum velocity
            maxVel += 0.5;
            // Update turnSpeed
            turnSpeed = calcTurnSpeed(maxVel);

            // Increase score by distance between the spawn positions of the previous and current food.
            score += distBetween(prevFoodX, prevFoodY, foodX, foodY);
            // Also add current velocity to the equation.
            score += maxVel * 50;
            // Increase time till game ends by a fraction of itself
            field.setGameTime(field.getGameTime() + 0.05 * field.getGameTime());

            // Update prevFood co-ordinates and spawn another piece of food.
            prevFoodX = foodX;
            prevFoodY = foodY;
            field.spawnFood();
        }
    }

    // This function figures out the quadrant the snake is heading in.
    private int findDirectionQuad() {
        // Simple stuff...
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

        // Turn the snake accordingly
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

        // Make sure that velocity sum is equivalent to maxVel
        double marginalError = Math.abs(xVel) + Math.abs(yVel) - maxVel;
        // If not, update accordingly
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

        // if snake hits a wall, bounce it off.
        if (x + xVel < 0 || x + xVel > screenWidth - beadDiameter) {
            xVel = -1 * xVel;
        }
        if (y + yVel < 0 || y + yVel > screenHeight - beadDiameter) {
            yVel = -1 * yVel;
        }

        // Update the board with latest ctr values.
        board[x][y] = ctr;
        ctr++;

        // Update snake head co-ordinates
        x = x + (int)xVel;
        y = y + (int)yVel;
        // Update velVector values
        velVector = Math.sqrt((xVel*xVel) + (yVel*yVel));

        checkFoodConsumed();

    }

    double getScore() {
        return score / 100;
    }

    // Paint the snake with all components
    void paint(Graphics2D g) {

        g.setColor(Color.GREEN);
        // For all the beads, check the board[][] and spawn the beads in decreasing
        // order of ctr till all beads are spawned
        for (int count = 1; count <= beadCount; count++) {
            for (int i = 0; i < screenWidth; i++) {
                for (int j = 0; j < screenHeight; j++) {
                    // ctr - board[i][j] -> Gives me the distance travelled by the snake since he visited board[i][j]
                    // beadDiameter * count -> Distance of the bead "count" from head-bead
                    // velVector -> Divide by velVector so that they appear at uniform lengths no matter the speed
                    // If they are equal, a bead should be spawned
                    if (ctr - board[i][j] == (int)(beadDiameter * count / velVector)) {
                        // Print the bead!
                        g.fillOval(i, j, beadDiameter, beadDiameter);
                    }
                }
            }
        }
    }

}
