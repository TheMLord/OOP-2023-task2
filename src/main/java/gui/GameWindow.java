package gui;

import controller.GameController;
import localization.LocalizedComponent;
import model.robot.ModelRobot;
import model.robot.ModelTarget;
import view.GameVisualizer;

import java.awt.BorderLayout;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import static localization.ApplicationLocalizer.applicationLocalizer;

public class GameWindow extends JInternalFrame implements LocalizedComponent {
    private final GameVisualizer m_visualizer;

    public GameWindow(ModelTarget modelTarget, ModelRobot modelRobot, GameController gameController) {
        super(applicationLocalizer.getLocalizedText("titleGameWindow"), true, true, true, true);

        m_visualizer = new GameVisualizer(modelTarget, modelRobot, gameController);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }

    @Override
    public void updateComponents() {
        this.setTitle(applicationLocalizer.getLocalizedText("titleGameWindow"));
    }
}
