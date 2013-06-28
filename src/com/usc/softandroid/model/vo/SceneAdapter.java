package com.usc.softandroid.model.vo;

import org.andengine.entity.scene.Scene;

public class SceneAdapter {

	private static String SCENE_ID;
	private Scene currScene;
	private String backgroundTex;

	private String upScene = "", 
				   downScene = "", 
				   rightScene = "", 
				   leftScene = "";
	
	private int upArrowX = 0, upArrowY = 0,
				downArrowX = 0, downArrowY = 0,
				rightArrowX = 0, rightArrowY = 0,
				leftArrowX = 0, leftArrowY = 0;
	
	private int stanStartPosX = 0;
	
	public SceneAdapter(){}

	public void setStanStartPosX(int position){
		stanStartPosX = position;
	}
	
	public int getStanStartPosX(){
		return stanStartPosX;
	}
	
	public void setCurrentScene(Scene scene){
		this.currScene = scene;
	}
	
	public Scene getCurrentScene(){
		return currScene;
	}
	
	public String getSCENE_ID() {
		return SCENE_ID;
	} 	

	public void setSCENE_ID(String sCENE_ID) {
		SCENE_ID = sCENE_ID;
	}

	public String getBackgroundTex() {
		return backgroundTex;
	}

	public void setBackgroundTex(String backgroundTex) {
		this.backgroundTex = backgroundTex;
	}

	public String getDownScene() {
		return downScene;
	}

	public void setDownScene(String downScene) {
		this.downScene = downScene;
	}

	public void setDownArrowAttr(int X, int Y){
		downArrowX = X;
		downArrowY = Y;
	}
	
	public int getDownArrowX(){
		return downArrowX;
	}
	
	public int getDownArrowY(){
		return downArrowY;
	}

	public String getUpScene() {
		return upScene;
	}

	public void setUpScene(String upScene) {
		this.upScene = upScene;
	}

	public void setUpArrowAttr(int X, int Y){
		upArrowX = X;
		upArrowY = Y;
	}
	
	public int getUpArrowX(){
		return upArrowX;
	}
	
	public int getUpArrowY(){
		return upArrowY;
	}

	public String getRightScene() {
		return rightScene;
	}

	public void setRightScene(String rightScene) {
		this.rightScene = rightScene;
	}

	public void setRightArrowAttr(int X, int Y){
		rightArrowX = X;
		rightArrowY = Y;
	}

	public int getRightArrowX(){
		return rightArrowX;
	}
	
	public int getRightArrowY(){
		return rightArrowY;
	}

	public String getLeftScene() {
		return leftScene;
	}

	public void setLeftScene(String leftScene) {
		this.leftScene = leftScene;
	}
	
	public void setLeftArrowAttr(int X, int Y){
		leftArrowX = X;
		leftArrowY = Y;
	}		
	
	public int getLeftArrowX(){
		return leftArrowX;
	}
	
	public int getLeftArrowY(){
		return leftArrowY;
	}	
}
