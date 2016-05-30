package com.unisa.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;


/**
 * Created by Jon on 18/04/2016.
 */
public class Enemy {

    private float health;
    private int type;
    private int speed;
    private float healthMod = 1.0f;
    private int x;
    private int y;
    private boolean carryingGold;
    private int movementPoint;
    private Rectangle hitBox;
    private Circle circle;
    private int id;
    private int goldX;
    private int goldY;
    private String enemyType; // to identify enemy type for certain abilities and towers
    private int state; // 0 = normal, 1 = scared, 2 = enraged
    private int stateTime; // how long target is scared or enraged

    private TextureRegion texture;
    private Sprite sprite;
    private Sprite altSprite;

    public Enemy(int type){
        switch (type){
            //basic footmen
            case (0):

                texture = new TextureRegion(new Texture("Swordsman.png"));
                sprite = new Sprite(texture);

                texture = new TextureRegion(new Texture("Swordsman_Carrying.png"));
                altSprite = new Sprite(texture);

                id = 0;
                health = 800;
                speed = 1;

                enemyType = "footmen";
                break;

            //thief
            case (1):
                texture = new TextureRegion(new Texture("Thief.png"));
                sprite = new Sprite(texture);

                texture = new TextureRegion(new Texture("Thief_Carrying.png"));
                altSprite = new Sprite(texture);

                id = 1;
                health = 500;
                speed = 2;

                enemyType = "thief";
                //implement appropriate spriteSheets
                break;

            //tank
            case (2):
                texture = new TextureRegion(new Texture("knight.png"));
                sprite = new Sprite(texture);

                texture = new TextureRegion(new Texture("knight_Carrying.png"));
                altSprite = new Sprite(texture);

                id = 2;
                health = 1000;
                speed = 1;

                enemyType = "tank";
                //implement appropriate spriteSheets
                break;

            // barbarian, added by Brandon (coobj006)
            case (3):
                texture = new TextureRegion(new Texture("knight.png")); //need to replace at some point
                sprite = new Sprite(texture);

                texture = new TextureRegion(new Texture("knight_Carrying.png")); //this one as well
                altSprite = new Sprite(texture);

                id = 3;
                health = 900;
                speed = 1;

                enemyType = "barbarian";
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
        state = 0;
    }

    public Enemy(){};

    public float getHealth() {
        return health;
    }

    public int getType() {
        return type;
    }

    public int getSpeed() {
        return speed;
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

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setX(int x) {
        this.x = x;
        this.sprite.setPosition(x,y);
    }

    public void setY(int y) {
        this.y = y;
        this.sprite.setPosition(x,y);
    }

    public void setHealth(float health) {
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

    public int getId() {
        return id;
    }

    public void setId(int val) {id = val;}

    public void setAltSprite(){
        sprite = altSprite;
        altSprite = null;
    }

    public int getGoldX() {
        return goldX;
    }

    public void setGoldX(int goldX) {
        this.goldX = goldX;
    }

    public int getGoldY() {
        return goldY;
    }

    public void setGoldY(int goldY) {
        this.goldY = goldY;
    }

    public String getEnemyType() { return this.enemyType; }

    public int getState() { return state; }

    public void setState(int state) { this.state = state; }

    public int getStateTime() { return stateTime; }

    public void setStateTime(int stateTime) { this.stateTime = stateTime; }

}
