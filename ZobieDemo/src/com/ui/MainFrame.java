package com.ui;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import com.ui.MenuPanel;

import com.common.ConstantData;

public class MainFrame extends JFrame
{
    private static final long serialVersionUID = 1L;
    MenuPanel menuPanel;
    JPanel panel;
    GamePlay gamePlay;
    private CardLayout layout;

    public MainFrame()
    {
        // 设置窗体
        setTitle("演示版");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 创建窗体中的组件：panel（主界面）,菜单（入口）panel,游戏panel（游戏界面）
        panel = new JPanel();
        menuPanel = new MenuPanel(this);
        gamePlay = new GamePlay();
        layout = new CardLayout();
        // 设置窗体布局
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panel, BorderLayout.CENTER);
        // 设置主界面panel布局
        panel.setLayout(layout);
        panel.setPreferredSize(new Dimension(ConstantData.WIDTH,
                ConstantData.HEIGHT)); // 设置panel界面的高，宽
        panel.add(menuPanel, "menu"); // 加入两个卡片
        panel.add(gamePlay, "play");
        // 得到屏幕的尺寸
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension dim = tk.getScreenSize();
        setLocation((dim.width - ConstantData.WIDTH) / 2,
                (dim.height - ConstantData.HEIGHT) / 2);
        pack();
        setVisible(true);

    }

    public void switchToGame()
    {
        layout.show(panel, "play"); // 显示游戏界面（第二个卡片） 也可layout.next(panel);
        gamePlay.startGame();
    }

    public static void main(String[] args)
    {
        new MainFrame();
    }
}
