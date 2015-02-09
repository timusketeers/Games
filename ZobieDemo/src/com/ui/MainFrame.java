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
        // ���ô���
        setTitle("��ʾ��");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ���������е������panel�������棩,�˵�����ڣ�panel,��Ϸpanel����Ϸ���棩
        panel = new JPanel();
        menuPanel = new MenuPanel(this);
        gamePlay = new GamePlay();
        layout = new CardLayout();
        // ���ô��岼��
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panel, BorderLayout.CENTER);
        // ����������panel����
        panel.setLayout(layout);
        panel.setPreferredSize(new Dimension(ConstantData.WIDTH,
                ConstantData.HEIGHT)); // ����panel����ĸߣ���
        panel.add(menuPanel, "menu"); // ����������Ƭ
        panel.add(gamePlay, "play");
        // �õ���Ļ�ĳߴ�
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension dim = tk.getScreenSize();
        setLocation((dim.width - ConstantData.WIDTH) / 2,
                (dim.height - ConstantData.HEIGHT) / 2);
        pack();
        setVisible(true);

    }

    public void switchToGame()
    {
        layout.show(panel, "play"); // ��ʾ��Ϸ���棨�ڶ�����Ƭ�� Ҳ��layout.next(panel);
        gamePlay.startGame();
    }

    public static void main(String[] args)
    {
        new MainFrame();
    }
}
