package com.unisa.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;

import java.util.ArrayList;
import java.util.Random;


/**
 * Created by Jon on 14/04/2016.
 */
public class GameScreen extends ApplicationAdapter implements Screen , InputProcessor{


    TowerDefense game;
    private Skin skin;

    private Stage mainStage;
    private Stage buildStage;
    private Stage upgradeStage;
    private Stage pauseStage;

    private SpriteBatch mainbatch;
    private SpriteBatch buildBatch;
    private SpriteBatch upgradeBatch;
    private SpriteBatch pauseBatch;

    private OrthographicCamera camera;

    boolean combatPhase = false;//determine if in build or combat phase
    boolean playing = false;
    boolean dragging = false;
    boolean building = false;
    boolean upgrading =false;

    int coinsRemaining;
    int enemiesRemaining;
    int waveCount;
    int meat;
    int enemiesSpawned;

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
    TextButton defenseOne;
    TextButton defenseTwo;
    TextButton defenseThree;
    TextButton defenseFour;
    TextButton buildbackButton;
    TextButton upgradebackButton;
    TextButton upgradeButtonOne;
    TextButton upgradeButtonTwo;
    TextButton upgradeButtonThree;

    private TextureRegion texture;
    private Sprite dragonSpr;
    private Image background;
    private Image UIBackAbility1;
    private Image UIBackAbility2;
    private Image UIBackPauseB;
    private Image overlay;
    private Image buildUIBack;

    private ArrayList<Enemy> enemyList;
    private ArrayList<Defense> defenseList;
    private Defense toBuild;

    private float movementCD;
    private float ability1CD;
    private float ability2CD;
    private float ability3CD;
    private float ability4CD;
    private float elapsedTime;
    private float lastTime;
    private float spawnTime;

    private Rectangle pathPart1;
    private Rectangle pathPart2;
    private Rectangle pathPart3;
    private Rectangle pathPart4;
    private Rectangle pathPart5;

    Sprite Test1;
    Sprite Test2;
    Sprite Test3;
    Sprite Test4;
    Sprite Test5;




    // constructor to keep a reference to the main Game class
    public GameScreen(TowerDefense game){
        this.game = game;
    }



    public void create() {
        Gdx.app.log("GameScreen: ", "gameScreen create " + Gdx.graphics.getWidth() + " " + Gdx.graphics.getHeight());
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        mainStage = new Stage();
        buildStage = new Stage();
        upgradeStage = new Stage();
        pauseStage = new Stage();

        mainbatch = new SpriteBatch();
        buildBatch = new SpriteBatch();
        upgradeBatch = new SpriteBatch();
        pauseBatch = new SpriteBatch();


        movementCD = 0.0f;
        coinsRemaining = 20;
        waveCount = 0;
        meat = 0;
        enemiesSpawned = 1;
        enemiesRemaining = 0;

        spawnTime = 0.0f;

        defenseList = new ArrayList<Defense>();
        toBuild = null;

        pathPart1 = new Rectangle();
        pathPart2 = new Rectangle();
        pathPart3 = new Rectangle();
        pathPart4 = new Rectangle();
        pathPart5 = new Rectangle();


        Pixmap testmap= new Pixmap(75, 325, Pixmap.Format.RGBA8888);
        testmap.setColor(1, 0, 0, 05f);
        testmap.fill();

        texture = new TextureRegion(new Texture(testmap));
        Test1 = new Sprite(texture);
        Test1.setPosition(345, Gdx.graphics.getHeight()-325);
        pathPart1.set(290, Gdx.graphics.getHeight() - 325, 180, 325);

        testmap= new Pixmap(1000, 190, Pixmap.Format.RGBA8888);
        testmap.setColor(1, 0, 0, 05f);
        testmap.fill();

        texture = new TextureRegion(new Texture(testmap));
        Test2 = new Sprite(texture);
        Test2.setPosition(300, Gdx.graphics.getHeight()-350);



        texture =  new TextureRegion(new Texture("Game_Background3.png"));
        background = new Image(texture);
        background.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("inside ", x + " " +y);
                if (toBuild == null && combatPhase == false) {

                    for(Defense defense: defenseList){

                        if(defense.getDefenseBounds().contains(x,y)){
                            Gdx.app.log("Defense ", "clicked");
                            upgrading = true;
                            Gdx.input.setInputProcessor(upgradeStage);

                            overlay.setHeight(Gdx.graphics.getHeight());
                            overlay.setWidth(Gdx.graphics.getWidth());
                        }
                    }
                }}});
        background.addListener(new DragListener() {
            @Override
            public void dragStart(InputEvent event, float x, float y, int pointer) {
                if (toBuild != null) {
                    dragging = true;
                }
            }

            public void drag(InputEvent event, float x, float y, int pointer) {

                if (toBuild != null) {
                    toBuild.getDefenseBounds().setPosition(x,y);


                    if(toBuild.getDefenseBounds().overlaps(pathPart1)){
                        Gdx.app.log("path1", "overlaps object");
                        if(x < pathPart1.getX() + pathPart1.getWidth()/2){
                            x = pathPart1.getX();
                        } else if(x > pathPart1.getX() + pathPart1.getWidth()/2){
                            x = pathPart1.getX() + pathPart1.getWidth();
                        }
                    }



                    if (x-50 < 0){
                        x = 40;
                    } if(x + 50 > Gdx.graphics.getWidth()){
                        x = Gdx.graphics.getWidth() - 40;
                    }if (y-50 < 240){
                        y = 300;
                    }if(y + 50 > Gdx.graphics.getHeight()){
                        y = Gdx.graphics.getHeight() - 40;
                    }


                    for(Defense defense: defenseList){
                        if(defense.getDefenseBounds().overlaps(toBuild.getDefenseBounds())){
                            if (x-50 < defense.getSprite().getWidth()/2 + defense.getSprite().getX()){
                                x = defense.getSprite().getX()-40;
                            } if(x+50 > defense.getSprite().getWidth()/2 + defense.getSprite().getX()){
                                x = defense.getSprite().getX()-80;
                            }if (y-50 < defense.getSprite().getHeight()/2 + defense.getSprite().getY()){
                                y = defense.getSprite().getY()-40;
                            }if(y+50 > defense.getSprite().getHeight()/2 + defense.getSprite().getY()){
                                y = defense.getSprite().getY()-80;
                            }
                        }
                    }
                        toBuild.getSprite().setPosition(x - 50, y - 50);

                }
            }

            public void dragStop(InputEvent event, float x, float y, int pointer) {

                if(toBuild != null) {


                    if(toBuild.getDefenseBounds().overlaps(pathPart1)){
                        Gdx.app.log("path1", "overlaps object");
                        if(x < pathPart1.getX() + pathPart1.getWidth()/2){
                            x = pathPart1.getX()-50;
                        } else if(x > pathPart1.getX() + pathPart1.getWidth()/2){
                            x = pathPart1.getX() + pathPart1.getWidth()+50;
                        }
                    }


                    if (x-50 < 0){
                        x = 40;
                    } if(x + 50 > Gdx.graphics.getWidth()){
                        x = Gdx.graphics.getWidth() - 40;
                    }if (y-50 < 240){
                        y = 300;
                    }if(y + 50 > Gdx.graphics.getHeight()){
                        y = Gdx.graphics.getHeight() - 40;
                    }
                    for(Defense defense: defenseList){
                        if(defense.getDefenseBounds().overlaps(toBuild.getDefenseBounds())){
                            if (x-50 < defense.getSprite().getWidth()/2 + defense.getSprite().getX()){
                                x = defense.getSprite().getX()-50;
                            }else if(x+50 > defense.getSprite().getWidth()/2 + defense.getSprite().getX()){
                                x = defense.getSprite().getX()-80;
                            }else if (y-50 < defense.getSprite().getHeight()/2 + defense.getSprite().getY()){
                                y = defense.getSprite().getY()-50;
                            }else if(y+50 > defense.getSprite().getHeight()/2 + defense.getSprite().getY()){
                                y = defense.getSprite().getY()-80;
                            }
                        }
                    }

                    toBuild.getSprite().setPosition(x - 50, y - 50);
                    toBuild.setDefenseBounds(new Rectangle(x - 50, y - 50, 100, 100));

                    toBuild.setRangeRadious(new Circle(x - 50, y-50, 300));

                    mainStage.addActor(toBuild);
                    defenseList.add(toBuild);

                    dragging = false;
                    toBuild = null;
                    building = false;

                }
            }
        });

        mainStage.addActor(background);



        //This pixmap just draws a red square PH for dragon sprite
        Pixmap pixmapDragon = new Pixmap(300, 250, Pixmap.Format.RGBA8888);
        pixmapDragon.setColor(1, 0, 1, 1f);
        pixmapDragon.fill();

        texture = new TextureRegion(new Texture(pixmapDragon));
        dragonSpr = new Sprite(texture);
        dragonSpr.setPosition(Gdx.graphics.getWidth() / 2 - dragonSpr.getWidth() / 2, 0);

        //Pixmap pixmap= new Pixmap(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Pixmap.Format.RGBA8888);
        Pixmap pixmap= new Pixmap(0, 0, Pixmap.Format.RGBA8888);
        pixmap.setColor(1, 0, 0, 05f);
        pixmap.fill();

        texture = new TextureRegion(new Texture(pixmap));
        overlay = new Image(texture);


        buildStage.addActor(overlay);
        upgradeStage.addActor(overlay);



        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.translate(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        camera.update();




        texture = new TextureRegion(new Texture("UIBackground1.png"));
        UIBackAbility1 = new Image(texture);
        UIBackAbility1.setPosition(Gdx.graphics.getWidth() - 600, 0);
        mainStage.addActor(UIBackAbility1);
        UIBackAbility2 = new Image(texture);
        UIBackAbility2.setPosition(0, 0);
        mainStage.addActor(UIBackAbility2);
        UIBackPauseB = new Image(texture);
        UIBackPauseB.setHeight(150);
        UIBackPauseB.setWidth(150);
        UIBackPauseB.setPosition(Gdx.graphics.getWidth() - 150, Gdx.graphics.getHeight() - 150);
        mainStage.addActor(UIBackPauseB);
        buildUIBack = new Image(texture);
        buildUIBack.setHeight(800);
        buildUIBack.setWidth(1200);
        buildUIBack.setPosition(Gdx.graphics.getWidth() / 2 - 600, Gdx.graphics.getHeight() / 2 - 350);
        //buildStage.addActor(UIBackPauseB);

        enemeiesLabel.setColor(Color.WHITE);

        CreateButtons();


        Gdx.input.setInputProcessor(mainStage);

    }

    public void CreateButtons(){
        pauseButton = new TextButton("||", skin, "default");
        SetButtonValues(pauseButton,mainStage, 100f, 100f, Gdx.graphics.getWidth() - 125f, Gdx.graphics.getHeight() - 125f);
        abilityOne = new TextButton("1", skin, "default");
        SetButtonValues(abilityOne,mainStage, 150f,150f, 75, 45);
        abilityTwo = new TextButton("2", skin, "default");
        SetButtonValues(abilityTwo,mainStage, 150f,150f, 375, 45);
        abilityThree = new TextButton("3", skin, "default");
        SetButtonValues(abilityThree,mainStage, 150f,150f, Gdx.graphics.getWidth() - 525, 45);
        abilityFour = new TextButton("4", skin, "default");
        SetButtonValues(abilityFour,mainStage, 150f, 150f, Gdx.graphics.getWidth() - 225, 45);
        startButton = new TextButton("Start", skin, "default");
        SetButtonValues(startButton,mainStage, 200f, 100f, Gdx.graphics.getWidth() - 210f, Gdx.graphics.getHeight() /2);
        buildButton = new TextButton("Build", skin, "default");
        SetButtonValues(buildButton,mainStage, 200f, 100f, Gdx.graphics.getWidth() - 210f, Gdx.graphics.getHeight() /2 - 200f);
        buildbackButton = new TextButton("Back", skin, "default");
        SetButtonValues(buildbackButton,buildStage, 400f , 150f, Gdx.graphics.getWidth() /2 - 200f, 35);
        defenseOne = new TextButton("Goblin",skin,"default");
        SetButtonValues(defenseOne,buildStage, 200f, 200f, Gdx.graphics.getWidth() /2 - 550, 250);
        defenseTwo = new TextButton("Minotaur",skin,"default");
        SetButtonValues(defenseTwo,buildStage, 200f, 200f, Gdx.graphics.getWidth() /2 - 250, 250);
        defenseThree = new TextButton("Drummer",skin,"default");
        SetButtonValues(defenseThree,buildStage, 200f, 200f, Gdx.graphics.getWidth() /2 + 50, 250);
        defenseFour = new TextButton("Drake",skin,"default");
        SetButtonValues(defenseFour,buildStage, 200f, 200f, Gdx.graphics.getWidth() /2 + 350, 250);
        upgradeButtonOne = new TextButton("Attack", skin, "default");
        SetButtonValues(upgradeButtonOne, upgradeStage,200f,200f, Gdx.graphics.getWidth() /2 - 450, 250);
        upgradeButtonTwo = new TextButton("Range", skin, "default");
        SetButtonValues(upgradeButtonTwo, upgradeStage,200f,200f, Gdx.graphics.getWidth() /2-100, 250);
        upgradeButtonThree = new TextButton("Speed", skin, "default");
        SetButtonValues(upgradeButtonThree, upgradeStage,200f,200f, Gdx.graphics.getWidth() /2 + 250, 250);
        upgradebackButton = new TextButton("Back", skin, "default");
        SetButtonValues(upgradebackButton,upgradeStage, 400f , 150f, Gdx.graphics.getWidth() /2 - 200f, 35);
    }

    public void update(){
        // this solves the concurrent issue of removing an element from an iterating list;
        Enemy toRemove = null;

        if (movementCD <= 0.0f) {
            if(combatPhase == true) {
                for (Enemy enemy : enemyList) {
                    if (enemy.getMovementPoint() == 0) {
                        enemy.setY(enemy.getY() + enemy.getSpeed());
                        if (enemy.getY() >= Gdx.graphics.getHeight()) {
                            toRemove = enemy;
                            coinsRemaining--;

                        }
                    } else if (enemy.getMovementPoint() == 1) {

                        if (enemy.isCarryingGold() == true) {
                            enemy.setX(enemy.getX() - enemy.getSpeed());
                            if (enemy.getX() <= 365) {
                                enemy.setX(365);
                                enemy.setMovementPoint(0);
                            }
                        } else {
                            enemy.setY(enemy.getY() - enemy.getSpeed());
                            if (enemy.getY() <= 780) {
                                enemy.setY(780);
                                enemy.setMovementPoint(2);
                            }
                        }
                    } else if (enemy.getMovementPoint() == 2) {
                        if (enemy.isCarryingGold() == true) {
                            enemy.setY(enemy.getY() + enemy.getSpeed());
                            if (enemy.getY() >= 780) {
                                enemy.setY(780);
                                enemy.setMovementPoint(1);
                            }
                        } else {
                            enemy.setX(enemy.getX() + enemy.getSpeed());
                            if (enemy.getX() >= 1420) {
                                enemy.setX(1420);
                                enemy.setMovementPoint(3);
                            }
                        }
                    } else if (enemy.getMovementPoint() == 3) {
                        if (enemy.isCarryingGold() == true) {
                            enemy.setX(enemy.getX() + enemy.getSpeed());
                            if (enemy.getX() >= 1420) {
                                enemy.setX(1420);
                                enemy.setMovementPoint(2);
                            }
                        } else {
                            enemy.setY(enemy.getY() - enemy.getSpeed());
                            if (enemy.getY() <= 450) {
                                enemy.setY(450);
                                enemy.setMovementPoint(4);
                            }
                        }
                    } else if (enemy.getMovementPoint() == 4) {
                        if (enemy.isCarryingGold() == true) {
                            enemy.setY(enemy.getY() + enemy.getSpeed());
                            if (enemy.getY() >= 450) {
                                enemy.setY(450);
                                enemy.setMovementPoint(3);
                            }
                        } else {
                            enemy.setX(enemy.getX() - enemy.getSpeed());
                            if (enemy.getX() <= 870) {
                                enemy.setX(870);
                                enemy.setMovementPoint(5);
                            }
                        }
                    } else if (enemy.getMovementPoint() == 5) {

                        enemy.setY(enemy.getY() - enemy.getSpeed());
                        if (enemy.getY() <= 250) {

                            enemy.setCarryingGold(true);
                            enemy.setMovementPoint(4);
                        }
                    }

                    enemy.getCircle().setPosition(enemy.getX(),enemy.getY());

                    for(Defense defense: defenseList){
                        //defense.getRangeRadious().contains(enemy.getX()/2, enemy.getY()/2)
                        if(defense.getTarget() == null) {
                            if (defense.getRangeRadious().overlaps(enemy.getCircle())) {
                                //Gdx.app.log("ENEMY", "Within range overlaps");
                                enemy.setHealth(enemy.getHealth() - 1);
                                if (enemy.getHealth() < 0) {
                                    toRemove = enemy;
                                }
                            }
                        }
                    }
                }

                spawnTime++;

                if(spawnTime == 100 && enemiesSpawned != 10){
                    Random randInt = new Random();

                    int rand = randInt.nextInt(3);
                    enemyList.add(new Enemy(rand));
                    enemiesSpawned++;
                    enemiesRemaining++;
                    spawnTime = 0;
                }
            }
            movementCD = 0.0f;
        }

        movementCD -= elapsedTime;
        if(toRemove != null){
            enemyList.remove(toRemove);
            enemiesRemaining--;
            if(enemiesRemaining <= 0){
                combatPhase = false;
                if(coinsRemaining <= 0){
                    waveCount++;
                }
            }
        }
    }

    public void render(float f) {

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        update();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        long currentTime = System.currentTimeMillis();
        //Divide by a thousand to convert from milliseconds to seconds
        elapsedTime = (currentTime - lastTime) / 1000.0f;
        lastTime = currentTime;

        mainbatch.begin();

        background.draw(mainbatch, 1);
        dragonSpr.draw(mainbatch);
        UIBackAbility2.draw(mainbatch,1);
        UIBackAbility1.draw(mainbatch,1);

        if(combatPhase == true) {
            for (Enemy enemy : enemyList) {
                enemy.getSprite().draw(mainbatch);
            }

            if(startButton.getX() < Gdx.graphics.getWidth()){
                startButton.setPosition(startButton.getX() +5 , startButton.getY());
                buildButton.setPosition(buildButton.getX() +5 , buildButton.getY());
            }
        }

        else{
            if(startButton.getX() > 1585){
                buildButton.setPosition(buildButton.getX() -5, buildButton.getY());
                startButton.setPosition(startButton.getX() -5, startButton.getY());
            }

            if(dragging){
                toBuild.getSprite().draw(mainbatch, 0.5f);
            }
        }

        if (defenseList.size() >0){
            for(Defense defense: defenseList){
                defense.getSprite().draw(mainbatch);
            }
        }

        pauseButton.draw(mainbatch, 1);
        abilityOne.draw(mainbatch, 1);
        abilityTwo.draw(mainbatch, 1);
        abilityThree.draw(mainbatch, 1);
        abilityFour.draw(mainbatch, 1);
        startButton.draw(mainbatch, 1);
        buildButton.draw(mainbatch, 1);




        UIText();


       // Test1.draw(mainbatch);
      //  Test2.draw(mainbatch);
     //   Test3.draw(mainbatch);
     //   Test4.draw(mainbatch);
     //   Test5.draw(mainbatch);

        mainbatch.end();

        if(building && toBuild == null){



            buildBatch.begin();
            overlay.draw(buildBatch, 0.5f);
            buildUIBack.draw(buildBatch, 1);
            buildbackButton.draw(buildBatch, 1);
            defenseOne.draw(buildBatch,1);
            defenseThree.draw(buildBatch,1);
            defenseTwo.draw(buildBatch,1);
            defenseFour.draw(buildBatch,1);


            buildBatch.end();

        }

        if(upgrading){

            upgradeBatch.begin();

            overlay.draw(upgradeBatch, 0.5f);
            buildUIBack.draw(upgradeBatch, 1);
            upgradebackButton.draw(upgradeBatch,1);
            upgradeButtonOne.draw(upgradeBatch,1);
            upgradeButtonTwo.draw(upgradeBatch,1);
            upgradeButtonThree.draw(upgradeBatch,1);

            upgradeBatch.end();

        }


    }

    public void UIText(){
        int height = Gdx.graphics.getHeight();
        int width = Gdx.graphics.getWidth();
        enemeiesLabel.draw(mainbatch, "Enemies Remaining  " + enemiesRemaining + "\n" + "Coins                  " +
                "       " + coinsRemaining + "\nMeat                          " + meat + "\nWave Count              " + waveCount, 50, height / 2);
        enemeiesLabel.getData().setScale(2, 2);
    }

    @Override

    public void dispose() { }

    @Override

    public void resize(int width, int height) { }

    @Override

    public void pause() {
        // TODO:
        // add code to pause game
    }

    @Override

    public void resume() {
        // TODO:
        // add code to resume game
    }

    @Override

    public void show() {

        Gdx.app.log("GameScreen: ", "show called ");
        if(playing == false){
            create();
        }
        else{
            game.resume();
        }


    }

    @Override

    public void hide() {

    }

    private void SetButtonValues(final TextButton button, Stage stage, float width, float height,float x, float y){
        button.getLabel().setFontScale(3);
        button.setWidth(width);
        button.setHeight(height);
        button.setPosition(x, y);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("MenuScreen: ", "About to call gameScreen");
                if (button.equals(buildButton)) {
                    building= true;
                    toBuild = null;

                    Gdx.input.setInputProcessor(buildStage);


                    overlay.setHeight(Gdx.graphics.getHeight());
                    overlay.setWidth(Gdx.graphics.getWidth());

                } else if (button.equals(pauseButton)) {
                    game.pause();
                    game.setScreen(TowerDefense.menuScreen);
                } else if (button.equals(startButton)) {
                    if (combatPhase == false) {
                        enemyList = new ArrayList<Enemy>();
                        enemyList.add(new Enemy(0));
                        enemiesRemaining++;
                        enemiesSpawned = 1;
                        spawnTime = 0f;
                        waveCount++;
                    }
                    combatPhase = true;
                    //call method to remove build and start UI elements
                } else if (button.equals(abilityOne)) {
                    //do ability
                } else if (button.equals(abilityTwo)) {
                    //do ability
                } else if (button.equals(abilityThree)) {
                    //do ability
                } else if (button.equals(abilityFour)) {
                    //do ability
                } else if (button.equals(buildbackButton)) {
                    if (building) {
                        building = false;
                        resetStage();
                    }
                } else if(button.equals(upgradebackButton)){
                    if(upgrading){
                        upgrading = false;
                        resetStage();
                    }
                } else if (button.equals(defenseOne)){
                    toBuild = new Defense(0);
                    resetStage();

                }else if (button.equals(defenseTwo)){
                    toBuild = new Defense(1);
                    resetStage();

                }else if (button.equals(defenseThree)){
                    toBuild = new Defense(2);
                    resetStage();

                }else if (button.equals(defenseFour)){
                    toBuild = new Defense(3);
                    resetStage();
                }


                Gdx.app.log("MenuScreen: ", "gameScreen started");
            }
        });
        button.toFront();

        if(stage.equals(mainStage)){
            mainStage.addActor(button);
        }
        else if(stage.equals(buildStage)){
            buildStage.addActor(button);
        }else if(stage.equals(upgradeStage)){
            upgradeStage.addActor(button);
        }
    }

    public void resetStage(){

        overlay.setHeight(0);
        overlay.setWidth(0);
        Gdx.input.setInputProcessor(mainStage);
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {



        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

}
