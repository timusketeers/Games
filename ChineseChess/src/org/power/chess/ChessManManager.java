package org.power.chess;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;


public class ChessManManager {
	private static List<ChessMan> chessMans = new ArrayList<ChessMan>();
	public static int xTableOffset = 0;
	public static int yTableOffset = 0;
	
	private ChessMan chessPickedUp = null;
	private ChessMan lastMoveChessMan = null;

	public static List<ChessMan> getChessMans() {
		return chessMans;
	}

	public void setTableOffset(int x, int y) {
		xTableOffset = x;
		yTableOffset = y;
	}
	
	public void draw(Graphics g) {
		for (int i = 0;i < chessMans.size();i++) {
			chessMans.get(i).draw(g);
		}
		
		if (chessPickedUp != null) {
			chessPickedUp.drawFrame(g, Color.RED);
			//chessPickedUp.drawCanGo(g, Color.YELLOW);
		}
		
		if (lastMoveChessMan != null) {
			lastMoveChessMan.drawFrame(g, Color.GREEN);
			
		}
		/*for (int i = 0;i < chessMans.size();i++) {
			ChessMan cm = chessMans.get(i);
			if(cm.isSelected()){
				cm.drawFrame(g, Color.RED);
				break;
			}
		}*/
	}
	
	public void initialServerGame() {
		chessMans.add(new ChessMan("1車", 0, 9, ChessManType.CAR, true));
		chessMans.add(new ChessMan("1馬", 1, 9, ChessManType.HORSE, true));
		chessMans.add(new ChessMan("1象", 2, 9, ChessManType.ELEPHANT, true));
		chessMans.add(new ChessMan("1士", 3, 9, ChessManType.GUARD, true));
		chessMans.add(new ChessMan("1將", 4, 9, ChessManType.GENERAL, true));
		chessMans.add(new ChessMan("1士", 5, 9, ChessManType.GUARD, true));
		chessMans.add(new ChessMan("1象", 6, 9, ChessManType.ELEPHANT, true));
		chessMans.add(new ChessMan("1馬", 7, 9, ChessManType.HORSE, true));
		chessMans.add(new ChessMan("1車", 8, 9, ChessManType.CAR, true));		
		chessMans.add(new ChessMan("1炮", 1, 7, ChessManType.CANNON, true));
		chessMans.add(new ChessMan("1炮", 7, 7, ChessManType.CANNON, true));
		chessMans.add(new ChessMan("1卒", 0, 6, ChessManType.SOLDIER, true));
		chessMans.add(new ChessMan("1卒", 2, 6, ChessManType.SOLDIER, true));
		chessMans.add(new ChessMan("1卒", 4, 6, ChessManType.SOLDIER, true));
		chessMans.add(new ChessMan("1卒", 6, 6, ChessManType.SOLDIER, true));
		chessMans.add(new ChessMan("1卒", 8, 6, ChessManType.SOLDIER, true));
		
		chessMans.add(new ChessMan("2車", 8, 0, ChessManType.CAR, false));
		chessMans.add(new ChessMan("2馬", 7, 0, ChessManType.HORSE, false));
		chessMans.add(new ChessMan("2相", 6, 0, ChessManType.ELEPHANT, false));
		chessMans.add(new ChessMan("2仕", 5, 0, ChessManType.GUARD, false));
		chessMans.add(new ChessMan("2帥", 4, 0, ChessManType.GENERAL, false));
		chessMans.add(new ChessMan("2仕", 3, 0, ChessManType.GUARD, false));
		chessMans.add(new ChessMan("2相", 2, 0, ChessManType.ELEPHANT, false));
		chessMans.add(new ChessMan("2馬", 1, 0, ChessManType.HORSE, false));
		chessMans.add(new ChessMan("2車", 0, 0, ChessManType.CAR, false));		
		chessMans.add(new ChessMan("2炮", 7, 2, ChessManType.CANNON, false));
		chessMans.add(new ChessMan("2炮", 1, 2, ChessManType.CANNON, false));
		chessMans.add(new ChessMan("2兵", 8, 3, ChessManType.SOLDIER, false));
		chessMans.add(new ChessMan("2兵", 6, 3, ChessManType.SOLDIER, false));
		chessMans.add(new ChessMan("2兵", 4, 3, ChessManType.SOLDIER, false));
		chessMans.add(new ChessMan("2兵", 2, 3, ChessManType.SOLDIER, false));
		chessMans.add(new ChessMan("2兵", 0, 3, ChessManType.SOLDIER, false));
		
System.out.println("Server Game initialed!");
	}
	
	public void initialClientGame() {
		chessMans.add(new ChessMan("1車", 8, 0, ChessManType.CAR, false));
		chessMans.add(new ChessMan("1馬", 7, 0, ChessManType.HORSE, false));
		chessMans.add(new ChessMan("1象", 6, 0, ChessManType.ELEPHANT, false));
		chessMans.add(new ChessMan("1士", 5, 0, ChessManType.GUARD, false));
		chessMans.add(new ChessMan("1將", 4, 0, ChessManType.GENERAL, false));
		chessMans.add(new ChessMan("1士", 3, 0, ChessManType.GUARD, false));
		chessMans.add(new ChessMan("1象", 2, 0, ChessManType.ELEPHANT, false));
		chessMans.add(new ChessMan("1馬", 1, 0, ChessManType.HORSE, false));
		chessMans.add(new ChessMan("1車", 0, 0, ChessManType.CAR, false));		
		chessMans.add(new ChessMan("1炮", 7, 2, ChessManType.CANNON, false));
		chessMans.add(new ChessMan("1炮", 1, 2, ChessManType.CANNON, false));
		chessMans.add(new ChessMan("1卒", 8, 3, ChessManType.SOLDIER, false));
		chessMans.add(new ChessMan("1卒", 6, 3, ChessManType.SOLDIER, false));
		chessMans.add(new ChessMan("1卒", 4, 3, ChessManType.SOLDIER, false));
		chessMans.add(new ChessMan("1卒", 2, 3, ChessManType.SOLDIER, false));
		chessMans.add(new ChessMan("1卒", 0, 3, ChessManType.SOLDIER, false));
		
		chessMans.add(new ChessMan("2車", 0, 9, ChessManType.CAR, true));
		chessMans.add(new ChessMan("2馬", 1, 9, ChessManType.HORSE, true));
		chessMans.add(new ChessMan("2相", 2, 9, ChessManType.ELEPHANT, true));
		chessMans.add(new ChessMan("2仕", 3, 9, ChessManType.GUARD, true));
		chessMans.add(new ChessMan("2帥", 4, 9, ChessManType.GENERAL, true));
		chessMans.add(new ChessMan("2仕", 5, 9, ChessManType.GUARD, true));
		chessMans.add(new ChessMan("2相", 6, 9, ChessManType.ELEPHANT, true));
		chessMans.add(new ChessMan("2馬", 7, 9, ChessManType.HORSE, true));
		chessMans.add(new ChessMan("2車", 8, 9, ChessManType.CAR, true));		
		chessMans.add(new ChessMan("2炮", 1, 7, ChessManType.CANNON, true));
		chessMans.add(new ChessMan("2炮", 7, 7, ChessManType.CANNON, true));
		chessMans.add(new ChessMan("2兵", 0, 6, ChessManType.SOLDIER, true));
		chessMans.add(new ChessMan("2兵", 2, 6, ChessManType.SOLDIER, true));
		chessMans.add(new ChessMan("2兵", 4, 6, ChessManType.SOLDIER, true));
		chessMans.add(new ChessMan("2兵", 6, 6, ChessManType.SOLDIER, true));
		chessMans.add(new ChessMan("2兵", 8, 6, ChessManType.SOLDIER, true));
System.out.println("Client Game initialed!");
	}
	
	public String parseClicked(int x, int y) {
		// TODO Auto-generated method stub
System.out.println("解析鼠标消息：（" + x + "，" + y + "）");
		if (chessPickedUp != null) {//已经有棋子被拾起
			if(chessPickedUp.canGoto(x, y, chessMans)){
				String chessId = chessPickedUp.getId();
				String newPosAndResult = chessPickedUp.moveAdaptSlot(x, y);
				lastMoveChessMan = chessPickedUp;
				chessPickedUp = null;
				return chessId + newPosAndResult;
			}
		} else {
			for (int i = 0;i < chessMans.size();i++) {
				ChessMan cm = chessMans.get(i);
				if(cm.isClicked(x, y)){
					if (!cm.isBelongToPlayer()) return null;//不能操作對方棋子
					chessPickedUp = cm.pickUp();
System.out.println("拾起棋子id:" + cm.getId());
					break;
				}
			}
		}
		return null;
		
	}

	public void releaseChessMan() {
		// TODO Auto-generated method stub
		if (chessPickedUp != null) {
			chessPickedUp = null;
		}
	}
	
	public ChessMan getChessManByCoordinatePos(int xCoordinatePos, int yCoordinatePos){
		for (int i = 0; i < chessMans.size(); i++) {
			ChessMan cm = chessMans.get(i);
			if (cm.getxCoordinatePos() == xCoordinatePos && cm.getyCoordinatePos() == yCoordinatePos) {
				return cm;
			}
		}
		return null;
	}
	
	public String parseMsg(int chessValue, int newPosX, int newPosY) {
		// TODO Auto-generated method stub
		return moveChess(chessValue, 8 - newPosX, 9 - newPosY);//移动(坐标转换)
	}

	private String moveChess(int chessValue, int newPosX, int newPosY) {
		// TODO Auto-generated method stub
		ChessMan cm = null;
		cm = getChessManById(chessValue);
		if (cm != null) {
			lastMoveChessMan = cm;
			return cm.moveToSlotPos(newPosX, newPosY);
		}
		return null;
	}

	private ChessMan getChessManById(int chessValue) {
		// TODO Auto-generated method stub
		ChessMan cm = null;
		for (int i = 0; i < chessMans.size(); i++) {
			cm = chessMans.get(i);
			if (Integer.parseInt(cm.getId()) == chessValue) {
				return cm;
			}
		}
		return null;
	}

	public void cleanUp() {
		// TODO Auto-generated method stub
		chessMans.clear();
		
	}
}
