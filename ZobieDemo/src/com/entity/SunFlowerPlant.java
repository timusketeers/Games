package com.entity;

import java.awt.Image;
import com.util.ImageUtil;

//œÚ»’ø˚¿‡
public class SunFlowerPlant extends Plant
{

    public SunFlowerPlant()
    {
        plantImage = ImageUtil.loadImage("sun_flower1.png");
    }

    public Image getPlantImage()
    {
        return plantImage;
    }

    public Image getBulletImage()
    {
        return null;
    }

    public void doCollision(int bulletX, int bulletY, int zombieX, int zombieY)
    {
    }

}
