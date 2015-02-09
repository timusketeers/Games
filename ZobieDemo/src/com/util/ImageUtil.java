package com.util;

import java.awt.Image;
import java.util.HashMap;

import javax.swing.ImageIcon;

public class ImageUtil
{
    private static HashMap<String, Image> imageMap = new HashMap<String, Image>();

    public static Image loadImage(String name)
    {
        if (!imageMap.containsKey(name))
        {
            ImageIcon icon = new ImageIcon("image/" + name);
            imageMap.put(name, icon.getImage());
        }
        return imageMap.get(name);
    }
}
