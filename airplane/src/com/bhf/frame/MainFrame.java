package com.bhf.frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;

import com.bhf.entities.Bullet;
import com.bhf.entities.EnemyPlane;
import com.bhf.entities.Plane;
import com.bhf.factory.EnemyPlaneFactory;
import com.bhf.listener.BulletListener;
import com.bhf.listener.EnemyPlaneListener;

public class MainFrame extends Frame implements MouseMotionListener,
        BulletListener, EnemyPlaneListener
{
    private static final long serialVersionUID = 1L;
    // 窗口的长宽
    public static final int WIDTH = 500;
    public static final int HEIGTH = 700;
    // 当前窗口中的游戏图片
    private Image offScreenImage = this.createImage(WIDTH, HEIGTH);
    // 我自己的飞机
    private Plane myPlane = new Plane(this);
    // 屏幕上所有的炮弹
    private List<Bullet> bullets = new LinkedList<Bullet>();
    // 屏幕中所有的敌机
    private List<EnemyPlane> enemyPlanes = new LinkedList<EnemyPlane>();
    // 随机数
    private static final Random rand = new Random();
    // 打死一架飞机的分数
    public static int score = 0;
    // 游戏是否正在运行中
    private boolean isRunning = true;

    public MainFrame()
    {
        initFrame();
    }

    private void initFrame()
    {
        this.setTitle("小张--飞机大战");
        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenHeigth = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.setLocation((screenWidth - WIDTH) / 2, (screenHeigth - HEIGTH) / 2);
        this.setSize(WIDTH, HEIGTH);
        // this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
        this.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
        this.setResizable(false);
        this.addMouseMotionListener(this);
        this.setVisible(true);

        // 每隔50毫秒重新画一次
        new Thread(new PaintThread()).start();
    }

    /**
     * 画出所有的组件
     */
    public void paint(Graphics g)
    {
        if (!isRunning)
        {
            return;
        }
        System.out.println("bullet size : " + bullets.size());
        System.out.println("enemy's plane size : " + enemyPlanes.size());
        // 画出所有的炮弹
        for (int i = 0; i < this.bullets.size(); i++)
        {
            Bullet b = this.bullets.get(i);
            b.draw(g);
        }
        // 画出我的飞机
        myPlane.draw(g);
        // 画出所有的敌机
        for (int i = 0; i < this.enemyPlanes.size(); i++)
        {
            EnemyPlane ep = this.enemyPlanes.get(i);
            ep.draw(g);
        }
        DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance();
        df.setGroupingSize(4);
        // 画出得分
        g.setColor(Color.RED);
        g.setFont(new Font("宋体", Font.BOLD, 15));
        g.drawString("分数：" + df.format(score), 10, 50);
    }

    @Override
    public void update(Graphics g)
    {
        if (this.offScreenImage == null)
        {
            this.offScreenImage = this.createImage(WIDTH, HEIGTH);
        }
        // 覆盖背景图片
        Graphics gOffScreen = this.offScreenImage.getGraphics();
        // 取得原始颜色
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.WHITE);
        gOffScreen.fill3DRect(0, 0, WIDTH, HEIGTH, true);

        paint(gOffScreen);
        // 把原来的颜色设置回去
        gOffScreen.setColor(c);
        g.drawImage(this.offScreenImage, 0, 0, null);
    }

    @Override
    public void mouseDragged(MouseEvent e)
    {
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        if (!isRunning)
        {
            return;
        }
        myPlane.mouseMoved(e);
        this.repaint();
    }

    /**
     * 每重画一次，就改变一次炮弹的位置
     */
    @Override
    public void onChangeLocation(Bullet b)
    {
        // 每次移动以后改变位置
        if (b != null)
        {
            b.setHeight(b.getHeight() - b.getSpeed());
            // 如果除了边界，将自己从list中移除
            if (b.getHeight() <= 30)
            {
                this.bullets.remove(b);
            }
            // 打击敌机
            EnemyPlane ep = b.hitEnemyPlanes();
            if (ep != null)
            {
                this.enemyPlanes.remove(ep);
                this.bullets.remove(b);
            }
        }
    }

    /**
     * 重画线程
     */
    class PaintThread implements Runnable
    {
        public PaintThread()
        {

        }

        public void run()
        {
            while (isRunning)
            {
                try
                {
                    Thread.sleep(25);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                // 改变炮弹的位置,响应监听器的方法
                for (int i = 0; i < bullets.size(); i++)
                {
                    Bullet b = bullets.get(i);
                    onChangeLocation(b);
                }
                // 改变敌机下落的位置
                for (int i = 0; i < enemyPlanes.size(); i++)
                {
                    EnemyPlane ep = enemyPlanes.get(i);
                    onChangeEPLocation(ep);
                }
                // 每次加入一架敌机
                int r = rand.nextInt(100);
                if (r < 20)
                {
                    EnemyPlane ep = EnemyPlaneFactory
                            .createEnemyPlane(MainFrame.this);
                    enemyPlanes.add(ep);
                    score = score + 1000;
                }
                // 重画
                MainFrame.this.repaint();
            }
        }

    }

    public List<Bullet> getBullets()
    {
        return bullets;
    }

    public List<EnemyPlane> getEnemyPlanes()
    {
        return enemyPlanes;
    }

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        MainFrame mainFrame = new MainFrame();
    }

    /**
     * 屏幕每重画一次，改变敌机的位置
     */
    @Override
    public void onChangeEPLocation(EnemyPlane ep)
    {
        if (ep != null)
        {
            ep.setHeight(ep.getHeight() + ep.getSpeed());

            // 判断边界
            if (ep.getHeight() > MainFrame.HEIGTH)
            {
                enemyPlanes.remove(ep);
            }

            // 打击我的飞机
            if (ep.getRectangle().intersects(myPlane.getRectangle()))
            {
                // 游戏停止
                removeGame();
                int state = JOptionPane.showConfirmDialog(null,
                        "哈哈,你死了^_^,需要重新开始吗？");
                System.out.println(state);
                switch (state)
                {
                case JOptionPane.OK_OPTION:
                    restartGame();
                    break;
                case JOptionPane.NO_OPTION:
                    break;
                case JOptionPane.CANCEL_OPTION:
                    break;
                }
            }
        }
    }

    /**
     * 清除游戏角色
     */
    private void removeGame()
    {
        isRunning = false;
        myPlane.setLive(false);
        myPlane = null;
        enemyPlanes.clear();
    }

    /**
     * 重新开始游戏
     */
    private void restartGame()
    {
        score = 0;
        myPlane = new Plane(this);
        isRunning = true;
    }

}
