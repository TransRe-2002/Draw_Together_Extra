package com.mr.draw;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JWindow;
import com.mr.util.BackgroundPanel;

/**
 *
 */

public class PictureWindow extends JWindow
{
    private JButton changeButton;
    private JButton hiddenButton;
    private BackgroundPanel centerPanel;
    File list[];
    int index;
    DrawPictureFrame frame;

    /**
     * 构造方法
     *
     * @param frame - 父窗体
     */
    public PictureWindow(DrawPictureFrame frame)
    {
        this.frame = frame;
        setSize(400,460);
        init();
        addListener();
    }

    /**
     * 组件初始化方法
     */

    private void init()
    {
        Container c = getContentPane();
        File dir = new File("src/img/picture");
        list = dir.listFiles();
        centerPanel = new BackgroundPanel(getListImage());
        c.add(centerPanel,BorderLayout.CENTER);
        FlowLayout flow = new FlowLayout(FlowLayout.RIGHT);
        flow.setHgap(20);
        JPanel southPanel = new JPanel();
        southPanel.setLayout(flow);
        changeButton = new JButton("更换图片");
        southPanel.add(changeButton);
        hiddenButton = new JButton("隐藏");
        southPanel.add(hiddenButton);
        c.add(southPanel,BorderLayout.SOUTH);
    }

    /**
     * 添加监听
     */
    private void addListener()
    {
        hiddenButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                setVisible(false);
                frame.initShowPicButton();
            }
        });

        changeButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                centerPanel.setImage(getListImage());
            }
        });
    }

    private Image getListImage()
    {
        String imgPath = list[index].getAbsolutePath();
        ImageIcon image = new ImageIcon(imgPath);
        index++;
        if (index >= list.length)
        {
            index = 0;
        }
        return image.getImage();
    }
}
