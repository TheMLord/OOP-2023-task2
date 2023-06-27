package gui;

import controller.GameController;
import model.robot.ModelRobot;
import model.robot.ModelTarget;
import view.GameVisualizer;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import static localization.ApplicationLocalizer.applicationLocalizer;

public class GameWindow extends JInternalFrame implements Observer {
    private final GameVisualizer m_visualizer;
    private static String titleWindow = applicationLocalizer.getLocalizedText("titleGameWindow");

    public GameWindow(ModelTarget modelTarget, ModelRobot modelRobot, GameController gameController) {
        super(titleWindow, true, true, true, true);
        applicationLocalizer.addObserver(this);

        m_visualizer = new GameVisualizer(modelTarget, modelRobot, gameController);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof String) {
            String argument = (String) arg;
            if (argument.equals("CHANGE_LANGUAGE")) {
                updateComponents();
            }
        }
    }

    private void updateComponents() {
        titleWindow = applicationLocalizer.getLocalizedText("titleGameWindow");
        this.setTitle(titleWindow);
    }
}
