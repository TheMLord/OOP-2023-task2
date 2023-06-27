package guiConfig;

import javax.swing.*;
import java.awt.*;
import java.io.*;

import static localization.ApplicationLocalizer.applicationLocalizer;

/**
 * Класс с конфигурацией InternalFrame
 */
public class ConfigInternalFrame implements Serializable {
    private final boolean closedStatus;
    private final boolean viewStatus;
    private final Dimension frameSize;
    private final Point frameLocation;
    private final String frameId;

    public ConfigInternalFrame(JInternalFrame desktopFrames) {
        this.closedStatus = desktopFrames.isClosed();
        this.viewStatus = desktopFrames.isIcon();
        this.frameSize = desktopFrames.getSize();
        this.frameLocation = desktopFrames.getLocation();
        this.frameId = getIdFrame(desktopFrames.getTitle());
    }

    private String getIdFrame(String titleFrame) {
        if (titleFrame.equals(applicationLocalizer.getLocalizedText("titleGameWindow"))) {
            return "gameFrame";
        } else if (titleFrame.equals(applicationLocalizer.getLocalizedText("titleLogWindow"))) {
            return "logFrame";
        }
        return "";
    }

    public ConfigInternalFrame(boolean closedStatus, boolean viewStatus, Dimension frameSize,
                               Point frameLocation, String frameName) {
        this.closedStatus = closedStatus;
        this.viewStatus = viewStatus;
        this.frameSize = frameSize;
        this.frameLocation = frameLocation;
        this.frameId = frameName;
    }


    public boolean getClosedStatus() {
        return this.closedStatus;
    }

    public boolean getViewStatus() {
        return this.viewStatus;
    }

    public Dimension getFrameSize() {
        return this.frameSize;
    }

    public Point getFrameLocation() {
        return this.frameLocation;
    }

    public String getFrameId() {
        return this.frameId;
    }

}
