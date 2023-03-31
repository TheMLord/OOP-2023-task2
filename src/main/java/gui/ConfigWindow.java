package gui;

import java.awt.*;

public class ConfigWindow {

    private Integer locationX;
    private Integer locationY;
    private Integer SizeWidth;
    private Integer SizeHeight;
    private Dimension size;


    public ConfigWindow(Integer locationX, Integer locationY, Dimension size, Integer width, Integer height) {
        this.locationX = locationX;
        this.locationY = locationY;
        this.size = size;
        this.SizeWidth = width;
        this.SizeHeight = height;

    }

    public Integer getLocationX() {
        return locationX;
    }

    public Integer getLocationY() {
        return locationY;
    }

    public Integer getWidth() {
        return SizeWidth;
    }

    public Integer getHeight() {
        return SizeHeight;
    }

    public Dimension getSize() {
        return size;
    }


}
