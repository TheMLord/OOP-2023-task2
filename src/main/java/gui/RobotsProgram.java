package gui;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;

import static gui.FileConfig.createTxtFile;
import static gui.FileConfig.readTxtConfig;

/**
 * Метод main
 * UIManager.setLookAndFeel() - устанавливает внешний вид приложения
 * С помощью MainApplicationFrame создаем объект пользовательского окна JFrame приложения
 * frame.setExtendedState(Frame.MAXIMIZED_BOTH) - устанавливает размер и видимость фрейма
 * SwingUtilities.invokeLater() - запускает создание и отображение фрейма в главном потоке событий Swing.
 */
public class RobotsProgram {

    private static HashMap<String, String> configWindowHashMap = new HashMap<>();
    public static ConfigWindow configLogWindow;
    public static ConfigWindow configGameWindow;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
//        UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
//        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
            getConfig(); // получаем конфигурацию панелей
            MainApplicationFrame frame = new MainApplicationFrame();
            frame.pack();
            frame.setVisible(true);
            frame.setExtendedState(Frame.MAXIMIZED_BOTH);

            // Обработка закрытия окна
            frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    System.out.println("Окно закрыто");
                    configWindowHashMap = getMapWindow(frame);
                    try {
                        createTxtFile(configWindowHashMap);
                        System.out.println("Файл успешно создан и заполнен");
                    } catch (IOException exception) {
                        System.out.println("Ошибка при создании файла: " + exception.getMessage());
                    }
                }
            });

        });
    }

    private static HashMap<String, String> getMapWindow(MainApplicationFrame frame) {
        HashMap<String, String> windowConfig = new HashMap<>();

        Pattern patternInt = Pattern.compile("\\d+");
        Pattern patternTitle = Pattern.compile("title=(.*)\\]");

        Component[] components = frame.getContentPane().getComponents();
        for (Component component : components) {
            String name = "";
            Matcher matcherTitle = patternTitle.matcher(component.toString());
            if (matcherTitle.find()) {
                name = matcherTitle.group(1).trim();
            }
//            String showPanel = String.valueOf(component.isShowing());
            String strSize = component.getSize().toString();
            String strLocatiom = component.getLocationOnScreen().toString();

            Matcher matcherSize = patternInt.matcher(strSize);
            Matcher matcherLocation = patternInt.matcher(strLocatiom);

            List<String> sizePanel = new ArrayList<>();
            List<String> locationPanel = new ArrayList<>();

            while (matcherSize.find()) {
                sizePanel.add(matcherSize.group());
            }
            if (sizePanel.size() == 2) {
                windowConfig.put(name + "Size", sizePanel.get(0) + " " + sizePanel.get(1));
            }

            while (matcherLocation.find()) {
                locationPanel.add(matcherLocation.group());
            }
            if (locationPanel.size() == 2) {
                windowConfig.put(name + "Location", locationPanel.get(0) + " " + locationPanel.get(1));
            }
        }
        return windowConfig;
    }

    private static void getConfig() {
        //получаем файл конфигурации
        configWindowHashMap = readTxtConfig();
        System.out.println(configWindowHashMap);
        try {
            String[] locationGameWindow = configWindowHashMap.get("Игровое полеLocation").split(" ");
            String[] SizeGameWindow = configWindowHashMap.get("Игровое полеSize").split(" ");
            String[] locationLogWindow = configWindowHashMap.get("Протокол работыLocation").split(" ");
            String[] SizeLogWindow = configWindowHashMap.get("Протокол работыSize").split(" ");

            configLogWindow = new ConfigWindow(Integer.parseInt(locationLogWindow[0]), Integer.parseInt(locationLogWindow[1]),
                    new Dimension(Integer.parseInt(SizeLogWindow[0]), Integer.parseInt(SizeLogWindow[1])),
                    Integer.parseInt(SizeLogWindow[0]),
                    Integer.parseInt(SizeLogWindow[1]));
            configGameWindow = new ConfigWindow(Integer.parseInt(locationGameWindow[0]), Integer.parseInt(locationGameWindow[1]),
                    new Dimension(Integer.parseInt(SizeGameWindow[0]), Integer.parseInt(SizeGameWindow[1])),
                    Integer.parseInt(SizeGameWindow[0]),
                    Integer.parseInt(SizeGameWindow[1]));
        } catch (Exception e) {
            configLogWindow = new ConfigWindow(0, 0,
                    new Dimension(200, 500), 200, 500);
            configGameWindow = new ConfigWindow(0, 0,
                    new Dimension(400, 400), 400, 400);
            System.out.println("Восстановление параметров по умолчанию");
        }
    }
}
