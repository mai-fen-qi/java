package com.test.tank;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ResourceMgr {
   /*己方坦克四个方向的图片*/	
   public  static BufferedImage tankL,tankR,tankU,tankD;
   /*敌方坦克*/
   public  static BufferedImage radTankL,radTankR,radTankU,radTankD;
   /*地方子弹*/
   public static  BufferedImage radL,radR,radU,radD;
   /*己方子弹*/
   public  static BufferedImage bultL,bultR,bultU,bultD;
   /*爆炸的图片*/
   public  static BufferedImage[] explodes = new BufferedImage[16]; 
   static {
	   try {
		/*己方坦克*/
		tankU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("imge/blueTank1.png"));
		tankL = ImageUtil.rotateImage(tankU, -90);
		tankR = ImageUtil.rotateImage(tankU, 90);
		tankD = ImageUtil.rotateImage(tankU, 180);
		/*敌方坦克*/
		radTankU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("imge/radTank1.png"));
		radTankL = ImageUtil.rotateImage(radTankU, -90);
		radTankR = ImageUtil.rotateImage(radTankU, 90);
		radTankD = ImageUtil.rotateImage(radTankU, 180);
		/*己方子弹*/
		bultU=ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("imge/bulletU.gif"));
		bultL=ImageUtil.rotateImage(bultU, -90);
		bultR=ImageUtil.rotateImage(bultU, 90);
		bultD=ImageUtil.rotateImage(bultU, 180);
	    /*敌方子弹*/
		radU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("imge/bulletL.gif"));
		radL = ImageUtil.rotateImage(radU, -90);;
		radR = ImageUtil.rotateImage(radU, 90);;
		radD = ImageUtil.rotateImage(radU, 180);;
		
		for(int i=0;i<16;i++) {
			explodes[i]=ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("imge/e"+(i+1)+".gif"));
		}
	   
	   } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }
}
