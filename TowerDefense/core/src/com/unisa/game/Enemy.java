package com.unisa.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Jon on 18/04/2016.
 */
public class Enemy {

    private int health;
    private int type;
    private float speed;
    private int dmgMod;
    private int x;
    private int y;
    private boolean carryingGold;
    private Sprite[] walkingSouth;
    private Sprite[] walkingNorth;
    private Sprite[] walkingEast;
    private Sprite[] walkingWest;

    private TextureRegion texture;
    private Sprite sprite;

    public Enemy(int type){

        switch (type){
            //basic footmen

            case (0):
                Pixmap pixmap= new Pixmap(40, 40, Pixmap.Format.RGBA8888);
                pixmap.setColor(1, 0, 0, 05f);
                pixmap.fill();

                texture = new TextureRegion(new Texture(pixmap));
                sprite = new Sprite(texture);


                health = 50;
                speed = 0.3f;
                dmgMod = 1;
                //implement appropriate spriteSheets
                break;

            //thief
            case (1):
                health = 30;
                speed = 8;
                dmgMod = 1;
                //implement appropriate spriteSheets
                break;

            //tank
            case (2):
                health = 30;
                speed = 5;
                dmgMod = 1;
                //implement appropriate spriteSheets
                break;
        }

        y = Gdx.graphics.getHeight() - 45;
        x = 365;
        this.sprite.setPosition(x,y);
        carryingGold = false;
    }

    public int getHealth() {
        return health;
    }

    public int getType() {
        return type;
    }

    public float getSpeed() {
        return speed;
    }

    public int getDmgMod() {
        return dmgMod;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isCarryingGold() {
        return carryingGold;
    }

    public Sprite[] getWalkingSouth() {
        return walkingSouth;
    }

    public Sprite[] getWalkingNorth() {
        return walkingNorth;
    }

    public Sprite[] getWalkingEast() {
        return walkingEast;
    }

    public Sprite[] getWalkingWest() {
        return walkingWest;
    }



    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setDmgMod(int dmgMod) {
        this.dmgMod = dmgMod;
    }

    public void setX(int x) {
        this.x = x;
        this.sprite.setPosition(x,y);
    }
    public void setY(int y) {
        this.y = y;
        this.sprite.setPosition(x,y);
    }
    public void setHealth(int health) {
        this.health = health;
    }

    public void setCarryingGold(boolean carryingGold) {
        this.carryingGold = carryingGold;
    }

    public Sprite getSprite() {
        return sprite;
    }
}
