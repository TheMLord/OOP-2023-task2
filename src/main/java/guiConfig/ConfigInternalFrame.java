package guiConfig;

import javax.swing.*;
import java.awt.*;
import java.io.*;

/**
 * Класс с конфигурацией InternalFrame
 */
public class ConfigInternalFrame implements Serializable {
    public static final String gameFrameId = "gameFrame";
    public static final String logFrameId = "logFrame";
    private final boolean closedStatus;
    private final boolean frameIsIconStatus;
    private final Dimension frameSize;
    private final Point frameLocation;
    private final String frameId;

    public ConfigInternalFrame(JInternalFrame desktopFrames) {
        this.closedStatus = desktopFrames.isClosed();
        this.frameIsIconStatus = desktopFrames.isIcon();
        this.frameSize = desktopFrames.getSize();
        this.frameLocation = desktopFrames.getLocation();
        this.frameId = getIdFrame(desktopFrames.getTitle());
    }

    private String getIdFrame(String titleFrame) {
        if (titleFrame.equals("Игровое поле")) {
            return gameFrameId;
        } else if (titleFrame.equals("Протокол работы")) {
            return logFrameId;
        }
        return "";
    }

    public ConfigInternalFrame(boolean closedStatus, boolean viewStatus, Dimension frameSize,
                               Point frameLocation, String frameName) {
        this.closedStatus = closedStatus;
        this.frameIsIconStatus = viewStatus;
        this.frameSize = frameSize;
        this.frameLocation = frameLocation;
        this.frameId = frameName;
    }


    public boolean getClosedStatus() {
        return this.closedStatus;
    }

    public boolean getFrameIsIconStatus() {
        return this.frameIsIconStatus;
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
