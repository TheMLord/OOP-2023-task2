package gui;

import java.awt.*;

public class ConfigWindow {

    //    private Integer locationX;
//    private Integer locationY;
//    private Integer SizeWidth;
//    private Integer SizeHeight;
    private Dimension size;
    private Point location;

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
