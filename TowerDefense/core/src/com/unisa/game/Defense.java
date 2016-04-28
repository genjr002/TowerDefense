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

/**
 * Created by Jon on 18/04/2016.
 */
public class Defense extends Actor {

    private int damage;
    private int atkSpeed;
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


    private Sprite sprite;
    private Sprite rangeSprite;

    public Defense(int type) {

        rangeRadious = new Circle();
        target = null;
        projectile = new Projectile(0);

        switch (type){
            case 0:
                damage = 5;
                atkSpeed = 5;
                range = 200;
                cost = 200;

                Pixmap pixmap= new Pixmap(100, 100, Pixmap.Format.RGBA8888);
                pixmap.setColor(1, 0, 0, 05f);
                pixmap.fill();

                texture = new TextureRegion(new Texture(pixmap));
                sprite = new Sprite(texture);
                sprite.setRegionWidth(100);
                sprite.setRegionHeight(100);

                break;
            case 1:
                damage = 5;
                atkSpeed = 5;
                range = 300;
                cost = 200;

                Pixmap pixmap1= new Pixmap(100, 100, Pixmap.Format.RGBA8888);
                pixmap1.setColor(1, 0, 1, 05f);
                pixmap1.fill();

                texture = new TextureRegion(new Texture(pixmap1));
                sprite = new Sprite(texture);

                break;
            case 2:
                damage = 5;
                atkSpeed = 5;
                range = 400;
                cost = 200;

                Pixmap pixmap2= new Pixmap(100, 100, Pixmap.Format.RGBA8888);
                pixmap2.setColor(1, 1, 0, 05f);
                pixmap2.fill();

                texture = new TextureRegion(new Texture(pixmap2));
                sprite = new Sprite(texture);

                break;
            case 3:
                damage = 5;
                atkSpeed = 5;
                range = 700;
                cost = 200;

                Pixmap pixmap3= new Pixmap(100, 100, Pixmap.Format.RGBA8888);
                pixmap3.setColor(1, 1, 1, 05f);
                pixmap3.fill();

                texture = new TextureRegion(new Texture(pixmap3));
                sprite = new Sprite(texture);

                break;

        }
        defenseBounds = new Rectangle();
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




// to give visual representation of the defenses range radious
    public Sprite getRangeSprite() {
        return rangeSprite;
    }

    public void setRangeSprite(Sprite rangeSprite) {
        this.rangeSprite = rangeSprite;
    }


}
