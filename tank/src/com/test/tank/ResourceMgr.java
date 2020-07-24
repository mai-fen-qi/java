package com.test.tank;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ResourceMgr {
   /*坦克四个方向的图片*/	
   public  static BufferedImage tankL,tankR,tankU,tankD;
   /*子弹四个方向的图片*/
   public  static BufferedImage bultL,bultR,bultU,bultD;
   /*爆炸的图片*/
   public  static BufferedImage[] explodes = new BufferedImage[16]; 
   static {
	   try {
		/*坦克*/
		tankL=ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("imge/tankL.gif"));
		tankR=ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("imge/tankR.gif"));
		tankU=ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("imge/tankU.gif"));
		tankD=ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("imge/tankD.gif"));
	    /*子弹*/
		bultL=ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("imge/bulletL.gif"));
		bultR=ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("imge/bulletR.gif"));
		bultU=ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("imge/bulletU.gif"));
		bultD=ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("imge/bulletD.gif"));
	   
		for(int i=0;i<16;i++) {
			explodes[i]=ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("imge/e"+(i+1)+".gif"));
		}
	   
	   } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }
}
