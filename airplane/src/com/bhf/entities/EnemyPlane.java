package com.bhf.entities;

import java.awt.Color ;
import java.awt.Graphics ;
import java.awt.Rectangle ;

import com.bhf.frame.MainFrame ;
import com.bhf.listener.EnemyPlaneListener ;

public class EnemyPlane{
	// 飞机的宽度
	public static final int PLANE_WIDTH = 30 ;
	public static final int PLANE_HEIGHT = 20 ;
	// 敌人飞机的位置
	private int left ;
	private int height ;
	// 炮弹速度,默认移动1个像素
	private int speed ; 
	// 对于主窗口的引用
	private MainFrame frame ;
	// 监听器
	private EnemyPlaneListener listener ;
	

	public EnemyPlane(){
	}
	
	public EnemyPlane(MainFrame frame){
		this.frame = frame ;
	}
	
	/**
	 * 取得敌机的区域
	 * @return
	 */
	public Rectangle getRectangle(){
		Rectangle rec = new Rectangle(left , height , PLANE_WIDTH , PLANE_HEIGHT) ;
		return rec ;
	}
	
	/**
	 * 注册监听器
	 * @param listener
	 */
	public void addEnemyPlaneListener(EnemyPlaneListener listener){
		this.listener = listener ;
	}
	
	/**
	 * 画出自己
	 * 
	 * @param g
	 */
	public void draw(Graphics g){
		g.setColor(Color.BLACK) ;
		g.fillRect(left , height , PLANE_WIDTH , PLANE_HEIGHT) ;

	}

	public int getLeft(){
		return left ;
	}

	public void setLeft(int left){
		this.left = left ;
	}

	public int getHeight(){
		return height ;
	}

	public void setHeight(int height){
		this.height = height ;
	}

	public int getSpeed(){
		return speed ;
	}

	public void setSpeed(int speed){
		this.speed = speed ;
	}

	public MainFrame getFrame(){
		return frame ;
	}
}
