package com.unisa.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;



/**
 * Created by Jon on 15/04/2016.
 */
public class SettingsScreen implements Screen {

    TowerDefense game;
    private SpriteBatch batch;
    private Skin skin;
    private Stage stage;
    private Texture background;
    private OrthographicCamera camera;
    private Sprite sprite;
    private TextButton button;
    BitmapFont font;

    public SettingsScreen(TowerDefense towerDefense) {
        game = towerDefense;




    }

    public void create() {
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage();
        batch = new SpriteBatch();
        button = new TextButton("Back", skin, "default");
        font = new BitmapFont(Gdx.files.internal("default.fnt"));




        button.getLabel().setFontScale(4);
        button.setWidth(800f);
        button.setHeight(200f);
        button.setPosition(Gdx.graphics.getWidth() / 2 - 400f, 150f);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(TowerDefense.menuScreen);
            }
        });

        String musicLabel = "Music:";



        stage.addActor(button);
        Gdx.input.setInputProcessor(stage);

    }



    @Override
    public void show() {
        create();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        stage.draw();
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
