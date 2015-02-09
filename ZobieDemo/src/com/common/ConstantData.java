package com.common;

public interface ConstantData
{
    int WIDTH = 800;
    int HEIGHT = 600;

    int LEFT_OFFSET = 220;
    int SEED_OFFSET = 15;

    int ADD_SUN_OFFSET = 79;
    int ADD_SUN_COUNT_X_OFFSET = 16;
    int ADD_SUN_COUNT_y_OFFSET = 80;
    int TOP_OFFSET = 9;
    int CARD_WIDTH = 50;
    int CARD_HEIGHT = 70;
    int CARD_GAP_W = 2;
    int CARD_GAP_H = 1;

    int DEFAULT_FPS = 120; // 缺省间隔秒数

    int MAP_WEST_OFFSET = 32; // 画面左边距
    int MAP_TOP_OFFSET = 82; // 画面上边距

    int MAP_COL = 9;
    int MAP_ROW = 5;

    int MAP_RECT_WIDTH = 81; // 草坪每格宽度
    int MAP_RECT_HEIGHT = 98; // 草坪每格高度

    int PLANT_WIDTH = 75; // 植物宽度
    int PLANT_HEIGHT = 90; // 植物高度

    int Normal_Zombie_WIDTH = 90;
    int Normal_Zombie_HEIGHT = 130;

    PlantType[][] seedMap = new PlantType[][] {
            { PlantType.SingleBullet, PlantType.SunFlower, PlantType.Cherry,
                    PlantType.SmallStone, PlantType.Mine, PlantType.ColdBullet,
                    PlantType.Eat, PlantType.DoubBullet }, {} };
}
