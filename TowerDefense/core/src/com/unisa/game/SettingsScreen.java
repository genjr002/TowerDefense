package com.unisa.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;


/**
 * Created by Jon on 15/04/2016.
 */
public class SettingsScreen implements Screen {

    TowerDefense game;
    private SpriteBatch batch;
    private Skin skin;
    private Stage stage;
    private Texture background;
    private Image bg;
    private OrthographicCamera camera;
    private Sprite sprite;
    private TextButton button;
    private Music buttonSound;

    BitmapFont font;


    Label settingsLabel;
    Label musicLabel;
    Label sfxLabel;
    TextButton musicButton;
    TextButton sfxButton;

    boolean music = true;
    boolean SFX = true;

    public String sender;   //reference to the screen that enable SettingsScreen

    public SettingsScreen(TowerDefense towerDefense) {
        game = towerDefense;




    }

    public void create() {
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage();
        batch = new SpriteBatch();
        button = new TextButton("Back", skin, "default");
        font = new BitmapFont(Gdx.files.internal("default.fnt"));

        buttonSound = Gdx.audio.newMusic(Gdx.files.internal("button_Press.mp3"));

        //Labels
        settingsLabel = new Label("Settings", skin);
        settingsLabel.setFontScale(6);
        settingsLabel.setHeight(200f);
        settingsLabel.setWidth(800f);
        settingsLabel.setAlignment(Align.center);
        settingsLabel.setPosition(Gdx.graphics.getWidth() / 2 - 400f, Gdx.graphics.getHeight() - 250f);

        musicLabel = new Label ("Music", skin);
        musicLabel.setFontScale(5);
        musicLabel.setHeight(200f);
        musicLabel.setWidth(400f);
        musicLabel.setPosition(Gdx.graphics.getWidth() / 2 - 400f, Gdx.graphics.getHeight() / 2 + 50f);

        sfxLabel = new Label ("SFX", skin);
        sfxLabel.setFontScale(5);
        sfxLabel.setHeight(200f);
        sfxLabel.setWidth(400f);
        sfxLabel.setPosition(Gdx.graphics.getWidth() / 2 - 400f, Gdx.graphics.getHeight() / 2 - 150f);

        //Toggle Buttons
        musicButton = new TextButton("X", skin, "default");
        musicButton.setWidth(100f);
        musicButton.setHeight(100f);
        musicButton.getLabel().setFontScale(5);
        musicButton.getLabel().setColor(Color.YELLOW);
        musicButton.setPosition(Gdx.graphics.getWidth() / 2 + 200f, Gdx.graphics.getHeight() / 2 + 100f);
        musicButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                if (music) {
                    music = false;
                    musicButton.getLabel().setText("");
                } else {
                    music = true;
                    musicButton.getLabel().setText("X");
                }
            }
        });


        sfxButton = new TextButton("X", skin, "default");
        sfxButton.setWidth(100f);
        sfxButton.setHeight(100f);
        sfxButton.getLabel().setFontScale(5);
        sfxButton.getLabel().setColor(Color.YELLOW);
        sfxButton.setPosition(Gdx.graphics.getWidth() / 2 + 200f, Gdx.graphics.getHeight() / 2 + -100f);
        sfxButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                if(SFX) {
                    SFX = false;
                    sfxButton.getLabel().setText("");
                }
                else {
                    SFX = true;
                    sfxButton.getLabel().setText("X");
                }
                buttonSound.play();
            }
        });

        button.getLabel().setFontScale(4);
        button.setWidth(800f);
        button.setHeight(200f);
        button.setPosition(Gdx.graphics.getWidth() / 2 - 400f, 150f);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonSound.play();
                if (sender.equals("menu")) {
                    game.setScreen(TowerDefense.menuScreen);
                } else if (sender.equals("game")) {
                    game.setScreen(TowerDefense.gameScreen);
                }


            }
        });

        background = new Texture("TowerDefense_MainScreen_Background.png");
        bg = new Image(background);
        bg.setHeight(Gdx.graphics.getHeight());
        bg.setWidth(Gdx.graphics.getWidth());

        stage.addActor(bg);
        stage.addActor(settingsLabel);
        stage.addActor(musicButton);
        stage.addActor(sfxButton);
        stage.addActor(musicLabel);
        stage.addActor(sfxLabel);
        stage.addActor(button);

        Gdx.input.setInputProcessor(stage);

    }


    @Override
    public void show() {
        create();
    }

    @Override
    public void render(float delta) {
       // Gdx.gl.glClearColor(0.1f, 0.1f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        bg.draw(batch,1);
        stage.draw();
        //musicButton.draw(batch,1);
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
