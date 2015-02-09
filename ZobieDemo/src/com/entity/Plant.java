package com.entity;

import java.awt.Image;

public abstract class Plant
{
    public Image plantImage;
    public Image bulletImage;
    public int offset;
    public int times;

    public abstract Image getPlantImage();

    public abstract Image getBulletImage();

    public abstract void doCollision(int bulletX, int bulletY, int zombieX, int zombieY);

}
