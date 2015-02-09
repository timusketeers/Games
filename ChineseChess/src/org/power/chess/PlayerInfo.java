package org.power.chess;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.text.DecimalFormat;

public class PlayerInfo
{
    private Timer t = null;
    private int x = 0, y = 0, width = 0, height = 0;
    private Color fontColor = null;
    private int piecesOwn = 16, moveSteps = 0;
    private PlayerInfo oppositeInfo = null;

    public PlayerInfo(int x, int y, int width, int height, Color c,
            PlayerInfo oppositeInfo)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        fontColor = c;
        this.oppositeInfo = oppositeInfo;
        t = new Timer();
        new Thread(t).start();
    }

    public void decreaseChessMan()
    {
        piecesOwn--;
    }

    public void plusStep()
    {
        moveSteps++;
    }

    public boolean isMyTurn()
    {
        return t.isCounting();
    }

    public void yourTurn()
    {
        oppositeInfo.stopClock();
        startClock();
    }

    public void setOppositeInfo(PlayerInfo oppositeInfo)
    {
        this.oppositeInfo = oppositeInfo;
    }

    public void draw(Graphics g)
    {
        Color oldC = g.getColor();
        Font oldF = g.getFont();

        g.setColor(fontColor);
        g.setFont(new Font("宋体", Font.BOLD, 15));
        java.text.DecimalFormat format = new DecimalFormat("00");
        if (t.isCounting())
        {
            g.drawString(
                    String.format(
                            "使用时间：" + format.format(t.getSecondEclise() / 60)
                                    + ":"
                                    + format.format(t.getSecondEclise() % 60)
                                    + "    剩余棋子：%d    所走步数：%d    现在轮到%s了...",
                            piecesOwn, moveSteps, fontColor == Color.BLUE ? "你"
                                    : "对方"), x, y);
        }
        else
        {
            g.drawString(String.format(
                    "使用时间：" + format.format(t.getSecondEclise() / 60) + ":"
                            + format.format(t.getSecondEclise() % 60)
                            + "    剩余棋子：%d    所走步数：%d", piecesOwn, moveSteps),
                    x, y);
        }

        g.setFont(oldF);
        g.setColor(oldC);
    }

    public void startClock()
    {
        t.start();
    }

    public void stopClock()
    {
        t.stop();
    }

    public void close()
    {
        t.stop();
        t.close();
    }

    private class Timer implements Runnable
    {
        private int secondEclise = 0;
        private boolean counting = false;
        private boolean keepRunning = true;

        @Override
        public void run()
        {
            while (keepRunning)
            {
                try
                {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                if (counting)
                {
                    secondEclise++;
                }
            }
        }

        public void close()
        {
            stop();
            keepRunning = false;
        }

        public void stop()
        {
            if (counting)
            {
                counting = false;
                secondEclise++;
            }
        }

        public void start()
        {
            counting = true;
        }

        public int getSecondEclise()
        {
            return secondEclise;
        }

        public boolean isCounting()
        {
            return counting;
        }

    }
}
