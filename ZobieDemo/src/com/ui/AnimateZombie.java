package com.ui;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import com.util.ImageUtil;
import com.common.ConstantData;
import com.entity.Zombie;

public class AnimateZombie implements ConstantData
{
    List<Zombie> zombieList = null;
    private int currentZombie;
    private int counter;
    private static final int FPS_PER_PIXEL = 10;
    public int posX, posY;
    public int isVisible;

    public AnimateZombie(int x, int y, int visible)
    {
        zombieList = new ArrayList<Zombie>();
        zombieList.add(new Zombie(ImageUtil.loadImage("Normal_Zombie_1.png"),
                (int) (0.5f * DEFAULT_FPS)));
        zombieList.add(new Zombie(ImageUtil.loadImage("Normal_Zombie_2.png"),
                (int) (0.5f * DEFAULT_FPS)));
        zombieList.add(new Zombie(ImageUtil.loadImage("Normal_Zombie_3.png"),
                (int) (0.5f * DEFAULT_FPS)));
        zombieList.add(new Zombie(ImageUtil.loadImage("Normal_Zombie_4.png"),
                (int) (0.5f * DEFAULT_FPS)));

        currentZombie = 0;
        counter = 0;
        posX = x;
        posY = y;
        isVisible = visible;

    }

    // �����ƶ�����,�ı���λ��
    public void updateLocation()
    {
        if (isOffScreen())
        {
            return;
        }
        currentZombie++;
        if (currentZombie > zombieList.size())
        {
            currentZombie = 0;
        }
        counter++;
        if (counter > FPS_PER_PIXEL)
        {
            posX -= 1;
            counter = 0;
        }

    }

    // ����ͼ��
    public void draw(Graphics g)
    {
        if (isOffScreen())
        {
            return;
        }
        if (currentZombie < zombieList.size())
        {
            if (zombieList.get(currentZombie) != null)
                g.drawImage(zombieList.get(currentZombie).img, posX, posY, null);
        }
        else
        {
            if (zombieList.get(0) != null)
                g.drawImage(zombieList.get(0).img, posX, posY, null);
        }
    }

    // �Ƿ����Ƴ���Ļ
    public boolean isOffScreen()
    {
        if (posX + Normal_Zombie_WIDTH < 0)
        {
            return true;
        }
        return false;
    }
}
