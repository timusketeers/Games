package com.ui;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.common.ConstantData;
import com.common.PlantType;
import com.entity.Plant;
import com.entity.SingleBulletPlant;
import com.entity.SunFlowerPlant;

public class PlantsMap implements ConstantData
{
    private Plant[][] plantsMap;
    private int bulletX, bulletY;

    // private AnimateZombie animaZombie;
    public List<AnimateZombie> animaZombieList;
    public HashMap<Integer, AnimateZombie> animaZombieList_1;

    public PlantsMap()
    {
        plantsMap = new Plant[MAP_ROW][MAP_COL];

        // animaZombie = new AnimateZombie(740, 430);
        animaZombieList = new ArrayList<AnimateZombie>();
        animaZombieList.add(new AnimateZombie(740, 30, 0));
        animaZombieList.add(new AnimateZombie(800, 130, 0));
        animaZombieList.add(new AnimateZombie(860, 230, 0));
        animaZombieList.add(new AnimateZombie(920, 330, 0));
        animaZombieList.add(new AnimateZombie(980, 430, 0));

        bulletX = 0;
        bulletY = 0;
    }

    public void updateLocation(AnimateZombie animaZombie)
    {
        animaZombie.updateLocation();
    }

    /*
     * public void updateLocation() { animaZombie.updateLocation(); }
     */
    // 绘制所种植物
    public void drawPlant(Graphics g)
    {
        for (int i = 0; i < MAP_COL; ++i)
        {
            for (int j = 0; j < MAP_ROW; ++j)
            {
                int x = MAP_WEST_OFFSET + i * MAP_RECT_WIDTH;
                int y = MAP_TOP_OFFSET + j * MAP_RECT_HEIGHT;

                Plant p = plantsMap[j][i];
                if (p != null)
                {
                    Image img = p.getPlantImage();
                    Image bulletImg = p.getBulletImage();
                    g.drawImage(img, x + 3, y + 3, null);
                    if (p.offset < (MAP_WEST_OFFSET + MAP_COL * MAP_RECT_WIDTH))
                        p.offset = p.offset + 10;
                    else
                        p.offset = 0;
                    g.drawImage(bulletImg, x + 55 + p.offset, y + 25, null);
                    bulletX = x + 55 + p.offset;
                    bulletY = y + 25;
                    // 当对应行的僵尸存在，且子弹与僵尸在Y方向距离小于一个僵尸高度，判断该僵尸已出现时是否被击中
                    if (animaZombieList.get(j) != null)
                    {
                        int zombieY = animaZombieList.get(j).posY;
                        int zombieX = animaZombieList.get(j).posX;
                        if (bulletY - zombieY < ConstantData.Normal_Zombie_HEIGHT
                                && animaZombieList.get(j).isVisible == 1)
                        {
                            p.doCollision(bulletX, bulletY, zombieX, zombieY);
                            if (p.times > 1)
                            {
                                animaZombieList.set(j, null); // 被子弹击中次数达到某个值，僵尸死亡
                            }
                        }
                    }
                }
            }
        }
    }

    // 画出僵尸
    public void drawZombie(Graphics g, AnimateZombie animaZombie)
    {
        animaZombie.draw(g);
    }

    // 坐标是否在当前草坪范围内
    public boolean inTheMap(int posX, int posY)
    {
        if ((posX > MAP_WEST_OFFSET)
                && (posX < MAP_WEST_OFFSET + MAP_COL * MAP_RECT_WIDTH)
                && (posY > MAP_TOP_OFFSET)
                && (posY < MAP_TOP_OFFSET + MAP_ROW * MAP_RECT_HEIGHT))
        {
            return true;
        }
        return false;
    }

    // 将所选植物种入相应的草坪块中
    public void putPlantInMap(PlantType pt, int posX, int posY)
    {
        Plant p = null;
        switch (pt)
        {
        case SunFlower:
            p = new SunFlowerPlant();
            break;
        case SingleBullet:
            p = new SingleBulletPlant();
            break;
        }
        if (p != null)
        {
            int col = (posX - MAP_WEST_OFFSET) / MAP_RECT_WIDTH; // 得到列
            int row = (posY - MAP_TOP_OFFSET) / MAP_RECT_HEIGHT; // 得到行
            plantsMap[row][col] = p;
        }
    }
}
