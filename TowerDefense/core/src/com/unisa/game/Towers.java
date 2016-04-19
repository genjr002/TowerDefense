package com.unisa.game;

/**
 * Created by Jon on 18/04/2016.
 */
public class Towers {

    private int damage;
    private int atkSpeed;
    private int range;
    private int x;
    private int y;
    private int type;

    public Towers(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getDamage() {
        return damage;
    }

    public int getAtkSpeed() {
        return atkSpeed;
    }

    public int getRange() {
        return range;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setAtkSpeed(int atkSpeed) {
        this.atkSpeed = atkSpeed;
    }

    public void setRange(int range) {
        this.range = range;
    }
}
