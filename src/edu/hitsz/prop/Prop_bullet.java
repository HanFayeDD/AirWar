package edu.hitsz.prop;

import edu.hitsz.aircraft.*;
import edu.hitsz.application.Game;
import edu.hitsz.shootstrategy.CircleShoot;
import edu.hitsz.shootstrategy.DefaultShoot;
import edu.hitsz.shootstrategy.ScatterShoot;

public class Prop_bullet extends AbstractProp{
    public Prop_bullet(int X, int Y, int speedX, int speedY){
        super(X, Y, speedX, speedY);
    }
    public void activeProp(){
        this.vanish();
        Runnable r = ()->{
            System.out.println("Bullet_Plus Supply Active!");
            HeroAircraft.getInstance().setStrategy(new ScatterShoot());
            for (int i = 0; i < 6; i++) {
                try {
                    if(Game.getgameOverFlag()){
                        break;
                    }
                    Thread.sleep(1000);
                    System.out.println(HeroAircraft.getInstance().getShoot_way()+"第 "+ (i+1) + " s");
                } catch (InterruptedException e) {
                    System.out.println("原有道具被中断");
                    break;
                }
            }
            System.out.println("change to DefaultShootWay");
            HeroAircraft.getInstance().setStrategy(new DefaultShoot());
        };
        while(currentthread !=null && currentthread.isAlive()){
            currentthread.interrupt();
        }
        currentthread = new Thread(r);
        currentthread.start();

    }
}
