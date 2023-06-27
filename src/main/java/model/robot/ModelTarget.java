package model.robot;


import java.awt.Point;

public class ModelTarget {
    private volatile int targetPositionX;
    private volatile int targetPositionY;

    public ModelTarget(int x_position, int y_position) {
        targetPositionX = x_position;
        targetPositionY = y_position;
    }

    public int getTargetPositionX() {
        return targetPositionX;
    }

    public int getTargetPositionY() {
        return targetPositionY;
    }

    public void setTargetPosition(Point p) {
        this.targetPositionX = p.x;
        this.targetPositionY = p.y;
    }

}
