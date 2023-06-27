package gui;

import model.robot.ModelRobot;

import javax.swing.*;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class PositionWindow extends JInternalFrame implements Observer {
    private final ModelRobot modelRobot;

    private final JLabel xPositionLabel;
    private final JLabel yPositionLabel;


    public PositionWindow(ModelRobot modelRobot) {
        super("Координаты робота", true, true, true, true);
        this.modelRobot = modelRobot;
        modelRobot.addObserver(this);

        xPositionLabel = new JLabel("X : %f".formatted(modelRobot.getRobotPositionX()));
        yPositionLabel = new JLabel("Y : %f".formatted(modelRobot.getRobotPositionY()));

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(xPositionLabel, BorderLayout.CENTER);
        panel.add(yPositionLabel, BorderLayout.AFTER_LAST_LINE);
        getContentPane().add(panel);
        pack();

    }

    @Override
    public void update(Observable o, Object arg) {
        EventQueue.invokeLater(this::updateRobotPosition);
    }

    private void updateRobotPosition() {
        xPositionLabel.setText("X : %f".formatted(modelRobot.getRobotPositionX()));
        yPositionLabel.setText("Y : %f".formatted(modelRobot.getRobotPositionY()));

    }
}
