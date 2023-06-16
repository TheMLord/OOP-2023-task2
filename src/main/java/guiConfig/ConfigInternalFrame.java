package guiConfig;

import javax.swing.*;
import java.awt.*;
import java.io.*;

/**
 * Класс с конфигурацией InternalFrame
 */
public class ConfigInternalFrame implements Serializable {
    private final Boolean closedStatus;
    private final Boolean viewStatus;
    private final Dimension frameSize;
    private final Point frameLocation;
    private final String frameName;

    public ConfigInternalFrame(JInternalFrame desktopFrames) {
        this.closedStatus = desktopFrames.isClosed();
        this.viewStatus = desktopFrames.isIcon();
        this.frameSize = desktopFrames.getSize();
        this.frameLocation = desktopFrames.getLocation();
        this.frameName = desktopFrames.getTitle();
    }

    public ConfigInternalFrame(Boolean closedStatus, Boolean viewStatus, Dimension frameSize,
                               Point frameLocation, String frameName) {
        this.closedStatus = closedStatus;
        this.viewStatus = viewStatus;
        this.frameSize = frameSize;
        this.frameLocation = frameLocation;
        this.frameName = frameName;
    }


    public Boolean getClosedStatus() {
        return this.closedStatus;
    }

    public Boolean getViewStatus() {
        return this.viewStatus;
    }

    public Dimension getFrameSize() {
        return this.frameSize;
    }

    public Point getFrameLocation() {
        return this.frameLocation;
    }

    public String getFrameName() {
        return this.frameName;
    }

}
