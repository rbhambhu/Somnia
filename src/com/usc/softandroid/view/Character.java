package com.usc.softandroid.view;

import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;


public class Character extends AnimatedSprite{
	float pos_x, pos_y, char_h, char_w;
	int myx;
	private float eventX;
	private boolean isFacingForward = true;
	public PhysicsHandler mPhysicsHandler; 
	
	public Character(float pX, float pY, TiledTextureRegion pTextureRegion, VertexBufferObjectManager vertexBufferObjectManager) {
		super(pX, pY, pTextureRegion, vertexBufferObjectManager);
		this.mPhysicsHandler = new PhysicsHandler(this);
		this.registerUpdateHandler(this.mPhysicsHandler);
	}

	public void moveRight(){
		this.mPhysicsHandler.setVelocityX(1);
		this.mPhysicsHandler.setVelocityY(0);
	
	}
	
	public void moveLeft(){
		this.mPhysicsHandler.setVelocityX(-1);
		this.mPhysicsHandler.setVelocityY(0);
		
	}
	
	public void faceForward(){
		isFacingForward = true;
		setFlippedHorizontal(false);
	}
	
	public void faceBackward(){
		isFacingForward = false;
		setFlippedHorizontal(true);
	}
	
	public boolean getDirection(){
		return isFacingForward;
	}
	
	public void stop(float f,float g){
		this.setPosition(f, g);
	}
	
	public void stopFromWalking(){
	  this.mPhysicsHandler.setVelocityX(0);
	}

	
	public void setEventX( float x )
	{
		eventX = x;
	}
	
	public float getEventX()
	{
		return eventX;
	}
}

