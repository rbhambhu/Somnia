package com.usc.softandroid.managers;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import com.usc.softandroid.model.vo.Dimension;
import com.usc.softandroid.model.vo.GameAdapter;
import com.usc.softandroid.model.vo.Position;
import com.usc.softandroid.model.vo.SceneAdapter;
import com.usc.softandroid.model.vo.SceneObject;
import com.usc.softandroid.view.Character;

public class SceneManager {
	/******************** VARIABLE DECLARATIONS	*******************/
	private static SceneManager mInstance; //instance of this class
	private TextureManager texManager; //instance of the texture manager that manages the textures
	private DatabaseManager dbManager;
	private SimpleBaseGameActivity core; //instance of the base activity
	private static Boolean canWalk; //variable to restrict character movements
	private int touchX; //the area of the scene the user touched
	private IUpdateHandler splashHandler, sceneTouchHandler;
	
	private SceneAdapter mSceneData, //the scene info located in the database 
					  nextSceneInfo; //the programmatically retrieved succeeding scene informations
	
	private Scene mScene; //the scene retrieved from the base activity
	
	private Entity backgroundLayer, //the layer that will contain the scene background images
				   objectLayer; //the layer that will contain the objects, characters and buttons in the game
	
	private Sprite sDownArrow, sUpArrow,sRightArrow,sLeftArrow,
				   sMISCMusicOn, sMISCSoundOn;  //the sprite button for sound toggle on
	
	private Character sCHARStanley; //the main character of the game


	/************************PRIVATE CONSTRUCTOR********************/
	private SceneManager(SimpleBaseGameActivity core) {
		this.core = core; //the context of the base activity
		texManager = TextureManager.getInstance(core); //retrieves an instance of the texture manager
		dbManager = DatabaseManager.getInstance(core);
		backgroundLayer = new Entity(); //creates a layer to contain scene background images
		objectLayer = new Entity(); // creates a layer which has a higher z index that the backgroundlayer
		canWalk = false; //disables walking on the beginning of the game
		sceneTouchHandler = createSceneTouchHandler();
	}

	/****************RETURNS THE INSTANCE OF THIS CLASS*************/	
	public static SceneManager getInstance(SimpleBaseGameActivity core) {
		if (mInstance == null) {
			mInstance = new SceneManager(core);
		}
		return mInstance;
	}
	
	//RETURNS THE SCENE INFORMATION
	public SceneAdapter getSceneInfo(){
		return mSceneData;
	}
	
	//UPDATES THE NEXT SCENE INFORMATION
	public void updateNextSceneInfo(SceneAdapter nextSI){
		this.nextSceneInfo = nextSI;
	}
	
	//RETRIEVES THE NEXT SCENE INFORMATION
	public SceneAdapter getNextSceneInfo(){
		return nextSceneInfo;
	}
	
	//MANAGES THE BACKGROUND LAYER FOR CHANGING SCENES
	public void updateBackgroundImage(Sprite newBackgroundImage){
		if(backgroundLayer.getChildCount() > 0)
			backgroundLayer.detachChildren();
		backgroundLayer.attachChild(newBackgroundImage);
	}
	
	//RETURNS THE BACKGROUND LAYER
	public Entity getBackgroundLayer(){
		return backgroundLayer;
	}
	
	//RETURNS THE OBJECT LAYER
	public Entity getObjectLayer(){
		return objectLayer;
	}
	
	//RETURNS THE SCENE LAYER
	public Entity getSceneLayer(){
		return objectLayer;
	}
	
	public void setSplashHandler(IUpdateHandler mHandler){
		splashHandler = mHandler;
	}
	
	public IUpdateHandler createSceneTouchHandler(){
		IUpdateHandler sceneTouchHandler = new IUpdateHandler() {
			public void onUpdate(float pSecondsElapsed) {
				/* Arrow visibility manipulation */
				updateArrowVisibility();
				updateSceneObjectOutofBounds();
				manageStanleyMovements();
			}

			@Override
			public void reset() {
				mScene.unregisterUpdateHandler(this);
			}
		};
		
		return sceneTouchHandler;
	}
	
	public int getTouchX(){
		return touchX;
	}
	
	public void setTouchX(int x){
		touchX = x;
	}
	
	//change the scene background, objects and navigation buttons according to the scene info passed as its parameter
	public void changeSceneVisuals(SceneAdapter sceneData, Scene currScene) {
		mSceneData = sceneData; //retrieve the scene information stored in the database		
		mScene = currScene;

		mScene.clearEntityModifiers();
		mScene.clearTouchAreas();
		mScene.clearUpdateHandlers();
//		mScene.setOnSceneTouchListenerBindingOnActionDownEnabled(true);

		System.out.println("scene pre load contains " + mScene.getUpdateHandlerCount() + " update handlers and " + mScene.getTouchAreas() + " touch areas.");
//		mScene.unregisterUpdateHandler(splashHandler);	
//		mScene.unregisterUpdateHandler(sceneTouchHandler);
//		System.out.println("unregistered update handler in current scene id is " + mSceneData.getSCENE_ID() );
		
		//remove scene update handlers
//		if(mSceneData.getSCENE_ID() == "SCENE1")
//			mScene.unregisterUpdateHandler(splashHandler);	
//		else
//			mScene.unregisterUpdateHandler(sceneTouchHandler);	

		
		/*************** POPULATE THE SCENE **************/
		//manage the scene background image
		Sprite thisSceneBgSprite = texManager.getSpriteWithID(sceneData.getBackgroundTex()); //creates a sprite with the background texture reference retrieved from database
		thisSceneBgSprite.setSize(GameAdapter.CAMERA_WIDTH, GameAdapter.CAMERA_HEIGHT);
		backgroundLayer.detachChildren();
		backgroundLayer.attachChild(thisSceneBgSprite);
		objectLayer.detachChildren();
		
		//add interactive objects
		for(int objAddCtr=0;objAddCtr<dbManager.getSceneObjectsTotal(mSceneData.getSCENE_ID());objAddCtr++){
			SceneObject sceneObjectData = dbManager.getObjectData(mSceneData.getSCENE_ID(), objAddCtr);
			
			if(sceneObjectData.getObjectStatus().equals("OK")){
				Sprite dbObject = texManager.getSpriteWithID(sceneObjectData.getObjectName());
				Position objPos = sceneObjectData.getObjectPosition();
				Dimension objDim = sceneObjectData.getObjectDimensions();
				
				dbObject.setPosition(objPos.getX(), objPos.getY());
				dbObject.setHeight(objDim.getHeight());
				dbObject.setWidth(objDim.getWidth());
				
				objectLayer.attachChild(dbObject);
				mScene.registerTouchArea(dbObject);
			}
		}
		
		//retrieves an instance of stanley and attach it on the object layer
		sCHARStanley = texManager.getStanley();
		sCHARStanley.setPosition(mSceneData.getStanStartPosX(), GameAdapter.Stan_PosY); //set the initial position of stanley	
		objectLayer.attachChild(sCHARStanley);

		//lock the walking capability of the character as the scene starts
		
		//retrieve instances of scene buttons
		sMISCMusicOn = texManager.getMusicOn();
		sMISCMusicOn.setPosition(GameAdapter.CAMERA_WIDTH - (GameAdapter.chargap * 2), 0);
		sMISCMusicOn.setSize(50,50);
		objectLayer.attachChild(sMISCMusicOn);
		sMISCSoundOn = texManager.getSoundOn();
		sMISCSoundOn.setPosition(GameAdapter.CAMERA_WIDTH - (GameAdapter.chargap * 2) + 50, 0);
		sMISCSoundOn.setSize(50,50);
		objectLayer.attachChild(sMISCSoundOn);
		
		//manage scene arrows
		if(!mSceneData.getUpScene().equals("NA")){
			sUpArrow = texManager.getUpArrow();
			sUpArrow.setPosition(mSceneData.getUpArrowX(), mSceneData.getUpArrowY());
			sUpArrow.setVisible(false);
			objectLayer.attachChild(sUpArrow);
		}

		if(!mSceneData.getDownScene().equals("NA")){
			sDownArrow = texManager.getDownArrow();
			sDownArrow.setPosition(mSceneData.getDownArrowX(), mSceneData.getDownArrowY());
			sDownArrow.setVisible(false);
			objectLayer.attachChild(sDownArrow);
		}
		
		if(!mSceneData.getRightScene().equals("NA")){		
			sRightArrow = texManager.getRightArrow();
			sRightArrow.setPosition(mSceneData.getRightArrowX(), mSceneData.getRightArrowY());
			sRightArrow.setVisible(false);
			objectLayer.attachChild(sRightArrow);
		}
		
		if(!mSceneData.getLeftScene().equals("NA")){
			sLeftArrow = texManager.getLeftArrow();
			sLeftArrow.setPosition(mSceneData.getLeftArrowX(), mSceneData.getLeftArrowY());
			sLeftArrow.setVisible(false);
			objectLayer.attachChild(sLeftArrow);
		}

		mScene.registerUpdateHandler(sceneTouchHandler);
		addSceneTouchListeners();
		System.out.println("registered update handler in current scene id is " + mSceneData.getSCENE_ID() );
		

		//register touch listeners for the navigational arrows
		mScene.registerTouchArea(sDownArrow);
		mScene.registerTouchArea(sUpArrow);
		mScene.registerTouchArea(sLeftArrow);
		mScene.registerTouchArea(sRightArrow);

		canWalk = false;
		sCHARStanley.stopAnimation();
		
		System.out.println("scene pre load contains " + mScene.getUpdateHandlerCount() + " update handlers and " + mScene.getTouchAreas().size() + " touch areas.");
	}

	protected Scene getUsedScene(){
		return mScene;
	}
	
	protected SceneAdapter getUsedSceneData(){
		return mSceneData;
	}

	public void addSceneTouchListeners(){
		mScene.setOnSceneTouchListener(new IOnSceneTouchListener() {
			@Override
			public boolean onSceneTouchEvent(Scene scene, TouchEvent event) {
				//action are touches (down or up)
				int action = event.getAction();
				
//				sCHARStanley.setEventX((float) action);
				touchX = (int) (event.getX() - (sCHARStanley.getWidth() / 2)); 
				int indices[] = { 1, 2, 3, 4 };
				long framerate[] = { 250, 250, 250, 250 };

				switch (action) {
				case TouchEvent.ACTION_DOWN:
					if (touchX > sCHARStanley.getX()) {
						canWalk = false;
						sCHARStanley.faceForward();
						sCHARStanley.setFlippedHorizontal(false);
					}

					else if (touchX < sCHARStanley.getX()) {
						canWalk = false;
						sCHARStanley.faceBackward();
						sCHARStanley.setFlippedHorizontal(true); 
					}

					break;

				case TouchEvent.ACTION_UP:
					canWalk = true;
					sCHARStanley.animate(framerate, indices);
					break;
				}
				
//				if(event.equals(sMISCMusicOn))
//				{
//					System.out.println("Music should be off");
//				}
				
				return true;
			}
		}); 

	}
	
	public void removeSceneTouchListeners(){
		mScene.setOnSceneTouchListener(new IOnSceneTouchListener() {

			@Override
			public boolean onSceneTouchEvent(Scene pScene,
					TouchEvent pSceneTouchEvent) {
				// TODO Auto-generated method stub
				return false;
			}
			
		});		
	}
	
	public void manageStanleyMovements(){
		
//		System.out.println("Stanley X = " + sCHARStanley.getX() + " dest X = " + touchX);

		
		int destX = (touchX / GameAdapter.walking_interval)
				* GameAdapter.walking_interval;
		
		if (canWalk) {
//			System.out.println("stan CAN move!");
			
			if (sCHARStanley.getX() < destX){
				sCHARStanley.setX(sCHARStanley.getX() + GameAdapter.walking_interval);
			} else if (sCHARStanley.getX() > destX){
				sCHARStanley.setX(sCHARStanley.getX() - GameAdapter.walking_interval);
			} else{
				sCHARStanley.stopAnimation(0);
			}
		} else {
//			System.out.println("Stan CANNOT move!");
			sCHARStanley.stopAnimation(0);
		}

	}
	
	public void updateSceneObjectOutofBounds(){
		if ((int) sCHARStanley.getX() >= GameAdapter.CAMERA_WIDTH) {
			sCHARStanley.stopAnimation(0);
			sCHARStanley.setX(GameAdapter.CAMERA_WIDTH
					- sCHARStanley.getWidth());
		} else if ((int) sCHARStanley.getX() <= 0) {
			sCHARStanley.stopAnimation(0);
			sCHARStanley.setX(0);
		}
	}
	
	public void updateArrowVisibility(){
		if (sCHARStanley.collidesWith(sDownArrow)) {
			if(!mSceneData.getDownScene().equals("NA"))
				sDownArrow.setVisible(true);
		} else
			sDownArrow.setVisible(false);

		if (sCHARStanley.collidesWith(sUpArrow)) {
			if(!mSceneData.getUpScene().equals("NA"))
				sUpArrow.setVisible(true);
		} else
			sUpArrow.setVisible(false);

		if (sCHARStanley.collidesWith(sLeftArrow)) {
			if(!mSceneData.getLeftScene().equals("NA"))
				sLeftArrow.setVisible(true);
		} else
			sLeftArrow.setVisible(false);

		if (sCHARStanley.collidesWith(sRightArrow)) {
			if(!mSceneData.getRightScene().equals("NA"))
				sRightArrow.setVisible(true);
		} else
			sRightArrow.setVisible(false);
	}
	
	public void setScene(Scene scene) {
		core.getEngine().setScene(scene);
	}

	public void unload(Scene scene) {
		scene.detachChildren();
	}

}
