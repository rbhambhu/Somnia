package com.usc.softandroid.somniagame;

import java.io.IOException;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.database.SQLException;

import com.usc.softandroid.managers.DatabaseManager;
//import com.usc.softandroid.managers.MusicManager;
import com.usc.softandroid.managers.SceneManager;
import com.usc.softandroid.managers.TextureManager;
import com.usc.softandroid.model.vo.GameAdapter;
import com.usc.softandroid.model.vo.SceneAdapter;

// This is where the game flow programming starts
public class GameMainActivity extends SimpleBaseGameActivity {
	
	/*points to all manager addresses to retrieve links of manager 
	 * by the time it is built to be referenced throughout the game*/
	private TextureManager mTextureManager;
	private SceneManager mSceneManager;
	private DatabaseManager mDbManager;
	private GameMainActivity thisContext;
	private IUpdateHandler splashSceneHandler;
//	private MusicManager mMusicManager;
	
	
	private Camera camera; //cameras will define the parts of the scene to be seen
	private Scene curScene; //stores the link of the current scene
	private SceneAdapter curSceneData;
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		camera = new Camera(0, 0, GameAdapter.CAMERA_WIDTH, GameAdapter.CAMERA_HEIGHT); //the camera will show all the available visualizations
		EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(GameAdapter.CAMERA_WIDTH, GameAdapter.CAMERA_HEIGHT), camera);
//		engineOptions.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);
		engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
		return engineOptions;
	}

	
	@Override
	protected void onCreateResources() {
		mTextureManager = TextureManager.getInstance(this); // creates a text manager		
		mSceneManager = SceneManager.getInstance(this); //creates a scene manager
		mDbManager = DatabaseManager.getInstance(this); //creates a database manager
//		mMusicManager = MusicManager.getInstance(this); //creates a music manager

    	try {
			mDbManager.createDataBase();
		} catch (IOException ioe) {
			throw new Error("Unable to create database");
		}


		try {
			mDbManager.openDataBase();
		} catch (SQLException sqle) {
			throw sqle;
		}
		
//		mMusicManager.buildMusicAssets(this);
//		mMusicManager.buildSoundAssets(this);
	}
		
	@Override
	protected Scene onCreateScene() {
		thisContext = this; //stores the context of this activity to be referenced to other classes
		curScene =  new Scene();
		curSceneData = new SceneAdapter();
		curSceneData.setCurrentScene(curScene);
			
    	curScene.detachChildren(); //makes sure that there are no entities left attached before the splash scene
    	curScene.attachChild(mSceneManager.getBackgroundLayer()); //attach the layer for scene background visuals
    	curScene.attachChild(mSceneManager.getObjectLayer()); //attach the layer that will contain the dynamic objects within the game

    	mSceneManager.getBackgroundLayer().attachChild(mTextureManager.getSplash()); //displays the splash screen on the camera

    	//stores the splash screen handler for reference so that it can be removed after showing the splash screen while loading resources
    	splashSceneHandler = new IUpdateHandler() {
			public void onUpdate(float pSecondsElapsed) {
				//checks if all the needed resources are loaded before showing the first scene
				if(mTextureManager.buildGfxAssets(thisContext))
		    		mSceneManager.changeSceneVisuals(mDbManager.getSceneDataWithID("SCENE1"),curScene);
			}
			@Override
			public void reset() {
				curScene.unregisterUpdateHandler(this);
			}
		};
    	
		//add threads to the splash scene to execute showing splash scene while loading the resources
		mSceneManager.setSplashHandler(splashSceneHandler);
		curScene.registerUpdateHandler(splashSceneHandler);
    	
		return curScene;			
	}	
	
}

