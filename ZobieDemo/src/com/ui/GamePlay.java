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
            // ����ֲ���б�״̬,�ݵ�״̬
            sceneUpdate();
            // ��������,��ʾ����,��ʾֲ���б�,�ݵ�
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

    // �������(λ�ñ仯�γɶ���Ч��)
    public void sceneUpdate()
    {
        plantBar.seedUpdate();
        // plantMap.updateLocation();
        // ʹ��ѭ������ÿ����ʬ��λ��
        if (plantMap.animaZombieList.get(0) != null)
            plantMap.updateLocation(plantMap.animaZombieList.get(0));
        if (plantMap.animaZombieList.get(1) != null)
            plantMap.updateLocation(plantMap.animaZombieList.get(1));

    }

    // �������ϵ���Ϸ����
    public void gameRender()
    {
        if (gameImage == null)
        {
            gameImage = this.createImage(this.getWidth(), this.getHeight()); // ����panel��IMAGE����С������
            if (gameImage == null)
            {
                System.out.println("gameImage is null");
                return;
            }
            else
            {
                graphic = gameImage.getGraphics(); // ���һ��gameImage��Graphics�����ģ����ڻ�ͼ
            }

        }
        // ��ʾgameImage�ϵ�ͼ��
        graphic.drawImage(grasslandImage, 0, 0, getWidth(), getHeight(),
                ConstantData.LEFT_OFFSET, 0, ConstantData.LEFT_OFFSET
                        + getWidth(), getHeight(), null);
        plantBar.draw(graphic); // ��ֲ���б�
        drawMoveImage(graphic); // �������ƶ���ֲ��
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
        // �ӳ�7�����ֵڶ��н�ʬ
        if (beginTime - nowTime > 10000)
        {
            if (plantMap.animaZombieList.get(1) != null)
            {
                plantMap.drawZombie(graphic, plantMap.animaZombieList.get(1));
                plantMap.animaZombieList.get(1).isVisible = 1;
            }
        }
        // ....�Ӵ��ӳٻ������н�ʬ��Ҳ����ѭ��

    }

    // ������������ʾ����Ļ��
    public void paintScreen()
    {
        Graphics g;
        try
        {
            g = this.getGraphics(); // ���panel��Graphics�����ģ����ڻ�ͼ
            if (g != null && gameImage != null)
            {
                g.drawImage(gameImage, 0, 0, null); // ����Ļ����ʾpanel��ͼ��
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
                    mouseY + 2, null); // ������ƶ���λ�û�����ѡֲ��
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

    // ��ֲ�������б������ڲ�ƺ���ƶ�ʱ�ĵ�������
    @Override
    public void mouseClicked(MouseEvent e)
    {
        int clickMouseX = e.getX();
        int clickMouseY = e.getY();
        Point point = new Point(clickMouseX, clickMouseY);
        // �ڲ�ƺ���ƶ���ѡֲ������뵥������Ӧ�ĸ�����
        if (selectedPlantType != PlantType.NONE
                && plantMap.inTheMap(clickMouseX, clickMouseY))
        {
            plantMap.putPlantInMap(selectedPlantType, clickMouseX, clickMouseY);
        }
        selectedPlantType = plantBar.selectedPlant(point); // ����ֲ���б��е�ĳ��ֲ��
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
