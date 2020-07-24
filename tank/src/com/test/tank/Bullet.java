package com.test.tank;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Bullet {
    /*定义子弹移动速度*/
    private static  final int SPEED=10;
    /*定义子弹宽高*/
    private static  final int WIDTH=ResourceMgr.bultD.getWidth();
    private static  final int HEIGHT=ResourceMgr.bultD.getHeight();
    /*定义子弹方向*/
    private Dir dir;
    /*定义子弹位置*/
    private int x,y;
    /*子弹生命状态*/
    private boolean live = true;
    /*引入tankfram*/
    TankFram tf=null;
    /*子弹阵营*/
    private Group group=Group.Rad;
     
   
    
    public Bullet(int x, int y,Dir dir,TankFram tf,Group group) {
        this.x=x;
        this.y=y;
        this.dir=dir;
        this.tf=tf;
        this.group=group;
    }
    public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}
	public  void paint(Graphics g){
        if(!live){
            tf.bullets.remove(this);
        }
    /*    
        Color c=g.getColor();
        g.setColor(Color.RED);
        g.fillOval(x,y,WIDTH,HEIGHT);
        g.setColor(c);
       */ 
        /*根据方向把子弹换成对应图片*/
        switch (dir) {
		case LEFT:
			g.drawImage(ResourceMgr.bultL, x, y, null);
			break;
			
		case RIGHT:
			g.drawImage(ResourceMgr.bultR, x, y, null);			
			break;
			
		case UP:
			g.drawImage(ResourceMgr.bultU, x, y, null);			
			break;
		case DOWN:
			g.drawImage(ResourceMgr.bultD, x, y, null);			
			break;

		default:
			break;
		}
        move();


    }

    private void move() {
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
        if(x < 0 || y < 0 || x > TankFram.GAME_WIDTH || y > TankFram.GAME_HEIGHT ) live=false;

    }

    /*处理敌方坦克死亡*/
	public void collideWidth(Tank tank ) {
		if(this.group.equals(tank.getGroup()) ) return;
		//TODO:一个rect就行
		Rectangle rect1 = new Rectangle(this.x,this.y,WIDTH,HEIGHT);
		Rectangle rect2 = new Rectangle(tank.getX(),tank.getY(),tank.WIDTH,tank.HEIGHT);
		if(rect1.intersects(rect2)) {
			tank.die();
			this.die();
			tf.es.add(new Explode(tank.getX(),tank.getY(),tf));
		}
		 
	}
	
	

	/*子弹死亡*/
	private  void die() {
	      this.live=false;
	}

}
