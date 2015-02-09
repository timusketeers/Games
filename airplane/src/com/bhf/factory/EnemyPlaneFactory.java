package com.bhf.factory;

import java.util.Random ;

import com.bhf.entities.Bullet ;
import com.bhf.entities.EnemyPlane ;
import com.bhf.entities.Plane ;
import com.bhf.frame.MainFrame ;

public class EnemyPlaneFactory{
	public static final Random rand = new Random() ;

	/**
	 * 生产子弹
	 * @return
	 */
	public static final EnemyPlane createEnemyPlane(MainFrame frame){
		EnemyPlane ep = new EnemyPlane(frame) ;
		// 敌机的位置
		int epLeft = rand.nextInt(MainFrame.WIDTH - EnemyPlane.PLANE_WIDTH) ;
		int epHeight = 0 ;
		ep.setLeft(epLeft) ;
		ep.setHeight(epHeight) ;
		// 下落的速度5-8
		int speed = rand.nextInt(15) ;
		while(speed < 5){
			speed = rand.nextInt(15) ;
		}
		ep.setSpeed(speed) ;
		// 注册监听器
		ep.addEnemyPlaneListener(frame) ;
		return ep ;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args){

	}

}
