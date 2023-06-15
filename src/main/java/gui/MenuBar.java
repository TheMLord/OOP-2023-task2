package gui;


import log.Logger;

import javax.swing.*;
import java.awt.event.KeyEvent;

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
        add(prepareLookAndFeelMenu());
        add(prepareTestMenu());
    }

    /**
     * Функция, которая подготавливает меню отображения
     *
     * @return возвращает LookAndFeelMenu для JMenuBar
     */
    private JMenu prepareLookAndFeelMenu() {
        JMenu lookAndFeelMenu = new JMenu("Режим отображения");
        lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
        lookAndFeelMenu.getAccessibleContext().setAccessibleDescription(
                "Управление режимом отображения приложения");

        {
            JMenuItem systemLookAndFeel = new JMenuItem("Системная схема", KeyEvent.VK_S);
            systemLookAndFeel.addActionListener((event) -> {
                setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                mainFrame.invalidate();
            });
            lookAndFeelMenu.add(systemLookAndFeel);
        }

        {
            JMenuItem crossplatformLookAndFeel = new JMenuItem("Универсальная схема", KeyEvent.VK_S);
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
        JMenu testMenu = new JMenu("Тесты");
        testMenu.setMnemonic(KeyEvent.VK_T);
        testMenu.getAccessibleContext().setAccessibleDescription(
                "Тестовые команды");

        {
            JMenuItem addLogMessageItem = new JMenuItem("Сообщение в лог", KeyEvent.VK_S);
            addLogMessageItem.addActionListener((event) -> {
                Logger.debug("Новая строка");
            });
            testMenu.add(addLogMessageItem);
        }
        return testMenu;
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
