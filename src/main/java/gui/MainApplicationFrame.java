package gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;


import controller.GameController;
import model.log.Logger;
import model.robot.ModelRobot;
import model.robot.ModelTarget;

public class MainApplicationFrame extends JFrame {
    private final JDesktopPane desktopPane = new JDesktopPane();

    public MainApplicationFrame() {
        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
                screenSize.width - inset * 2,
                screenSize.height - inset * 2);

        setContentPane(desktopPane);

        ModelTarget modelTarget = new ModelTarget(100, 100);
        ModelRobot modelRobot = new ModelRobot(modelTarget, 100, 150);
        GameController gameController = new GameController(modelTarget);

        LogWindow logWindow = createLogWindow();
        addWindow(logWindow);

        GameWindow gameWindow = createGameWindow(modelTarget, modelRobot, gameController);
        addWindow(gameWindow);

        PositionWindow positionWindow = createPositionWindow(modelRobot);
        addWindow(positionWindow);

        setJMenuBar(new MenuBar(this));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    protected LogWindow createLogWindow() {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        logWindow.setLocation(10, 10);
        logWindow.setSize(300, 800);
        setMinimumSize(logWindow.getSize());
        logWindow.pack();
        Logger.debug("Протокол работает");
        return logWindow;
    }

    protected GameWindow createGameWindow(ModelTarget modelTarget, ModelRobot modelRobot, GameController gameController) {
        GameWindow gameWindow = new GameWindow(modelTarget, modelRobot, gameController);
        gameWindow.setSize(400, 400);
        return gameWindow;
    }

    protected PositionWindow createPositionWindow(ModelRobot modelRobot) {
        PositionWindow positionWindow = new PositionWindow(modelRobot);
        positionWindow.setLocation(20, 20);
        positionWindow.setSize(360, 100);
        return positionWindow;
    }

    protected void addWindow(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
    }
}
