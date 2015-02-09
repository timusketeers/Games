package org.power.chess;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

@SuppressWarnings("serial")
public class ChineseChess extends Applet
{
    // 提供对网页的接口函数、启动连接、启动游戏
    private Image OffScreenImage = null;
    private GameManager gm = new GameManager(this);
    private NetManager nm = new NetManager(this);

    private int appletWidth = gm.getGameWidth();
    private int appletHeight = gm.getGameHeight();// 630棋盘 20px显示信息
    private Thread tPainter = null;
    private boolean keepRunning = true;
    private MyMouseAdapter ma = null;
    private MyKeyAdapter ka = null;// for debug
    private boolean runningByApplet = true;

    public ChineseChess()
    {
        super();
    }

    public ChineseChess(boolean runningByApplet)
    {
        // TODO Auto-generated constructor stub
        this.runningByApplet = runningByApplet;
    }

    public GameManager getGameManager()
    {
        return gm;
    }

    public NetManager getNetManager()
    {
        return nm;
    }

    public void update(Graphics g)
    {// 双缓冲机制
        if (OffScreenImage == null)
        {
            OffScreenImage = createImage(appletWidth, appletHeight + 30);
        }
        Graphics gImage = OffScreenImage.getGraphics();
        super.update(gImage);
        paintImage(gImage);
        g.drawImage(OffScreenImage, 0, 0, null);
    }

    public void paintImage(Graphics gImage)
    {// 主绘画函数
        gm.draw(gImage);

        if (!nm.isConnected())
        {
            Font f = gImage.getFont();
            Color cOld = gImage.getColor();
            gImage.setColor(Color.RED);
            gImage.setFont(new Font("黑体", Font.PLAIN, 50));
            gImage.drawString("等待对方加入游戏...", 50, 275);
            gImage.setFont(f);
            gImage.setColor(cOld);
        }
    }

    public int getAppletWidth()
    {
        return appletWidth;
    }

    public int getAppletHeight()
    {
        return appletHeight;
    }

    @Override
    public void init()
    {
        // TODO Auto-generated method stub
        super.init();
        if (!runningByApplet)
        {
            return;
        }
        if (tPainter == null)
        {
            System.out.println("象棋对弈平台V1.01");
            System.out.println("入口、启动自动绘画函数");
            tPainter = new Thread(new Painter(Integer.parseInt(PropertyMgr
                    .getProperty("paintInterval"))));
            tPainter.start();
        }
        if (ma == null)
        {
            ma = new MyMouseAdapter(gm);
            addMouseListener(ma);
            ka = new MyKeyAdapter(this);
            addKeyListener(ka);
        }
    }

    public class Painter implements Runnable
    {// 自动重画线程
        int paintInterval = 50;

        public Painter(int paintInterval)
        {
            this.paintInterval = paintInterval;
        }

        public void run()
        {
            while (keepRunning)
            {
                try
                {
                    Thread.sleep(paintInterval);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                repaint();
                Thread.yield();
            }
        }
    }

    // 主机使用的函数
    public String doServer(int port)
    {// 作为服务器
        System.out.println("作为服务器:" + port);
        nm.startServer(port);
        return LocalIP.getIp();
    }

    /*
     * public String getLocalIp() { String ip = null; try { InetAddress addr =
     * InetAddress.getLocalHost(); ip = addr.getHostAddress().toString();//
     * 获得本机IP //address = addr.getHostName().toString();// 获得本机名称
     * 
     * // System.out.println("addr=:"+String.valueOf(addr)); } catch (Exception
     * e) { System.out.println("Bad IP Address!" + e); }
     * System.out.println("返回IP:" + ip); return ip; }
     */
    // 客人使用的函数
    public int connectServer(String ip, int port)
    {// 作为客户端、连接服务器
        System.out.println("作为客户端、连接服务器(" + ip + ":" + port + ")。");
        int rtnVal = -1, failedCount = -1;
        rtnVal = nm.connectServer(ip, port);
        while (rtnVal != 1 && failedCount < 5)
        {
            failedCount++;
            try
            {
                Thread.sleep(3000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            rtnVal = nm.connectServer(ip, port);
        }
        return rtnVal;
    }

    // 返回结果函数
    public String getWinnerIp()
    {// 取得胜利者Ip
        System.out.println("取得胜利者Ip。");
        return gm.getWinnerIp();
    }

    // 销毁时的清理
    @Override
    public void destroy()
    {
        System.out.println("析构中。。。");
        keepRunning = false;
        try
        {
            if (tPainter != null && tPainter.isAlive())
            {
                tPainter.join(1000);
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        gm.close();
        nm.close();
        super.destroy();
    }

}
