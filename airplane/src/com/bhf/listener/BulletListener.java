package com.bhf.listener;

import com.bhf.entities.Bullet ;

/**
 * 炮弹监听类，窗口每重画一次，炮弹就改变一次位置
 * @author 边宏飞
 *
 */
public interface BulletListener{
	
	void onChangeLocation(Bullet b) ;
}
