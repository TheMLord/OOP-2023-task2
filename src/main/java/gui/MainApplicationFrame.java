package gui;


import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

import java.awt.Dimension;
import java.awt.Toolkit;

import java.beans.PropertyVetoException;
import java.util.Map;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;


import controller.GameController;
import model.log.Logger;
import model.robot.ModelRobot;
import model.robot.ModelTarget;

import guiConfig.ConfigInternalFrame;
import guiConfig.ConfigMainPane;
import guiConfig.FileConfig;


public class MainApplicationFrame extends JFrame {
    private final JDesktopPane desktopPane = new JDesktopPane();

    private final ConfigMainPane configMainPane = FileConfig.restoreConfigMainPane();
    private final Map<String, ConfigInternalFrame> configInternalFrames = FileConfig.restoreConfigInternalPane();
    private final ConfigInternalFrame configLogWindow = configInternalFrames.get("Протокол работы");
    private final ConfigInternalFrame configGameWindow = configInternalFrames.get("Игровое поле");

    public MainApplicationFrame() {
        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
                screenSize.width - inset * 2,
                screenSize.height - inset * 2);

        desktopPane.setSize(configMainPane.getMainPaneSize());
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
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveConfigApp();
                exitWindow();
            }
        });
    }

    protected LogWindow createLogWindow() {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        try {
            logWindow.setIcon(configLogWindow.getViewStatus());
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }
        try {
            logWindow.setClosed(configLogWindow.getClosedStatus());
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }
        setMinimumSize(logWindow.getSize());
        logWindow.pack();
        Logger.debug("Протокол работает");
        logWindow.setLocation(configLogWindow.getFrameLocation());
        logWindow.setSize(configLogWindow.getFrameSize());
        return logWindow;
    }

    protected GameWindow createGameWindow(ModelTarget modelTarget, ModelRobot modelRobot, GameController gameController) {
        GameWindow gameWindow = new GameWindow(modelTarget, modelRobot, gameController);
        gameWindow.setLocation(configGameWindow.getFrameLocation());
        gameWindow.setSize(configGameWindow.getFrameSize());
        try {
            gameWindow.setIcon(configGameWindow.getViewStatus());
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }
        try {
            gameWindow.setClosed(configGameWindow.getClosedStatus());
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }
        return gameWindow;
    }

    protected PositionWindow createPositionWindow(ModelRobot modelRobot) {
        PositionWindow positionWindow = new PositionWindow(modelRobot);
        positionWindow.setLocation(20, 20);
        positionWindow.setSize(360, 100);
        return positionWindow;
    }

    protected void exitWindow() {
        UIManager.put("OptionPane.yesButtonText", "Да");
        UIManager.put("OptionPane.noButtonText", "Нет");

        int option = JOptionPane.showConfirmDialog(this,
                "Вы уверены, что хотите закрыть приложение?",
                "Подтверждение",
                JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            setDefaultCloseOperation(EXIT_ON_CLOSE);
        }
    }


    protected void addWindow(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
    }

    private void saveConfigApp() {
        FileConfig fileConfig = new FileConfig();
        fileConfig.saveFiles(desktopPane);
    }
}
