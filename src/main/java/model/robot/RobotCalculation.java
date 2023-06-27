package model.robot;

public class RobotCalculation {
    public static double distance(double x1, double y1, double x2, double y2) {
        double diffX = x1 - x2;
        double diffY = y1 - y2;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    public static double angleTo(double fromX, double fromY, double toX, double toY) {
        double diffX = toX - fromX;
        double diffY = toY - fromY;

        return asNormalizedRadians(Math.atan2(diffY, diffX));
    }

    public static double calculateAngularVelocity(double posX_robot, double posY_robot,
                                                  double posX_target, double posY_target,
                                                  double currDirection, double maxAngularVelocity) {
        double angleToTarget = angleTo(posX_robot, posY_robot, posX_target, posY_target);
        double angularVelocity = 0;
        if (angleToTarget > currDirection) {
            angularVelocity = maxAngularVelocity;
        }
        if (angleToTarget < currDirection) {
            angularVelocity = -maxAngularVelocity;
        }
        if (Math.abs(angleToTarget - currDirection) > Math.PI) {
            angularVelocity = -angularVelocity;

        }
        return angularVelocity;
    }

    public static double applyLimits(double value, double min, double max) {
        if (value < min)
            return min;
        return Math.min(value, max);
    }

    public static int round(double value) {
        return (int) (value + 0.5);
    }

    public static double asNormalizedRadians(double angle) {
        while (angle < 0) {
            angle += 2 * Math.PI;
        }
        while (angle >= 2 * Math.PI) {
            angle -= 2 * Math.PI;
        }
        return angle;
    }
}
