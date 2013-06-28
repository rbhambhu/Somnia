package com.usc.softandroid.managers;

import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.Texture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import com.usc.softandroid.model.vo.GameAdapter;
import com.usc.softandroid.model.vo.SpriteList;
import com.usc.softandroid.somniagame.GameMainActivity;
import com.usc.softandroid.view.Character;

/*
 * TextureManager manages the textures used in the game.
 * texture conversion from texture to sprites,etc.
 * adding functionalities to sprites
 * 
 * Here is where the only reservoir of objects where the game objects are referred from different classes
 */

public class TextureManager {

	private static TextureManager mInstance;
	private SimpleBaseGameActivity core;
	private SceneManager mSceneManager;
	private DatabaseManager mDBManager;
	
	private BitmapTextureAtlas 
		bCHARStanley,  
		bMISCArrowUp, bMISCArrowDown, bMISCArrowLeft, bMISCArrowRight, 
		bMISCMusicOff, bMISCMusicOn, bMISCSoundOff, bMISCSoundOn,
		bSplashScene,			
		bSCENE1BG, 
			bOBJKey, bOBJRag,		   		
		bSCENE2BG, 
//			bOBJPlankUnused,
		bSCENE3BG,
		bSCENE4BG,
		bSCENE5BG,
		bSCENE6BG,
		bSCENE7BG,
		bSCENE8BG,
		bSCENE9BG,
		bSCENE9ABG,
		bSCENE9BBG,
		bSCENE10BG,
		bSCENE11BG,
		bSCENE11DBG,
		bSCENE11D_L1BG,
		bSCENE11D_L2BG,
		bSCENE11D_L3BG,
		bSCENE11D_R1BG,
		bSCENE11D_R1ABG,
		bSCENE11D_R1BBG,
		bSCENE11D_R2BG,
		bSCENE11D_R2ABG,
		bSCENE11D_R2BBG,
		bSCENE11D_R2CBG,
		bSCENE12BG,
		bSCENE13BG;

	private TextureRegion 
		MISCArrowUp, MISCArrowDown, MISCArrowLeft, MISCArrowRight, 
		MISCMusicOff, MISCMusicOn, MISCSoundOff, MISCSoundOn,
		SCENE1BG, 
			OBJRag, 
			OBJKey,
		SCENE2BG,
//			OBJPlankUnused,
		SCENE3BG,
		SCENE4BG,
		SCENE5BG,
		SCENE6BG,
		SCENE7BG,
		SCENE8BG,
		SCENE9BG,
		SCENE9ABG,
		SCENE9BBG,
		SCENE10BG,
		SCENE11BG,
		SCENE11DBG,
		SCENE11D_L1BG,
		SCENE11D_L2BG,
		SCENE11D_L3BG,
		SCENE11D_R1BG,
		SCENE11D_R1ABG,
		SCENE11D_R1BBG,
		SCENE11D_R2BG,
		SCENE11D_R2ABG,
		SCENE11D_R2BBG,
		SCENE11D_R2CBG,
		SCENE12BG,
		SCENE13BG;

	private TiledTextureRegion CHARStanley;

	private Sprite
		sMISCArrowDown, sMISCArrowUp, sMISCArrowRight, sMISCArrowLeft,
		sMISCMusicOff, sMISCMusicOn, sMISCSoundOff, sMISCSoundOn,
		sSplashScene,
		sSCENE1BG, 
			sOBJKey, sOBJRag, 
		sSCENE2BG, 
//			sOBJPlankUnused,
		sSCENE3BG, 
		sSCENE4BG, 
		sSCENE5BG, 
		sSCENE6BG,
		sSCENE7BG, 
		sSCENE8BG, 
		sSCENE9BG, 
		sSCENE9ABG,
		sSCENE9BBG,
		sSCENE10BG, 
		sSCENE11BG, 
		sSCENE11DBG, 
		sSCENE11D_L1BG, 
		sSCENE11D_L2BG, 
		sSCENE11D_L3BG,
		sSCENE11D_R1BG, 
		sSCENE11D_R1ABG, 
		sSCENE11D_R1BBG, 
		sSCENE11D_R2BG, 
		sSCENE11D_R2ABG,
		sSCENE11D_R2BBG, 
		sSCENE11D_R2CBG, 
		sSCENE12BG, 
		sSCENE13BG;
	
	private Character sCHARStanley;

	private TextureManager(SimpleBaseGameActivity core) {
		super();
		this.core = core;
	}

	public static TextureManager getInstance(SimpleBaseGameActivity core) {
		if (mInstance == null) {
			mInstance = new TextureManager(core);
		}
		return mInstance;
	}

	// Separate loading function for loading the splash scene
	public Sprite getSplash(){
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("images/splash/"); // sets the path to get the assets
		bSplashScene = new BitmapTextureAtlas(core.getTextureManager(), 1024, 768, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		loadTexture(bSplashScene);
		sSplashScene = new Sprite(0, 0, BitmapTextureAtlasTextureRegionFactory.createFromAsset(bSplashScene, core, "loading_bg.png", 0, 0),core.getVertexBufferObjectManager());
		sSplashScene.setWidth(GameAdapter.CAMERA_WIDTH);
		sSplashScene.setHeight(GameAdapter.CAMERA_HEIGHT);
		
		return sSplashScene;
	}
	
	//loads all the scenes in the game
	public boolean buildGfxAssets(final GameMainActivity core) {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("images/maingame/"); // sets the path to get the assets
		mSceneManager = SceneManager.getInstance(core); //retrieve the scene instance		
		mDBManager = DatabaseManager.getInstance(core); //retrieve the scene instance	

		/********************************************************* SCENES ***************************************************************/
		bSCENE1BG = new BitmapTextureAtlas(core.getTextureManager(), 1024, 768, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		SCENE1BG = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bSCENE1BG, core, "Scene1_Attic.png", 0, 0);
		loadTexture(bSCENE1BG);
		sSCENE1BG = new Sprite(0, 0, SCENE1BG,core.getVertexBufferObjectManager());
		
		bSCENE2BG = new BitmapTextureAtlas(core.getTextureManager(), 1024, 768, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		SCENE2BG = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bSCENE2BG, core, "Scene2_BelowAttic.png", 0, 0);
		loadTexture(bSCENE2BG);
		sSCENE2BG = new Sprite(0, 0, SCENE2BG, core.getVertexBufferObjectManager());

		bSCENE3BG = new BitmapTextureAtlas(core.getTextureManager(), 1024, 768, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		SCENE3BG = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bSCENE3BG, core, "Scene3_Hallway.png", 0, 0);
		loadTexture(bSCENE3BG);
		sSCENE3BG = new Sprite(0, 0, SCENE3BG, core.getVertexBufferObjectManager());

		bSCENE4BG = new BitmapTextureAtlas(core.getTextureManager(), 1024, 768, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		SCENE4BG = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bSCENE4BG, core, "Scene4_BrokenHallway.png", 0, 0);
		loadTexture(bSCENE4BG);
		sSCENE4BG = new Sprite(0, 0, SCENE4BG, core.getVertexBufferObjectManager());

		bSCENE5BG = new BitmapTextureAtlas(core.getTextureManager(), 1024, 768, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		SCENE5BG = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bSCENE5BG, core, "Scene5_Stairs.png", 0, 0);
		loadTexture(bSCENE5BG);
		sSCENE5BG = new Sprite(0, 0, SCENE5BG, core.getVertexBufferObjectManager());

		bSCENE6BG = new BitmapTextureAtlas(core.getTextureManager(), 1024, 768, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		SCENE6BG = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bSCENE6BG, core, "Scene6_DoorNearStairs.png", 0, 0);
		loadTexture(bSCENE6BG);
		sSCENE6BG = new Sprite(0, 0, SCENE6BG, core.getVertexBufferObjectManager());

		bSCENE7BG = new BitmapTextureAtlas(core.getTextureManager(), 1024, 768, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		SCENE7BG = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bSCENE7BG, core, "Scene7_CandlesInBlue.png", 0, 0);
		loadTexture(bSCENE7BG);
		sSCENE7BG = new Sprite(0, 0, SCENE7BG, core.getVertexBufferObjectManager());

		bSCENE8BG = new BitmapTextureAtlas(core.getTextureManager(), 1024, 768, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		SCENE8BG = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bSCENE8BG, core, "Scene8_2CandlesInBlue.png", 0, 0);
		loadTexture(bSCENE8BG);
		sSCENE8BG = new Sprite(0, 0, SCENE8BG, core.getVertexBufferObjectManager());

		bSCENE9BG = new BitmapTextureAtlas(core.getTextureManager(), 1024, 768, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		SCENE9BG = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bSCENE9BG, core, "Scene9_2DoorsInBlue.png", 0, 0);
		loadTexture(bSCENE9BG);
		sSCENE9BG = new Sprite(0, 0, SCENE9BG, core.getVertexBufferObjectManager());

		bSCENE9ABG = new BitmapTextureAtlas(core.getTextureManager(), 1024, 768, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		SCENE9ABG = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bSCENE9ABG, core, "Scene9_Scene1.png", 0, 0);
		loadTexture(bSCENE9ABG);
		sSCENE9ABG = new Sprite(0, 0, SCENE9ABG, core.getVertexBufferObjectManager());

		bSCENE9BBG = new BitmapTextureAtlas(core.getTextureManager(), 1024, 768, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		SCENE9BBG = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bSCENE9BBG, core, "Scene9_Scene2.png", 0, 0);
		loadTexture(bSCENE9BBG);
		sSCENE9BBG = new Sprite(0, 0, SCENE9BBG, core.getVertexBufferObjectManager());		

		bSCENE10BG = new BitmapTextureAtlas(core.getTextureManager(), 1024, 768, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		SCENE10BG = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bSCENE10BG, core, "Scene10.png", 0, 0);
		loadTexture(bSCENE10BG);
		sSCENE10BG = new Sprite(0, 0, SCENE10BG, core.getVertexBufferObjectManager());				

		bSCENE11BG = new BitmapTextureAtlas(core.getTextureManager(), 1024, 768, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		SCENE11BG = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bSCENE11BG, core, "Scene11.png", 0, 0);
		loadTexture(bSCENE11BG);
		sSCENE11BG = new Sprite(0, 0, SCENE11BG, core.getVertexBufferObjectManager());				

		bSCENE11DBG = new BitmapTextureAtlas(core.getTextureManager(), 1024, 768, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		SCENE11DBG = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bSCENE11DBG, core, "Scene11_SceneDown.png", 0, 0);
		loadTexture(bSCENE11DBG);
		sSCENE11DBG = new Sprite(0, 0, SCENE11DBG, core.getVertexBufferObjectManager());				

		bSCENE11D_L1BG = new BitmapTextureAtlas(core.getTextureManager(), 1024, 768, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		SCENE11D_L1BG = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bSCENE11D_L1BG, core, "Scene11_SceneLeft1.png", 0, 0);
		loadTexture(bSCENE11D_L1BG);
		sSCENE11D_L1BG = new Sprite(0, 0, SCENE11D_L1BG, core.getVertexBufferObjectManager());				

		bSCENE11D_L2BG = new BitmapTextureAtlas(core.getTextureManager(), 1024, 768, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		SCENE11D_L2BG = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bSCENE11D_L2BG, core, "Scene11_SceneLeft2.png", 0, 0);
		loadTexture(bSCENE11D_L2BG);
		sSCENE11D_L2BG = new Sprite(0, 0, SCENE11D_L2BG, core.getVertexBufferObjectManager());				

		bSCENE11D_L3BG = new BitmapTextureAtlas(core.getTextureManager(), 1024, 768, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		SCENE11D_L3BG = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bSCENE11D_L3BG, core, "Scene11_SceneLeft3.png", 0, 0);
		loadTexture(bSCENE11D_L3BG);
		sSCENE11D_L3BG = new Sprite(0, 0, SCENE11D_L3BG, core.getVertexBufferObjectManager());				

		bSCENE11D_R1BG = new BitmapTextureAtlas(core.getTextureManager(), 1024, 768, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		SCENE11D_R1BG = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bSCENE11D_R1BG, core, "Scene11_SceneRight1.png", 0, 0);
		loadTexture(bSCENE11D_R1BG);
		sSCENE11D_R1BG = new Sprite(0, 0, SCENE11D_R1BG, core.getVertexBufferObjectManager());				

		bSCENE11D_R1ABG = new BitmapTextureAtlas(core.getTextureManager(), 1024, 768, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		SCENE11D_R1ABG = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bSCENE11D_R1ABG, core, "Scene11_SceneRight1A.png", 0, 0);
		loadTexture(bSCENE11D_R1ABG);
		sSCENE11D_R1ABG = new Sprite(0, 0, SCENE11D_R1ABG, core.getVertexBufferObjectManager());				

		bSCENE11D_R1BBG = new BitmapTextureAtlas(core.getTextureManager(), 1024, 768, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		SCENE11D_R1BBG = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bSCENE11D_R1BBG, core, "Scene11_SceneRight1B.png", 0, 0);
		loadTexture(bSCENE11D_R1BBG);
		sSCENE11D_R1BBG = new Sprite(0, 0, SCENE11D_R1BBG, core.getVertexBufferObjectManager());				

		bSCENE11D_R2BG = new BitmapTextureAtlas(core.getTextureManager(), 1024, 768, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		SCENE11D_R2BG = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bSCENE11D_R2BG, core, "Scene11_SceneRight2.png", 0, 0);
		loadTexture(bSCENE11D_R2BG);
		sSCENE11D_R2BG = new Sprite(0, 0, SCENE11D_R2BG, core.getVertexBufferObjectManager());				

		bSCENE11D_R2ABG = new BitmapTextureAtlas(core.getTextureManager(), 1024, 768, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		SCENE11D_R2ABG = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bSCENE11D_R2ABG, core, "Scene11_SceneRight2A.png", 0, 0);
		loadTexture(bSCENE11D_R2ABG);
		sSCENE11D_R2ABG = new Sprite(0, 0, SCENE11D_R2ABG, core.getVertexBufferObjectManager());				

		bSCENE11D_R2BBG = new BitmapTextureAtlas(core.getTextureManager(), 1024, 768, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		SCENE11D_R2BBG = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bSCENE11D_R2BBG, core, "Scene11_SceneRight2B.png", 0, 0);
		loadTexture(bSCENE11D_R2BBG);
		sSCENE11D_R2BBG = new Sprite(0, 0, SCENE11D_R2BBG, core.getVertexBufferObjectManager());				

		bSCENE11D_R2CBG = new BitmapTextureAtlas(core.getTextureManager(), 1024, 768, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		SCENE11D_R2CBG = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bSCENE11D_R2CBG, core, "Scene11_SceneRight2C.png", 0, 0);
		loadTexture(bSCENE11D_R2CBG);
		sSCENE11D_R2CBG = new Sprite(0, 0, SCENE11D_R2CBG, core.getVertexBufferObjectManager());				

		bSCENE12BG = new BitmapTextureAtlas(core.getTextureManager(), 1024, 768, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		SCENE12BG = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bSCENE12BG, core, "Scene12.png", 0, 0);
		loadTexture(bSCENE12BG);
		sSCENE12BG = new Sprite(0, 0, SCENE12BG, core.getVertexBufferObjectManager());				

		bSCENE13BG = new BitmapTextureAtlas(core.getTextureManager(), 1024, 768, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		SCENE13BG = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bSCENE13BG, core, "Scene13.png", 0, 0);
		loadTexture(bSCENE13BG);
		sSCENE13BG = new Sprite(0, 0, SCENE13BG, core.getVertexBufferObjectManager());				
		
		/******************************************************** CHARACTERS ***************************************************************/
		bCHARStanley = new BitmapTextureAtlas(null, 300, 165,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		CHARStanley = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(bCHARStanley, core,"Anim_Stanley_Walk.png", 0, 0, 5, 1);
		loadTexture(bCHARStanley);
		sCHARStanley = new Character(0, 0, CHARStanley,core.getVertexBufferObjectManager());

		/********************************************************* ARROWS ***************************************************************/
		//DOWN ARROW
		bMISCArrowDown = new BitmapTextureAtlas(null, 39, 50, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		MISCArrowDown = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bMISCArrowDown, core, "Arrow_Down.png", 0, 0);
		loadTexture(bMISCArrowDown);
		sMISCArrowDown = new Sprite(0, 0, MISCArrowDown,core.getVertexBufferObjectManager()){
		    @Override
		    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X, float Y) {
		        if (pSceneTouchEvent.isActionDown()){
		        	core.runOnUpdateThread(new Runnable(){ 
		            	@Override
		            	public void run(){ //sets the next scene
		            		if(sCHARStanley.collidesWith(sMISCArrowDown)){
		            			System.out.println("destX before is " + mSceneManager.getTouchX());
			            		mSceneManager.setTouchX(GameAdapter.upArrowPosX);
		            			System.out.println("destX now is " + mSceneManager.getTouchX());
			            		mSceneManager.changeSceneVisuals(mDBManager.getSceneDataWithID(mSceneManager.getUsedSceneData().getDownScene()),mSceneManager.getUsedScene());
			            		sCHARStanley.setPosition(GameAdapter.upArrowPosX, GameAdapter.Stan_PosY);
		            		}
		            	}		            	
		            });		            
		        }
		        return true;
		    };
		};
		
		bMISCArrowUp = new BitmapTextureAtlas(null, 39, 50, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		MISCArrowUp = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bMISCArrowUp, core, "Arrow_Up.png", 0, 0); 
		loadTexture(bMISCArrowUp);
		sMISCArrowUp = new Sprite(0, 0, MISCArrowUp,core.getVertexBufferObjectManager()){
		    @Override
		    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X, float Y) {
		        if (pSceneTouchEvent.isActionDown()){
		        	core.runOnUpdateThread(new Runnable(){ 
		            	@Override
		            	public void run(){ //sets the next scene	
		            	//	sCHARStanley.setPosition(mSceneInfo.getDownArrowX(), GameAdapter.Stan_PosY);
		            		if(sCHARStanley.collidesWith(sMISCArrowUp)){
		            			System.out.println("destX before is " + mSceneManager.getTouchX());
			            		mSceneManager.setTouchX(GameAdapter.downArrowPosX);
		            			System.out.println("destX now is " + mSceneManager.getTouchX());
			            		mSceneManager.changeSceneVisuals(mDBManager.getSceneDataWithID(mSceneManager.getUsedSceneData().getUpScene()),mSceneManager.getUsedScene());
			            		sCHARStanley.setPosition(GameAdapter.downArrowPosX, GameAdapter.Stan_PosY);
		            		}
		            	}		            	
		            });		            
		        }
		        return true;
		    };
		};
		
		bMISCArrowLeft = new BitmapTextureAtlas(null, 50, 39, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		MISCArrowLeft = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bMISCArrowLeft, core, "Arrow_Left.png", 0, 0); // Down Arrow Bitmaps
		loadTexture(bMISCArrowLeft);
		sMISCArrowLeft = new Sprite(0, 0, MISCArrowLeft, core.getVertexBufferObjectManager()){
		    @Override
		    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X, float Y) {
		        if (pSceneTouchEvent.isActionDown()){
		        	core.runOnUpdateThread(new Runnable(){ 
		            	@Override
		            	public void run(){ //sets the next scene		
		            		if(sCHARStanley.collidesWith(sMISCArrowLeft)){
			            		mSceneManager.setTouchX(GameAdapter.rightArrowPosX);
			            		mSceneManager.changeSceneVisuals(mDBManager.getSceneDataWithID(mSceneManager.getUsedSceneData().getLeftScene()),mSceneManager.getUsedScene());
			            		sCHARStanley.setPosition(GameAdapter.rightArrowPosX, GameAdapter.Stan_PosY);
		            		}		            	
		            	}
		            });		            
		        }
		        return true;
		    };
		};

		bMISCArrowRight = new BitmapTextureAtlas(null, 50, 39, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		MISCArrowRight = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bMISCArrowRight, core, "Arrow_Right.png", 0, 0);
		loadTexture(bMISCArrowRight);
		sMISCArrowRight = new Sprite(0, 0, MISCArrowRight, core.getVertexBufferObjectManager()){
		    @Override
		    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X, float Y) {
		        if (pSceneTouchEvent.isActionDown()){
		        	core.runOnUpdateThread(new Runnable(){ 
		            	@Override
		            	public void run(){ //sets the next scene		
		            		if(sCHARStanley.collidesWith(sMISCArrowRight)){
			            		mSceneManager.setTouchX(GameAdapter.leftArrowPosX);
			            		mSceneManager.changeSceneVisuals(mDBManager.getSceneDataWithID(mSceneManager.getUsedSceneData().getRightScene()),mSceneManager.getUsedScene());
			            		sCHARStanley.setPosition(GameAdapter.leftArrowPosX, GameAdapter.Stan_PosY);
		            		}
		            	}		            	
		            });		            
		        }
		        return true;
		    };
		};
		
		/******************************************************** OBJECTS ***************************************************************/
		BitmapTextureAtlasTextureRegionFactory
				.setAssetBasePath("images/maingame/objects/");
		
		bOBJRag = new BitmapTextureAtlas(null, 264, 158, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		OBJRag = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bOBJRag, core, "obj_greenold_carpet.png", 0, 0);
		loadTexture(bOBJRag);
		sOBJRag = new Sprite(0, 0, OBJRag, core.getVertexBufferObjectManager()){
		    @Override
		    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X, float Y) {
		        if (pSceneTouchEvent.isActionDown()){
		        	core.runOnUpdateThread(new Runnable(){ 
		            	@Override
		            	public void run(){ //sets the next scene	
		            		mSceneManager.getSceneLayer().detachChild(sOBJRag);
		            		mSceneManager.getUsedScene().unregisterTouchArea(sOBJRag);
		            		if(mSceneManager.getUsedSceneData().getSCENE_ID().length() > 0)
		            			mDBManager.toggleObjectStatus(mSceneManager.getUsedSceneData().getSCENE_ID(), "eOBJRag");
		            	}		            	
		            });		            
		        }
		        return true;
		    };
		};
		
		bOBJKey = new BitmapTextureAtlas(null, 23, 23, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		OBJKey = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bOBJKey, core, "obj_key.png", 0, 0);
		loadTexture(bOBJKey);
		sOBJKey = new Sprite(0, 0, OBJKey, core.getVertexBufferObjectManager()){
		    @Override
		    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X, float Y) {
		        if (pSceneTouchEvent.isActionDown()){
		        	core.runOnUpdateThread(new Runnable(){ 
		            	@Override
		            	public void run(){ //sets the next scene	
		            		mSceneManager.getSceneLayer().detachChild(sOBJKey);
		            		mSceneManager.getUsedScene().unregisterTouchArea(sOBJKey);
		            		if(mSceneManager.getUsedSceneData().getSCENE_ID().length() > 0)
		            			mDBManager.toggleObjectStatus(mSceneManager.getUsedSceneData().getSCENE_ID(), "eOBJKey");

		            	}		            	
		            });		            
		        }
		        return true;
		    };
		};

		/******************************************************** OPTIONS ***************************************************************/
		BitmapTextureAtlasTextureRegionFactory
				.setAssetBasePath("images/options/");
		bMISCMusicOn = new BitmapTextureAtlas(null, 264, 158,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		MISCMusicOn = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(bMISCMusicOn, core, "btn_music_on.png", 0, 0);
		loadTexture(bMISCMusicOn);
		sMISCMusicOn = new Sprite(0, 0, MISCMusicOn, core.getVertexBufferObjectManager());
		
		BitmapTextureAtlasTextureRegionFactory
				.setAssetBasePath("images/options/");
		bMISCMusicOff = new BitmapTextureAtlas(null, 264, 158,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		MISCMusicOff = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(bMISCMusicOff, core, "btn_music_off.png", 0, 0);
		loadTexture(bMISCMusicOff);
		sMISCMusicOff = new Sprite(0, 0, MISCMusicOff, core.getVertexBufferObjectManager());
		
		BitmapTextureAtlasTextureRegionFactory
				.setAssetBasePath("images/options/");
		bMISCSoundOn = new BitmapTextureAtlas(null, 264, 158,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		MISCSoundOn = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(bMISCSoundOn, core, "btn_soundfx_on.png", 0, 0);
		loadTexture(bMISCSoundOn);
		sMISCSoundOn = new Sprite(0, 0, MISCSoundOn, core.getVertexBufferObjectManager());
		
		BitmapTextureAtlasTextureRegionFactory
				.setAssetBasePath("images/options/");
		bMISCSoundOff = new BitmapTextureAtlas(null, 264, 158,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		MISCSoundOff = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(bMISCSoundOff, core, "btn_soundfx_off.png", 0, 0);
		loadTexture(bMISCSoundOff);
		sMISCSoundOff = new Sprite(0, 0, MISCSoundOff, core.getVertexBufferObjectManager());
		
		return true;
	}

	/*************************** END of  BuildGfxAssets function *****************************/
	
	private void loadTexture(Texture tex) {
		core.getEngine().getTextureManager().loadTexture(tex);
	}

	protected Character getStanley(){
		return sCHARStanley;
	}
	
	protected Sprite getUpArrow(){
		return sMISCArrowUp;
	}
	
	protected Sprite getDownArrow(){
		return sMISCArrowDown;
	}
	
	protected Sprite getLeftArrow(){
		return sMISCArrowLeft;
	}
	
	protected Sprite getRightArrow(){
		return sMISCArrowRight;
	}
	
	protected Sprite getMusicOn(){
		return sMISCMusicOn;
	}
	
	protected Sprite getMusicOff(){
		return sMISCMusicOff;
	}
	
	protected Sprite getSoundOn(){
		return sMISCSoundOn;
	}
	
	protected Sprite getSoundOff(){
		return sMISCSoundOff;
	}

	protected Sprite getObjKey(){
		return sOBJKey;
	}
	
	protected Sprite getObjRag(){
		return sOBJRag;
	}
	
	public Sprite getSpriteWithID(String sprID) {
		// Why did we use if statements rather than switch?
		// Because of the JRE issue and changing the compliance creates more
		// errors

		Sprite sprite = null;
		
		SpriteList spriteID = SpriteList.valueOf(sprID);
	
		switch (spriteID) {		
			case eSCENE1BG:
				sprite = sSCENE1BG;
				break;
				
			case eSCENE2BG:
				sprite = sSCENE2BG;
				break;
				
			case eSCENE3BG:
				sprite = sSCENE3BG;
				break;
				
			case eSCENE4BG:
				sprite = sSCENE4BG;
				break;
				
			case eSCENE5BG:
				sprite = sSCENE5BG;
				break;
				
			case eSCENE6BG:
				sprite = sSCENE6BG;
				break;
	
			case eSCENE7BG:
				sprite = sSCENE7BG;
				break;
	
			case eSCENE8BG:
				sprite = sSCENE8BG;
				break;
	
			case eSCENE9BG:
				sprite = sSCENE9BG;
				break;
	
			case eSCENE9ABG:
				sprite = sSCENE9ABG;
				break;
	
			case eSCENE9BBG:
				sprite = sSCENE9BBG;
				break;
	
			case eSCENE10BG:
				sprite = sSCENE10BG;
				break;
	
			case eSCENE11BG:
				sprite = sSCENE11BG;
				break;
	
			case eSCENE11DBG:
				sprite = sSCENE11DBG;
				break;
	
			case eSCENE11D_L1BG:
				sprite = sSCENE11D_L1BG;
				break;
	
			case eSCENE11D_L2BG:
				sprite = sSCENE11D_L2BG;
				break;
	
			case eSCENE11D_L3BG:
				sprite = sSCENE11D_L3BG;
				break;
	
			case eSCENE11D_R1BG:
				sprite = sSCENE11D_R1BG;
				break;
	
			case eSCENE11D_R1ABG:
				sprite = sSCENE11D_R1ABG;
				break;
	
			case eSCENE11D_R1BBG:
				sprite = sSCENE11D_R1BBG;
				break;
	
			case eSCENE11D_R2BG:
				sprite = sSCENE11D_R2BG;
				break;
	
			case eSCENE11D_R2ABG:
				sprite = sSCENE11D_R2ABG;
				break;
	
			case eSCENE11D_R2BBG:
				sprite = sSCENE11D_R2BBG;
				break;
	
			case eSCENE11D_R2CBG:
				sprite = sSCENE11D_R2CBG;
				break;
	
			case eSCENE12BG:
				sprite = sSCENE12BG;
				break;
	
			case eSCENE13BG:
				sprite = sSCENE13BG;
				break;
				
			case eOBJKey:
				sprite = sOBJKey;
				break;
				
			case eOBJRag:
				sprite = sOBJRag;
				break;
	
			default:
				break;

		}

		return sprite;
	}
}
