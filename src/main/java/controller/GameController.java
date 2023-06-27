package controller;

import model.robot.ModelTarget;

import java.awt.Point;

public class GameController {
    private final ModelTarget modelTarget;

    public GameController(ModelTarget target) {
        this.modelTarget = target;
    }

    public void changeTargetPosition(Point p) {
        modelTarget.setTargetPosition(p);
    }

}
