package com.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

import com.common.ConstantData;
import com.common.PlantType;
import com.util.ImageUtil;
import com.entity.*;

public class PlantsBar implements ConstantData
{
    private int plantSum;
    private Image seedBank;
    private SeedCard[] cards;
    private int lights;
    private Font lightFont;

    // 创建植物种子列表
    public PlantsBar()
    {
        seedBank = ImageUtil.loadImage("SeedBank.png");
        // allSeedImg = ImageUtil.loadImage("allseeds.png");
        plantSum = 7;
        cards = new SeedCard[plantSum];
        for (int i = 0; i < plantSum; ++i)
        {
            cards[i] = new SeedCard(new Point(SEED_OFFSET + ADD_SUN_OFFSET
                    + (CARD_WIDTH + CARD_GAP_W) * i, TOP_OFFSET), new Point(i,
                    0));
        }
        // 设置阳光值的显示字体和最大值
        lightFont = new Font(Font.DIALOG, Font.BOLD, 20);
        lights = 9999;
    }

    public void seedUpdate()
    {
        for (SeedCard sc : cards)
        {
            sc.seedUpdate();
        }
    }

    // 获取所选植物类型
    public PlantType selectedPlant(Point pos)
    {
        if ((pos.x > SEED_OFFSET + ADD_SUN_OFFSET)
                && (pos.x < SEED_OFFSET + ADD_SUN_OFFSET + seedBank.getWidth(null)) 
                && (pos.y > TOP_OFFSET)
                && (pos.y < TOP_OFFSET + seedBank.getHeight(null)))
        {
            for (SeedCard sc : cards)
            {
                if (sc.mouseIn(pos.x, pos.y))
                {
                    return sc.getPlantType();
                }
            }
            return PlantType.NONE;
        }
        else
        {
            return PlantType.NONE;
        }
    }

    public void draw(Graphics g)
    {
        g.drawImage(seedBank, ConstantData.SEED_OFFSET, 0, null);
        for (SeedCard sc : cards)
        {
            sc.draw(g);
        }

        // cards[0].draw(g);
        // cards[1].draw(g);
        // 绘制阳光值
        g.setColor(Color.black);
        g.setFont(lightFont);
        g.drawString(String.valueOf(lights), 
                SEED_OFFSET + ADD_SUN_COUNT_X_OFFSET, 
                ADD_SUN_COUNT_y_OFFSET);

    }
}
