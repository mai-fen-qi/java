package com.test.tank;


public class Main {

    public static void main(String[] args) throws InterruptedException {
	// write your code  here

   TankFram f=new TankFram();
   
   /*初始化敌方坦克*/
   for(int i=0;i<5;i++) {
	   f.tks.add(new Tank(50+i*70,100,Dir.DOWN,f,Group.Rad));
   } 
   	   //f.myTank.add(new Tank(200,600,Dir.UP,f,Group.Blue));
   		 
   
   while(true){
       Thread.sleep(50);
       /*重画一次*/
       f.repaint();
   }

    }
}
