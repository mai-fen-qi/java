package com.test.tank;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;


public class TankFram extends Frame {
	/*定义己方坦克*/
	//List<Tank> myTank =new ArrayList<>();
    Tank          myTank1  = new Tank(200,600,Dir.UP,this,Group.Blue);    
    /*定义敌方坦克*/
    List<Tank>    tks      = new ArrayList<>();
    /*定义子弹列表*/
    List<Bullet>  bullets  = new ArrayList<>();  
    /*定义爆炸图片*/
    List<Explode> es       = new ArrayList<>();
    //Explode e=new Explode(100,100,this);
    /*定义窗口大小*/
    static  final  int GAME_WIDTH=800, GAME_HEIGHT=600;
    public TankFram(){
    	/*设置位置*/
    	setLocation(400,30);
        /*设置大小*/
        setSize(GAME_WIDTH,GAME_HEIGHT);
        Color black1 = new Color(123,191,234);
		/*设置背景颜色*/
        setBackground(black1);;
        /*设置 窗口能不能随意改变大小*/
        setResizable(false);
        /*设置标题*/
        setTitle("tanke");
        /* */
        setVisible(true);
        /* */
        this.addKeyListener(new MyKeyListener());
        /* */
        addWindowListener(new WindowAdapter() {
            /* */
            @Override
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });
    }
    /*去掉闪烁问题*/
    Image offScreenImage = null;
    @Override
    public void update(Graphics g) {
        if(offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        Color black1 = new Color(123,191,234);
        gOffScreen.setColor(black1);
        gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        g.drawImage(offScreenImage, 0, 0, null);
    }

    @Override
    public void paint(Graphics g){
        g.drawString("射出的子弹个数"+bullets.size(),10,60);
        g.drawString("敌方坦克个数"+tks.size(),10,80);
    	g.drawString("爆炸的数量:" + es.size(), 10, 100);
        myTank1.paint(g);
       
        /*初始化敌方坦克*/
        for(int i=0;i<tks.size();i++){
        	tks.get(i).paint(g); 
        }
        /*初始化子弹*/
        for(int i=0;i<bullets.size();i++){
            bullets.get(i).paint(g);
        }
        /*初始化爆炸*/
        for(int i=0;i<es.size();i++) {
        	es.get(i).paint(g);
        }
        /*处理坦克碰撞*/
        for(int i=0;i<bullets.size();i++) {
        	for(int j=0;j<tks.size();j++) { 
        		/*处理敌方坦克碰撞*/       	 
        		 bullets.get(i).collideWidth(tks.get(j) ); 
        		/*处理己方坦克碰撞*/
        		// bullets.get(i).collideWidth(myTank1  );
        		 
        		
        	}        	
        }
        
     }
        /* */
    class MyKeyListener extends KeyAdapter{

        boolean bL =false;
        boolean bU =false;
        boolean bR =false;
        boolean bD =false;
        @Override
        public void keyPressed(KeyEvent e){
            int key =e.getKeyCode();
            switch (key){
                case KeyEvent.VK_LEFT:
                     bL = true;
                     break;
                case KeyEvent.VK_UP:
                      bU = true;
                     break;

                case KeyEvent.VK_DOWN:
                     bD =true;
                     break;
                case KeyEvent.VK_RIGHT:
                     bR =true;
                     break;
                default:
                     break;
            }
            /* */
            setMainTankDir();
        }

        @Override
        public void keyReleased(KeyEvent e){
            int key =e.getKeyCode();
            switch (key){
                case KeyEvent.VK_LEFT:
                    bL=false;
                    break;
                case KeyEvent.VK_DOWN:
                    bD =false;
                    break;
                case KeyEvent.VK_UP:
                    bU =false;
                    break;
                case KeyEvent.VK_RIGHT:
                    bR =false;
                    break;
                case KeyEvent.VK_CONTROL:
                    //myTank.get(0).fire();
                	myTank1.fire();
                    break;

                default:
                    break;
            }
            setMainTankDir();

        }

        private  void  setMainTankDir(){
        	/*
            if(!bL&&!bU&&!bR&&!bD)
                myTank.get(0).setMoveing(false);
            else
                myTank.get(0).setMoveing(true);
            if(bL)  myTank.get(0).setDir(Dir.LEFT);
            if(bU)  myTank.get(0).setDir(Dir.UP);
            if(bR)  myTank.get(0).setDir(Dir.RIGHT);
            if(bD)  myTank.get(0).setDir(Dir.DOWN);
        	 */
        	 if(!bL&&!bU&&!bR&&!bD)
                 myTank1.setMoveing(false);
             else
                 myTank1.setMoveing(true);
             if(bL)  myTank1.setDir(Dir.LEFT);
             if(bU)  myTank1.setDir(Dir.UP);
             if(bR)  myTank1.setDir(Dir.RIGHT);
             if(bD)  myTank1.setDir(Dir.DOWN);
        	
            }
         }

    }


