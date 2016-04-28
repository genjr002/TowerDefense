package com.unisa.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;




/**
 * Created by Jon on 18/04/2016.
 */
public class Enemy {

    private int health;
    private int type;
    private int speed;
    private int dmgMod;
    private int x;
    private int y;
    private boolean carryingGold;
    private int movementPoint;
    private Rectangle hitBox;
    private Circle circle;

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


                health = 800;
                speed = 1;
                dmgMod = 1;
                //implement appropriate spriteSheets
                break;

            //thief
            case (1):
                Pixmap pixmap1= new Pixmap(40, 40, Pixmap.Format.RGBA8888);
                pixmap1.setColor(1, 0, 1, 05f);
                pixmap1.fill();

                texture = new TextureRegion(new Texture(pixmap1));
                sprite = new Sprite(texture);
                health = 500;
                speed = 2;
                dmgMod = 1;
                //implement appropriate spriteSheets
                break;

            //tank
            case (2):
                Pixmap pixmap2= new Pixmap(40, 40, Pixmap.Format.RGBA8888);
                pixmap2.setColor(1, 1, 0, 05f);
                pixmap2.fill();

                texture = new TextureRegion(new Texture(pixmap2));
                sprite = new Sprite(texture);
                health = 1000;
                speed = 1;
                dmgMod = 1;
                //implement appropriate spriteSheets
                break;
        }


        y = Gdx.graphics.getHeight() - 45;
        x = 365;
        hitBox = new Rectangle();
        hitBox.setPosition(x,y);
        circle = new Circle();
        circle.setPosition(x,y);
        this.sprite.setPosition(x,y);
        carryingGold = false;
        movementPoint = 1;

    }

    public Enemy(){};

    public int getHealth() {
        return health;
    }

    public int getType() {
        return type;
    }

    public int getSpeed() {
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

    public int getMovementPoint() {
        return movementPoint;
    }

    public void setMovementPoint(int movementPoint) {
        this.movementPoint = movementPoint;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public void setHitBox(Rectangle hitBox) {
        this.hitBox = hitBox;
    }

    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }
}
