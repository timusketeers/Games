package com.entity;

import java.awt.Image;

import com.common.ConstantData;
import com.util.ImageUtil;

//�㶹��
public class SingleBulletPlant extends Plant
{

    public SingleBulletPlant()
    {
        plantImage = ImageUtil.loadImage("single_bullet_plant.png");
        bulletImage = ImageUtil.loadImage("bullet.gif");
        offset = 0;
        times = 0; // �ۼƻ��н�ʬ���ӵ���
    }

    public Image getPlantImage()
    {
        return plantImage;
    }

    public Image getBulletImage()
    {
        return bulletImage;
    }

    // �жϲ��������
    public void doCollision(int bulletX, int bulletY, int zombieX, int zombieY)
    {
        if (zombieX - bulletX > 0 && zombieX - bulletX < 10
                && bulletY - zombieY < ConstantData.Normal_Zombie_HEIGHT)
        {
            System.out.println("Collision occure" + "\n");
            times++;
        }
    }

}
