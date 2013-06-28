package com.usc.softandroid.model.vo;


public class PlayerData {

	private String datakey	= "SomniaACERSY";
	private float playerPosition; // The X coordinate of the player
	private int curScene; // The String ID of the Scene the player is current on

	public PlayerData() {
		this.setPlayerPosition(0);
		this.setCurScene(1);
	}

	
	// This constructor is used when the current scene is not the first
	public PlayerData(float playerPosition, int curScene) {
		this.setPlayerPosition(playerPosition);
		this.setCurScene(curScene);
	}

	public void saveData(float savedPosition, int savedScene) {
		this.setPlayerPosition(savedPosition);
		this.setCurScene(savedScene);
	}

	public float getPlayerPosition() {
		return playerPosition;
	}

	public void setPlayerPosition(float playerPosition) {
		this.playerPosition = playerPosition;
	}

	public int getCurScene() {
		return curScene;
	}

	public void setCurScene(int curScene) {
		this.curScene = curScene;
	}

	public String getDatakey() {
		return datakey;
	}

}
