package com.unisa.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.files.FileHandle;




public class TowerDefense extends Game implements ApplicationListener {

	SpriteBatch batch;
	Texture img;
	public static MenuScreen menuScreen;
	public static GameScreen gameScreen;
	public static SettingsScreen settingsScreen;
	public static Preferences prefs;
	public static boolean continuing = false;
	private Music backgroundMusic;

	//Game Stats
	int coinsRemaining;
	int waveCount;
	int meat;
	boolean sfx;
	boolean music;

	@Override
	public void create () {
		this.prefs = Gdx.app.getPreferences("My Preferences");
		Gdx.app.log("MyGame:", " Create");
		//Load();

		gameScreen = new GameScreen(this);
		menuScreen = new MenuScreen(this);
		settingsScreen = new SettingsScreen(this);
		Gdx.app.log("MyGame:", "about to change screen to menuscreen");
		// change screen to the menu
		setScreen(menuScreen);
		Gdx.app.log("MyGame", "changed to menuscreen");
		Save.load();
		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Vanguard&#039;s Assault.mp3"));
		backgroundMusic.setLooping(true);
		backgroundMusic.setVolume(50);
		backgroundMusic.play();

	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	public void SetScreen(Screen newScreen){
		super.setScreen(newScreen);
	}

	@Override
	public void render () {
		super.render();
//		Gdx.gl.glClearColor(1, 0, 0, 1);
//		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//		batch.begin();
//		batch.draw(img, 0, 0);
//		batch.end();
	}

	public void ResetPrefs(){
		Gdx.app.log("In Tower Defense", "--- Reset Prefs");
		this.prefs.putInteger("coinsValue", 20);
		this.prefs.putInteger("waveValue", 0);
		this.prefs.putInteger("meatValue", 5000);
	}

	void Load(){





//		int coinsRemaining;
//		int waveCount;
//		int meat;
//		boolean sfx;
//		boolean music;
//
//		boolean fileExists = Gdx.files.internal("SavedGame.txt").exists();
//
//		if (fileExists){
//
//			try {
//
//				FileHandle dataFile = Gdx.files.local("SavedGame.txt");
//
//
//			} catch (Exception e) {
//
//			}
//
//		}

//		if (File.Exists(Application.persistentDataPath + "/SavedData.dat"))
//		{
//			BinaryFormatter bf = new BinaryFormatter();
//			FileStream fs = File.Open(Application.persistentDataPath + "/SavedData.dat", FileMode.Open);
//			SavedData data = (SavedData)bf.Deserialize(fs);
//			fs.Close();
//
//			this.initialSetup = data.intialSetup;
//
//			this.notifCount = data.notifCount;
//			NotificationController.notifControl.notificationInterval = data.notificationInterval;
//
//			NotificationController.notifControl.utcSavedTime = data.utcSavedTime;
//
//			NotificationController.notifControl.pendingGiftNotification = data.pendingGiftNotification;
//
//			MissionController.missionControl.collectableMissionGift = data.collectableMissionGift;
//
//			NotificationController.notifControl.collectableNotifGift = data.collectableNotifGift;
//			this.bestScore = data.bestScore;
//			this.currentCharacter = data.currentCharacter;
//
//			this.rated = data.rated;
//			MissionController.missionControl.currentMission = data.currentMission;
//
//
//			StatTracker.track.stats = data.stats;
//		}
//		else
//		{
//			//if no tutorial completed virtual item
//			//set notifcount to 9
//			initialSetup = true;
//			currentCharacter = 1;
//			bestScore = 0;
//
//			MissionController.missionControl.GenerateMission();
//		}
//		DEBUGPANELBACKEND.localLoaded = true;
//		Debug.Log("LOAD RUN");
//		Debug.Log("inital setup");
	}

	void Save(){


		FileHandle dataFile = Gdx.files.local("SavedGame.txt");

		byte[] bytes = dataFile.readBytes();

	}

//		BinaryFormatter bf = new BinaryFormatter();
//		FileStream fs = File.Create(Application.persistentDataPath + "/SavedData.dat");
//		SavedData data = new SavedData();
//
//		data.intialSetup = this.initialSetup;
//
//		data.notifCount = this.notifCount;
//		data.notificationInterval = NotificationController.notifControl.notificationInterval;
//
//		data.utcSavedTime = NotificationController.notifControl.utcSavedTime;
//
//		data.pendingGiftNotification = NotificationController.notifControl.pendingGiftNotification;
//
//		data.collectableMissionGift = MissionController.missionControl.collectableMissionGift;
//
//		data.collectableNotifGift = NotificationController.notifControl.collectableNotifGift;
//		data.bestScore = this.bestScore;
//		data.currentCharacter = this.currentCharacter;
//
//		data.currentMission = MissionController.missionControl.currentMission;
//		data.rated = this.rated;
//
//		data.stats = StatTracker.track.stats;
//
//		bf.Serialize(fs, data);
//		fs.Close();

//	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}

	@Override
	public void dispose() {
		super.dispose();
	}
}

//class SavedGame implements Serializable {
//
//	public int coinsRemaining;
//	public int waveCount;
//	public int meat;
//	public boolean sfx;
//	public boolean music;
//
//}