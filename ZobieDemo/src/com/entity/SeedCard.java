package com.entity;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import com.common.ConstantData;
import com.common.PlantType;
import com.util.ImageUtil;

public class SeedCard implements ConstantData
{

    private float percent;

    private static Image allSeed = ImageUtil.loadImage("allseeds.png");
    private static Image allSeedDark = ImageUtil.loadImage("allseeds_dark.png");

    private Point pos;
    private Point coord;

    private PlantType plantType;

    public PlantType getPlantType()
    {
        return plantType;
    }

    private int freezingSeconds;
    private int count;

    public SeedCard(Point pos, Point coord)
    {
        this.pos = pos;
        this.coord = coord;
        plantType = seedMap[coord.y][coord.x]; // �õ���Ӧ��ֲ�����Ͷ���
        percent = .5f; // ����ֲ���б�ˢ�µĽ���

        initFreezingTime(plantType); // ����ÿ��ֲ�ﲻ����ʱ��
        count = 0;
    }

    public boolean mouseIn(int x, int y)
    {
        if ((x > pos.x) && (x < pos.x + CARD_WIDTH) && (y > pos.y) && (y < pos.y + CARD_HEIGHT))
        {
            return true;
        }
        return false;
    }

    // ����ֲ�ﶳ��ʱ��
    private void initFreezingTime(PlantType plantType)
    {
        freezingSeconds = 300; // freezingSecondsԽ����ˢ��Խ��
        switch (plantType)
        {
        case SunFlower:
            freezingSeconds = 5;
            break;
        case SingleBullet:
            freezingSeconds = 10;
            break;
        case Cherry:
            freezingSeconds = 30;
            break;
        case SmallStone:
            freezingSeconds = 20;
            break;
        case Mine:
            freezingSeconds = 100;
            break;
        case ColdBullet:
            freezingSeconds = 150;
            break;
        case Eat:
            freezingSeconds = 300;
            break;
        case DoubBullet:
            freezingSeconds = 500;
            break;
        }

    }

    public void reset()
    {
        count = 0;
    }

    // �����ӳ٣�����Ŀ���Դͼ����ʼ����Y�ĵ�����
    public void seedUpdate()
    {
        ++count;
        percent = ((float) count) / (freezingSeconds * DEFAULT_FPS);
        // System.out.println("percent: "+percent);
        if (count >= freezingSeconds * DEFAULT_FPS)
        {
            count = 0;
        }
    }

    // public int topH=0;
    public void draw(Graphics g)
    {
        int picX = CARD_WIDTH * coord.x + CARD_GAP_W * coord.x; // �õ�Դͼ����ʼ����X
        int picY = CARD_HEIGHT * coord.y + CARD_GAP_H * coord.y; // �õ�Դͼ����ʼ����Y
        int topH = (int) (CARD_HEIGHT * percent);
        /*
         * if(topH<CARD_HEIGHT) topH+=1; else topH=0;
         */
        // System.out.println("topH: "+topH);
        g.drawImage(allSeed, pos.x, pos.y, pos.x + CARD_WIDTH, pos.y + topH,
                picX, picY, picX + CARD_WIDTH, picY + topH, null);
        /*
         * System.out.println("seed: "+ pos.x + " " + pos.y+ " " + (pos.x +
         * CARD_WIDTH)+ " " + (pos.y + topH)+ " " + picX+ " " +picY+ " " + (picX
         * + CARD_WIDTH)+ " " + (picY + topH)+"        "+percent);
         */
        g.drawImage(allSeedDark, pos.x, pos.y + topH, pos.x + CARD_WIDTH, pos.y
                + CARD_HEIGHT, picX, picY + topH, picX + CARD_WIDTH, picY
                + CARD_HEIGHT, null);
    }

}
