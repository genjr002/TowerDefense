package com.unisa.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TowerDefense extends Game implements ApplicationListener {

	SpriteBatch batch;
	Texture img;
	public static MenuScreen menuScreen;
	public static GameScreen gameScreen;
	public static SettingsScreen settingsScreen;

	@Override
	public void create () {
		Gdx.app.log("MyGame:"," Create");
		gameScreen = new GameScreen(this);
		menuScreen = new MenuScreen(this);
		settingsScreen = new SettingsScreen(this);
		Gdx.app.log("MyGame:", "about to change screen to menuscreen");
		// change screen to the menu
		setScreen(menuScreen);
		Gdx.app.log("MyGame","changed to menuscreen");
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
