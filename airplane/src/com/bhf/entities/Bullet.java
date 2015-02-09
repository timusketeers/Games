package com.bhf.entities;

import java.awt.Color ;
import java.awt.Graphics ;
import java.awt.Rectangle ;
import java.util.List ;

import com.bhf.frame.MainFrame ;
import com.bhf.listener.BulletListener ;

public class Bullet{
	// 宽度
	public static final int BULLET_WIDTH = 4 ;
	public static final int BULLET_HEIGHT = 10 ;
	// 炮弹速度,默认移动1个像素
	private int speed = 15 ; 
	// 炮弹位置
	private int left ;
	private int height ;
	// 炮弹监听器
	public BulletListener listener ;
	// 
	private MainFrame frame ;
	
	public Bullet(MainFrame frame){
		this.frame = frame ;
	}
	
	public Bullet(int left , int height){
		this.left = left ;
		this.height = height ;
	}
	
	/**
	 * 取得子弹的区域
	 * @return
	 */
	public Rectangle getRectangle(){
		Rectangle rec = new Rectangle(left , height , BULLET_WIDTH , BULLET_HEIGHT) ;
		return rec ;
	}
	
	/**
	 * 打击敌机
	 * @param ep
	 * @return
	 */
	public EnemyPlane hitEnemyPlanes(){
		List<EnemyPlane> enmeyPlanes = this.frame.getEnemyPlanes() ;
		for(int i = 0 ; i < enmeyPlanes.size() ; i ++){
			EnemyPlane ep = enmeyPlanes.get(i) ;
			if(this.getRectangle().intersects(ep.getRectangle())){
				return ep ;
			}
		}
		return null ;
	}
	
	/**
	 * 注册监听器
	 * @param listener
	 */
	public void addBulletListener(BulletListener listener){
		this.listener = listener ;
	}
	
	/**
	 * 画出自己
	 * @param g
	 */
	public void draw(Graphics g){
		g.setColor(Color.RED) ;
		g.fillRect(left , height , BULLET_WIDTH , BULLET_HEIGHT) ;
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
	
}
