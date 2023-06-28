package gui;

import model.log.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import static localization.ApplicationLocalizer.applicationLocalizer;


/**
 * Класс с MenuBar для графического интерфейса
 */
public class MenuBar extends JMenuBar {
    private final MainApplicationFrame mainFrame;

    /**
     * Конструктор класса
     *
     * @param mainAppFrame - объект главного фрейма, на который добавляется JMenuBar
     */
    public MenuBar(MainApplicationFrame mainAppFrame) {
        mainFrame = mainAppFrame;

        add(prepareFileMenu());
        add(prepareLookAndFeelMenu());
        add(prepareTestMenu());
        add(prepareLanguageMenu());
    }

    private JMenu prepareFileMenu() {
        JMenu fileMenu = new JMenu(applicationLocalizer.getLocalizedText("menuTitleFile"));
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
        return fileMenu;
    }

    /**
     * Функция, которая подготавливает меню отображения
     *
     * @return возвращает LookAndFeelMenu для JMenuBar
     */
    private JMenu prepareLookAndFeelMenu() {
        JMenu lookAndFeelMenu = new JMenu(applicationLocalizer.getLocalizedText("menuTitleDisplayMode"));
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
        return lookAndFeelMenu;
    }

    /**
     * Функция, которая подготавливает Test меню
     *
     * @return возвращает TestMenu для JMenuBar
     */
    private JMenu prepareTestMenu() {
        JMenu testMenu = new JMenu(applicationLocalizer.getLocalizedText("menuTitleTests"));
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
        return testMenu;
    }

    private JMenu prepareLanguageMenu() {
        JMenu languageMenu = new JMenu(applicationLocalizer.getLocalizedText("menuTitleLanguage"));

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
        return languageMenu;
    }

    private void setLookAndFeel(String className) {
        try {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (ClassNotFoundException | InstantiationException
                 | IllegalAccessException | UnsupportedLookAndFeelException ignored) {
        }
    }

}
