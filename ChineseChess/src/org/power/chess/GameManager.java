package org.power.chess;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameManager
{
    private static Toolkit tk = Toolkit.getDefaultToolkit();
    private static Image[] chessImages = null;

    public static Map<String, Image> imgs = new HashMap<String, Image>();
    static
    {
        chessImages = new Image[] {
                tk.getImage(ChineseChess.class.getClassLoader().getResource(
                        "images/ChessBoard.jpg")),
                tk.getImage(ChineseChess.class.getClassLoader().getResource(
                        "images/1將.gif")),
                tk.getImage(ChineseChess.class.getClassLoader().getResource(
                        "images/1車.gif")),
                tk.getImage(ChineseChess.class.getClassLoader().getResource(
                        "images/1炮.gif")),
                tk.getImage(ChineseChess.class.getClassLoader().getResource(
                        "images/1馬.gif")),
                tk.getImage(ChineseChess.class.getClassLoader().getResource(
                        "images/1象.gif")),
                tk.getImage(ChineseChess.class.getClassLoader().getResource(
                        "images/1士.gif")),
                tk.getImage(ChineseChess.class.getClassLoader().getResource(
                        "images/1卒.gif")),

                tk.getImage(ChineseChess.class.getClassLoader().getResource(
                        "images/2帥.gif")),
                tk.getImage(ChineseChess.class.getClassLoader().getResource(
                        "images/2車.gif")),
                tk.getImage(ChineseChess.class.getClassLoader().getResource(
                        "images/2炮.gif")),
                tk.getImage(ChineseChess.class.getClassLoader().getResource(
                        "images/2馬.gif")),
                tk.getImage(ChineseChess.class.getClassLoader().getResource(
                        "images/2相.gif")),
                tk.getImage(ChineseChess.class.getClassLoader().getResource(
                        "images/2仕.gif")),
                tk.getImage(ChineseChess.class.getClassLoader().getResource(
                        "images/2兵.gif")), };

        imgs.put("ChessBoard", chessImages[0]);
        imgs.put("1將", chessImages[1]);
        imgs.put("1車", chessImages[2]);
        imgs.put("1炮", chessImages[3]);
        imgs.put("1馬", chessImages[4]);
        imgs.put("1象", chessImages[5]);
        imgs.put("1士", chessImages[6]);
        imgs.put("1卒", chessImages[7]);

        imgs.put("2帥", chessImages[8]);
        imgs.put("2車", chessImages[9]);
        imgs.put("2炮", chessImages[10]);
        imgs.put("2馬", chessImages[11]);
        imgs.put("2相", chessImages[12]);
        imgs.put("2仕", chessImages[13]);
        imgs.put("2兵", chessImages[14]);

    }

    private ChessTable ct = null;
    private ChessManManager cmm = null;
    private PlayerInfo myInfo = null;
    private PlayerInfo oppositeInfo = null;
    private String winnerIp = null;
    private ChineseChess cc = null;
    private NetManager nm = null;
    private boolean gameBreaked = false;

    public GameManager(ChineseChess chineseChess)
    {
        int chessTableWidth = Integer.parseInt(PropertyMgr
                .getProperty("chessTableWidth"));
        int chessTableHeight = Integer.parseInt(PropertyMgr
                .getProperty("chessTableHeight"));
        int playerInfoHeight = Integer.parseInt(PropertyMgr
                .getProperty("playerInfoHeight"));
        ct = new ChessTable(0, 15, chessTableWidth, chessTableHeight,
                imgs.get("ChessBoard"));
        cmm = new ChessManManager();
        cmm.setTableOffset(0, playerInfoHeight);
        myInfo = new PlayerInfo(0, chessTableHeight + playerInfoHeight + 5,
                chessTableWidth, playerInfoHeight, Color.BLUE, null);
        oppositeInfo = new PlayerInfo(0, playerInfoHeight + 5, chessTableWidth,
                playerInfoHeight, Color.RED, myInfo);
        myInfo.setOppositeInfo(oppositeInfo);
        this.cc = chineseChess;
    }

    public void draw(Graphics g)
    {
        ct.draw(g);
        cmm.draw(g);
        myInfo.draw(g);
        oppositeInfo.draw(g);
        if (gameBreaked)
        {
            Font f = g.getFont();
            Color cOld = g.getColor();
            g.setColor(Color.RED);
            g.setFont(new Font("黑体", Font.PLAIN, 50));

            g.drawString("对方退出，对弈终止！", 60, 325);

            g.setFont(f);
            g.setColor(cOld);
        }
        else if (winnerIp != null)
        {
            Font f = g.getFont();
            Color cOld = g.getColor();
            g.setColor(Color.RED);
            g.setFont(new Font("黑体", Font.PLAIN, 50));
            if (winnerIp.length() != 0)
            {
                g.drawString("恭喜你胜出此次对局！", 60, 325);
                g.setFont(new Font("黑体", Font.PLAIN, 30));
                g.drawString("请点击网页上的结束对弈来保存提交结果。", 10, 400);
                // System.out.println("WinnerIp:" + winnerIp);
            }
            else
            {
                g.drawString("很遗憾你没有战胜对手！", 40, 325);
                g.setFont(new Font("黑体", Font.PLAIN, 40));
                g.drawString("现在你可以直接关闭网页了。", 10, 400);
            }
            g.setFont(f);
            g.setColor(cOld);
        }
    }

    public String getWinnerIp()
    {
        return winnerIp;
    }

    public void close()
    {
        // TODO Auto-generated method stub
        cmm.cleanUp();
    }

    public int getGameWidth()
    {
        return ct.getTableWidth();
    }

    public int getGameHeight()
    {
        int playerInfoHeight = Integer.parseInt(PropertyMgr
                .getProperty("playerInfoHeight"));
        return ct.getTableHeight() + playerInfoHeight * 2;
    }

    /**
     * 解析对方发送过来的消息 以'MSG:'开头，之后接着四位数字
     * 
     * 0000:连接成功，欢迎信息(服务器)。本地操作：开始游戏 0001:连接成功，确认信息(客户端)。本地操作：等待对方发送棋子移动()指令
     * 0000:棋子移动
     * 
     * @param oppositeMsg
     *            接收到的消息
     */
    public void parseOppositeMsg(String oppositeMsg)
    {
        Pattern p = Pattern.compile("MSG:((\\d\\d)(\\d)(\\d))");
        Matcher m = p.matcher(oppositeMsg);

        if (m.matches())
        {// 如果消息合法
            int chessValue = -1, newPosX = 0, newPosY = 0, codeValue = 0;
            chessValue = Integer.parseInt(m.group(2));
            newPosX = Integer.parseInt(m.group(3));
            newPosY = Integer.parseInt(m.group(4));
            codeValue = Integer.parseInt(m.group(1));

            switch (codeValue)
            {
            case 0:
                System.out.println("收到欢迎信息！(来自服务器)");
                this.startGame(false);
                oppositeInfo.yourTurn();
                break;
            case 1:
                System.out.println("收到确认信息！(来自客户端)");
                this.startGame(true);
                myInfo.yourTurn();
                break;
            case 2:
                System.out.println("游戏结束信息！");
                endingGame();
                break;
            case 3:
                System.out.println("游戏中断信息！");
                gameBreak();
                break;
            default:
                // TODO 解析象棋移动消息
                System.out.println("象棋移动信息（棋子id：" + chessValue + ", 到坐标：（"
                        + newPosX + ", " + newPosY + "））");
                String msgResult = cmm.parseMsg(chessValue, newPosX, newPosY);
                oppositeInfo.plusStep();
                myInfo.yourTurn();
                parseMoveResult(msgResult);
                break;
            }

        }
        else
        {
            System.out.println("接收到不合法信息:" + oppositeMsg);
        }
    }

    private void startGame(boolean isServer)
    {
        if (isServer)
        {
            cmm.initialServerGame();
        }
        else
        {
            cmm.initialClientGame();
        }
        nm = cc.getNetManager();
    }

    public void parseLBClicked(int x, int y)
    {
        // TODO Auto-generated method stub
        String clickResult = null;
        if (myInfo.isMyTurn())
        {
            clickResult = cmm.parseClicked(x, y);
            if (clickResult != null)
            {
                // 保留chessId + newPos，去除moveResult
                Pattern p = Pattern.compile("(\\d\\d\\d\\d):(.*)");
                Matcher m = p.matcher(clickResult);
                String moveResult = null;

                if (m.matches())
                {
                    clickResult = m.group(1);
                    moveResult = m.group(2);
                }

                myInfo.plusStep();
                nm.sendMsg("MSG:" + clickResult);
                System.out.println("发出消息\"MSG:" + clickResult + "\"");
                oppositeInfo.yourTurn();
                if (moveResult != null)
                {
                    parseMoveResult(moveResult);
                }
            }
        }
    }

    private void parseMoveResult(String msgResult)
    {
        // TODO Auto-generated method stub
        if (msgResult.equals("win"))
        {
            if (nm.isServer())
            {
                winnerIp = "myselfIp";// nm.getMyIp();
            }
            else
            {
                winnerIp = nm.getOppositeIp();
            }
            nm.sendMsg("MSG:0002");
            endingGame();
        }
        else if (msgResult.equals("lose"))
        {
            winnerIp = "";
            nm.sendMsg("MSG:0002");
            endingGame();
        }
        else if (msgResult.equals("weak"))
        {
            myInfo.decreaseChessMan();
        }
        else if (msgResult.equals("strong"))
        {
            oppositeInfo.decreaseChessMan();
        }
    }

    public void parseRBClicked(int x, int y)
    {
        // TODO Auto-generated method stub
        cmm.releaseChessMan();
    }

    private void endingGame()
    {
        // cmm.cleanUp();
        myInfo.close();
        oppositeInfo.close();
        // nm.close();
    }

    public void gameBreak()
    {
        // TODO Auto-generated method stub
        if (winnerIp == null)
        {
            gameBreaked = true;
            endingGame();
        }
        cc.getNetManager().close();
    }

}
