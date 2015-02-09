package org.power.chess;

import java.awt.Graphics;
import java.awt.Image;

public class ChessTable
{
    private int x = 0, y = 0;
    private int tableWidth = 0;
    private int tableHeight = 0;
    Image chessTable = null;

    public ChessTable(int x, int y, int tableWidth, int tableHeight,
            Image chessTable)
    {
        this.x = x;
        this.y = y;
        this.tableWidth = tableWidth;
        this.tableHeight = tableHeight;
        this.chessTable = chessTable;
    }

    public int getTableWidth()
    {
        return tableWidth;
    }

    public int getTableHeight()
    {
        return tableHeight;
    }

    public void draw(Graphics g)
    {
        g.drawImage(chessTable, x, y, null);// 10PX for opposite info
    }

}
