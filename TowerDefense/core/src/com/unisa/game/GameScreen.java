package com.unisa.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
import com.badlogic.gdx.graphics.Pixmap;


/**
 * Created by Jon on 14/04/2016.
 */
public class GameScreen implements Screen{



    TowerDefense game; // Note it's "MyGdxGame" not "Game"
    private Skin skin;
    private Stage stage;
    private SpriteBatch batch;
    private OrthographicCamera camera;

    int pointOneY = 200;
    int pointTwoX;
    int pointThreeY;
    int pointFourX;
    int pointFiveY;

  //  private ParticleEffect particleEffect = new ParticleEffect();

    //point 1 of map Height 280px, Width 380px
    //Point 2 of path Height 280px, Width getwidth() - 350
    //Point 3 of path Height  , Width getWidth() - 350

    boolean combatPhase = false;//determine if in build or combat phase

    int coinsRemaining = 20;
    int enemiesRemaining = 0;
    int waveCount = 0;
    int meat = 0;

    BitmapFont waveCountLabel = new BitmapFont();
    BitmapFont enemeiesLabel = new BitmapFont();
    BitmapFont coinsLabel = new BitmapFont();

    TextButton pauseButton;
    TextButton buildButton;
    TextButton startButton;
    TextButton abilityOne;
    TextButton abilityTwo;
    TextButton abilityThree;
    TextButton abilityFour;
    
    private TextureRegion texture;
    private Sprite dragonSpr;
    private Image background;

    private Enemy enemy;

    int time;
    // constructor to keep a reference to the main Game class

    public GameScreen(TowerDefense game){
        this.game = game;
        coinsRemaining = 20;
        waveCount = 0;
        meat = 0;
    }



    public void create() {
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage();
        batch = new SpriteBatch();


        //This pixmap just draws a red square PH for dragon sprite
        Pixmap pixmapDragon = new Pixmap(300, 250, Pixmap.Format.RGBA8888);
        pixmapDragon.setColor(1, 0, 0, 05f);
        pixmapDragon.fill();

        texture = new TextureRegion(new Texture(pixmapDragon));
        dragonSpr = new Sprite(texture);
        dragonSpr.setPosition(Gdx.graphics.getWidth() / 2 - dragonSpr.getWidth()/2, 0);

        texture = new TextureRegion(new Texture(pixmapDragon));

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.translate(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        camera.update();
        Gdx.app.log("GameScreen: ", "gameScreen create");
        //batch.setProjectionMatrix(camera.combined);

        pauseButton = new TextButton("||", skin, "default");

        pauseButton.setWidth(100f);
        pauseButton.setHeight(100f);
        pauseButton.getLabel().setFontScale(2);
        pauseButton.setPosition(Gdx.graphics.getWidth() - 125f, Gdx.graphics.getHeight() - 125f);
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                Gdx.app.log("GameScreen: ", "About to call MenuScreen");
                game.setScreen(TowerDefense.menuScreen);
                Gdx.app.log("GameScreen: ", "MenuScreen started");

            }
        });

        //stage.addActor(dragon);
        stage.addActor(pauseButton);

        texture =  new TextureRegion(new Texture("Game_Background2.png"));

        background = new Image(texture);

        enemy = new Enemy(0);

        enemeiesLabel.setColor(Color.BLACK);


        Gdx.input.setInputProcessor(stage);
    }



    public void render(float f) {

     //   Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float dt = Gdx.graphics.getDeltaTime();
        enemy.setY((int)( 150*(dt / 1000000000.0)));

        batch.begin();

       // stage.draw();
        background.draw(batch, 1);
        dragonSpr.draw(batch);

        enemy.getSprite().draw(batch);

        pauseButton.draw(batch, 1);
        UIText();
        batch.end();
    }

    public void UIText(){
        int height = Gdx.graphics.getHeight();
        int width = Gdx.graphics.getWidth();
        enemeiesLabel.draw(batch, "Enemines Remaining", height - 50, width - 750);
        enemeiesLabel.draw(batch, Integer.toString(enemiesRemaining), height - 50, width - 600);
    }

    @Override

    public void dispose() { }

    @Override

    public void resize(int width, int height) { }

    @Override

    public void pause() { }

    @Override

    public void resume() { }

    @Override

    public void show() {

        Gdx.app.log("GameScreen: ", "show called ");

        create();

    }

    @Override

    public void hide() {

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
                if (button.equals(buildButton)) {
                    game.setScreen(TowerDefense.settingsScreen);

                } else if (button.equals(pauseButton)) {
                    game.setScreen(TowerDefense.menuScreen);
                }
              //  else if
                Gdx.app.log("MenuScreen: ", "gameScreen started");
            }
        });
        button.toFront();
        stage.addActor(button);
    }

}
