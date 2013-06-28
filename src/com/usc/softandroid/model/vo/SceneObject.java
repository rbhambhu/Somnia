package com.usc.softandroid.model.vo;

public class SceneObject {

	private String objName;
	private Dimension objDim;
	private Position objPos;
	private String objStat;

	public SceneObject(){
		objDim = new Dimension();
		objPos = new Position();
	}
	
	public void setObjectName(String dbObjName){
		objName = dbObjName;
	}
	
	public String getObjectName(){
		return objName;
	}
	
	public void setObjectDimensions(int dbObjWidth, int dbObjHeight){
		objDim.setWidth(dbObjWidth);
		objDim.setHeight(dbObjHeight);
	}
	
	public Dimension getObjectDimensions(){
		return objDim;
	}
	
	public void setObjectPosition(int dbObjPosX, int dbObjPosY){
		objPos.setX(dbObjPosX);
		objPos.setY(dbObjPosY);
	}
	
	public Position getObjectPosition(){
		return objPos;
	}
	
	public void setObjectStatus(String dbObjstat){
		objStat = dbObjstat;
	}
	
	public String getObjectStatus(){
		return objStat;
	}
}
