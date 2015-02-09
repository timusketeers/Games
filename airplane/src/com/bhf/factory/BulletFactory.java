package com.bhf.factory;

import com.bhf.entities.Bullet ;
import com.bhf.entities.Plane ;

public class BulletFactory{

	/**
	 * 生产子弹
	 * @return
	 */
	public static final Bullet createBullet(Plane myPlane){
		int planeLeft = myPlane.getLeft() ;
		int planeHeight = myPlane.getHeight() ;
		// 炮弹的位置
		int bulletLeft = planeLeft + Plane.PLANE_WIDTH / 2 - Bullet.BULLET_WIDTH / 2 ;
		int bulletHeight = planeHeight + Bullet.BULLET_HEIGHT ;
		// 生产炮弹
		Bullet b = new Bullet(myPlane.getFrame()) ;
		b.setLeft(bulletLeft) ;
		b.setHeight(bulletHeight) ;
		return b ;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args){

	}

}
