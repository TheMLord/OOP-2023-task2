package gui;

import model.log.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;

import static localization.ApplicationLocalizer.applicationLocalizer;


/**
 * Класс с MenuBar для графического интерфейса
 */
public class MenuBar extends JMenuBar implements Observer {
    private final MainApplicationFrame mainFrame;

    private static String fileMenuTitle = applicationLocalizer.getLocalizedText("menuTitleFile");
    private static String displayModeMenuTitle = applicationLocalizer.getLocalizedText("menuTitleDisplayMode");
    private static String testMenuTitle = applicationLocalizer.getLocalizedText("menuTitleTests");
    private static String languageMenuTitle = applicationLocalizer.getLocalizedText("menuTitleLanguage");

    private final JMenu fileMenu = new JMenu(fileMenuTitle);
    private final JMenu lookAndFeelMenu = new JMenu(displayModeMenuTitle);
    private final JMenu testMenu = new JMenu(testMenuTitle);
    private final JMenu languageMenu = new JMenu(languageMenuTitle);

    /**
     * Конструктор класса
     *
     * @param mainAppFrame - объект главного фрейма, на который добавляется JMenuBar
     */
    public MenuBar(MainApplicationFrame mainAppFrame) {
        mainFrame = mainAppFrame;

        applicationLocalizer.addObserver(this);

        prepareFileMenu();
        prepareLookAndFeelMenu();
        prepareTestMenu();
        prepareLanguageMenu();

        add(fileMenu);
        add(lookAndFeelMenu);
        add(testMenu);
        add(languageMenu);
    }

    private void prepareFileMenu() {
        fileMenu.setMnemonic(KeyEvent.VK_T);
        fileMenu.getAccessibleContext().setAccessibleDescription(
                applicationLocalizer.getLocalizedText("menuFileDescriptionComponent"));
        {
            JMenuItem addLogMessageItem = new JMenuItem(applicationLocalizer.getLocalizedText("menuFileItemExit"), KeyEvent.VK_S);
            addLogMessageItem.addActionListener((event) -> {
                WindowEvent closingEvent = new WindowEvent(mainFrame, WindowEvent.WINDOW_CLOSING);
                Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closingEvent);
            });
            fileMenu.add(addLogMessageItem);
        }
    }

    /**
     * Функция, которая подготавливает меню отображения
     *
     * @return возвращает LookAndFeelMenu для JMenuBar
     */
    private void prepareLookAndFeelMenu() {
        lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
        lookAndFeelMenu.getAccessibleContext().setAccessibleDescription(
                applicationLocalizer.getLocalizedText("menuDisplayModeDescriptionComponent"));

        {
            JMenuItem systemLookAndFeel = new JMenuItem(applicationLocalizer.getLocalizedText("menuFileItemSystemScheme"), KeyEvent.VK_S);
            systemLookAndFeel.addActionListener((event) -> {
                setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                mainFrame.invalidate();
            });
            lookAndFeelMenu.add(systemLookAndFeel);
        }

        {
            JMenuItem crossplatformLookAndFeel = new JMenuItem(applicationLocalizer.getLocalizedText("menuFileItemUniversalScheme"), KeyEvent.VK_S);
            crossplatformLookAndFeel.addActionListener((event) -> {
                setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                mainFrame.invalidate();
            });
            lookAndFeelMenu.add(crossplatformLookAndFeel);
        }
    }

    /**
     * Функция, которая подготавливает Test меню
     *
     * @return возвращает TestMenu для JMenuBar
     */
    private void prepareTestMenu() {
        testMenu.setMnemonic(KeyEvent.VK_T);
        testMenu.getAccessibleContext().setAccessibleDescription(
                applicationLocalizer.getLocalizedText("menuTestsModeDescriptionComponent"));

        {
            JMenuItem addLogMessageItem = new JMenuItem(applicationLocalizer.getLocalizedText("menuTestsItemMessageInLog"), KeyEvent.VK_S);
            addLogMessageItem.addActionListener((event) -> {
                Logger.debug(Logger.loggerDefaultMessage);
            });
            testMenu.add(addLogMessageItem);
        }
    }

    private void prepareLanguageMenu() {
        languageMenu.setMnemonic(KeyEvent.VK_T);

        JRadioButtonMenuItem englishMenuItem = new JRadioButtonMenuItem(applicationLocalizer.getLocalizedText("menuButtonLanguageEn"));
        englishMenuItem.addActionListener((event) -> {
            applicationLocalizer.changeLanguage("en");
            mainFrame.revalidate();
            mainFrame.repaint();

        });

        JRadioButtonMenuItem russianMenuItem = new JRadioButtonMenuItem(applicationLocalizer.getLocalizedText("menuButtonLanguageRu"));
        russianMenuItem.addActionListener((event) -> {
            applicationLocalizer.changeLanguage("ru");
            mainFrame.revalidate();
            mainFrame.repaint();
        });

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(englishMenuItem);
        buttonGroup.add(russianMenuItem);

        if (applicationLocalizer.getCurrentLanguage().equals("en")) {
            englishMenuItem.setSelected(true);

        } else {
            russianMenuItem.setSelected(true);
        }

        languageMenu.add(englishMenuItem);
        languageMenu.add(russianMenuItem);
    }

    private void setLookAndFeel(String className) {
        try {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (ClassNotFoundException | InstantiationException
                 | IllegalAccessException | UnsupportedLookAndFeelException ignored) {
        }
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
        fileMenuTitle = applicationLocalizer.getLocalizedText("menuTitleFile");
        displayModeMenuTitle = applicationLocalizer.getLocalizedText("menuTitleDisplayMode");
        testMenuTitle = applicationLocalizer.getLocalizedText("menuTitleTests");
        languageMenuTitle = applicationLocalizer.getLocalizedText("menuTitleLanguage");

        fileMenu.setText(fileMenuTitle);
        lookAndFeelMenu.setText(displayModeMenuTitle);
        testMenu.setText(testMenuTitle);
        languageMenu.setText(languageMenuTitle);

        fileMenu.removeAll();
        lookAndFeelMenu.removeAll();
        testMenu.removeAll();
        languageMenu.removeAll();


        prepareFileMenu();
        prepareLookAndFeelMenu();
        prepareTestMenu();
        prepareLanguageMenu();
    }
}
