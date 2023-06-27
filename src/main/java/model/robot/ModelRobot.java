package model.robot;

import java.util.Observable;

import static model.robot.RobotCalculation.*;

public class ModelRobot extends Observable {
    private final ModelTarget modelTarget;

    private double robotPositionX;
    private double robotPositionY;
    private double robotDirection = 0;

    private static final double maxVelocity = 0.1;
    private static final double maxAngularVelocity = 0.001;

    public ModelRobot(ModelTarget target, double x_position, double y_position) {
        this.modelTarget = target;
        this.robotPositionX = x_position;
        this.robotPositionY = y_position;
    }

    public void changePositionRobot() {
        if (isNearby()) {
            return;
        }
        moveRobot(
                maxVelocity,
                calculateAngularVelocity(
                        robotPositionX, robotPositionY,
                        modelTarget.getTargetPositionX(), modelTarget.getTargetPositionY(),
                        robotDirection, maxAngularVelocity),
                10
        );
        setChanged();
        notifyObservers();
    }

    private boolean isNearby() {
        return distance(modelTarget.getTargetPositionX(), modelTarget.getTargetPositionY(),
                robotPositionX, robotPositionY) < 0.5;
    }

    private void moveRobot(double velocity, double angularVelocity, double duration) {
        velocity = applyLimits(velocity, 0, maxVelocity);
        angularVelocity = applyLimits(angularVelocity, -maxAngularVelocity, maxAngularVelocity);

        double newX = robotPositionX + velocity / angularVelocity *
                (Math.sin(robotDirection + angularVelocity * duration) -
                        Math.sin(robotDirection));
        if (!Double.isFinite(newX)) {
            newX = robotPositionX + velocity * duration * Math.cos(robotDirection);
        }

        double newY = robotPositionY - velocity / angularVelocity *
                (Math.cos(robotDirection + angularVelocity * duration) -
                        Math.cos(robotDirection));
        if (!Double.isFinite(newY)) {
            newY = robotPositionY + velocity * duration * Math.sin(robotDirection);
        }

        robotPositionX = newX;
        robotPositionY = newY;

        robotDirection = asNormalizedRadians(robotDirection + angularVelocity * duration);
    }

    public double getRobotPositionX() {
        return robotPositionX;
    }

    public double getRobotPositionY() {
        return robotPositionY;
    }

    public double getRobotDirection() {
        return robotDirection;
    }
}
