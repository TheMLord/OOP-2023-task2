package gui;

import java.awt.*;

public class ConfigWindow {

    private final Dimension size;
    private final Point location;

    public ConfigWindow(Point location, Dimension size) {
        this.size = size;
        this.location = location;
    }

    public Point getLocation() {
        return location;
    }

    public Dimension getSize() {
        return size;
    }


}
