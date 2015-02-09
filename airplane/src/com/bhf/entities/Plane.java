package com.bhf.entities ;

import java.awt.Color ;
import java.awt.Graphics ;
import java.awt.Rectangle ;
import java.awt.event.MouseEvent ;

import com.bhf.factory.BulletFactory ;
import com.bhf.frame.MainFrame ;

/**
 * 飞机类
 * 
 * @author 边宏飞
 * 
 */
public class Plane{
	// 飞机的宽度
	public static final int PLANE_WIDTH = 40 ;
	public static final int PLANE_HEIGHT = 50 ;

	private int left ;
	private int height ;

	private MainFrame frame ;
	
	private boolean live ;

	public Plane(MainFrame frame){
		this.frame = frame ;
		this.live = true ;
		// 启动发射炮弹的线程，从而不停地发射炮弹
		new Thread(new LauchBulletThread()).start() ;
	}

	/**
	 * 飞机发出一枚炮弹
	 * 
	 * @return
	 */
	public Bullet lauchBullet(){
		if(!live){
			return null;
		}
		Bullet b = BulletFactory.createBullet(this) ;
		// 注册监听器
		b.addBulletListener(frame) ;
		frame.getBullets().add(b) ;
		return b ;
	}

	
	/**
	 * 取得我方飞机的区域
	 * @return
	 */
	public Rectangle getRectangle(){
		Rectangle rec = new Rectangle(left , height , PLANE_WIDTH , PLANE_HEIGHT) ;
		return rec ;
	}
	
	/**
	 * 画出自己
	 * 
	 * @param g
	 */
	public void draw(Graphics g){
		g.setColor(Color.BLUE) ;
		g.fillRect(left , height , PLANE_WIDTH , PLANE_HEIGHT) ;

	}

	/**
	 * 随着鼠标的移动，也移动飞机的位置
	 * 
	 * @param e
	 */
	public void mouseMoved(MouseEvent e){
		int x = e.getX() ;
		int y = e.getY() ;
		left = x ;
		if(left >= MainFrame.WIDTH - Plane.PLANE_WIDTH){
			left = MainFrame.WIDTH - Plane.PLANE_WIDTH ;
		}
		height = y ;
		if(height >= MainFrame.HEIGTH - Plane.PLANE_HEIGHT){
			height = MainFrame.HEIGTH - Plane.PLANE_HEIGHT ;
		}
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
	

	public MainFrame getFrame(){
		return frame ;
	}


	public boolean isLive(){
		return live ;
	}

	public void setLive(boolean live){
		this.live = live ;
	}



	/**
	 * 连续发射炮弹的线程
	 * 
	 * @author 边宏飞
	 * 
	 */
	class LauchBulletThread implements Runnable{
		public void run(){
			while(true){
				try{
					Thread.sleep(100) ;
				}catch(InterruptedException e){
					e.printStackTrace() ;
				}
				Bullet b = lauchBullet() ;
			}
		}
	}
}
