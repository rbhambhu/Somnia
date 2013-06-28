package com.usc.softandroid.managers;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.debug.Debug;


public class MusicManager {

	private static MusicManager mInstance;
	private SimpleBaseGameActivity core;
	private Sound mSound1;
	private Music mMusic1;
	
	private MusicManager(SimpleBaseGameActivity core) {
		super();
		this.core = core;
	}
	
	public static MusicManager getInstance(SimpleBaseGameActivity core) {
		if (mInstance == null) {
			mInstance = new MusicManager(core);
		}
		return mInstance;
	}
	
	public void buildMusicAssets(SimpleBaseGameActivity con) {
		MusicFactory.setAssetBasePath("music/");
		
		try {
			this.mMusic1 = MusicFactory.createMusicFromAsset(core.getMusicManager(), core, "fivee.mp3");
			this.mMusic1.setLooping(true);
		} catch (final IOException e) {
			Debug.e(e);
		}
	}
	
	public void buildSoundAssets(SimpleBaseGameActivity con) {
		SoundFactory.setAssetBasePath("sound/");
		
		try {
			mSound1 = SoundFactory.createSoundFromAsset(core.getSoundManager(), core, "fivee.mp3");
		} catch (IOException e) {
			Debug.e(e);
		}
	}
	
		
	protected Music getMusic1(){
		return mMusic1;
	}
	
	protected Sound getSound1(){
		return mSound1;
	}
	
}
