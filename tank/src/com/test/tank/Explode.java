package com.test.tank;

import java.awt.*;

public class Explode {
    
	/*定义图片宽高*/
    private static  final int WIDTH=ResourceMgr.explodes[0].getWidth();
    private static  final int HEIGHT=ResourceMgr.explodes[0].getHeight();
    /*定义图片位置*/
    private int x,y;
    /*引入tankfram*/
    TankFram tf=null;
    /*标记图片运行到第几张*/
    private  int step=0;

    public Explode(int x, int y,TankFram tf) {
        this.x=x;
        this.y=y;
        this.tf=tf;  
        // new Audio1("audio/war1.wav").run();;
    }
    public  void paint(Graphics g){
    	   
    	g.drawImage(ResourceMgr.explodes[step++],x,y,null);
    	if(step >= ResourceMgr.explodes.length)	tf.es.remove(this);		
	      		
    }
}
