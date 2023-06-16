package guiConfig;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

/**
 * Класс с конфигурацией JDesktopPane
 */
public class ConfigMainPane implements Serializable {
    private final Dimension mainPaneSize;

    public ConfigMainPane(JDesktopPane desktopPane) {
        this.mainPaneSize = desktopPane.getSize();
    }

    public ConfigMainPane(Dimension mainPaneSize) {
        this.mainPaneSize = mainPaneSize;
    }

    public Dimension getMainPaneSize() {
        return this.mainPaneSize;
    }

}
