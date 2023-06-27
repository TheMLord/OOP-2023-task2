package guiConfig;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileConfig {
    private static final String PATH_TO_SAVE = System.getProperty("user.home") + File.separator + "guiRobotsConfig";
    private static final File FILE_CONFIG_MAIN_PANE = new File(PATH_TO_SAVE + File.separator + "configMainPane.bin");
    private static final File FILE_CONFIG_INTERNAL_FRAME = new File(PATH_TO_SAVE + File.separator + "configInternalFrame.bin");

    public FileConfig() {
        checkExistDir();
        checkExistFile();
    }


    private void checkExistDir() {
        File pathToDir = new File(PATH_TO_SAVE);
        if (!pathToDir.exists()) {
            pathToDir.mkdirs();
        }
    }


    private void checkExistFile() {
        if (!FILE_CONFIG_MAIN_PANE.exists()) {
            createConfigFile(FILE_CONFIG_MAIN_PANE);
        }
        if (!FILE_CONFIG_INTERNAL_FRAME.exists()) {
            createConfigFile(FILE_CONFIG_INTERNAL_FRAME);
        }
    }

    private void createConfigFile(File fileConfigName) {
        try {
            fileConfigName.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveFiles(JDesktopPane jDesktopPane) {
        ConfigMainPane configMainPane = new ConfigMainPane(jDesktopPane);

        JInternalFrame[] jInternalFrames = jDesktopPane.getAllFrames();

        ConfigInternalFrame[] configInternalFrames = new ConfigInternalFrame[jInternalFrames.length];
        for (int i = 0; i < jInternalFrames.length; i++) {
            configInternalFrames[i] = new ConfigInternalFrame(jInternalFrames[i]);
        }

        try {
            try (ObjectOutputStream fileOut = new ObjectOutputStream(new FileOutputStream(FILE_CONFIG_MAIN_PANE))) {
                fileOut.writeObject(configMainPane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

        try {
            try (ObjectOutputStream fileOut = new ObjectOutputStream(new FileOutputStream(FILE_CONFIG_INTERNAL_FRAME))) {
                fileOut.writeObject(configInternalFrames);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

    }

    public static ConfigMainPane restoreConfigMainPane() {
        if (FILE_CONFIG_MAIN_PANE.exists()) {
            try (FileInputStream fileIn = new FileInputStream(FILE_CONFIG_MAIN_PANE);
                 ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
                return (ConfigMainPane) objectIn.readObject();

            } catch (IOException | ClassNotFoundException e) {
                return new ConfigMainPane(Toolkit.getDefaultToolkit().getScreenSize());
            }
        } else {
            return new ConfigMainPane(Toolkit.getDefaultToolkit().getScreenSize());
        }
    }

    public static Map<String, ConfigInternalFrame> restoreConfigInternalPane() {
        Map<String, ConfigInternalFrame> configInternalFrameHashMaphashMap = new HashMap<>();

        if (FILE_CONFIG_INTERNAL_FRAME.exists()) {
            try (ObjectInputStream fileIn = new ObjectInputStream(new FileInputStream(FILE_CONFIG_INTERNAL_FRAME))) {

                ConfigInternalFrame[] configInternalFrames = (ConfigInternalFrame[]) fileIn.readObject();
                for (ConfigInternalFrame configInternalFrame : configInternalFrames) {
                    if (configInternalFrame.getFrameName().equals("Протокол работы")) {
                        configInternalFrameHashMaphashMap.put("Протокол работы", configInternalFrame);
                    } else if (configInternalFrame.getFrameName().equals("Игровое поле")) {
                        configInternalFrameHashMaphashMap.put("Игровое поле", configInternalFrame);
                    }
                }
                return configInternalFrameHashMaphashMap;

            } catch (IOException | ClassNotFoundException e) {
                configInternalFrameHashMaphashMap.put(
                        "Протокол работы",
                        new ConfigInternalFrame(
                                false,
                                false,
                                new Dimension(300, 800),
                                new Point(10, 10),
                                "Протокол работы"));
                configInternalFrameHashMaphashMap.put(
                        "Игровое поле",
                        new ConfigInternalFrame(
                                false,
                                false,
                                new Dimension(400, 400),
                                new Point(0, 0),
                                "Игровое поле"));
                return configInternalFrameHashMaphashMap;
            }
        } else {
            configInternalFrameHashMaphashMap.put(
                    "Протокол работы",
                    new ConfigInternalFrame(
                            false,
                            false,
                            new Dimension(300, 800),
                            new Point(10, 10),
                            "Протокол работы"));
            configInternalFrameHashMaphashMap.put(
                    "Игровое поле",
                    new ConfigInternalFrame(
                            false,
                            false,
                            new Dimension(400, 400),
                            new Point(0, 0),
                            "Игровое поле"));
            return configInternalFrameHashMaphashMap;
        }
    }
}