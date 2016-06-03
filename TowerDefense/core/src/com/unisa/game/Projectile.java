package com.unisa.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by Jon on 25/04/2016.
 */
public class Projectile {

    TextureRegion texture;
    Sprite sprite;
    Circle projectile;
    Defense defense;
    float x;
    float y;
    int damage;
    String origin; // what shot this projectile
    private Random rand = new Random();
    public Projectile(int type) {
        switch (type) {
            case 0:
                texture = new TextureRegion(new Texture("projectile.png"));
                sprite = new Sprite(texture);
                sprite.setSize(30, 30);
                sprite.rotate(rand.nextInt(180));
                origin = "tower";
                break;

            case 1:
                texture = new TextureRegion(new Texture("projectile.png"));
                sprite = new Sprite(texture);
                sprite.setSize(30, 30);
                sprite.rotate(rand.nextInt(180));
                origin = "dragon";
        }
    }

    public TextureRegion getTexture() {
        return texture;
    }

    public void setTexture(TextureRegion texture) {
        this.texture = texture;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public Circle getProjectile() {
        return projectile;
    }

    public void setProjectile(Circle projectile) {
        this.projectile = projectile;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public Defense getDefense() {
        return defense;
    }

    public void setDefense(Defense defense) {
        this.defense = defense;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public String getOrigin() {
        return origin;
    }
}
