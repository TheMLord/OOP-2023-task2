package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.Map;


import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;

import guiConfig.ConfigInternalFrame;
import guiConfig.ConfigMainPane;
import guiConfig.FileConfig;
import log.Logger;

public class MainApplicationFrame extends JFrame {
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

        LogWindow logWindow = createLogWindow();
        addWindow(logWindow);

        GameWindow gameWindow = createGameWindow();
        addWindow(gameWindow);

        setJMenuBar(new MenuBar(this));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveConfigApp();
            }

        });
    }

    protected LogWindow createLogWindow() {
        ConfigInternalFrame configLogWindow = configInternalFrames.get("logFrame");

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
        Logger.debug("Протокол работает");
        logWindow.setLocation(configLogWindow.getFrameLocation());
        logWindow.setSize(configLogWindow.getFrameSize());
        return logWindow;
    }

    protected GameWindow createGameWindow() {
        ConfigInternalFrame configGameWindow = configInternalFrames.get("gameFrame");

        GameWindow gameWindow = new GameWindow();
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

    protected void addWindow(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
    }

    private void saveConfigApp() {
        FileConfig.saveFiles(desktopPane);
    }
}
