package com.common;

import java.awt.Image;

import com.util.ImageUtil;

public enum PlantType
{
    NONE(null), 
    
    /** 调用了PlantType的构造方法，此时moveImg是sun_flower1.png **/
    SunFlower(ImageUtil.loadImage("sun_flower1.png")),
    
    /** 调用了PlantType的构造方法，此时moveImg是single_bullet_plant.png **/
    SingleBullet(ImageUtil.loadImage("single_bullet_plant.png")), 
    Cherry(null), 
    
    SmallStone(null), 
    
    Mine(null), 
    
    ColdBullet(null), 
    
    Eat(null), 
    
    DoubBullet(null);

    private Image plantImg;

    PlantType(Image plantImg)
    {
        this.plantImg = plantImg;
    }

    public Image getPlantImg()
    {
        return plantImg;
    }
}
