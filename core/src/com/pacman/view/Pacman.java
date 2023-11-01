package com.pacman.view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Pacman extends Game {
	SpriteBatch batch;
	BitmapFont font;
	Music sandstorm;
	boolean enabledSound = false;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont(Gdx.files.internal("times.fnt"));
		font.setColor(Color.BLACK);
		sandstorm = Gdx.audio.newMusic(Gdx.files.internal("sandstorm.mp3"));
		this.setScreen(new MainMenu(this));
	}

	@Override
	public void render () {
		sandstorm.setLooping(true);
		sandstorm.setVolume(0.3f);
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
	}
}
