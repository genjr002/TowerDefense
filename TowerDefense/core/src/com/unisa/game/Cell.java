package com.unisa.game;

import com.badlogic.gdx.math.Rectangle;

import org.w3c.dom.css.Rect;

/**
 * Created by Nick on 7/06/2016.
 */
public class Cell {

    private int xCoord;
    private int yCoord;
    private boolean cellOccupied;
    private Rectangle cellBounds;


    public Cell(int xCoord, int yCoord, boolean cellOccupied, Rectangle cellBounds) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.cellOccupied = cellOccupied;
        this.cellBounds = cellBounds;
    }

    public Rectangle getCellBounds() {
        return cellBounds;
    }

    public void setCellBounds(Rectangle cellBounds) {
        this.cellBounds = cellBounds;
    }

    public int getxCoord() {
        return xCoord;
    }

    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public void setyCoord(int yCoord) {
        this.yCoord = yCoord;
    }

    public boolean isCellOccupied() {
        return cellOccupied;
    }

    public void setCellOccupied(boolean cellOccupied) {
        this.cellOccupied = cellOccupied;
    }
}