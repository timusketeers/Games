package com.ui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.*;

import com.common.ConstantData;
import com.common.PlantType;
import com.util.ImageUtil;

public class GamePlay extends JPanel implements MouseListener,
        MouseMotionListener, Runnable
{
    private static final long serialVersionUID = 1L;
    private PlantsBar plantBar;
    private PlantsMap plantMap;
    private Image grasslandImage;
    private Thread gameThread;
    private Graphics graphic;
    private Image gameImage = null;
    private long nowTime = 0;
    private long beginTime = 0;
    private int mouseX;
    private int mouseY;

    private PlantType selectedPlantType = null;

    public GamePlay()
    {
        super();
        grasslandImage = ImageUtil.loadImage("background1.jpg");
        plantBar = new PlantsBar();
        plantMap = new PlantsMap();
        selectedPlantType = PlantType.NONE;
        nowTime = System.currentTimeMillis();

        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        gameThread = new Thread(this);
    }

    public void startGame()
    {
        gameThread.start();
    }

    public void run()
    {
        while (true)
        {
            // 更新植物列表状态,草地状态
            sceneUpdate();
            // 创建画布,显示画布,显示植物列表,草地
            gameRender();
            paintScreen();

            try
            {
                Thread.sleep(200); // already in ms
            }
            catch (InterruptedException ex)
            {
            }
            int j = 15;
            while (j-- > 0)
            {
                sceneUpdate();
            }
        }

    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
    }

    // 画面更新(位置变化形成动画效果)
    public void sceneUpdate()
    {
        plantBar.seedUpdate();
        // plantMap.updateLocation();
        // 使用循环更新每个僵尸的位置
        if (plantMap.animaZombieList.get(0) != null)
            plantMap.updateLocation(plantMap.animaZombieList.get(0));
        if (plantMap.animaZombieList.get(1) != null)
            plantMap.updateLocation(plantMap.animaZombieList.get(1));

    }

    // 画界面上的游戏内容
    public void gameRender()
    {
        if (gameImage == null)
        {
            gameImage = this.createImage(this.getWidth(), this.getHeight()); // 创建panel中IMAGE对象（小画布）
            if (gameImage == null)
            {
                System.out.println("gameImage is null");
                return;
            }
            else
            {
                graphic = gameImage.getGraphics(); // 获得一个gameImage的Graphics上下文，用于画图
            }

        }
        // 显示gameImage上的图像
        graphic.drawImage(grasslandImage, 0, 0, getWidth(), getHeight(),
                ConstantData.LEFT_OFFSET, 0, ConstantData.LEFT_OFFSET
                        + getWidth(), getHeight(), null);
        plantBar.draw(graphic); // 画植物列表
        drawMoveImage(graphic); // 绘制所移动的植物
        plantMap.drawPlant(graphic);
        beginTime = System.currentTimeMillis();
        if (beginTime - nowTime > 3000)
        {
            if (plantMap.animaZombieList.get(0) != null)
            {
                plantMap.drawZombie(graphic, plantMap.animaZombieList.get(0));
                plantMap.animaZombieList.get(0).isVisible = 1;
            }
        }
        // 延迟7秒后出现第二行僵尸
        if (beginTime - nowTime > 10000)
        {
            if (plantMap.animaZombieList.get(1) != null)
            {
                plantMap.drawZombie(graphic, plantMap.animaZombieList.get(1));
                plantMap.animaZombieList.get(1).isVisible = 1;
            }
        }
        // ....加大延迟画出所有僵尸，也可用循环

    }

    // 将界面内容显示于屏幕上
    public void paintScreen()
    {
        Graphics g;
        try
        {
            g = this.getGraphics(); // 获得panel的Graphics上下文，用于画图
            if (g != null && gameImage != null)
            {
                g.drawImage(gameImage, 0, 0, null); // 在屏幕上显示panel的图像
            }
            Toolkit.getDefaultToolkit().sync(); // sync the display on some
                                                // systems Synchronizes this
                                                // toolkit's graphics state.
                                                // Some window systems may do
                                                // buffering of graphics events.
            g.dispose();
        }
        catch (Exception e)
        {
            System.out.println("Graphics error: " + e);
        }
    }

    private void drawMoveImage(Graphics g)
    {
        if (selectedPlantType != PlantType.NONE)
        {
            g.drawImage(selectedPlantType.getPlantImg(), mouseX + 2,
                    mouseY + 2, null); // 在鼠标移动的位置绘制所选植物
        }
    }

    @Override
    public void mouseDragged(MouseEvent e)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    // 在植物种子列表单击和在草坪中移动时的单击处理
    @Override
    public void mouseClicked(MouseEvent e)
    {
        int clickMouseX = e.getX();
        int clickMouseY = e.getY();
        Point point = new Point(clickMouseX, clickMouseY);
        // 在草坪中移动已选植物，并种入单击所对应的格子中
        if (selectedPlantType != PlantType.NONE
                && plantMap.inTheMap(clickMouseX, clickMouseY))
        {
            plantMap.putPlantInMap(selectedPlantType, clickMouseX, clickMouseY);
        }
        selectedPlantType = plantBar.selectedPlant(point); // 单击植物列表中的某种植物
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        // TODO Auto-generated method stub

    }
}
