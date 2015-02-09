package org.power.chess;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class MainFrame extends Frame
{
    private ChineseChess cc = null;
    private boolean keepRunning = true;
    private Thread tPainter = null;
    private MyMouseAdapter ma = null;
    // private MyKeyAdapter ka = null;//for debug
    private Image OffScreenImage = null;
    private static int WIDTH = 0;
    private static int HEIGHT = 0;
    private int yOffsetHeight = 25;

    /**
     * 不在网页上运行就在此运行
     * 
     * @param args
     */
    public static void main(String[] args)
    {
        // TODO Auto-generated method stub
        MainFrame mf = new MainFrame("象棋网络对弈程序V1.0 power by RanWind");

        int select = JOptionPane.showConfirmDialog(mf,
                "选择\"是\"则 新建对局，否则加入别人新建的对局。", "新建对局吗？",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        try
        {
            if (select == JOptionPane.YES_OPTION)
            {
                String port = JOptionPane.showInputDialog(mf,
                        "请输入等待网络连接的端口，默认是：8887。", "确认端口",
                        JOptionPane.QUESTION_MESSAGE);
                mf.setUpServer(Integer.parseInt(port));
            }
            else
            {
                String ipAndPort = JOptionPane.showInputDialog(mf,
                        "请输入房主IP以及端口号，输入格式是：192.168.1.100:8887。", "确认IP/端口",
                        JOptionPane.QUESTION_MESSAGE);
                mf.setUpClient(ipAndPort);
            }
        }
        catch (NumberFormatException e)
        {
            JOptionPane.showConfirmDialog(mf, "输入有误，程序异常终止！", "出错了",
                    JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        catch (NetAddressFormatException e)
        {
            JOptionPane.showConfirmDialog(mf, "输入有误，程序异常终止！", "出错了",
                    JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void setUpClient(String ipAndPort) throws NetAddressFormatException
    {
        // TODO Auto-generated method stub
        Pattern p = Pattern
                .compile("(\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3}):(\\d+)");
        Matcher m = p.matcher(ipAndPort);
        if (m.matches())
        {
            cc.connectServer(m.group(1), Integer.parseInt(m.group(2)));
        }
        else
        {
            throw new NetAddressFormatException("语句不符合规范：" + ipAndPort);
        }

    }

    private void setUpServer(int port)
    {
        // TODO Auto-generated method stub
        cc.doServer(port);
    }

    public MainFrame(String name)
    {
        super(name);

        cc = new ChineseChess(false);
        cc.init();
        init();
        cc.start();
        WIDTH = cc.getAppletWidth();
        HEIGHT = cc.getAppletHeight() + yOffsetHeight;
        setSize(WIDTH, HEIGHT);
        setLocation(300, 100);
        setResizable(false);
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                super.windowClosing(e);
                setVisible(false);
                cc.stop();
                cc.destroy();
                dispose();
                System.exit(0);
            }
        });
        setVisible(true);
    }

    private void init()
    {
        // TODO Auto-generated method stub
        if (tPainter == null)
        {
            System.out.println("入口、启动自动绘画函数");
            tPainter = new Thread(new Painter(Integer.parseInt(PropertyMgr
                    .getProperty("paintInterval"))));
            tPainter.start();
        }

        if (ma == null)
        {
            ma = new MyMouseAdapter(cc.getGameManager(), 0, -yOffsetHeight);
            addMouseListener(ma);
            // ka = new MyKeyAdapter(cc);
            // addKeyListener(ka);
        }
    }

    private class Painter implements Runnable
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

    @Override
    public void update(Graphics g)
    {
        // TODO Auto-generated method stub
        super.update(g);
        if (OffScreenImage == null)
        {
            OffScreenImage = createImage(WIDTH, HEIGHT + 30);
        }
        Graphics gImage = OffScreenImage.getGraphics();
        super.update(gImage);
        cc.paintImage(gImage);
        g.drawImage(OffScreenImage, 0, yOffsetHeight, null);
    }

}
