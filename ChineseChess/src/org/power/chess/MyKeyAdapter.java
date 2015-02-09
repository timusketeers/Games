package org.power.chess;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MyKeyAdapter extends KeyAdapter {// 调试时使用，调试完成请删除勿使用类
	ChineseChess cc = null;

	public MyKeyAdapter(ChineseChess cc) {
		this.cc = cc;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		super.keyReleased(e);
		if (!cc.getNetManager().isConnected()) {
			if (e.getKeyCode() == KeyEvent.VK_F1) {
				cc.doServer(8887);
			} else if (e.getKeyCode() == KeyEvent.VK_F2) {
				cc.connectServer("127.0.0.1", 8887);
			}
		}
	}

}