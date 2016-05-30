package com.unisa.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Json;

import java.io.Serializable;

/**
 * Created by Jon on 18/04/2016.
 */
public class Defense extends Actor{

    private int damage;
    private float atkSpeed;
    private float atkCD;
    private int range;
    private int cost;
    private int x;
    private int y;
    private int type;
    private TextureRegion texture;
    private Circle rangeRadious;
    private Rectangle defenseBounds;
    private Enemy target;
    private Projectile projectile;
    private String description;
    private int numUpgrades;
    private Sprite portrait;


    private Sprite sprite;
    private Sprite rangeSprite;

    public Defense(int type) {

        this.type = type;
        rangeRadious = new Circle();
        target = null;
        projectile = new Projectile(0);

        numUpgrades = 1;

        switch (type){
            case 0:
                damage = 200;
                atkSpeed = 150;
                atkCD = 150;
                range = 200;
                cost = 400;

                texture = new TextureRegion(new Texture("DefenseBasic.png"));
                sprite = new Sprite(texture);
                portrait = new Sprite(texture);
                sprite.setRegionWidth(100);
                sprite.setRegionHeight(100);
                description = "Basic tower";
                break;
            case 1:
                damage = 500;
                atkSpeed = 300;
                atkCD = 300;
                range = 150;
                cost = 800;

                description = "Short range tower with high Damage";

                texture = new TextureRegion(new Texture("DefenseShortRange.png"));
                sprite = new Sprite(texture);
                portrait = new Sprite(texture);

                break;
            case 2:
                damage = 0;
                atkSpeed = 500;
                atkCD = 500;
                range = 400;
                cost = 200;

                Pixmap pixmap2= new Pixmap(100, 100, Pixmap.Format.RGBA8888);
                pixmap2.setColor(1, 1, 0, 05f);
                pixmap2.fill();
                description = "Buffing Tower that deals no damage but increases\n damage of all towers within range";

                texture = new TextureRegion(new Texture("DefenseBuff.png"));
                sprite = new Sprite(texture);
                portrait = new Sprite(texture);

                break;
            case 3:
                damage = 800;
                atkSpeed = 600;
                atkCD = 600;
                range = 400;
                cost = 1500;

                Pixmap pixmap3= new Pixmap(100, 100, Pixmap.Format.RGBA8888);
                pixmap3.setColor(1, 1, 1, 05f);
                pixmap3.fill();

                description = "High Damage long range tower with slow attack speed";
                texture = new TextureRegion(new Texture("DefenseLongRange.png"));
                sprite = new Sprite(texture);
                portrait = new Sprite(texture);

                break;
        }
        defenseBounds = new Rectangle();
    }

    public int getDamage() {
        return damage;
    }

    public float getAtkSpeed() {
        return atkSpeed;
    }

    public int getRange() {
        return range;
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

    public Sprite getSprite() {
        return sprite;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public Circle getRangeRadious() {
        return rangeRadious;
    }

    public void setRangeRadious(Circle rangeRadious) {
        this.rangeRadious = rangeRadious;
    }

    public Rectangle getDefenseBounds() {
        return defenseBounds;
    }

    public void setDefenseBounds(Rectangle defenseBounds) {
        this.defenseBounds = defenseBounds;
    }

    public Enemy getTarget() {
        return target;
    }

    public void setTarget(Enemy target) {
        this.target = target;
    }

    public float getAtkCD() {
        return atkCD;
    }

    public void setAtkCD(float atkCD) {
        this.atkCD = atkCD;
    }

    public void incremmentCD() {
        this.atkCD++;
    }

    public int getCost() {
        return cost;
    }

    public String getDescription() {
        return description;
    }

    // to give visual representation of the defenses range radious
    public Sprite getRangeSprite() {
        return rangeSprite;
    }

    public void setRangeSprite(Sprite rangeSprite) {
        this.rangeSprite = rangeSprite;
    }

    public int getNumUpgrades() {
        return numUpgrades;
    }

    public void setNumUpgrades(int numUpgrades) {
        this.numUpgrades = numUpgrades;
    }

    public Integer getType(){
        return type;
    }

    public Sprite getPortrait() {
        return portrait;
    }

    public void setPortrait(Sprite portrait) {
        this.portrait = portrait;
    }
}
