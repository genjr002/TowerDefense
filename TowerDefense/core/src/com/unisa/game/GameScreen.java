package com.unisa.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
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
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Align;


import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;


/**
 * Created by Jon on 14/04/2016.
 *
 * Problems encounter by Brandon
 * Stomp Ability needs ConcurrentModificationException problem to be fixed: line 1381
 * wave spawns random footmen before everything else in wave one: line 931
 */
public class GameScreen extends ApplicationAdapter implements Screen , InputProcessor{


    TowerDefense game;
    private Skin skin;
    SaveData currentSave;

    private Stage mainStage;
    private Stage buildStage;
    private Stage upgradeStage;
    private Stage pauseStage;
    private float healthMod;

    private SpriteBatch mainbatch;
    private SpriteBatch buildBatch;
    private SpriteBatch upgradeBatch;
    private SpriteBatch pauseBatch;

    private OrthographicCamera camera;

    boolean combatPhase = false;//determine if in build or combat phase
    boolean playing = false;
    boolean dragging = false;
    boolean building = false;
    boolean upgrading = false;
    boolean usingFireball = false;
    boolean usingMagamStrike = false;

    int coinsRemaining;
    int enemiesRemaining;
    int waveCount;
    int meat;
    int enemiesSpawned;

    public enum State
    {
        PAUSE,
        RUNNING,
    }

    State state = State.RUNNING;

    BitmapFont LCoins = new BitmapFont();
    BitmapFont LMeat = new BitmapFont();
    BitmapFont LEnemies = new BitmapFont();
    BitmapFont LWave = new BitmapFont();
    BitmapFont Lability1CD = new BitmapFont();
    BitmapFont Lability2CD = new BitmapFont();
    BitmapFont Lability3CD = new BitmapFont();
    BitmapFont Lability4CD = new BitmapFont();


    TextButton pauseButton;
    TextButton buildButton;
    TextButton startButton;
    TextButton abilityOne;  // Fireball
    TextButton abilityTwo;  // Roar
    TextButton abilityThree;    // Stomp
    TextButton abilityFour; // Magma Strike
    TextButton defenseOne;
    TextButton defenseTwo;
    TextButton defenseThree;
    TextButton defenseFour;
    TextButton buildbackButton;
    TextButton pauseContinueButton;
    TextButton pauseExitButton;
    TextButton pauseSettingsButton;
    TextButton upgradebackButton;
    TextButton upgradeButtonOne;
    TextButton upgradeButtonTwo;
    TextButton upgradeButtonThree;

    Label pauseLabel;
    Label upgradeAttackLabel;
    Label upgradeRangeLabel;
    Label upgradeSpeedLabel;
    Label upgradeOneCostLabel;
    Label upgradeAttackInfoLabel;
    Label upgradeTwoCostLabel;
    Label upgradeOneImprovementLabel;
    Label upgradeTwoImprovementLabel;
    Label upgradeThreeImprovementLabel;
    Label upgradeRangeInfoLabel;
    Label upgradeThreeCostLabel;
    Label upgradeSpeedInfoLabel;

    Label buildGoblinAttack;
    Label buildGoblinRange;
    Label buildGoblinSpeed;
    Label buildGoblinCost;
    Label buildMinoAttack;
    Label buildMinoRange;
    Label buildMinoSpeed;
    Label buildMinoCost;
    Label buildDrumAttack;
    Label buildDrumRange;
    Label buildDrumSpeed;
    Label buildDrumCost;
    Label buildDrakeAttack;
    Label buildDrakeRange;
    Label buildDrakeSpeed;
    Label buildDrakeCost;

    private TextureRegion texture;
    private Sprite dragonSpr;
    private Sprite portraitSpr;
    private Image background;
    private Image UIBackAbility1;
    private Image UIBackAbility2;
    private Image UIBackPauseB;
    private Image overlay;
    private Image buildUIBack;
    private Image upgradeUIBack2;
    private Image defencePortrait;
    private Image ability1BOverlay;
    private Image ability2BOverlay;
    private Image ability3BOverlay;
    private Image ability4BOverlay;

    private ArrayList<Enemy> enemyList;

    private ArrayList<Defense> defenseList = new ArrayList<Defense>();
    private ArrayList<Projectile> projectileList = new ArrayList<Projectile>();
    private ArrayList<Sprite> goldList = new ArrayList<Sprite>();
    public static ArrayList<Defense> savedDefenses = new ArrayList<Defense>();


    private Defense toBuild;
    private Defense toUpgrade;
    private float currentX; //the current x position of the defense
    private float currentY; //the current y position of the defense
    
    // Ability Cooldown times
    private final int ABILITY1_CD_TIME = 6;
    private final int ABILITY2_CD_TIME = 10;
    private final int ABILITY3_CD_TIME = 12;
    private final int ABILITY4_CD_TIME = 30;

    private float movementCD;
    private int ability1CD = 0; // CD time: 6 seconds
    private int ability2CD = 0; // CD time: 20 seconds
    private int ability3CD = 0; // CD time: 30 seconds
    private int ability4CD = ABILITY4_CD_TIME; // CD time: 120 seconds, starts on cooldown
    private float elapsedTime;
    private float lastTime;
    private float spawnTime;

    private Rectangle pathPart1;
    private Rectangle pathPart2;
    private Rectangle pathPart3;
    private Rectangle pathPart4;
    private Rectangle pathPart5;

    private ShapeRenderer shapeRenderer;

    private Enemy toRemove = null;
    long currentTime;

    private Music projectileSound;
    private Music buttonSound;
    private Music dragonStomp;
    private Music dragonRoar;
    private Music dragonMagmaBlast;
    private Music dragonFireball;
    private Music buildPlacement;
    private Music startWave;
    private Music success;
    private Music enemyDeath;

    private ParticleEffect stompEffect;
    private ParticleEffect fireBreath;
    private ParticleEffect magmaBastEffect;

    Sprite Test1;
    Sprite Test2;
    Sprite Test3;
    Sprite Test4;
    Sprite Test5;

    private Cell[][] screenGrid;
    private int[][] occupiedCellsSave;

    // constructor to keep a reference to the main Game class
    public GameScreen(TowerDefense game){
        this.game = game;
    }

    void LoadGameState(){
        Gdx.app.log("In GameScreen", "--- LoadGameState");

        combatPhase = false;
        state = State.RUNNING;

        if (game.continuing){
            this.coinsRemaining = game.prefs.getInteger("coinsValue");
            Gdx.app.log("wave prefs:", " " + this.coinsRemaining);
            this.waveCount = game.prefs.getInteger("waveValue");
            Gdx.app.log("wave prefs:", " " + this.waveCount);
            this.meat = game.prefs.getInteger("meatValue");
            Gdx.app.log("meat prefs:", " " + this.meat);
            LoadDefenses();
        } else {
            //new game
            ResetPrefs(); //reset the saved variables for round number, gold etc
            ResetDefenses();   //reset the defenses for the new game
        }
    }


    void SaveGameState(){
        Gdx.app.log("In GameScreen", "--- SaveGameState");
        game.prefs.putInteger("coinsValue", this.coinsRemaining);
        game.prefs.putInteger("waveValue", this.waveCount);
        game.prefs.putInteger("meatValue", this.meat);
        game.prefs.flush();
        Gdx.app.log("in SaveGameState:", " coins" + this.coinsRemaining);
        Gdx.app.log("in SaveGameState", " wave " + this.waveCount);
        Gdx.app.log("in SaveGameState", " meat" + this.meat);
    }

    void ResetPrefs(){
        Gdx.app.log("In GameScreen", "--- Reset Prefs");
        game.prefs.putInteger("coinsValue", 20);
        game.prefs.putInteger("waveValue", 0);
        game.prefs.putInteger("meatValue", 4000);
        game.prefs.flush();


        //set the local variables with the saved ones
        this.coinsRemaining = game.prefs.getInteger("coinsValue");
        Gdx.app.log("in ResetPrefs", " coins" + this.coinsRemaining);
        this.waveCount = game.prefs.getInteger("waveValue");
        Gdx.app.log("in ResetPrefs", " wave" + this.waveCount);
        this.meat = game.prefs.getInteger("meatValue");
        Gdx.app.log("in ResetPrefs", " meat" + this.meat);
    }

    void SaveDefenses(){
//        if (currentSave == null){
//            currentSave = new SaveData();
//        }
        Save.sd.setDefenses(defenseList);
        Save.sd.setScreenGrid(screenGrid);
        Save.save();
    }

    void LoadDefenses(){
        Save.load();
        defenseList = Save.sd.getDefenseList();
        occupiedCellsSave = Save.sd.getScreenGrid();
        RefreshDefensesFromLoad();
        //currentSave.toString();
    }

    void RefreshDefensesFromLoad(){

        for (Defense d : defenseList ){
            d.setRangeRadious(new Circle(d.getSprite().getX() + 50,
                    d.getSprite().getY() + 50, d.getRange()));
        }

        //set which cells were occupied pre-save
        for (int r = 0; r < 15; r++){
            for (int c = 0; c < 10; c++){
                if (occupiedCellsSave[r][c] == 1){
                    screenGrid[r][c].setCellOccupied(true);
                } else {
                    screenGrid[r][c].setCellOccupied(false);
                }
            }
        }

    }

    void ResetDefenses(){

        defenseList = new ArrayList<Defense>();

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

        projectileSound = Gdx.audio.newMusic(Gdx.files.internal("Bow_Fire_Arrow-Stephan_Schutze-2133929391.mp3"));
        buttonSound = Gdx.audio.newMusic(Gdx.files.internal("button_Press.mp3"));
        dragonFireball = Gdx.audio.newMusic(Gdx.files.internal("FireBall.wav"));
        dragonRoar = Gdx.audio.newMusic(Gdx.files.internal("Dragon_Roar.wav"));
        dragonStomp =  Gdx.audio.newMusic(Gdx.files.internal("stomp.wav"));
        dragonMagmaBlast = Gdx.audio.newMusic(Gdx.files.internal("magmaBlast.wav"));
        buildPlacement = Gdx.audio.newMusic(Gdx.files.internal("defenseSpawn.wav"));
        enemyDeath = Gdx.audio.newMusic(Gdx.files.internal("enemyDeath.wav"));;
        startWave = Gdx.audio.newMusic(Gdx.files.internal("startWave.wav"));;
        success = Gdx.audio.newMusic(Gdx.files.internal("victoryMusic.wav"));;

        enemyDeath.setVolume(200);

        texture = new TextureRegion(new Texture("Fireball Button.png"));
        ability1BOverlay = new Image(texture);
        ability1BOverlay.setHeight(150f);
        ability1BOverlay.setWidth(150f);
        ability1BOverlay.setPosition(75, 45);

        texture = new TextureRegion(new Texture("Roar Button.png"));
        ability2BOverlay = new Image(texture) ;
        ability2BOverlay.setSize(150f,150f);
        ability2BOverlay.setPosition(375,45);

        texture = new TextureRegion(new Texture("Stomp Button.png"));
        ability3BOverlay = new Image(texture) ;
        ability3BOverlay.setSize(150f,150f);
        ability3BOverlay.setPosition(Gdx.graphics.getWidth() - 525, 45);

        texture = new TextureRegion(new Texture("Magma Button.png"));
        ability4BOverlay = new Image(texture) ;
        ability4BOverlay.setSize(150f,150f);
        ability4BOverlay.setPosition(Gdx.graphics.getWidth() - 225, 45);


//        fireBreath = new ParticleEffect();
//        fireBreath.load(Gdx.files.internal("fireBreath"),
//                Gdx.files.internal(""));
//        fireBreath.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
//        fireBreath.start();

        currentTime = System.currentTimeMillis();
        movementCD = 0.0f;


        //load from gamestate instead
        //LoadGameState();

        enemiesSpawned = 1;
        enemiesRemaining = 0;
        healthMod = 1;

        spawnTime = 0.0f;


        toBuild = null;
        toUpgrade = null;

        pathPart1 = new Rectangle();
        pathPart2 = new Rectangle();
        pathPart3 = new Rectangle();
        pathPart4 = new Rectangle();
        pathPart5 = new Rectangle();

        //draws a circle around new defenses to show range
        shapeRenderer = new ShapeRenderer();


        //upgrade screen temp area
        texture = new TextureRegion(new Texture("UIBackground1.png"));
        upgradeUIBack2 = new Image(texture);
        upgradeUIBack2.setHeight(200);
        upgradeUIBack2.setWidth(900);
        upgradeUIBack2.setPosition(Gdx.graphics.getWidth() / 2 - 350, Gdx.graphics.getHeight() / 2 + 135);

        //selected tower's portrait
        Pixmap pixmapPortrait = new Pixmap(150, 150, Pixmap.Format.RGBA8888);
        pixmapPortrait.setColor(0f, 1f, 1f, 1f);
        pixmapPortrait.fill();

        Pixmap testmap= new Pixmap(75, 325, Pixmap.Format.RGBA8888);
        testmap.setColor(1, 0, 0, 05f);
        testmap.fill();

        texture = new TextureRegion(new Texture(testmap));
        Test1 = new Sprite(texture);
        Test1.setPosition(345, Gdx.graphics.getHeight() - 325);
        Gdx.app.log("Test 1", "X: " + Test1.getX() + " Y: " + Test1.getY());
        pathPart1.set(290, Gdx.graphics.getHeight() - 370, 180, 325);
        Gdx.app.log("Path 1", "X: " + pathPart1.getX() + " Y: " + pathPart1.getY() + " width: " + pathPart1.getWidth() + " height: " + pathPart1.getHeight());

        testmap= new Pixmap(980, 75, Pixmap.Format.RGBA8888);
        testmap.setColor(1, 1, 0, 05f);
        testmap.fill();

        texture = new TextureRegion(new Texture(testmap));
        Test2 = new Sprite(texture);
        Test2.setPosition(420, Gdx.graphics.getHeight() - 325);
        Gdx.app.log("Test 2", "X: " + Test2.getX() + " Y: " + Test2.getY());
        pathPart2.set(420, Gdx.graphics.getHeight() - 370, 980, 180);
        Gdx.app.log("Path 2", "X: " + pathPart2.getX() + " Y: " + pathPart2.getY() + " width: " + pathPart2.getWidth() + " height: " + pathPart2.getHeight());

        testmap= new Pixmap(75, 400, Pixmap.Format.RGBA8888);
        testmap.setColor(1, 1, 1, 05f);
        testmap.fill();

        texture = new TextureRegion(new Texture(testmap));
        Test3 = new Sprite(texture);
        Test3.setPosition(420 + 980, Gdx.graphics.getHeight() - 325 - 325);
        Gdx.app.log("Test 3", "X: " + Test3.getX() + " Y: " + Test3.getY());
        pathPart3.set(420 + 935, Gdx.graphics.getHeight() - 325 - 370, 180, 445);
        Gdx.app.log("Path 3", "X: " + pathPart3.getX() + " Y: " + pathPart3.getY() + " width: " + pathPart3.getWidth() + " height: " + pathPart3.getHeight());

        testmap= new Pixmap(465, 75, Pixmap.Format.RGBA8888);
        testmap.setColor(1, 0, 1, 05f);
        testmap.fill();

        texture = new TextureRegion(new Texture(testmap));
        Test4 = new Sprite(texture);
        Test4.setPosition(420 + 510, Gdx.graphics.getHeight() - 325 - 325);
        Gdx.app.log("Test 4", "X: " + Test4.getX() + " Y: " + Test4.getY());
        pathPart4.set(420 + 510, Gdx.graphics.getHeight() - 325 - 370, 465, 180);
        Gdx.app.log("Path 4", "X: " + pathPart4.getX() + " Y: " + pathPart4.getY() + " width: " + pathPart4.getWidth() + " height: " + pathPart4.getHeight());

        testmap= new Pixmap(75, 260, Pixmap.Format.RGBA8888);
        testmap.setColor(0, 0, 1, 05f);
        testmap.fill();

        texture = new TextureRegion(new Texture(testmap));
        Test5 = new Sprite(texture);
        Test5.setPosition(420 + 435, Gdx.graphics.getHeight() - 325 - 280 - 225);
        Gdx.app.log("Test 5", "X: " + Test5.getX() + " Y: " + Test5.getY());
        pathPart5.set(420 + 390, Gdx.graphics.getHeight() - 325 - 370 - 155, 180, 300);
        Gdx.app.log("Path 5", "X: " + pathPart5.getX() + " Y: " + pathPart5.getY() + " width: " + pathPart5.getWidth() + " height: " + pathPart5.getHeight());



        //fill gold list with 20 sprites of gold
        for(int i = 0; i < 20; i++){
            texture = new TextureRegion(new Texture("GoldSack.png"));
            Sprite newSprite = new Sprite(texture);
            newSprite.setSize(25,25);
            goldList.add(newSprite);
        }

        goldList.get(0).setPosition(Gdx.graphics.getWidth()/2 -100, 200);
        goldList.get(1).setPosition(Gdx.graphics.getWidth()/2 -77, 180);
        goldList.get(2).setPosition(Gdx.graphics.getWidth()/2 -60, 190);
        goldList.get(3).setPosition(Gdx.graphics.getWidth()/2 -78, 200);
        goldList.get(4).setPosition(Gdx.graphics.getWidth()/2 -90, 220);
        goldList.get(5).setPosition(Gdx.graphics.getWidth()/2 -50, 55);
        goldList.get(6).setPosition(Gdx.graphics.getWidth()/2 -70, 65);
        goldList.get(7).setPosition(Gdx.graphics.getWidth()/2 -65, 42);
        goldList.get(8).setPosition(Gdx.graphics.getWidth()/2 -95, 178);
        goldList.get(9).setPosition(Gdx.graphics.getWidth()/2 -60, 218);

        goldList.get(10).setPosition(Gdx.graphics.getWidth()/2 +80, 200);
        goldList.get(11).setPosition(Gdx.graphics.getWidth()/2 +67, 180);
        goldList.get(12).setPosition(Gdx.graphics.getWidth()/2 +40, 190);
        goldList.get(13).setPosition(Gdx.graphics.getWidth()/2 +58, 200);
        goldList.get(14).setPosition(Gdx.graphics.getWidth()/2 +70, 220);
        goldList.get(15).setPosition(Gdx.graphics.getWidth()/2 +35, 65);
        goldList.get(16).setPosition(Gdx.graphics.getWidth()/2 +52, 50);
        goldList.get(17).setPosition(Gdx.graphics.getWidth() / 2 + 32, 42);
        goldList.get(18).setPosition(Gdx.graphics.getWidth() / 2 + 50, 175);
        goldList.get(19).setPosition(Gdx.graphics.getWidth() / 2 + 40, 218);

        texture =  new TextureRegion(new Texture("Game_Background3.png"));
        background = new Image(texture);
        background.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("inside ", x + " " + y);
                if (toBuild == null && combatPhase == false) {

                    for (Defense defense : defenseList) {

                        if (defense.getDefenseBounds().contains(x, y)) {
                            Gdx.app.log("Defense ", "clicked");
                            upgrading = true;
                            toUpgrade = defense;
                            upgradeScreen();
                            Gdx.input.setInputProcessor(upgradeStage);
                            portraitSpr = defense.getPortrait();
                            portraitSpr.setPosition(Gdx.graphics.getWidth() / 2 - 525f, Gdx.graphics.getHeight() / 2 + 185f);

                            overlay.setHeight(Gdx.graphics.getHeight());
                            overlay.setWidth(Gdx.graphics.getWidth());
                        }
                    }
                }
            }
        });
        background.addListener(new DragListener() {
            @Override
            public void dragStart(InputEvent event, float x, float y, int pointer){
                if (toBuild != null) {
                    dragging = true;
                }
            }

            public void drag(InputEvent event, float x, float y, int pointer) {

                if (toBuild != null) {
                    toBuild.getDefenseBounds().setPosition(x, y);

                    CheckScreenConflict(x, y);
                    if (x != currentX || y != currentY ){
                        Gdx.app.log("Screen conflict" , " detected!!!!");
                        x = currentX;
                        y = currentY;
                        toBuild.getSprite().setPosition(x - 50, y - 50);
                        toBuild.getDefenseBounds().setPosition(x, y);
                    }

                    //loop through all the grid locations
                    for (int r = 0; r < 15; r++){
                        for (int c = 0; c < 10; c++){
                            //check if we have an overlap with the current grid slot
                            if (toBuild.getDefenseBounds().overlaps(screenGrid[r][c].getCellBounds())){

                                //if it's a path cell or there's already a tower placed -- don't allow placement
                                if (screenGrid[r][c].isCellOccupied()){
                                    Gdx.app.log("cell is " , "occupied!!!!!");
                                    toBuild.setSprite("DefenseInvalid.png");
                                    toBuild.setChangedSprite(true);
                                } else if (toBuild.isChangedSprite()){
                                    Gdx.app.log("cell is ", "empty!!");

                                    //set the tower back to it's normal sprite
                                    int defenseType = toBuild.getType();

                                    switch (defenseType){

                                        case 0:
                                            toBuild.setSprite("DefenseBasic.png");
                                            break;
                                        case 1:
                                            toBuild.setSprite("DefenseShortRange.png");
                                            break;
                                        case 2:
                                            toBuild.setSprite("DefenseBuff.png");
                                            break;
                                        case 3:
                                            toBuild.setSprite("DefenseLongRange.png");
                                            break;
                                    }
                                    toBuild.setChangedSprite(false);
                                }

                                x = screenGrid[r][c].getCellBounds().getX() +  (Gdx.graphics.getWidth() / 16) / 2;
                                y = screenGrid[r][c].getCellBounds().getY() + (Gdx.graphics.getHeight() / 9) / 2;
                                toBuild.getDefenseBounds().setPosition(x, y);
                                toBuild.getSprite().setPosition(x - 50, y - 50);
                                Gdx.app.log("screengrid x:" + r + " y: " + c, "collided");
                            }
                        }
                    }
                    toBuild.getDefenseBounds().setPosition(x, y);
                    toBuild.getSprite().setPosition(x - 50, y - 50);
                }
            }

            public void dragStop(InputEvent event, float x, float y, int pointer) {

                if (toBuild != null) {

                    //keep a record of what cell the tower was placed in
                    int cellNoX = 0;
                    int cellNoY = 0;

                    toBuild.getDefenseBounds().setPosition(x, y);

                    //CHECK SCREEN CONFLICT FIRST
                    CheckScreenConflict(x, y);
                    if (x != currentX || y != currentY ) {
                        Gdx.app.log("Screen conflict", " detected!!!!");
                        x = currentX;
                        y = currentY;
                        toBuild.getSprite().setPosition(x - 50, y - 50);
                        toBuild.getDefenseBounds().setPosition(x, y);
                    }

                    //loop through all the grid locations
                    for (int r = 0; r < 15; r++){
                        for (int c = 0; c < 10; c++){
                            //check if we have an overlap with the current grid slot

                            if (toBuild != null){

                                if (toBuild.getDefenseBounds().overlaps(screenGrid[r][c].getCellBounds())){

                                    //if it's a path cell or there's already a tower placed -- don't allow placement
                                    if (screenGrid[r][c].isCellOccupied()){
                                        Gdx.app.log("cell is ", "occupied!!!!!");
                                        //toBuild.setSprite("DefenseInvalid.png");
                                        //cancel the building
                                        dragging = false;
                                        toBuild = null;
                                        building = false;

                                    } else {

                                        if (toBuild.isChangedSprite()){
                                            Gdx.app.log("cell is " , "empty!!");
                                            //set the tower back to it's normal sprite
                                            int defenseType = toBuild.getType();

                                            switch (defenseType){

                                                case 0:
                                                    toBuild.setSprite("DefenseBasic.png");
                                                    break;
                                                case 1:
                                                    toBuild.setSprite("DefenseShortRange.png");
                                                    break;
                                                case 2:
                                                    toBuild.setSprite("DefenseBuff.png");
                                                    break;
                                                case 3:
                                                    toBuild.setSprite("DefenseLongRange.png");
                                                    break;
                                            }
                                            toBuild.setChangedSprite(false);
                                        }
                                        x = screenGrid[r][c].getCellBounds().getX() +  (Gdx.graphics.getWidth() / 16) / 2;
                                        y = screenGrid[r][c].getCellBounds().getY() + (Gdx.graphics.getHeight() / 9) / 2;
                                        toBuild.getDefenseBounds().setPosition(x, y);
                                        toBuild.getSprite().setPosition(x - 50, y - 50);
                                        Gdx.app.log("screengrid x:" + r + " y: " + c, "collided");
                                        //record the cell the tower was placed in
                                        cellNoX = r;
                                        cellNoY = c;
                                    }
                                }
                            }
                        }
                    }

                    //check if toBuild was cancelled from incorrect
                    //placement
                    if (toBuild != null){

                        toBuild.getDefenseBounds().setPosition(x, y);
                        toBuild.getSprite().setPosition(x - 50, y - 50);
                        toBuild.setDefenseBounds(new Rectangle(x - 50, y - 50, 100, 100));

                        meat -= toBuild.getCost();
                        toBuild.setRangeRadious(new Circle(toBuild.getSprite().getX() + 50,
                                toBuild.getSprite().getY() + 50, toBuild.getRange()));

                        mainStage.addActor(toBuild);
                        defenseList.add(toBuild);

                        dragging = false;
                        toBuild = null;
                        building = false;
                        buildPlacement.play();
                        screenGrid[cellNoX][cellNoY].setCellOccupied(true);

                    }
                }
            }
        });

        mainStage.addActor(background);




        texture = new TextureRegion(new Texture("Dragon.png"));
        dragonSpr = new Sprite(texture);
        dragonSpr.setSize(300, 250);
        dragonSpr.setPosition(Gdx.graphics.getWidth() / 2 - dragonSpr.getWidth() / 2, 0);

        Pixmap pixmap= new Pixmap(0, 0, Pixmap.Format.RGBA8888);
        pixmap.setColor(1, 0, 0, 05f);
        pixmap.fill();

        texture = new TextureRegion(new Texture(pixmap));
        overlay = new Image(texture);


        buildStage.addActor(overlay);
        upgradeStage.addActor(overlay);
        pauseStage.addActor(overlay);


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



        CreateButtons();
        CreateLabels();




        Gdx.input.setInputProcessor(mainStage);

    }

    /**
     * Checks the toBuild objects position and determines
     * moves it if it conflicts with another defense or
     * enemy walking path.
     * @param x
     * @param y
     */
    public void CheckPathConflict(float x, float y){
        if(toBuild.getDefenseBounds().overlaps(pathPart1)){
            Gdx.app.log("path1", "overlaps object");
            Gdx.app.log("Y pos ", " = "+ y);
            Gdx.app.log("PATh1 Y pos ", " = "+ pathPart1.getY());

            //y will never be greater than (off the screen)
            //so just test for less than
            if (x < pathPart1.getX() + pathPart1.getWidth()/2 && y > pathPart1.getY() + 1){

                Gdx.app.log("path1", "x less than overlaps object");
                x = pathPart1.getX();
                toBuild.getDefenseBounds().setPosition(x, y);
            } else if (x > pathPart1.getX() + pathPart1.getWidth()/2 && y > pathPart1.getY() + 1){
                Gdx.app.log("path1", "x greater than overlaps object");
                x = pathPart1.getX() + pathPart1.getWidth();
                toBuild.getDefenseBounds().setPosition(x, y);

                //does this now create an overlap?
                if (toBuild.getDefenseBounds().overlaps(pathPart2)){
                    if (y > pathPart2.getY()+ pathPart2.getHeight() / 2) {
                        Gdx.app.log("p1 overlap 2 AFTER CHANGE", "y greater than part2 object");
                        y = pathPart2.getY() + pathPart2.getHeight();
                        toBuild.getDefenseBounds().setPosition(x, y);
                    } else if (y < pathPart2.getY()+ pathPart2.getHeight() / 2){
                        Gdx.app.log("p1 overlap 2 AFTER CHANGE", "y less than than part2 object");
                        y = pathPart2.getY();
                        toBuild.getDefenseBounds().setPosition(x, y);
                    }
                }
                // if it's first overlap is on the bottom
            } else if (y > pathPart1.getY()){
                Gdx.app.log("path1", "y less than!!");
                y = pathPart1.getY();
                toBuild.getDefenseBounds().setPosition(x, y);
            }
        }

        else if(toBuild.getDefenseBounds().overlaps(pathPart2)) {
            if (y < pathPart2.getY() + pathPart2.getHeight() / 2) {
                Gdx.app.log("path2", "y less than overlaps object");
                y = pathPart2.getY();
                toBuild.getDefenseBounds().setPosition(x, y);
            } else if (y > pathPart2.getY() + pathPart2.getHeight() / 2) {
                Gdx.app.log("path2", "y greater than overlaps object");
                y = pathPart2.getY() + pathPart2.getHeight();
                toBuild.getDefenseBounds().setPosition(x, y);
            }
        }

        if (toBuild.getDefenseBounds().overlaps(pathPart3)) {
            Gdx.app.log("path3", "overlaps object");
            Gdx.app.log("Y pos ", " = " + y);

            if (y > pathPart3.getY() + pathPart3.getHeight()) {
                Gdx.app.log("path3", "y greater than!!");
                y = pathPart3.getY() + pathPart3.getHeight();
                toBuild.getDefenseBounds().setPosition(x, y);
            } else if (y < pathPart3.getY()) {
                Gdx.app.log("path3", "y less than!!");
                y = pathPart3.getY();
                toBuild.getDefenseBounds().setPosition(x, y);
            } else if (x < pathPart3.getX() + pathPart3.getWidth() / 2) {
                Gdx.app.log("path3", "x less than overlaps object");

                x = pathPart3.getX();
                toBuild.getDefenseBounds().setPosition(x, y);

                //does this now create an overlap?
                //path 2 overlap
                if (toBuild.getDefenseBounds().overlaps(pathPart2)) {
                    if (y < pathPart2.getY() + pathPart2.getHeight() / 2) {
                        Gdx.app.log("CORNER EXCEPTION", "y less than");
                        y = pathPart2.getY();
                        toBuild.getDefenseBounds().setPosition(x, y);
                    } else if (y > pathPart2.getY() + pathPart2.getHeight() / 2) {
                        Gdx.app.log("CORNER EXCEPTION", "y greater than");
                        y = pathPart2.getY() + pathPart2.getHeight();
                        toBuild.getDefenseBounds().setPosition(x, y);
                    }

                    //path 4 overlap
                } else if (toBuild.getDefenseBounds().overlaps(pathPart4)){
                    if (y < pathPart4.getY() + pathPart4.getHeight() / 2) {
                        Gdx.app.log("CORNER EXCEPTION", "y less than");
                        y = pathPart4.getY();
                        toBuild.getDefenseBounds().setPosition(x, y);
                    } else if (y > pathPart4.getY() + pathPart4.getHeight() / 2) {
                        Gdx.app.log("CORNER EXCEPTION", "y greater than");
                        y = pathPart4.getY() + pathPart4.getHeight();
                        toBuild.getDefenseBounds().setPosition(x, y);
                    }
                }

            } else if (x > pathPart3.getX() + pathPart3.getWidth() / 2) {
                Gdx.app.log("path3", "x greater than overlaps object");
                x = pathPart3.getX() + pathPart3.getWidth();
                toBuild.getDefenseBounds().setPosition(x, y);
            }
        }

        if (toBuild.getDefenseBounds().overlaps(pathPart4)) {
            Gdx.app.log("path4", "overlaps object");
            Gdx.app.log("Y pos ", " = " + y);

            if (y < pathPart4.getY() + pathPart4.getHeight() / 2) {
                Gdx.app.log("path4", "y less than overlaps object");
                //Gdx.app.log("overlap position: ", " x = " + x + " y = " + y);
                y = pathPart4.getY();
                toBuild.getDefenseBounds().setPosition(x, y);
            } else if (y > pathPart4.getY() + pathPart4.getHeight() / 2) {
                Gdx.app.log("path4", "y greater than overlaps object");
                //Gdx.app.log("overlap position: ", " x = " + x + " y = " + y);
                y = pathPart4.getY() + pathPart4.getHeight();
                toBuild.getDefenseBounds().setPosition(x, y);
            }
        }

        if(toBuild.getDefenseBounds().overlaps(pathPart5)){
            Gdx.app.log("path5", "overlaps object");
            Gdx.app.log("Y pos ", " = "+ y);

            if (y > pathPart5.getY() + pathPart5.getHeight() / 2) {
                Gdx.app.log("path5", "y greater than!!");
                y = pathPart5.getY() + pathPart5.getHeight();
                toBuild.getDefenseBounds().setPosition(x, y);
            } else if (y < pathPart5.getY()) {
                Gdx.app.log("path5", "y less than!!");
                y = pathPart5.getY();
                toBuild.getDefenseBounds().setPosition(x, y);

            } else if (x < pathPart5.getX() + pathPart5.getWidth()/2){

                Gdx.app.log("path5", "x less than overlaps object");
                x = pathPart5.getX();
                toBuild.getDefenseBounds().setPosition(x, y);
            } else if (x > pathPart5.getX() + pathPart5.getWidth()/2){
                Gdx.app.log("path5", "x greater than overlaps object");
                x = pathPart5.getX() + pathPart5.getWidth();
                toBuild.getDefenseBounds().setPosition(x, y);

                //does this now create an overlap?
                if (toBuild.getDefenseBounds().overlaps(pathPart4)){
                    if (y > pathPart4.getY()+ pathPart4.getHeight() / 2) {
                        Gdx.app.log("p5 overlap 4 AFTER CHANGE", "y greater than part4 object");
                        y = pathPart4.getY() + pathPart4.getHeight();
                        toBuild.getDefenseBounds().setPosition(x, y);
                    } else if (y < pathPart4.getY()+ pathPart4.getHeight() / 2){
                        Gdx.app.log("p5 overlap 4 AFTER CHANGE", "y less than than part4 object");
                        y = pathPart4.getY();
                        toBuild.getDefenseBounds().setPosition(x, y);
                    }
                }
            }
        }

        currentX = x;
        currentY = y;

        //positions[0] = x;
        //positions[1] = y;
        //return positions;

    }

    public void CheckDefenseConflict(float x, float y){

        for (Defense defense : defenseList) {
            if (defense.getDefenseBounds().overlaps(toBuild.getDefenseBounds())) {
                if (x - 50 < defense.getSprite().getWidth() / 2 + defense.getSprite().getX()) {
                    x = defense.getSprite().getX() - 50;
                } else if (x + 50 > defense.getSprite().getWidth() / 2 + defense.getSprite().getX()) {
                    x = defense.getSprite().getX() - 80;
                } else if (y - 50 < defense.getSprite().getHeight() / 2 + defense.getSprite().getY()) {
                    y = defense.getSprite().getY() - 50;
                } else if (y + 50 > defense.getSprite().getHeight() / 2 + defense.getSprite().getY()) {
                    y = defense.getSprite().getY() - 80;
                }
            }
        }

        currentX = x;
        currentY = y;
    }

    public void CheckScreenConflict(float x, float y){

        if (x - 50 < 0) {
            x = 40;
        }
        if (x + 50 > Gdx.graphics.getWidth()) {
            x = Gdx.graphics.getWidth() - 40;
        }
        if (y - 50 < 240) {
            y = 300;
        }
        if (y + 50 > Gdx.graphics.getHeight()) {
            y = Gdx.graphics.getHeight() - 40;
        }

        currentX = x;
        currentY = y;
    }

    public void CreateScreenGrid(){

        screenGrid = new Cell[15][10];

        //screenGrid[0][0] = new Rectangle(0f , Gdx.graphics.getHeight() - 120f,  Gdx.graphics.getWidth() / 16,  Gdx.graphics.getHeight() / 9);
        for (int x = 0; x < 15; x++){
            for (int y = 0; y < 10; y++){

                //int
                Rectangle temp;

                Gdx.app.log("creating grid position: ", "x: " + x + " y: " + y);
                Gdx.app.log("x pos: ", x * (Gdx.graphics.getWidth() / 17) + "" );
                Gdx.app.log("y pos: ", (y * (Gdx.graphics.getHeight() / 13) + 240) + "" );
                Gdx.app.log("width: ", Gdx.graphics.getWidth() / 17 + "");
                Gdx.app.log("height: ", Gdx.graphics.getHeight() / 13 + "");
                temp = new Rectangle(x * (Gdx.graphics.getWidth() / 17) , (y * (Gdx.graphics.getHeight() / 13) + 240),  Gdx.graphics.getWidth() / 17,  Gdx.graphics.getHeight() / 13);

                //check if it's a cell on the path
                boolean pathCell = false;

                if (x == 3 && y >= 7 && y <= 9){
                    pathCell = true;
                } else if (y == 6 && x >= 3 && x <= 13){
                    pathCell = true;
                } else if (x == 13 && y >= 3 && y <= 5){
                    pathCell = true;
                } else if (y == 2 && x >= 8 && x <= 13){
                    pathCell = true;
                } else if (x == 8 && y >= 0 && y <= 1){
                    pathCell = true;
                }
                Gdx.app.log("cellOccupied : ", pathCell + "");
                Cell theCell = new Cell(x, y, pathCell, temp);

                screenGrid[x][y] = theCell;

            }
        }
    }

    public void CreateButtons(){
        pauseButton = new TextButton("||", skin, "default");
        SetButtonValues(pauseButton,mainStage, 100f, 100f, Gdx.graphics.getWidth() - 125f, Gdx.graphics.getHeight() - 125f);
        abilityOne = new TextButton("", skin, "default");
        SetButtonValues(abilityOne,mainStage, 150f,150f, 75, 45);
        abilityTwo = new TextButton("", skin, "default");
        SetButtonValues(abilityTwo,mainStage, 150f,150f, 375, 45);
        abilityThree = new TextButton("", skin, "default");
        SetButtonValues(abilityThree,mainStage, 150f,150f, Gdx.graphics.getWidth() - 525, 45);
        abilityFour = new TextButton("", skin, "default");
        SetButtonValues(abilityFour,mainStage, 150f, 150f, Gdx.graphics.getWidth() - 225, 45);
        startButton = new TextButton("Start", skin, "default");
        SetButtonValues(startButton,mainStage, 200f, 100f, Gdx.graphics.getWidth() - 210f, Gdx.graphics.getHeight() /2);
        buildButton = new TextButton("Build", skin, "default");
        SetButtonValues(buildButton,mainStage, 200f, 100f, Gdx.graphics.getWidth() - 210f, Gdx.graphics.getHeight() /2 - 200f);
        buildbackButton = new TextButton("Back", skin, "default");
        SetButtonValues(buildbackButton,buildStage, 400f , 150f, Gdx.graphics.getWidth() /2 - 200f, 35);

        pauseContinueButton = new TextButton("Continue", skin, "default");
        SetButtonValues(pauseContinueButton,pauseStage, 700f , 150f, Gdx.graphics.getWidth() /2 - 350f,  Gdx.graphics.getHeight() / 2 - 50f);
        pauseExitButton = new TextButton("Exit Level", skin, "default");
        SetButtonValues(pauseExitButton,pauseStage, 700f , 150f, Gdx.graphics.getWidth() /2 - 350f, Gdx.graphics.getHeight() / 2 - 250f);
        pauseSettingsButton = new TextButton("Settings", skin, "default");
        SetButtonValues(pauseSettingsButton,pauseStage, 200f , 180f, Gdx.graphics.getWidth() /2 + 390f, Gdx.graphics.getHeight() / 2 + 250f);

        defenseOne = new TextButton("Goblin",skin,"default");
        SetButtonValues(defenseOne,buildStage, 200f, 200f, Gdx.graphics.getWidth() /2 - 550, 250);
        defenseTwo = new TextButton("Minotaur",skin,"default");
        SetButtonValues(defenseTwo,buildStage, 200f, 200f, Gdx.graphics.getWidth() /2 - 250, 250);
        defenseThree = new TextButton("Drummer",skin,"default");
        SetButtonValues(defenseThree,buildStage, 200f, 200f, Gdx.graphics.getWidth() /2 + 50, 250);
        defenseFour = new TextButton("Drake",skin,"default");
        SetButtonValues(defenseFour,buildStage, 200f, 200f, Gdx.graphics.getWidth() /2 + 350, 250);
        upgradeButtonOne = new TextButton("Upgrade", skin, "default");
        SetButtonValues(upgradeButtonOne, upgradeStage,200f,200f, Gdx.graphics.getWidth() /2 - 450, 275);
        upgradeButtonTwo = new TextButton("Upgrade", skin, "default");
        SetButtonValues(upgradeButtonTwo, upgradeStage, 200f, 200f, Gdx.graphics.getWidth() / 2 - 100, 275);
        upgradeButtonThree = new TextButton("Upgrade", skin, "default");
        SetButtonValues(upgradeButtonThree, upgradeStage, 200f, 200f, Gdx.graphics.getWidth() / 2 + 250, 275);
        upgradebackButton = new TextButton("Back", skin, "default");
        SetButtonValues(upgradebackButton, upgradeStage, 400f, 150f, Gdx.graphics.getWidth() / 2 - 200f, 35);


    }

    public void CreateLabels(){

        //PAUSE screen
        pauseLabel = new Label("Paused", skin);
        SetLabelValues(pauseLabel, pauseStage, 800f, 200f, Gdx.graphics.getWidth() /2 - 400f,  Gdx.graphics.getHeight() / 2 + 100f, 5);

        //BUILD screen
        buildGoblinAttack = new Label("Attack: 200" , skin);
        SetLabelValues(buildGoblinAttack, buildStage, 200f, 100f, Gdx.graphics.getWidth() / 2 - 550, 700, 2);
        buildGoblinAttack.setAlignment(Align.center);
        buildGoblinRange = new Label("Range: 200", skin);
        SetLabelValues(buildGoblinRange, buildStage, 200f, 100f, Gdx.graphics.getWidth() / 2 - 550, 600, 2);
        buildGoblinRange.setAlignment(Align.center);
        buildGoblinSpeed = new Label("Speed: 25", skin);
        SetLabelValues(buildGoblinSpeed, buildStage, 200f, 100f, Gdx.graphics.getWidth() / 2 - 550, 500, 2);
        buildGoblinSpeed.setAlignment(Align.center);
        buildGoblinCost = new Label("Cost: 400", skin);
        SetLabelValues(buildGoblinCost, buildStage, 200f, 100f, Gdx.graphics.getWidth() / 2 - 550, 800, 3);
        buildGoblinCost.setAlignment(Align.center);

        buildMinoAttack = new Label("Attack: 500", skin);
        SetLabelValues(buildMinoAttack, buildStage, 200f, 100f, Gdx.graphics.getWidth() / 2 - 250, 700, 2);
        buildMinoAttack.setAlignment(Align.center);
        buildMinoRange = new Label("Range: 150", skin);
        SetLabelValues(buildMinoRange, buildStage, 200f, 100f, Gdx.graphics.getWidth() / 2 - 250, 600, 2);
        buildMinoRange.setAlignment(Align.center);
        buildMinoSpeed = new Label("Speed: 20", skin);
        SetLabelValues(buildMinoSpeed, buildStage, 200f, 100f, Gdx.graphics.getWidth() / 2 - 250, 500, 2);
        buildMinoSpeed.setAlignment(Align.center);
        buildMinoCost = new Label("Cost: 800", skin);
        SetLabelValues(buildMinoCost, buildStage, 200f, 100f, Gdx.graphics.getWidth() / 2 - 250, 800, 3);
        buildMinoCost.setAlignment(Align.center);

        buildDrumAttack = new Label("Attack: XX", skin);
        SetLabelValues(buildDrumAttack, buildStage, 200f, 100f, Gdx.graphics.getWidth() / 2 + 50, 700, 2);
        buildDrumAttack.setAlignment(Align.center);
        buildDrumRange = new Label("Range: XX", skin);
        SetLabelValues(buildDrumRange, buildStage, 200f, 100f, Gdx.graphics.getWidth() / 2 + 50, 600, 2);
        buildDrumRange.setAlignment(Align.center);
        buildDrumSpeed = new Label("Speed: XX", skin);
        SetLabelValues(buildDrumSpeed, buildStage, 200f, 100f, Gdx.graphics.getWidth() / 2 + 50, 500, 2);
        buildDrumSpeed.setAlignment(Align.center);
        buildDrumCost = new Label("Cost: $$$", skin);
        SetLabelValues(buildDrumCost, buildStage, 200f, 100f, Gdx.graphics.getWidth() / 2 + 50, 800, 3);
        buildDrumCost.setAlignment(Align.center);

        buildDrakeAttack = new Label("Attack: 800", skin);
        SetLabelValues(buildDrakeAttack, buildStage, 200f, 100f, Gdx.graphics.getWidth() / 2 + 350, 700, 2);
        buildDrakeAttack.setAlignment(Align.center);
        buildDrakeRange = new Label("Range: 500", skin);
        SetLabelValues(buildDrakeRange, buildStage, 200f, 100f, Gdx.graphics.getWidth() / 2 + 350, 600, 2);
        buildDrakeRange.setAlignment(Align.center);
        buildDrakeSpeed = new Label("Speed: 10", skin);
        SetLabelValues(buildDrakeSpeed, buildStage, 200f, 100f, Gdx.graphics.getWidth() / 2 + 350, 500, 2);
        buildDrakeSpeed.setAlignment(Align.center);
        buildDrakeCost = new Label("Cost: 1500", skin);
        SetLabelValues(buildDrakeCost, buildStage, 200f, 100f, Gdx.graphics.getWidth() / 2 + 350, 800, 3);
        buildDrakeCost.setAlignment(Align.center);


        LEnemies.setColor(Color.WHITE);
        LCoins.setColor(Color.WHITE);
        LMeat.setColor(Color.WHITE);
        LWave.setColor(Color.WHITE);
        Lability1CD.setColor(Color.WHITE);
        Lability2CD.setColor(Color.WHITE);
        Lability3CD.setColor(Color.WHITE);
        Lability4CD.setColor(Color.WHITE);

        LCoins.getData().setScale(3, 3);
        LMeat.getData().setScale(3, 3);
        LWave.getData().setScale(3, 3);
        LEnemies.getData().setScale(3, 3);
        Lability1CD.getData().setScale(5, 5);
        Lability2CD.getData().setScale(5, 5);
        Lability3CD.getData().setScale(5, 5);
        Lability4CD.getData().setScale(5, 5);

    }


    public void upgradeScreen(){
        //UPGRADE screen
        upgradeAttackInfoLabel = new Label(toUpgrade.getDescription(), skin);
        SetLabelValues(upgradeAttackInfoLabel, upgradeStage, 200f, 100f, Gdx.graphics.getWidth() /2 - 275f,  Gdx.graphics.getHeight() / 2 + 175f, 2);

        upgradeOneCostLabel = new Label("Cost: 50", skin);
        SetLabelValues(upgradeOneCostLabel, upgradeStage, 200f, 100f, Gdx.graphics.getWidth() /2 - 400f,  Gdx.graphics.getHeight() / 2 - 350f, 2);
        upgradeTwoCostLabel = new Label("Cost: 50", skin);
        SetLabelValues(upgradeTwoCostLabel, upgradeStage, 200f, 100f, Gdx.graphics.getWidth() /2 - 50f ,  Gdx.graphics.getHeight() / 2 - 350f, 2);
        upgradeThreeCostLabel = new Label("Cost: 50", skin);
        SetLabelValues(upgradeThreeCostLabel, upgradeStage, 200f, 100f, Gdx.graphics.getWidth() /2 + 300f,  Gdx.graphics.getHeight() / 2 - 350f, 2);

        upgradeOneImprovementLabel = new Label(Integer.toString(toUpgrade.getDamage()) + "-->>" + Integer.toString(toUpgrade.getDamage()+50), skin);
        SetLabelValues(upgradeOneImprovementLabel, upgradeStage, 200f, 100f, Gdx.graphics.getWidth() /2 - 450f,  Gdx.graphics.getHeight() / 2 - 80f, 2);
        upgradeOneImprovementLabel.setAlignment(Align.center);
        upgradeTwoImprovementLabel = new Label(Integer.toString(toUpgrade.getRange()) + "-->>" + Integer.toString(toUpgrade.getRange()+50), skin);
        SetLabelValues(upgradeTwoImprovementLabel, upgradeStage, 200f, 100f, Gdx.graphics.getWidth() /2 - 100f ,  Gdx.graphics.getHeight() / 2 - 80f, 2);
        upgradeTwoImprovementLabel.setAlignment(Align.center);
        upgradeThreeImprovementLabel = new Label(Integer.toString((int)toUpgrade.getAtkSpeed()) + "-->>" + Integer.toString((int)toUpgrade.getAtkSpeed()+50), skin);
        SetLabelValues(upgradeThreeImprovementLabel, upgradeStage, 200f, 100f, Gdx.graphics.getWidth() /2 + 250f,  Gdx.graphics.getHeight() / 2 - 80f, 2);
        upgradeThreeImprovementLabel.setAlignment(Align.center);

        upgradeAttackLabel = new Label("Attack", skin);
        SetLabelValues(upgradeAttackLabel, upgradeStage, 200f, 100f, Gdx.graphics.getWidth() /2 - 450f,  Gdx.graphics.getHeight() / 2 - 15f, 3);
        upgradeAttackLabel.setAlignment(Align.center);
        upgradeRangeLabel = new Label("Range", skin);
        SetLabelValues(upgradeRangeLabel, upgradeStage, 200f, 100f, Gdx.graphics.getWidth() /2 - 100f ,  Gdx.graphics.getHeight() / 2 - 15f, 3);
        upgradeRangeLabel.setAlignment(Align.center);
        upgradeSpeedLabel = new Label("Speed", skin);
        SetLabelValues(upgradeSpeedLabel, upgradeStage, 200f, 100f, Gdx.graphics.getWidth() /2 + 250f,  Gdx.graphics.getHeight() / 2 - 15f, 3);
        upgradeSpeedLabel.setAlignment(Align.center);
    }

    public void update(float deltaTime){
        // this solves the concurrent issue of removing an element from an iterating list;
        //Enemy toRemove = null;
        Projectile bulletHit = null;




        if (movementCD <= 0.0f) {

            if(combatPhase == true) {
                for (Enemy enemy : enemyList) {
                    if (enemy.getState() == 0){
                        if (enemy.getMovementPoint() == 0) {
                            enemy.setY(enemy.getY() + enemy.getSpeed());
                            if (enemy.getY() >= Gdx.graphics.getHeight()) {
                                toRemove = enemy;
                            }
                        } else if (enemy.getMovementPoint() == 1) {
                            if (enemy.isCarryingGold() == true) {
                                enemy.setX(enemy.getX() - enemy.getSpeed());
                                if (enemy.getX() <= 365) {
                                    enemy.setX(365);
                                    enemy.setMovementPoint(0);
                                    enemy.getSprite().rotate(-90);
                                }
                            } else {
                                enemy.setY(enemy.getY() - enemy.getSpeed());
                                if (enemy.getY() <= 780) {
                                    enemy.setY(780);
                                    enemy.setMovementPoint(2);
                                    enemy.getSprite().rotate(90);
                                }
                            }
                        } else if (enemy.getMovementPoint() == 2) {
                            if (enemy.isCarryingGold() == true) {
                                enemy.setY(enemy.getY() + enemy.getSpeed());
                                if (enemy.getY() >= 780) {
                                    enemy.setY(780);
                                    enemy.setMovementPoint(1);
                                    enemy.getSprite().rotate(90);
                                }
                            } else {
                                enemy.setX(enemy.getX() + enemy.getSpeed());
                                if (enemy.getX() >= 1420) {
                                    enemy.setX(1420);
                                    enemy.setMovementPoint(3);
                                    enemy.getSprite().rotate(-90);
                                }
                            }
                        } else if (enemy.getMovementPoint() == 3) {
                            if (enemy.isCarryingGold() == true) {
                                enemy.setX(enemy.getX() + enemy.getSpeed());
                                if (enemy.getX() >= 1420) {
                                    enemy.setX(1420);
                                    enemy.setMovementPoint(2);

                                    enemy.getSprite().rotate(90);
                                }
                            } else {
                                enemy.setY(enemy.getY() - enemy.getSpeed());
                                if (enemy.getY() <= 450) {
                                    enemy.setY(450);
                                    enemy.setMovementPoint(4);
                                    enemy.getSprite().rotate(-90);
                                }
                            }
                        } else if (enemy.getMovementPoint() == 4) {
                            if (enemy.isCarryingGold() == true) {
                                enemy.setY(enemy.getY() + enemy.getSpeed());
                                if (enemy.getY() >= 450) {
                                    enemy.setY(450);
                                    enemy.setMovementPoint(3);
                                    enemy.getSprite().rotate(-90);
                                }
                            } else {
                                enemy.setX(enemy.getX() - enemy.getSpeed());
                                if (enemy.getX() <= 870) {
                                    enemy.setX(870);
                                    enemy.setMovementPoint(5);
                                    enemy.getSprite().rotate(90);
                                }
                            }
                        } else if (enemy.getMovementPoint() == 5) {

                            enemy.setY(enemy.getY() - enemy.getSpeed());
                            if (enemy.getY() <= 250) {

                                enemy.setAltSprite();
                                enemy.setCarryingGold(true);
                                enemy.setMovementPoint(4);
                                enemy.getSprite().rotate(-180);
                                Random rand = new Random();
                                int num = rand.nextInt(goldList.size() - 1);
                                enemy.setGoldX((int) goldList.get(num).getX());
                                enemy.setGoldY((int) goldList.get(num).getY());
                                goldList.remove(num);
                                coinsRemaining--;
                            }
                        }
                    }
                    // all enemies that are frighten do not move
                    else if (enemy.getState() == 1) {
                        enemy.setStateTime(enemy.getStateTime() - 1);
                        if (enemy.getStateTime() <= 0){
                            enemy.setState(0);
                        }
                    }
                    // all enemies that are enraged move at double speed
                    else if (enemy.getState() == 2) {
                        if (enemy.getMovementPoint() == 0) {
                            enemy.setY(enemy.getY() + (enemy.getSpeed()*2));
                            if (enemy.getY() >= Gdx.graphics.getHeight()) {
                                toRemove = enemy;
                            }
                        } else if (enemy.getMovementPoint() == 1) {
                            if (enemy.isCarryingGold() == true) {
                                enemy.setX(enemy.getX() - (enemy.getSpeed()*2));
                                if (enemy.getX() <= 365) {
                                    enemy.setX(365);
                                    enemy.setMovementPoint(0);
                                    enemy.getSprite().rotate(-90);
                                }
                            } else {
                                enemy.setY(enemy.getY() - (enemy.getSpeed()*2));
                                if (enemy.getY() <= 780) {
                                    enemy.setY(780);
                                    enemy.setMovementPoint(2);
                                    enemy.getSprite().rotate(90);
                                }
                            }
                        } else if (enemy.getMovementPoint() == 2) {
                            if (enemy.isCarryingGold() == true) {
                                enemy.setY(enemy.getY() + (enemy.getSpeed()*2));
                                if (enemy.getY() >= 780) {
                                    enemy.setY(780);
                                    enemy.setMovementPoint(1);
                                    enemy.getSprite().rotate(90);
                                }
                            } else {
                                enemy.setX(enemy.getX() + (enemy.getSpeed()*2));
                                if (enemy.getX() >= 1420) {
                                    enemy.setX(1420);
                                    enemy.setMovementPoint(3);
                                    enemy.getSprite().rotate(-90);
                                }
                            }
                        } else if (enemy.getMovementPoint() == 3) {
                            if (enemy.isCarryingGold() == true) {
                                enemy.setX(enemy.getX() + (enemy.getSpeed()*2));
                                if (enemy.getX() >= 1420) {
                                    enemy.setX(1420);
                                    enemy.setMovementPoint(2);

                                    enemy.getSprite().rotate(90);
                                }
                            } else {
                                enemy.setY(enemy.getY() - (enemy.getSpeed()*2));
                                if (enemy.getY() <= 450) {
                                    enemy.setY(450);
                                    enemy.setMovementPoint(4);
                                    enemy.getSprite().rotate(-90);
                                }
                            }
                        } else if (enemy.getMovementPoint() == 4) {
                            if (enemy.isCarryingGold() == true) {
                                enemy.setY(enemy.getY() + (enemy.getSpeed()*2));
                                if (enemy.getY() >= 450) {
                                    enemy.setY(450);
                                    enemy.setMovementPoint(3);
                                    enemy.getSprite().rotate(-90);
                                }
                            } else {
                                enemy.setX(enemy.getX() - (enemy.getSpeed()*2));
                                if (enemy.getX() <= 870) {
                                    enemy.setX(870);
                                    enemy.setMovementPoint(5);
                                    enemy.getSprite().rotate(90);
                                }
                            }
                        } else if (enemy.getMovementPoint() == 5) {

                            enemy.setY(enemy.getY() - (enemy.getSpeed()*2));
                            if (enemy.getY() <= 250) {

                                enemy.setAltSprite();
                                enemy.setCarryingGold(true);
                                enemy.setMovementPoint(4);
                                enemy.getSprite().rotate(-180);
                                Random rand = new Random();
                                int num = rand.nextInt(goldList.size() - 1);
                                enemy.setGoldX((int) goldList.get(num).getX());
                                enemy.setGoldY((int) goldList.get(num).getY());
                                goldList.remove(num);
                                coinsRemaining--;
                            }
                        }
                        enemy.setStateTime(enemy.getStateTime() - 1);
                        if (enemy.getStateTime() <= 0) {
                            enemy.setState(0);
                        }
                    }

                    enemy.getCircle().setPosition(enemy.getX(),enemy.getY());

                    for(Defense defense: defenseList){


                        if(defense.getAtkCD() == defense.getAtkSpeed()) {

                            if (defense.getTarget() == null) {
                                if (defense.getRangeRadious().overlaps(enemy.getCircle())) {
                                    Gdx.app.log("Target", "new Target" + enemy.getId());
                                    defense.setTarget(enemy);
                                    Projectile projectile = new Projectile(0);
                                    projectile.setDefense(defense);
                                    projectile.setDamage(defense.getDamage());
                                    projectile.setProjectile(new Circle(defense.getSprite().getX() + 50, defense.getSprite().getY() + 50, 20f));
                                    projectile.getSprite().setPosition(defense.getSprite().getX() + 50, defense.getSprite().getY() + 50);
                                    projectileList.add(projectile);
                                    projectileSound.play();

                                }
                            } else {

                                if (defense.getRangeRadious().overlaps(defense.getTarget().getCircle())) {

                                    Projectile projectile = new Projectile(0);
                                    projectile.setDefense(defense);
                                    projectile.setDamage(defense.getDamage());
                                    projectile.setProjectile(new Circle(defense.getSprite().getX() + 50, defense.getSprite().getY() + 50, 20f));
                                    projectile.getSprite().setPosition(defense.getSprite().getX() + 50, defense.getSprite().getY() + 50);
                                    projectileList.add(projectile);
                                    projectileSound.play();

                                } else {
                                    Gdx.app.log("Target", "lost");
                                    defense.setTarget(null);
                                }
                            }

                            defense.setAtkCD(0);
                        }else{
                            defense.incremmentCD();
                        }

                    }
                }

                spawnTime++;

                if(spawnTime == 100 && enemiesSpawned != 10) {
                    Enemy newEnemy;
                    if(waveCount > 1) {
                        Random randInt = new Random();

                        int rand = randInt.nextInt(3);
                        newEnemy = new Enemy(rand);

                        addEnemy(newEnemy);
                    }

                    if(waveCount == 1) {
                        // 2 thieves, tank, 3 footmen, 2 barbarians, 2 footmen
                        if(enemiesSpawned == 0) {
                            newEnemy = new Enemy(0);
                            addEnemy(newEnemy);
                        }

                        if(enemiesSpawned == 1) {
                            newEnemy = new Enemy(1);
                            addEnemy(newEnemy);
                        }

                        else if(enemiesSpawned == 2) {
                            newEnemy = new Enemy(1);
                            addEnemy(newEnemy);
                        }

                        else if(enemiesSpawned == 3) {
                            newEnemy = new Enemy(2);
                            addEnemy(newEnemy);
                        }

                        else if(enemiesSpawned == 4) {
                            newEnemy = new Enemy(0);
                            addEnemy(newEnemy);
                        }

                        else if(enemiesSpawned == 5) {
                            newEnemy = new Enemy(0);
                            addEnemy(newEnemy);
                        }

                        else if(enemiesSpawned == 6) {
                            newEnemy = new Enemy(0);
                            addEnemy(newEnemy);
                        }

                        else if(enemiesSpawned == 7) {
                            newEnemy = new Enemy(3);
                            addEnemy(newEnemy);
                        }

                        else if(enemiesSpawned == 8) {
                            newEnemy = new Enemy(3);
                            addEnemy(newEnemy);
                        }

                        else if(enemiesSpawned == 9) {
                            newEnemy = new Enemy(0);
                            addEnemy(newEnemy);
                        }

                        else if(enemiesSpawned == 10) {
                            newEnemy = new Enemy(0);
                            addEnemy(newEnemy);
                        }
                    }
                    spawnTime = 0;
                }

                if(projectileList.size() > 0) {
                    for (Projectile projectile : projectileList) {

                        if(projectile.getDefense().getTarget() != null) {

                            float x = projectile.getSprite().getX();
                            float y = projectile.getSprite().getY();

                            float targetx = projectile.getDefense().getTarget().getX();
                            float targety = projectile.getDefense().getTarget().getY();

                            double deltaX = targetx - x;
                            double deltaY = targety - y;
                            double angle = atan2(deltaY, deltaX);



                            x = (float) (x + 1000 * deltaTime * cos(angle));
                            y = (float) (y + 1000 * deltaTime * sin(angle));

                            projectile.getSprite().rotate(25);
                            projectile.getSprite().setPosition(x, y);
                            projectile.getProjectile().setPosition(x, y);

                            if (projectile.getDefense().getTarget().getCircle().overlaps(projectile.getProjectile())) {
                                projectile.getDefense().getTarget().setHealth(projectile.getDefense().getTarget().getHealth() -
                                        projectile.getDamage());
                                bulletHit = projectile;
                            }
                            if (projectile.getDefense().getTarget().getHealth() < 0) {

                                if(enemyList.contains(projectile.getDefense().getTarget())) {
                                    toRemove = projectile.getDefense().getTarget();
                                    Gdx.app.log("Target", "killed");
                                    meat += 25;

                                }
                                projectile.getDefense().setTarget(null);
                            }

                        }else{
                            bulletHit = projectile;
                        }
                    }
                    if(bulletHit != null){
                        projectileList.remove(bulletHit);
                        bulletHit = null;
                    }
                }

            }
            movementCD = 0.0f;
        }

        movementCD -= elapsedTime;
        if(toRemove != null){
            if(toRemove.isCarryingGold() && toRemove.getHealth() <= 0){
                coinsRemaining++;
                texture = new TextureRegion(new Texture("GoldSack.png"));
                Sprite newSprite = new Sprite(texture);
                newSprite.setSize(25,25);
                newSprite.setPosition(toRemove.getGoldX(), toRemove.getGoldY());
                goldList.add(newSprite);
            }
            enemyDeath.play();

            enemyList.remove(toRemove);
            toRemove = null;
            enemiesRemaining--;
            if(enemiesRemaining <= 0 && enemiesSpawned == 10){
                success.play();
                success.setVolume(100);
                combatPhase = false;
                enemiesSpawned = 0;
                healthMod += 0.1;
                ability1CD = 0; // CD time: 6 seconds
                abilityOne.setText("");
                ability2CD = 0; // CD time: 20 seconds
                abilityTwo.setText("");
                ability3CD = 0; // CD time: 30 seconds
                abilityThree.setText("");
                ability4CD = ABILITY4_CD_TIME; // CD time: 120 seconds, starts on cooldown
                abilityFour.setText(Integer.toString(ABILITY4_CD_TIME));
                if(coinsRemaining >= 0){
                    SaveGameState();
                    SaveDefenses();
                }
            }
        }
    }

    public void render(float f) {

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        if (state == State.RUNNING){    //only run update if the game is running
            update(f);
        }
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        //Divide by a thousand to convert from milliseconds to seconds


        elapsedTime = (System.currentTimeMillis() - currentTime) / 1000.0f;
        //Gdx.app.log("et" + (int)currentTime, "lt:" +(int) elapsedTime);



        mainbatch.begin();
//        shapeRenderer.begin();

        background.draw(mainbatch, 1);
        dragonSpr.draw(mainbatch);
        UIBackAbility2.draw(mainbatch,1);
        UIBackAbility1.draw(mainbatch,1);

        //determine state of the game
        switch (state) {

            case RUNNING:


                if (combatPhase == true) {

                    if((int)elapsedTime > (int)lastTime){
                        if (ability1CD > 0)
                        {
                            ability1CD--;
                            abilityOne.setText((ability1CD > 0)?Integer.toString(ability1CD):"");
                        }
                        if (ability2CD > 0)
                        {
                            ability2CD--;
                            abilityTwo.setText((ability2CD > 0)?Integer.toString(ability2CD):"");
                        }
                        if (ability3CD > 0)
                        {
                            ability3CD--;
                            abilityThree.setText((ability3CD > 0)?Integer.toString(ability3CD):"");
                        }
                        if (ability4CD > 0)
                        {
                            ability4CD--;
                            abilityFour.setText((ability4CD > 0)?Integer.toString(ability4CD):"");
                        }
                    }
                    for (Enemy enemy : enemyList) {
                        enemy.getSprite().draw(mainbatch);
                    }

                    if (startButton.getX() < Gdx.graphics.getWidth()) {
                        startButton.setPosition(startButton.getX() + 5, startButton.getY());
                        buildButton.setPosition(buildButton.getX() + 5, buildButton.getY());
                    }
                } else {
                    if (startButton.getX() > 1585) {
                        buildButton.setPosition(buildButton.getX() - 5, buildButton.getY());
                        startButton.setPosition(startButton.getX() - 5, startButton.getY());
                    }

                    if (dragging) {
                        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                        shapeRenderer.setColor(1, 1, 1, 0.3f);
                        shapeRenderer.circle(toBuild.getSprite().getX() + 50,
                                toBuild.getSprite().getY() + 50, toBuild.getRange());
                        toBuild.getSprite().draw(mainbatch, 0.5f);

                    }
                }

                if (defenseList.size() > 0) {
                    for (Defense defense : defenseList) {
                        defense.getSprite().draw(mainbatch);
                    }
                }

                if(goldList.size() > 0){
                    for(Sprite gold: goldList){
                        gold.draw(mainbatch);
                    }
                }

                if(projectileList.size() > 0){
                    for(Projectile projectile: projectileList){
                        projectile.getSprite().draw(mainbatch);
                    }
                }

                pauseButton.draw(mainbatch, 1);
                abilityOne.draw(mainbatch, 1);
                ability1BOverlay.draw(mainbatch, 1);
                abilityTwo.draw(mainbatch, 1);
                ability2BOverlay.draw(mainbatch, 1);
                abilityThree.draw(mainbatch, 1);
                ability3BOverlay.draw(mainbatch, 1);
                abilityFour.draw(mainbatch, 1);
                ability4BOverlay.draw(mainbatch, 1);
                startButton.draw(mainbatch, 1);
                buildButton.draw(mainbatch, 1);


                UIText();
                lastTime = elapsedTime;

                //   Test1.draw(mainbatch);
                //   Test2.draw(mainbatch);
                //   Test3.draw(mainbatch);
                //   Test4.draw(mainbatch);
                //   Test5.draw(mainbatch);


                mainbatch.end();
                shapeRenderer.end();

                if (building && toBuild == null) {


                    buildBatch.begin();
                    overlay.draw(buildBatch, 0.5f);
                    buildUIBack.draw(buildBatch, 1);
                    buildbackButton.draw(buildBatch, 1);
                    defenseOne.draw(buildBatch, 1);
                    defenseThree.draw(buildBatch, 1);
                    defenseTwo.draw(buildBatch, 1);
                    defenseFour.draw(buildBatch, 1);
                    buildGoblinAttack.draw(buildBatch,1);
                    buildGoblinRange.draw(buildBatch,1);
                    buildGoblinSpeed.draw(buildBatch,1);
                    buildGoblinCost.draw(buildBatch,1);

                    buildMinoAttack.draw(buildBatch,1);
                    buildMinoRange.draw(buildBatch,1);
                    buildMinoSpeed.draw(buildBatch,1);
                    buildMinoCost.draw(buildBatch,1);

                    buildDrumAttack.draw(buildBatch,1);
                    buildDrumRange.draw(buildBatch,1);
                    buildDrumSpeed.draw(buildBatch,1);
                    buildDrumCost.draw(buildBatch,1);

                    buildDrakeAttack.draw(buildBatch,1);
                    buildDrakeRange.draw(buildBatch,1);
                    buildDrakeSpeed.draw(buildBatch,1);
                    buildDrakeCost.draw(buildBatch,1);

                    buildBatch.end();

                }

                if (upgrading) {

                    upgradeBatch.begin();

                    overlay.draw(upgradeBatch, 0.5f);
                    buildUIBack.draw(upgradeBatch, 1);
                    upgradeUIBack2.draw(upgradeBatch,1);
                    upgradebackButton.draw(upgradeBatch, 1);
                    upgradeAttackLabel.draw(upgradeBatch,1);
                    upgradeRangeLabel.draw(upgradeBatch,1);
                    upgradeSpeedLabel.draw(upgradeBatch,1);
                    upgradeButtonOne.draw(upgradeBatch, 1);
                    upgradeOneCostLabel.draw(upgradeBatch,1);
                    upgradeButtonTwo.draw(upgradeBatch, 1);
                    upgradeTwoCostLabel.draw(upgradeBatch,1);
                    upgradeButtonThree.draw(upgradeBatch, 1);
                    upgradeThreeCostLabel.draw(upgradeBatch,1);
                    upgradeOneImprovementLabel.draw(upgradeBatch,1);
                    upgradeTwoImprovementLabel.draw(upgradeBatch,1);
                    upgradeThreeImprovementLabel.draw(upgradeBatch,1);
                    upgradeAttackInfoLabel.draw(upgradeBatch,1);
                    portraitSpr.draw(upgradeBatch);

                    upgradeBatch.end();

                }


                break;


            case PAUSE:

                pauseButton.draw(mainbatch, 1);
                abilityOne.draw(mainbatch, 1);
                abilityTwo.draw(mainbatch, 1);
                abilityThree.draw(mainbatch, 1);
                abilityFour.draw(mainbatch, 1);
                startButton.draw(mainbatch, 1);
                buildButton.draw(mainbatch, 1);


                UIText();

                mainbatch.end();

                pauseBatch.begin();

                overlay.draw(pauseBatch, 0.5f);
                buildUIBack.draw(pauseBatch, 1);
                pauseContinueButton.draw(pauseBatch, 1);
                pauseExitButton.draw(pauseBatch, 1);
                pauseSettingsButton.draw(pauseBatch, 1);
                pauseLabel.draw(pauseBatch, 1);

                pauseBatch.end();

                break;
        }


    }

    public void UIText(){
        int height = Gdx.graphics.getHeight();
        int width = Gdx.graphics.getWidth();

        LMeat.draw(mainbatch, "Meat: " + meat, width-250, 310);
        LCoins.draw(mainbatch, "Coins: " + coinsRemaining, width/2 - 85, 50);
        LWave.draw(mainbatch, "Wave: " + waveCount, 10, 380);
        LEnemies.draw(mainbatch, "Enemies: " + enemiesRemaining, 10 , 310);

        if(combatPhase) {
            Lability1CD.draw(mainbatch, (ability1CD > 0) ? "" + ability1CD : "", 125, 150);
            Lability2CD.draw(mainbatch, (ability2CD > 0) ? "" + ability2CD : "", 425, 150);
            Lability3CD.draw(mainbatch, (ability3CD > 0) ? "" + ability3CD : "", Gdx.graphics.getWidth() - 475, 150);
            Lability4CD.draw(mainbatch, (ability4CD > 0) ? "" + ability4CD : "", Gdx.graphics.getWidth() - 200, 150);
        }
    }

    @Override

    public void dispose() { }

    @Override

    public void resize(int width, int height) { }

    @Override

    public void pause() {
        // TODO:
        // add code to pause game
        this.state = State.PAUSE;
        //might be bad to have this stuff in here
        //may need to create separate methods depeding
        //on what we want to happen when the user pauses
        //the app on their phone
    }

    @Override

    public void resume() {
        // TODO:
        // add code to resume game
        this.state = State.RUNNING;
        Gdx.app.log("GameScreen: ", "resume called");
        //might be bad to have this stuff in here
        //may need to create separate methods depeding
        //on what we want to happen when the user pauses
        //the app on their phone
    }

    @Override

    public void show() {

        Gdx.app.log("GameScreen: ", "show called ");
        if(playing == false){
            //create grid -- Must happen before LoadGameState!
            CreateScreenGrid();
            LoadGameState();
            create();
        }
        else{
            game.resume();
        }


    }

    @Override

    public void hide() {

    }

    private void SetLabelValues(final Label label, Stage stage, float width, float height,float x, float y, int fontScale){
        label.setFontScale(fontScale);
        label.setWidth(width);
        label.setHeight(height);
        label.setPosition(x, y);

        if (label.equals(pauseLabel)){
            label.setAlignment(Align.center);
        }

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
                    buttonSound.play();
                    building = true;
                    toBuild = null;

                    Gdx.input.setInputProcessor(buildStage);


                    overlay.setHeight(Gdx.graphics.getHeight());
                    overlay.setWidth(Gdx.graphics.getWidth());

                } else if (button.equals(pauseButton)) {
                    buttonSound.play();

                    overlay.setHeight(Gdx.graphics.getHeight());
                    overlay.setWidth(Gdx.graphics.getWidth());
                    Gdx.input.setInputProcessor(pauseStage);
                    game.pause();

                    //game.setScreen(TowerDefense.menuScreen);
                } else if (button.equals(startButton)) {
                    if (combatPhase == false) {
                        buttonSound.play();
                        startWave.play();
                        enemyList = new ArrayList<Enemy>();
                        spawnTime = 0f;
                        waveCount++;
                    }
                    combatPhase = true;
                    //call method to remove build and start UI elements
                } else if (button.equals(pauseContinueButton)) {
                    buttonSound.play();
                    Gdx.app.log("GameScreen: ", "About to call resume");
                    game.resume();
                    resetStage();
                } else if (button.equals(pauseExitButton)) {
                    buttonSound.play();
                    Gdx.app.log("GameScreen: ", "About to Exit Level");

                    game.resume();
                    resetStage();
                    game.setScreen(TowerDefense.menuScreen);


                } else if (button.equals(pauseSettingsButton)) {
                    buttonSound.play();
                    TowerDefense.settingsScreen.sender = "game";    //so we can go back to the right screen
                    game.resume();
                    game.setScreen(TowerDefense.settingsScreen);
                } else if (button.equals(abilityOne)) {
                    // if button is pressed during combat phase and skill is off cooldown
                    if (combatPhase == true && usingFireball == false && ability1CD <= 0) {
                        dragonFireball.play();
                        ability1CD = ABILITY1_CD_TIME;
                        usingFireball = true;
                        usingMagamStrike = false; // to prevent double casting

                        for(Enemy enemy: enemyList){
                            if(enemy.getMovementPoint() == 5 ||enemy.getMovementPoint() == 6) {
                                enemy.setHealth(enemy.getHealth() - 300);
                                if(enemy.getHealth() <= 0){
                                    toRemove = enemy;
                                }
                            }
                        }
                    } else if (combatPhase == true && usingMagamStrike == false) {
                        usingFireball = false;
                    }

                } else if (button.equals(abilityTwo)) {
                    // if button is pressed during combat and is off cooldown
                    if (combatPhase == true && ability2CD <= 0) {
                        dragonRoar.play();
                        ability2CD = ABILITY2_CD_TIME;
                        for (Enemy enemy : enemyList) {
                            if (enemy.getEnemyType().equals("barbarian")) {
                                enemy.setState(2);
                                enemy.setStateTime(120); // should be 2 seconds
                            } else {
                                enemy.setState(1);
                                enemy.setStateTime(120); // should be 2 seconds
                            }
                        }
                    }
                } else if (button.equals(abilityThree)) {
                    // if button is pressed during combat and is off cooldown
                    if (combatPhase == true && ability3CD <= 0) {
                        dragonStomp.play();
                        ability3CD = ABILITY3_CD_TIME;
                        for (Enemy enemy : enemyList) {
                            if (enemy.getMovementPoint() == 0) {
                                enemy.setHealth(enemy.getHealth() - 50);
                            } else if (enemy.getMovementPoint() == 1) {
                                enemy.setHealth(enemy.getHealth() - 100);
                            } else if (enemy.getMovementPoint() == 2 || enemy.getMovementPoint() == 9) {
                                enemy.setHealth(enemy.getHealth() - 175);
                            } else if (enemy.getMovementPoint() == 3 || enemy.getMovementPoint() == 8) {
                                enemy.setHealth(enemy.getHealth() - 250);
                            } else if (enemy.getMovementPoint() == 4 || enemy.getMovementPoint() == 5 || enemy.getMovementPoint() == 6
                                    || enemy.getMovementPoint() == 7) {
                                enemy.setHealth(enemy.getHealth() - 300);
                            }
                            if(enemy.getHealth() <= 0){
                                toRemove = enemy;
                            }
                        }
                    }
                } else if (button.equals(abilityFour)) {
                    //do ability
                    if (combatPhase == true && usingMagamStrike == false && ability4CD <= 0) {
                        dragonMagmaBlast.play();
                    }
                } else if (button.equals(buildbackButton)) {
                    buttonSound.play();
                    if (building) {
                        building = false;
                        resetStage();
                    }
                } else if (button.equals(upgradebackButton)) {
                    buttonSound.play();
                    if (upgrading) {
                        upgrading = false;
                        resetStage();
                    }
                } else if (button.equals(defenseOne)) {
                    buttonSound.play();
                    if (meat >= 400) {
                        toBuild = new Defense(0);
                        Gdx.app.log("new Building", "Goblin");
                        resetStage();
                    } else {

                    }


                } else if (button.equals(defenseTwo)) {
                    buttonSound.play();
                    if (meat >= 800) {
                        toBuild = new Defense(1);
                        Gdx.app.log("new Building", "Minotaur");
                        resetStage();
                    }


                } else if (button.equals(defenseThree)) {
                    buttonSound.play();
                    if (meat >= 800) {
                        toBuild = new Defense(2);
                        Gdx.app.log("new Building", "War Drummer");
                        resetStage();
                    }


                } else if (button.equals(defenseFour)) {
                    buttonSound.play();
                    if (meat >= 1500) {
                        toBuild = new Defense(3);
                        Gdx.app.log("new Building", "Drake");

                        resetStage();
                    }

                } else if (button.equals(upgradeButtonOne)) {
                    buttonSound.play();
                    toUpgrade.setDamage(toUpgrade.getDamage() + 50);

                    resetStage();
                    upgrading = false;
                    meat -= 50 * toUpgrade.getNumUpgrades();
                    toUpgrade = null;
                } else if (button.equals(upgradeButtonTwo)) {
                    buttonSound.play();
                    toUpgrade.setRange(toUpgrade.getRange() + 50);
                    toUpgrade.setRangeRadious(new Circle(toUpgrade.getSprite().getX() + 50,
                            toUpgrade.getSprite().getY() + 50, toUpgrade.getRange() + 50));

                    upgrading = false;
                    resetStage();
                    meat -= 50 * toUpgrade.getNumUpgrades();
                    toUpgrade = null;
                } else if (button.equals(upgradeButtonThree)) {
                    buttonSound.play();
                    if (toUpgrade.getAtkSpeed() > 50) {
                        toUpgrade.setAtkSpeed((int) toUpgrade.getAtkSpeed() - 50);
                        toUpgrade.setAtkCD(toUpgrade.getAtkSpeed());

                        upgrading = false;
                        resetStage();
                        meat -= 50 * toUpgrade.getNumUpgrades();
                        toUpgrade = null;
                    }

                }


                // Gdx.app.log("MenuScreen: ", "gameScreen started");
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
        } else if (stage.equals(pauseStage)){
            pauseStage.addActor(button);
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
    public void addEnemy(Enemy newEnemy) {
        newEnemy.setId(enemiesSpawned);
        newEnemy.setHealth(newEnemy.getHealth()* healthMod);
        enemyList.add(newEnemy);
        enemiesSpawned++;
        enemiesRemaining++;
    }

}


