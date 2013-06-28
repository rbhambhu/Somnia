package com.usc.softandroid.managers;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.usc.softandroid.model.vo.Dimension;
import com.usc.softandroid.model.vo.GameAdapter;
import com.usc.softandroid.model.vo.PlayerData;
import com.usc.softandroid.model.vo.Position;
import com.usc.softandroid.model.vo.SceneAdapter;
import com.usc.softandroid.model.vo.SceneObject;

@SuppressLint("SdCardPath")
public class DatabaseManager extends SQLiteOpenHelper {

	private static DatabaseManager mInstance;

	// All Static variables

	// Database path
	private static String DB_PATH = "/data/data/com.usc.softandroid.somniagame/databases/";

	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "SomniaDB";

	// PlayerData Table
	private static final String PLAYER_DATA = "PlayerData";
	private static final String KEY_DATA = "_id";
	private static final String KEY_POSITION = "PLAYER_X";
	private static final String KEY_SCENE = "CURRENT_SCENE";

	private static Context mContext;

	private SQLiteDatabase SomniaDB;
	
	public static DatabaseManager getInstance(Context ctx) {
		// Use the application context, which will ensure that you
		// don't accidentally leak an Activity's context.
		if (mInstance == null) {
			mInstance = new DatabaseManager(ctx.getApplicationContext(),
					DATABASE_NAME, null, DATABASE_VERSION);
			mContext = ctx;
		}
		return mInstance;
	}

	private DatabaseManager(Context context, String name, CursorFactory factory, int version) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Creates a empty database on the system and rewrites it with your own
	 * database.
	 * */
	public void createDataBase() throws IOException {
		boolean dbExist = checkDataBase();
		System.out.println("Creating database..");
		
		if (dbExist) {
			// do nothing - database already exist
			System.out.println("The database exists.");
		} else {
			System.out.println("the database don`t exist");
			// By calling this method and empty database will be created into
			// the default system path
			// of your application so we are gonna be able to overwrite that
			// database with our database.
			SomniaDB = this.getReadableDatabase();
			SomniaDB.close();

			try {
				copyDataBase();
				System.out.println("Database Copied");
			} catch (IOException e) {

				throw new Error("Error copying database");

			}
		}

	}

	/**
	 * Check if the database already exist to avoid re-copying the file each
	 * time you open the application.
	 * 
	 * @return true if it exists, false if it doesn't
	 */
	private boolean checkDataBase() {

		SQLiteDatabase checkDB = null;

		try {
			String myPath = DB_PATH + DATABASE_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READWRITE);
			


		} catch (SQLiteException e) {
			// database does't exist yet.
			System.out.println("The database doesn't exist.");

		}

		if (checkDB != null) {

			checkDB.close();

		}

		return checkDB != null ? true : false;
	}

	/**
	 * Copies your database from your local assets-folder to the just created
	 * empty database in the system folder, from where it can be accessed and
	 * handled. This is done by transfering bytestream.
	 * */
	private void copyDataBase() throws IOException {

		System.out.println("Copying data from database");
		// Open your local db as the input stream
		InputStream myInput = mContext.getAssets().open(DATABASE_NAME);

		// Path to the just created empty db
		String outFileName = DB_PATH + DATABASE_NAME;

		// Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);

		// transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}

		// Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();

	}

	public void openDataBase() throws SQLException {

		// Open the database
		String myPath = DB_PATH + DATABASE_NAME;
		System.out.println("Opening the database " + myPath);
		SomniaDB = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READWRITE);

		System.out.println("SomniaDB: " + SomniaDB);

	}

	@Override
	public synchronized void close() {

		if (SomniaDB != null)
			SomniaDB.close();

		super.close();

	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w("Database Msg", "Upgrading database from version " + oldVersion
				+ " to " + newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS titles");
		onCreate(db);
	}

	// Add your public helper methods to access and get content from the
	// database.
	// You could return cursors by doing "return myDataBase.query(....)" so it'd
	// be easy for you to create adapters for your views.

	// PlayerData

	public int updateGameData(PlayerData player) {
		ContentValues values = new ContentValues();
		values.put(KEY_POSITION, player.getPlayerPosition());
		values.put(KEY_SCENE, player.getCurScene());

		// updating row
		return SomniaDB.update(PLAYER_DATA, values, KEY_DATA + " = ?",
				new String[] { String.valueOf(player.getDatakey()) });
	}

	public PlayerData getGameData() {

		Cursor cursor = SomniaDB.query(PLAYER_DATA, new String[] { KEY_DATA,
				KEY_POSITION, KEY_SCENE }, null, null, null, null, null, null);

		if (cursor != null)
			cursor.moveToFirst();

		PlayerData plyrdata = new PlayerData(Float.parseFloat(cursor
				.getString(1)), Integer.parseInt(cursor.getString(2)));

		// return gamedata
		return plyrdata;
	}

	public SceneAdapter getSceneDataWithID(String sceneID) { //returns the scene information called by outer class
		SceneAdapter mScene = null;

		if (sceneID != null) {
			//retrieve data on the scene_info table in the database
			Cursor sceneInfoGetCursor = SomniaDB.query("scene_info", new String[] { "_id",
					"scene_bg", "scene_up", "scene_down",
					"scene_right", "scene_left" },
					"_id" + "=?", new String[] { sceneID }, null, null,
					null, null);
			//checks if the scene_info table has data
			if (sceneInfoGetCursor != null) 
				sceneInfoGetCursor.moveToFirst();

			mScene = cursorToSceneInfo(sceneInfoGetCursor);
		}
		
		return mScene;
	}
	
	private SceneAdapter cursorToSceneInfo(Cursor cursor) {
		SceneAdapter mScene = new SceneAdapter(); //creates a scene information object
		
		
		for(int i=0;i<cursor.getColumnCount();i++){
			System.out.println(cursor.getString(i) + " at index " + i);
		}

		
		mScene.setSCENE_ID(cursor.getString(0));
		mScene.setBackgroundTex(cursor.getString(1));
		
		//if there is an up scene then, you retrieve the up arrow and show it on the scene
		if(!cursor.getString(2).equals("NA")){
			mScene.setUpScene(cursor.getString(2));
			
			System.out.println(cursor.getString(0) + " has an up scene. Retrieving the up arrow....");
			
			//retrieve the up arrow in according to scene # to implement its proper placement on the scene
			Cursor tc = SomniaDB.rawQuery("SELECT * FROM scene_arrows WHERE _id='"+ cursor.getString(0) +"' AND obj_id='eMISCArrowUp'", null);

			if (tc != null){ 
				tc.moveToFirst();
				mScene.setUpArrowAttr(Integer.parseInt(tc.getString(2)), Integer.parseInt(tc.getString(3)));
				GameAdapter.upArrowPosX = Integer.parseInt(tc.getString(2));
				System.out.println("SUCCESS! up arrow has just been retrieved with a position of (" + tc.getString(2) + ", " + tc.getString(3) + ")");
			}
			
			System.out.println("up scene set!");
		}

		//if there is a down scene then, you retrieve the down arrow and show it on the scene
		if(!cursor.getString(3).equals("NA")){
			mScene.setDownScene(cursor.getString(3));
			
			System.out.println(cursor.getString(0) + " has a down scene. Retrieving the down arrow....");

			Cursor tc = SomniaDB.rawQuery("SELECT * FROM scene_arrows WHERE _id='"+ cursor.getString(0) +"' AND obj_id='eMISCArrowDown'", null);

			if (tc != null){ 
				tc.moveToFirst();
				mScene.setDownArrowAttr(Integer.parseInt(tc.getString(2)), Integer.parseInt(tc.getString(3)));
				GameAdapter.downArrowPosX = Integer.parseInt(tc.getString(2));
				System.out.println("SUCCESS! down arrow has just been retrieved with a position of (" + tc.getString(2) + ", " + tc.getString(3) + ")");
			}
			
			System.out.println("down scene set!");
		}

		//if there is a right scene, then retrieve the right arrow and show it on the scene
		if(!cursor.getString(4).equals("NA")){
			mScene.setRightScene(cursor.getString(4));
			
			System.out.println(cursor.getString(0) + " has a right scene. Retrieving the right arrow....");

			Cursor tc = SomniaDB.rawQuery("SELECT * FROM scene_arrows WHERE _id='"+ cursor.getString(0) +"' AND obj_id='eMISCArrowRight'", null);

			if (tc != null){ 
				tc.moveToFirst();
				mScene.setRightArrowAttr(Integer.parseInt(tc.getString(2)), Integer.parseInt(tc.getString(3)));
				GameAdapter.rightArrowPosX = Integer.parseInt(tc.getString(2));
				System.out.println("SUCCESS! right arrow has just been retrieved with a position of (" + tc.getString(2) + ", " + tc.getString(3) + ")");
			}
			
			System.out.println("right scene set!");
		}

		//if there is a left scene, then retrieve the left arrow and show it on the scene
		if(!cursor.getString(5).equals("NA")){
			mScene.setLeftScene(cursor.getString(5));
			
			System.out.println(cursor.getString(0) + " has a left scene. Retrieving the left arrow....");
			
			Cursor tc = SomniaDB.rawQuery("SELECT * FROM scene_arrows WHERE _id='"+ cursor.getString(0) +"' AND obj_id='eMISCArrowLeft'", null);

			if (tc != null){ 
				tc.moveToFirst();
				mScene.setLeftArrowAttr(Integer.parseInt(tc.getString(2)), Integer.parseInt(tc.getString(3)));
				GameAdapter.leftArrowPosX = Integer.parseInt(tc.getString(2));
				System.out.println("SUCCESS! left arrow has just been retrieved with a position of (" + tc.getString(2) + ", " + tc.getString(3) + ")");
			}
			
			System.out.println("down scene set!");
		}

		return mScene;
	}
	
	
	public void showAllTables() {
		String mySql = " SELECT name FROM sqlite_master "
				+ " WHERE type='table'";
		
		System.out.println("Show the tables");
		
		List<String> todoItems = new ArrayList<String>();
		
		Cursor c = SomniaDB.rawQuery(mySql, null);
        if (c.moveToFirst())
        {
        do{
           todoItems.add(c.getString(0));

           }while (c.moveToNext());
        }
        if (todoItems.size() >= 0)
        {
            for (int i=0; i<todoItems.size(); i++)
            {
                Log.d("TODOItems(" + i + ")", todoItems.get(i) + "");

            }

        }
	}

	public int getSceneObjectsTotal(String sceneID){
		Cursor cursor = SomniaDB.rawQuery("SELECT * FROM scene_objects WHERE _id='"+ sceneID +"'", null);
		
		if(cursor != null){
			cursor.moveToFirst();		
			return cursor.getCount();
		}else
			return 0;
	}
	
	public SceneObject getObjectData(String sceneID, int cursorPosition){
		SceneObject dbObjData = new SceneObject();
		Dimension dbObjDimen = new Dimension();
		Position dbObjPos = new Position();
		
		Cursor cursor = SomniaDB.rawQuery("SELECT * FROM scene_objects WHERE _id='"+ sceneID +"'", null);
		if(cursor != null){
			cursor.moveToPosition(cursorPosition);
			
			dbObjPos.setX(Integer.parseInt(cursor.getString(3)));
			dbObjPos.setY(Integer.parseInt(cursor.getString(4)));
			dbObjDimen.setWidth(Integer.parseInt(cursor.getString(5)));
			dbObjDimen.setHeight(Integer.parseInt(cursor.getString(6)));

			dbObjData.setObjectName(cursor.getString(1));
			dbObjData.setObjectStatus(cursor.getString(2));
			dbObjData.setObjectDimensions(dbObjDimen.getWidth(), dbObjDimen.getHeight());
			dbObjData.setObjectPosition(dbObjPos.getX(), dbObjPos.getY());
			
			return dbObjData;
		}else
			return null;
		
	}	
	
	public boolean toggleObjectStatus(String sceneID, String objName){
		Cursor cursor = SomniaDB.rawQuery("UPDATE scene_objects SET obj_status='NA' WHERE _id='"+ sceneID +"' AND obj_id='"+objName+"'", null);
		if(cursor != null){
			cursor.moveToFirst();
			return true;
		}		
		return false;

	}
}
