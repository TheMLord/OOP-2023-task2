package gui;


import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

import java.beans.PropertyVetoException;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;


import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;


import controller.GameController;
import localization.LocalizedComponent;
import model.log.Logger;
import model.robot.ModelRobot;
import model.robot.ModelTarget;

import guiConfig.ConfigInternalFrame;
import guiConfig.ConfigMainPane;
import guiConfig.FileConfig;

import static guiConfig.ConfigInternalFrame.*;
import static localization.ApplicationLocalizer.applicationLocalizer;

public class MainApplicationFrame extends JFrame implements Observer {
    private final JDesktopPane desktopPane = new JDesktopPane();

    private final ConfigMainPane configMainPane = FileConfig.restoreConfigMainPane();
    private final Map<String, ConfigInternalFrame> configInternalFrames = FileConfig.restoreConfigInternalPane();

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

        applicationLocalizer.addObserver(this);

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
        ConfigInternalFrame configLogWindow = configInternalFrames.get(logFrameId);

        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        try {
            logWindow.setIcon(configLogWindow.getFrameIsIconStatus());
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        try {
            logWindow.setClosed(configLogWindow.getClosedStatus());
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        setMinimumSize(logWindow.getSize());
        logWindow.pack();
        Logger.debug(Logger.loggerDefaultMessage);
        logWindow.setLocation(configLogWindow.getFrameLocation());
        logWindow.setSize(configLogWindow.getFrameSize());
        return logWindow;
    }

    protected GameWindow createGameWindow(ModelTarget modelTarget, ModelRobot modelRobot, GameController gameController) {
        ConfigInternalFrame configGameWindow = configInternalFrames.get(gameFrameId);
        GameWindow gameWindow = new GameWindow(modelTarget, modelRobot, gameController);

        gameWindow.setLocation(configGameWindow.getFrameLocation());
        gameWindow.setSize(configGameWindow.getFrameSize());
        try {
            gameWindow.setIcon(configGameWindow.getFrameIsIconStatus());
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        try {
            gameWindow.setClosed(configGameWindow.getClosedStatus());
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        return gameWindow;
    }

    protected PositionWindow createPositionWindow(ModelRobot modelRobot) {
        ConfigInternalFrame configPositionWindow = configInternalFrames.get(positionFrameId);
        PositionWindow positionWindow = new PositionWindow(modelRobot);

        positionWindow.setLocation(configPositionWindow.getFrameLocation());
        positionWindow.setSize(configPositionWindow.getFrameSize());
        try {
            positionWindow.setIcon(configPositionWindow.getFrameIsIconStatus());
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        try {
            positionWindow.setClosed(configPositionWindow.getClosedStatus());
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        return positionWindow;
    }

    protected void exitWindow() {
        UIManager.put("OptionPane.yesButtonText", applicationLocalizer.getLocalizedText("exitPaneYesButtonText"));
        UIManager.put("OptionPane.noButtonText", applicationLocalizer.getLocalizedText("exitPaneNoButtonText"));

        int option = JOptionPane.showConfirmDialog(this,
                applicationLocalizer.getLocalizedText("exitPaneMessageText"),
                applicationLocalizer.getLocalizedText("exitPaneTitle"),
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
        FileConfig.saveFiles(desktopPane);
    }

    private void changeLocalization() {
        for (Component component : desktopPane.getComponents()) {
            if (component instanceof LocalizedComponent) {
                ((LocalizedComponent) component).updateComponents();
            }
        }
        setJMenuBar(new MenuBar(this));
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof String) {
            String argument = (String) arg;
            if (argument.equals("CHANGE_LANGUAGE")) {
                changeLocalization();
            }
        }
    }
}
