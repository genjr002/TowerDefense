package com.unisa.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MenuScreen implements Screen {

    TowerDefense game;
    private SpriteBatch batch;
    private Skin skin;
    private Stage stage;

    private OrthographicCamera camera;
    private Sprite sprite;
    TextButton buttonContinue;
    TextButton buttonNewGame;
    TextButton buttonSettings;

    TextureRegion texture;


    Image background;

    public MenuScreen(TowerDefense prac3Scene) {
        this.game = prac3Scene;
    }

    public void create() {
        Gdx.app.log("MenuScreen:", "Menuscreen create");
        batch = new SpriteBatch();
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage();

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        texture = new TextureRegion(new Texture("TowerDefense_MainScreen_Background.png"));

        background = new Image(texture);
        background.toBack();
        background.setHeight(Gdx.graphics.getHeight());
        background.setWidth(Gdx.graphics.getWidth());
        stage.addActor(background);

        buttonContinue = new TextButton("Continue", skin, "default");
        SetButtonValues(buttonContinue, Gdx.graphics.getWidth() / 2 - 400f, 750f);
        buttonNewGame = new TextButton("New Game", skin, "default");
        SetButtonValues(buttonNewGame, Gdx.graphics.getWidth() / 2 - 400f, 450f);
        buttonSettings = new TextButton("Settings", skin, "default");
        SetButtonValues(buttonSettings, Gdx.graphics.getWidth() / 2 - 400f, 150f);




        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        Gdx.app.log("MenuScreen:", "MenuScreen show called");
        create();
    }

    @Override
    public void render(float delta) {


        Gdx.gl.glClearColor(1, 1, 1, 1);
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
        Gdx.app.log("MenuScren:", "menuScreen hide called");
    }



    @Override
    public void dispose() {

    }

    private void SetButtonValues(final TextButton button, float x, float y){
        button.getLabel().setFontScale(4);
        button.setWidth(800f);
        button.setHeight(200f);
        button.setPosition(x, y);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("MenuScreen: ", "About to call gameScreen");
                if (button.equals(buttonSettings)) {

                    stage.dispose();
                    TowerDefense.settingsScreen.sender = "menu";    //so we can go back to the right screen
                    game.setScreen(TowerDefense.settingsScreen);

                } else if (button.equals(buttonContinue) || button.equals(buttonNewGame)) {

                    stage.dispose();
                    game.setScreen(TowerDefense.gameScreen);
                }
                Gdx.app.log("MenuScreen: ", "gameScreen started");
            }
        });
        button.toFront();
        stage.addActor(button);
    }
}