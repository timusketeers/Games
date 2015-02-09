package org.power.chess;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.text.DecimalFormat;
import java.util.List;

public class ChessMan
{
    private static int sid = 1;
    private static int[] xSlot = new int[] { 40, 100, 160, 220, 280, 340, 400,
            460, 520 };
    private static int[] ySlot = new int[] { 45, 105, 165, 225, 285, 345, 405,
            465, 525, 585 };
    private static final int WIDTH = Integer.parseInt(PropertyMgr
            .getProperty("ChessManSize"));
    private static final int HEIGHT = Integer.parseInt(PropertyMgr
            .getProperty("ChessManSize"));
    private static Stroke bs = new BasicStroke(5);
    private static Stroke bs2 = new BasicStroke(2);

    private Image faceImage = null;
    private int xCentral = 0, x = 0, xCoordinatePos = 0;
    private int yCentral = 0, y = 0, yCoordinatePos = 0;
    private boolean belongToPlayer = true;
    private int id = 0;
    private boolean selected = false;
    private ChessManType cmt = null;
    private static List<ChessMan> chessMans = ChessManManager.getChessMans();

    public ChessMan(String imgName, int i, int j, ChessManType cmt,
            boolean belongToPlayer)
    {
        faceImage = GameManager.imgs.get(imgName);
        xCoordinatePos = i;
        yCoordinatePos = j;
        xCentral = xSlot[i] + ChessManManager.xTableOffset;
        yCentral = ySlot[j] + ChessManManager.yTableOffset;
        x = xCentral - WIDTH / 2;
        y = yCentral - HEIGHT / 2;
        this.belongToPlayer = belongToPlayer;
        this.id = sid++;
        this.cmt = cmt;
    }

    public boolean isBelongToPlayer()
    {
        return belongToPlayer;
    }

    public int getxCoordinatePos()
    {
        return xCoordinatePos;
    }

    public int getyCoordinatePos()
    {
        return yCoordinatePos;
    }

    public ChessManType getChessManType()
    {
        return cmt;
    }

    public String getId()
    {
        java.text.DecimalFormat format = new DecimalFormat("00");
        return format.format(id);
    }

    public boolean isSelected()
    {
        return selected;
    }

    public ChessMan pickUp()
    {
        selected = true;
        return this;
    }

    public void dropDown()
    {
        selected = false;
    }

    public void draw(Graphics g)
    {
        g.drawImage(faceImage, x, y, WIDTH, HEIGHT, null);
    }

    public void drawFrame(Graphics g, Color c)
    {
        Graphics2D g2 = (Graphics2D) g;

        Color oldColor = g.getColor();
        g.setColor(c);

        Stroke oldStroke = g2.getStroke();
        g2.setStroke(bs);

        g.drawRect(x, y, WIDTH, HEIGHT);

        g.setColor(oldColor);
        g2.setStroke(oldStroke);
    }

    public boolean isClicked(int xClicked, int yClicked)
    {
        if (Math.abs(xClicked - xCentral) < WIDTH / 2
                && Math.abs(yClicked - yCentral) < HEIGHT / 2)
        {
            return true;
        }
        return false;
    }

    public boolean canGoto(int xClicked, int yClicked, List<ChessMan> chessMans)
    {
        // TODO Auto-generated method stub
        /*
         * if (this.chessMans == null) { this.chessMans = chessMans; }
         */

        int newXCoordinatePos = toXCoordinatePos(xClicked);
        int newYCoordinatePos = toYCoordinatePos(yClicked);

        if (newXCoordinatePos >= 0 && newYCoordinatePos >= 0)
        {
            if (isOurChessMan(newXCoordinatePos, newYCoordinatePos))
                return false;// 不能吃自己人

            int xCoordinateOffset = newXCoordinatePos - xCoordinatePos;
            int yCoordinateOffset = newYCoordinatePos - yCoordinatePos;

            switch (cmt)
            {
            case CANNON:
                if (isOppositeChessMan(newXCoordinatePos, newYCoordinatePos))
                {// 如果有敵人則需要跳過一個棋子才可以吃
                    if (checkCannonPath(xCoordinatePos, yCoordinatePos,
                            xCoordinateOffset, yCoordinateOffset))
                    {
                        return true;
                    }
                }
                else
                {// 不吃的話檢查能否移動到目的地
                    if (checkCarPath(xCoordinatePos, yCoordinatePos,
                            xCoordinateOffset, yCoordinateOffset))
                    {
                        return true;
                    }
                }
                break;
            case CAR:
                if (checkCarPath(xCoordinatePos, yCoordinatePos,
                        xCoordinateOffset, yCoordinateOffset))
                {// 檢查能否移動到目的地
                    return true;
                }
                break;
            case ELEPHANT:
                if (Math.abs(xCoordinateOffset) != 2
                        || Math.abs(yCoordinateOffset) != 2)
                    return false;// 象只能田字移動，不能過河
                if (isCrossedRiver(newYCoordinatePos))
                    return false;
                if (checkElephantPath(xCoordinatePos, yCoordinatePos,
                        xCoordinateOffset, yCoordinateOffset))
                {
                    return true;
                }
                break;
            case GENERAL:
                if (isOppositeGeneral(newXCoordinatePos, newYCoordinatePos))
                {// 將殺
                    if (checkCarPath(xCoordinatePos, yCoordinatePos,
                            xCoordinateOffset, yCoordinateOffset))
                    {
                        return true;
                    }
                }
                if (willExitOurTent(newXCoordinatePos, newYCoordinatePos))
                    return false;
                if (checkOneStep(xCoordinateOffset, yCoordinateOffset))
                {
                    return true;
                }
                else if (checkGeneralSlantStep(xCoordinatePos, yCoordinatePos,
                        xCoordinateOffset, yCoordinateOffset))
                {
                    return true;
                }
                break;
            case GUARD:
                if (willExitOurTent(newXCoordinatePos, newYCoordinatePos))
                {
                    return false;
                }
                if (checkOneSlantStep(xCoordinateOffset, yCoordinateOffset))
                {
                    return true;
                }
                break;
            case HORSE:
                if ((Math.abs(xCoordinateOffset) == 1 && Math
                        .abs(yCoordinateOffset) == 2)
                        || (Math.abs(xCoordinateOffset) == 2 && Math
                                .abs(yCoordinateOffset) == 1))
                {
                    if (checkHorsePath(xCoordinatePos, yCoordinatePos,
                            xCoordinateOffset, yCoordinateOffset))
                    {
                        return true;
                    }
                }
                break;
            case SOLDIER:
                if (isCrossedRiver(yCoordinatePos))
                {
                    if (checkSoldierOneStep(xCoordinatePos, yCoordinatePos,
                            xCoordinateOffset, yCoordinateOffset, true))
                    {
                        return true;
                    }
                }
                else
                {
                    if (checkSoldierOneStep(xCoordinatePos, yCoordinatePos,
                            xCoordinateOffset, yCoordinateOffset, false))
                    {
                        return true;
                    }
                }
                break;
            default:
                break;
            }
        }
        return false;
    }

    private boolean checkGeneralSlantStep(int xCoordinatePos,
            int yCoordinatePos, int xCoordinateOffset, int yCoordinateOffset)
    {
        return Math.abs(xCoordinatePos - 4) + Math.abs(yCoordinatePos - 8) != 1
                && checkOneSlantStep(xCoordinateOffset, yCoordinateOffset);
    }

    private boolean isCrossedRiver(int yCoordinatePos)
    {
        return yCoordinatePos <= 4;
    }

    private boolean checkSoldierOneStep(int xCoordinatePos, int yCoordinatePos,
            int xCoordinateOffset, int yCoordinateOffset, boolean isCrossedRiver)
    {
        // TODO Auto-generated method stub
        if (yCoordinateOffset > 0)
            return false;// 不能後退

        if (Math.abs(xCoordinateOffset) + Math.abs(yCoordinateOffset) > 1)
            return false;// 不能左移/右移1步以上
        if (!isCrossedRiver)
        {
            if (xCoordinateOffset != 0 && yCoordinateOffset != -1)
                return false;// 只能前進1步
        }
        return true;
    }

    private boolean checkHorsePath(int xCoordinatePos, int yCoordinatePos,
            int xCoordinateOffset, int yCoordinateOffset)
    {
        // TODO Auto-generated method stub
        if (Math.abs(xCoordinateOffset) == 2)
        {
            ChessMan cm = getChessManByCoordinatePos(xCoordinatePos
                    + (int) Math.signum(xCoordinateOffset), yCoordinatePos);
            if (cm != null)
                return false;
        }
        else if (Math.abs(yCoordinateOffset) == 2)
        {
            ChessMan cm = getChessManByCoordinatePos(xCoordinatePos,
                    yCoordinatePos + (int) Math.signum(yCoordinateOffset));
            if (cm != null)
                return false;
        }
        else
        {
            return false;
        }
        return true;
    }

    private ChessMan getChessManByCoordinatePos(int xCoordinatePos,
            int yCoordinatePos)
    {
        for (int i = 0; i < chessMans.size(); i++)
        {
            ChessMan cm = chessMans.get(i);
            if (cm.getxCoordinatePos() == xCoordinatePos
                    && cm.getyCoordinatePos() == yCoordinatePos)
            {
                return cm;
            }
        }
        return null;
    }

    private boolean isOppositeGeneral(int newXCoordinatePos,
            int newYCoordinatePos)
    {
        // TODO Auto-generated method stub
        ChessMan cm = getChessManByCoordinatePos(newXCoordinatePos,
                newYCoordinatePos);
        if (cm != null && !cm.isBelongToPlayer()
                && cm.getChessManType() == ChessManType.GENERAL)
        {
            return true;
        }
        return false;
    }

    private boolean checkOneSlantStep(int xCoordinateOffset,
            int yCoordinateOffset)
    {
        // TODO Auto-generated method stub
        return Math.abs(xCoordinateOffset) == 1
                && Math.abs(yCoordinateOffset) == 1;
    }

    private boolean checkOneStep(int xCoordinateOffset, int yCoordinateOffset)
    {
        // TODO Auto-generated method stub
        return Math.abs(xCoordinateOffset) == 1
                && Math.abs(yCoordinateOffset) == 0
                || Math.abs(xCoordinateOffset) == 0
                && Math.abs(yCoordinateOffset) == 1;
    }

    private boolean willExitOurTent(int newXCoordinatePos, int newYCoordinatePos)
    {
        // TODO Auto-generated method stub
        if (newXCoordinatePos < 3 || newXCoordinatePos > 5
                || newYCoordinatePos < 7)
        {
            return true;
        }
        return false;
    }

    private boolean checkElephantPath(int xCoordinatePos, int yCoordinatePos,
            int xCoordinateOffset, int yCoordinateOffset)
    {
        // TODO Auto-generated method stub
        ChessMan cm = getChessManByCoordinatePos(xCoordinatePos
                + xCoordinateOffset / 2, yCoordinatePos + yCoordinateOffset / 2);
        if (cm != null)
        {
            return false;
        }
        return true;
    }

    private boolean isOurChessMan(int newXCoordinatePos, int newYCoordinatePos)
    {
        // TODO Auto-generated method stub
        ChessMan cm = getChessManByCoordinatePos(newXCoordinatePos,
                newYCoordinatePos);
        if (cm == null)
        {
            return false;
        }
        return cm.isBelongToPlayer();
    }

    private boolean checkCarPath(int xCoordinatePos, int yCoordinatePos,
            int xCoordinateOffset, int yCoordinateOffset)
    {
        // TODO Auto-generated method stub
        if (xCoordinateOffset != 0 && yCoordinateOffset != 0)
            return false;// 車不能打斜移動
        ChessMan cm = null;
        if (xCoordinateOffset != 0)
        {// 水平移动的车
            int signNum = (int) Math.signum(xCoordinateOffset);
            for (int i = signNum; i != xCoordinateOffset; i += signNum)
            {
                cm = getChessManByCoordinatePos(xCoordinatePos + i,
                        yCoordinatePos);
                if (cm != null)
                {
                    return false;
                }
            }
        }
        else
        {
            int signNum = (int) Math.signum(yCoordinateOffset);
            for (int i = signNum; i != yCoordinateOffset; i += signNum)
            {
                cm = getChessManByCoordinatePos(xCoordinatePos, yCoordinatePos
                        + i);
                if (cm != null)
                {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkCannonPath(int xCoordinatePos, int yCoordinatePos,
            int xCoordinateOffset, int yCoordinateOffset)
    {
        // TODO Auto-generated method stub
        if (xCoordinateOffset != 0 && yCoordinateOffset != 0)
            return false;// 炮不能打斜移動

        ChessMan cm = null;
        int chessManCount = 0;
        if (xCoordinateOffset != 0)
        {// 水平移动的车
            int signNum = (int) Math.signum(xCoordinateOffset);
            for (int i = signNum; i != xCoordinateOffset; i += signNum)
            {
                cm = getChessManByCoordinatePos(xCoordinatePos + i,
                        yCoordinatePos);
                if (cm != null)
                {
                    chessManCount++;
                    if (chessManCount >= 2)
                    {
                        return false;
                    }
                }
            }
        }
        else
        {
            int signNum = (int) Math.signum(yCoordinateOffset);
            for (int i = signNum; i != yCoordinateOffset; i += signNum)
            {
                cm = getChessManByCoordinatePos(xCoordinatePos, yCoordinatePos
                        + i);
                if (cm != null)
                {
                    chessManCount++;
                    if (chessManCount >= 2)
                    {
                        return false;
                    }
                }
            }
        }
        if (chessManCount == 1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private boolean isOppositeChessMan(int newXCoordinatePos,
            int newYCoordinatePos)
    {
        // TODO Auto-generated method stub
        ChessMan cm = getChessManByCoordinatePos(newXCoordinatePos,
                newYCoordinatePos);
        if (cm != null && !cm.isBelongToPlayer())
        {
            return true;
        }
        return false;
    }

    private int toXCoordinatePos(int xClicked)
    {
        for (int i = 0; i < xSlot.length; i++)
        {
            if (Math.abs(xClicked - xSlot[i]) < 30)
                return i;
        }
        return -1;
    }

    private int toYCoordinatePos(int yClicked)
    {
        for (int i = 0; i < ySlot.length; i++)
        {
            if (Math.abs(yClicked - ySlot[i]) < 30)
                return i;
        }
        return -1;
    }

    public String moveAdaptSlot(int xClicked, int yClicked)
    {// 移动或吃掉
        // TODO Auto-generated method stub
        int newXCoordinatePos = toXCoordinatePos(xClicked);
        int newYCoordinatePos = toYCoordinatePos(yClicked);

        String movResult = moveToSlotPos(newXCoordinatePos, newYCoordinatePos);

        return String.format("%d%d:%s", newXCoordinatePos, newYCoordinatePos,
                movResult);
    }

    private String removeChessMan(ChessMan cm)
    {
        // TODO Auto-generated method stub
        chessMans.remove(cm);
        if (cm.getChessManType() == ChessManType.GENERAL)
        {
            if (cm.isBelongToPlayer())
            {
                // lose
                return "lose";
            }
            else
            {
                // win
                return "win";
            }
        }
        else
        {
            if (cm.isBelongToPlayer())
            {
                // weak
                return "weak";
            }
            else
            {
                // strong
                return "strong";
            }
        }
    }

    public String moveToSlotPos(int newPosX, int newPosY)
    {// 移动或吃掉
        // TODO Auto-generated method stub
        ChessMan cm = getChessManByCoordinatePos(newPosX, newPosY);
        String moveResult = null;
        if (cm != null)
        {
            moveResult = removeChessMan(cm);// 吃掉
        }
        else
        {
            moveResult = "move";
        }

        this.xCoordinatePos = newPosX;
        this.yCoordinatePos = newPosY;

        xCentral = xSlot[newPosX] + ChessManManager.xTableOffset;
        yCentral = ySlot[newPosY] + ChessManManager.yTableOffset;
        x = xCentral - WIDTH / 2;
        y = yCentral - HEIGHT / 2;

        return moveResult;
    }

    public void drawCanGo(Graphics g, Color c)
    {
        Graphics2D g2 = (Graphics2D) g;

        Color oldColor = g.getColor();
        g.setColor(c);

        Stroke oldStroke = g2.getStroke();
        g2.setStroke(bs2);

        for (int i = 0; i < xSlot.length; i++)
        {
            for (int j = 0; j < ySlot.length; j++)
            {
                if (canGoto(xSlot[i], ySlot[j], chessMans))
                {
                    g.drawRect(xSlot[i] - WIDTH / 2
                            + ChessManManager.xTableOffset, ySlot[j] - HEIGHT
                            / 2 + ChessManManager.yTableOffset, WIDTH, HEIGHT);
                }
            }
        }

        g.setColor(oldColor);
        g2.setStroke(oldStroke);
    }
}
