package com.unisa.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;

/**
 * Created by Jon on 25/04/2016.
 */
public class Projectile {

    TextureRegion texture;
    Sprite sprite;
    Circle projectile;
    Enemy target;

    public Projectile(int type) {
        Pixmap pixmap = new Pixmap(5,5, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.LIGHT_GRAY);
        pixmap.fill();

        texture = new TextureRegion(new Texture(pixmap));
        sprite = new Sprite(texture);


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


    public Enemy getTarget() {
        return target;
    }
}
