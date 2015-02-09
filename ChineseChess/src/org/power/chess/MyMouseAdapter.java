package org.power.chess;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyMouseAdapter extends MouseAdapter
{
    private GameManager gm = null;
    private int xOffset = 0;
    private int yOffset = 0;

    public MyMouseAdapter(GameManager gm)
    {
        this.gm = gm;
    }

    public MyMouseAdapter(GameManager gm, int xOffset, int yOffset)
    {
        this.gm = gm;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        // TODO Auto-generated method stub
        super.mouseReleased(e);
        System.out.println("鼠标按键：");
        if (e.getButton() == MouseEvent.BUTTON1)
        {
            System.out.println("左键：（" + e.getX() + "，" + e.getY() + "）");
            gm.parseLBClicked(e.getX() + xOffset, e.getY() + yOffset);
        }
        else if (e.getButton() == MouseEvent.BUTTON3)
        {
            System.out.println("右键：（" + e.getX() + "，" + e.getY() + "）");
            gm.parseRBClicked(e.getX() + xOffset, e.getY() + yOffset);
        }
    }

}