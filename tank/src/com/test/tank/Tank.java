package com.test.tank;

import java.awt.*;
import java.util.Random;

public class Tank {

	 /**/
    static  final int WIDTH=ResourceMgr.tankD.getWidth();
    static  final int HEIGHT=ResourceMgr.tankD.getHeight();
    /*创建随机数*/
    Random random=new Random();
   
    /*坦克位置*/
    private int x,y;
    /*坦克初始方向*/
    private Dir dir=Dir.UP;
    /*坦克移动速度*/
    private static  final int  SPEED =2;
    /*引入TankFram*/
    private TankFram fram;
    /*坦克的移动状态*/
    private boolean moveing=true;
    /*坦克生命状态*/
   private boolean live =true;
   /*标记阵营*/
   private Group group = Group.Rad;
    public boolean isMoveing() {
        return moveing;
    }
    public void setMoveing(boolean moveing) {
        this.moveing = moveing;
    }
    public int getX() {
    	return this.x;
    }
    public void setX(int x) {
    	this.x=x;
    }
    public int getY() {
    	return this.y;
    }
    public void setY(int y) {
    	this.y=y;
    }
    public  Tank(int x,int y,Dir dir,TankFram fram,Group group){
        super();
        this.x=x;
        this.y=y;
        this.dir=dir;
        this.group=group;
        this.fram=fram;
    }
    public Dir getDir() {
        return dir;
    }
    public void setDir(Dir dir){
        this.dir=dir;
    }

    public void paint(Graphics g){
    	/*移除坦克fram.myTank.remove(this);*/
    	 
    	if(!live) {
    		if(this.group==Group.Rad) fram.tks.remove(this);
    		//if(this.group==Group.Blue) {  }
    	}	
    	//if(this.group==Group.Blue) {this.SPEED=10;}
        /*根据方向换自己的坦克图片*/
    	switch (dir) {
		case LEFT:
			//坦克换图片
	    	g.drawImage(ResourceMgr.tankL, x, y, null);	        
			break;
		case RIGHT:
		    //坦克换图片
    	    g.drawImage(ResourceMgr.tankR, x, y, null);	        
    	    break;
		case UP:
			//坦克换图片
	    	g.drawImage(ResourceMgr.tankU, x, y, null);	        
			break;
		case DOWN:
			//坦克换图片
	    	g.drawImage(ResourceMgr.tankD, x, y, null);	        
			break;
		default:
			break;
		}    	
        move();
      }

    private void move() {
        if(!moveing) return;
        if(this.group==Group.Blue) {
        	if(this.x<=10) this.x=10;
        	if(this.y<=30) this.y=30;
        	if(this.x>=this.fram.getWidth()-58) this.x=this.fram.getWidth()-58;
        	if(this.y>=this.fram.getHeight()-58) this.y=this.fram.getHeight()-58;
             }
        else if(this.x<0|this.x>this.fram.getWidth()|this.y>this.fram.getHeight()|this.y<0 ) this.die();
        
        switch (dir){
            case LEFT:
                x-=SPEED;
                break;
            case UP:
                y-=SPEED;
                break;
            case RIGHT:
                x+=SPEED;
                break;
            case DOWN:
                y+=SPEED;
                break;
            
            default:
                break;
        }
        if(this.group==Group.Rad) {        	

        	if(this.group == Group.Rad && random.nextInt(100) > 90) 
    			this.fire();
    		
    		if(this.group == Group.Rad && random.nextInt(1000) > 973)
    			randomDir();	
        	
        }
        
    }

    private void randomDir() {
    	this.dir = Dir.values()[random.nextInt(4)];
		
	}
	/*开火*/
    public void fire(){
        int bx=x+(WIDTH-ResourceMgr.bultD.getWidth())/2;
        int by=y+(HEIGHT-ResourceMgr.bultD.getHeight())/2;
        if(this.dir==Dir.LEFT) {
        	bx-=20;by+=2;
        }
        if(this.dir==Dir.RIGHT) {
        	bx+=20;by+=4;
        }
        if(this.dir==Dir.UP) {
        	bx+=1;by-=20;
        }
        if(this.dir==Dir.DOWN) {
        	bx-=1;by+=20;
        }
        fram.bullets.add(new Bullet(bx,by,this.dir,this.fram,this.group));
    }
    /**/
	public void die() {
		this.live=false;		
	}
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}

}
