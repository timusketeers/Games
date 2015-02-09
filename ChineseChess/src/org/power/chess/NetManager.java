package org.power.chess;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class NetManager
{
    private ServerSocket ss = null;
    private Socket oppositeSocket = null;
    private boolean connected = false;
    private String oppositeIp = "";
    // private String myIp = "";
    private boolean isServer = true;
    private Thread tSocketAccepter = null;
    private Thread tSocketIOMgr = null;
    private boolean keepRunning = true;
    private ChineseChess cc = null;
    private GameManager gm = null;
    private SocketIOManager siom = null;

    public NetManager(ChineseChess cc)
    {
        this.cc = cc;
        this.gm = cc.getGameManager();
    }

    public String getOppositeIp()
    {
        return oppositeIp;
    }

    /*
     * public String getMyIp() { return myIp; }
     */

    public void startServer(int port)
    {// 启动服务器
        try
        {
            ss = new ServerSocket(port);
            // myIp = ss.getInetAddress().toString();
            SocketAccepter sa = new SocketAccepter(ss);

            tSocketAccepter = new Thread(sa);
            tSocketAccepter.start();
        }
        catch (BindException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            // 关闭工作在SocketAccepter线程内处理
        }
    }

    public int connectServer(String ip, int port)
    {// 启动客户端
        isServer = false;
        oppositeIp = ip;
        int rtnVal = 0;
        try
        {
            oppositeSocket = new Socket(ip, port);
            System.out.println("已经连接到服务器。。。");
            siom = new SocketIOManager(oppositeSocket, gm);
            tSocketIOMgr = new Thread(siom);
            tSocketIOMgr.start();
            connected = true;
            oppositeIp = oppositeSocket.getInetAddress().toString();
            sendMsg("MSG:0001");
            System.out.println("发出确认信息。");
            rtnVal = 1;
        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();
            rtnVal = 0;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            rtnVal = 0;
        }
        return rtnVal;
    }

    private class SocketAccepter implements Runnable
    {// 监听、接收连接线程类
        ServerSocket ss = null;

        public SocketAccepter(ServerSocket ss)
        {
            this.ss = ss;
        }

        public void run()
        {
            try
            {
                System.out.println("等待客户端连接。。。");
                oppositeSocket = ss.accept();
                siom = new SocketIOManager(oppositeSocket, gm);
                tSocketIOMgr = new Thread(siom);
                tSocketIOMgr.start();
                System.out.println("接收到客户端连接！");
                connected = true;
                oppositeIp = oppositeSocket.getInetAddress().toString();
                sendMsg("MSG:0000");
                System.out.println("发出欢迎信息。");
            }
            catch (IOException e)
            {
                e.printStackTrace();
                connected = false;
            }
            finally
            {
                try
                {
                    if (ss != null)
                    {
                        ss.close();
                        ss = null;
                    }
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean isConnected()
    {
        return connected;
    }

    public void close()
    {
        // TODO Auto-generated method stub
        if (!keepRunning)
            return;

        keepRunning = false;
        if (cc.getWinnerIp() == null)
        {
            sendMsg("MSG:0003");
        }
        System.out.println("NetManager关闭中。。。");
        try
        {
            if (siom != null)
            {
                siom.close();
            }

            if (tSocketAccepter != null && tSocketAccepter.isAlive())
            {

                try
                {
                    tSocketAccepter.join(2500);
                    if (tSocketAccepter.isAlive())
                    {
                        System.out.println("强行关闭tSocketAccepter中。。。");
                        tSocketAccepter.stop();
                    }
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }

            if (tSocketIOMgr != null && tSocketIOMgr.isAlive())
            {
                try
                {
                    tSocketIOMgr.join(2500);
                    if (tSocketIOMgr.isAlive())
                    {
                        System.out.println("强行关闭tSocketIOMgr中。。。");
                        tSocketIOMgr.stop();
                    }
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }

            if (oppositeSocket != null)
            {
                oppositeSocket.close();
                oppositeSocket = null;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void sendMsg(String msg)
    {
        if (siom != null)
        {
            siom.outputMsg(msg);
        }
    }

    private class SocketIOManager implements Runnable
    {
        private DataInputStream dis = null;
        private DataOutputStream dos = null;
        private GameManager gm = null;
        private int checkInterval = Integer.parseInt(PropertyMgr
                .getProperty("socketRecvInterval"));

        public SocketIOManager(Socket sOpposite, GameManager gm)
        {
            this.gm = gm;
            try
            {
                this.dis = new DataInputStream(sOpposite.getInputStream());
                this.dos = new DataOutputStream(sOpposite.getOutputStream());
            }
            catch (IOException e)
            {
                System.out.println("取得socketIO流出错！");
                e.printStackTrace();
            }
        }

        public void close()
        {
            // TODO Auto-generated method stub
            keepRunning = false;
            try
            {
                if (dis != null)
                {
                    dis.close();
                    dis = null;
                }
                if (dos != null)
                {
                    dos.close();
                    dos = null;
                }
            }
            catch (IOException e)
            {
                System.out.println("关闭IO流时出错！");
                e.printStackTrace();
            }
        }

        @Override
        public void run()
        {
            // TODO Auto-generated method stub
            String oppositeMsg = null;
            while (keepRunning)
            {
                try
                {
                    oppositeMsg = dis.readUTF();
                }
                catch (SocketException e)
                {
                    System.out.println("对方可能已经退出对局！");
                    e.printStackTrace();
                    // gm.gameBreak();
                }
                catch (EOFException e)
                {
                    System.out.println("对方已经退出对局！");
                    e.printStackTrace();
                    gm.gameBreak();
                }
                catch (IOException e)
                {
                    System.out.println("接收MSG出错！");
                    e.printStackTrace();
                }
                System.out.println("接收并解析信息：" + oppositeMsg);
                gm.parseOppositeMsg(oppositeMsg);

            }
        }

        public void outputMsg(String msg)
        {
            if (msg == null)
                return;
            try
            {
                dos.writeUTF(msg);
            }
            catch (IOException e)
            {
                System.out.println("发送消息：" + msg + "出错！");
                e.printStackTrace();
            }
        }
    }

    public boolean isServer()
    {
        // TODO Auto-generated method stub
        return isServer;
    }
}
