package com.mr.draw;        //类所在的包名

import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.BorderLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import java.awt.BasicStroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JColorChooser;

/**
 * 画图主窗体
 */
public class DrawPictureFrame extends JFrame    //继承窗体类
{
    // 创建一个8位RGB颜色分量的图像
    BufferedImage image = new BufferedImage(570,390,BufferedImage.TYPE_INT_BGR);
    Graphics gs = image.getGraphics();          //获得图像的绘图对象
    Graphics2D g = (Graphics2D) gs;             //将绘图对象转换为Graphics2D类型
    DrawPictureCanvas canvas = new DrawPictureCanvas(); //创建画布对象
    Color foreColor = Color.BLACK;              //定义前景色
    Color backgroundColor = Color.WHITE;        //定义背景色
    int x = -1;                                 //上一鼠标绘制点的横坐标
    int y = -1;                                 //上一鼠标绘制点的纵坐标
    boolean rubber = false;                     //橡皮标识变量

    private JToolBar toolBar;                   //工具栏
    private JButton eraserButton;               //橡皮按钮
    private JToggleButton strokeButton1;        //细线按钮
    private JToggleButton strokeButton2;        //粗线按钮
    private JToggleButton strokeButton3;        //较粗按钮
    private JButton backgroundButton;           //背景色按钮
    private JButton foregroundButton;           //前景色按钮
    private JButton clearButton;                //清除按钮
    private JButton saveButton;                 //保存按钮
    private JButton shapeButton;                //图形按钮

    /**
     * 构造方法
     */
    public DrawPictureFrame()
    {
        setResizable(false);                    //窗体不能改变大小
        setTitle("画图程序");                    //设置标题
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//窗体关闭则停止程序
        setBounds(500, 100, 574, 460);//设置窗口位置和宽高
        init();                                 //组件初始化
        addListener();                          //添加组件监听
    }   // DrawPictureFrame()结束

    /**
     * 组件初始化
     */
    private void init()
    {
        g.setColor(backgroundColor);            //用背景色设置绘图对象的颜色
        g.fillRect(0,0,570,390);//用背景色填充整个画布
        g.setColor(foreColor);                  //用前景色设置绘图对象的颜色
        canvas.setImage(image);                 //设置画布的图像
        getContentPane().add(canvas);           //将画布添加到窗体容器默认布局的中部位置

        toolBar = new JToolBar();               //初始化工具栏
        getContentPane().add(toolBar,BorderLayout.NORTH);//工具栏添加到窗体最上边的位置

        saveButton = new JButton("保存");   //初始化按钮对象，并添加文本内容
        toolBar.add(saveButton);                //工具栏添加按钮
        toolBar.addSeparator();                 //添加分割条
        //初始化按钮对象并添加文本内容
        strokeButton1 = new JToggleButton("细线");
        strokeButton1.setSelected(true);        //细线按钮处于被选中状态
        toolBar.add(strokeButton1);             //工具栏添加按钮
        //初始化按钮对象并添加文本内容
        strokeButton2 = new JToggleButton("粗线");
        toolBar.add(strokeButton2);
        //初始化按钮对象并添加文本内容
        strokeButton3 = new JToggleButton("较粗");
        //画笔粗细按钮组，保证同时只有一个按钮被选中
        ButtonGroup strokeGroup = new ButtonGroup();
        strokeGroup.add(strokeButton1);         //按钮组添加按钮
        strokeGroup.add(strokeButton2);         //按钮组添加按钮
        strokeGroup.add(strokeButton3);         //按钮组添加按钮
        toolBar.add(strokeButton3);             //工具栏添加按钮
        toolBar.addSeparator();                 //添加分割
        backgroundButton = new JButton("背景颜色");//初始化按钮对象，并添加文本内容
        toolBar.add(backgroundButton);          //工具栏添加按钮
        foregroundButton = new JButton("前景颜色");//初始化按钮对象，并添加文本内容
        toolBar.add(foregroundButton);          //工具栏添加按钮
        toolBar.addSeparator();                 //添加分割条

        clearButton = new JButton("清 除"); //初始化按钮对象，并添加文本内容
        toolBar.add(clearButton);               //工具栏添加按钮
        eraserButton = new JButton("橡皮"); //初始化按钮对象，并添加文本内容
        toolBar.add(eraserButton);              //工具栏添加按钮
    }   // init()结束

    /**
     * 为组件添加动作监听
     */
    private void addListener()
    {
        //画板添加鼠标移动事件监听
        canvas.addMouseMotionListener(new MouseMotionAdapter()
        {
            @Override
            public void mouseDragged(final MouseEvent e)    //当鼠标拖曳时
            {
                if (x > 0 && y > 0)                         //如果x与y存在鼠标记录
                {
                    if (rubber)                             //如果橡皮标识为true，说明使用橡皮
                    {
                        g.setColor(backgroundColor);        //绘图工具使用背景色
                        g.fillRect(x,y,10,10);  //在鼠标滑过的位置填充正方形
                    }
                    else                                    //如果橡皮标识为false,表明画笔画图
                    {
                        g.drawLine(x,y,e.getX(),e.getY());  //在鼠标滑过的位置画直线
                    }   //else结束
                }   //if结束
                x = e.getX();                               //上一次鼠标绘制点的横坐标
                y = e.getY();                               //上一次鼠标绘制点的纵坐标
                canvas.repaint();                           //更新画布
            } //mouseDragged结束
        }); // canvas.addMouseMotionListener()结束
        canvas.addMouseListener(new MouseAdapter()          //画板添加鼠标单击事件监听
        {
            @Override
            public void mouseReleased(final MouseEvent arg0)//当按键抬起时
            {
                x = -1; //上一记录横坐标恢复为-1
                y = -1; //上一记录纵坐标恢复为-1
            }// mouseReleased()结束
        });// canvas.addMouseListener()结束

        strokeButton1.addActionListener(new ActionListener()    //”细线“按钮添加动作监听
        {
            @Override
            public void actionPerformed(final ActionEvent arg0)       //单击时
            {
                //声明画笔的属性，粗细为1像素，线条末端无修饰，折线处呈尖角
                BasicStroke bs = new BasicStroke(1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER);
                g.setStroke(bs);                                    //画图工具使用此画笔
            }   //actionPerformed()结束
        }); //strokeButton1.addActionListener()结束

        strokeButton2.addActionListener(new ActionListener()    //”粗线“按钮添加动作监听
        {
            @Override
            public void actionPerformed(final ActionEvent arg0)       //单击时
            {
                //声明画笔的属性，粗细为2像素，线条末端无修饰，折线处呈尖角
                BasicStroke bs = new BasicStroke(2,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER);
                g.setStroke(bs);                                    //画图工具使用此画笔
            }   //actionPerformed()结束
        }); //strokeButton2.addActionListener()结束

        strokeButton3.addActionListener(new ActionListener()    //”较粗“按钮添加动作监听
        {
            @Override
            public void actionPerformed(final ActionEvent arg0)       //单击时
            {
                //声明画笔的属性，粗细为4像素，线条末端无修饰，折线处呈尖角
                BasicStroke bs = new BasicStroke(4,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER);
                g.setStroke(bs);                                    //画图工具使用此画笔
            }   //actionPerformed()结束
        }); //strokeButton3.addActionListener()结束

        backgroundButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(final ActionEvent arg0)     //单击时
            {
                //打开选择颜色对话框，参数依次为：父窗体、标题、默认选中的颜色（青色）
                Color bgColor = JColorChooser.showDialog(DrawPictureFrame.this,"选择颜色对话框",Color.CYAN);
                if (bgColor != null)        //如果选中的颜色不是空的
                {
                    backgroundColor = bgColor;//将选中的颜色赋给背景色变量
                }
                //背景色按钮也更换为这种背景颜色
                backgroundButton.setBackground(backgroundColor);
                g.setColor(backgroundColor);    //绘图工具使用背景色
                g.fillRect(0,0,570,390);//画一个背景颜色的方形填满整个画布
                g.setColor(foreColor);          //绘图工具使用前景色
                canvas.repaint();               //更新画布
            } //actionPerformed()结束
        });//backgroundButton.addActionListener()结束

        foregroundButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(final ActionEvent arg0) //单击时
            {
                //打开选择颜色对话框，参数依次为：父窗体、标题、默认选中的颜色（青色）
                Color fColor = JColorChooser.showDialog(DrawPictureFrame.this,"选择颜色对话框",Color.CYAN);
                if (fColor != null)        //如果选中的颜色不是空的
                {
                    foreColor = fColor;//将选中的颜色赋给前景色变量
                }
                //前景色按钮的文字也更换为这种颜色
                foregroundButton.setForeground(foreColor);
                g.setColor(foreColor);  //绘图工具使用前景色
            } //actionPerformed()结束
        }); //foregroundButton.addActionListener()结束


    } //addListener()结束

    /**
     * 程序运行主方法
     * @param args - 运行时参数，本程序不使用
     */
    public static void main(String[] args)
    {
        DrawPictureFrame frame = new DrawPictureFrame();    //创建窗体对象
        frame.setVisible(true);                             //让窗体可见
    }   //main()结束
}   //DrawPictureFrame类结束
