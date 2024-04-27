package edu.hitsz.prop;

import edu.hitsz.aircraft.*;
import edu.hitsz.shootstrategy.CircleShoot;

public class Prop_bulletplus extends AbstractProp{
    public Prop_bulletplus(int X, int Y, int speedX, int speedY){
        super(X, Y, speedX, speedY);
    }
    public void activeProp(){
        System.out.println("Bullet_Plus Supply Active!");
        HeroAircraft.getInstance().setStrategy(new CircleShoot());
        this.vanish();
    }
}
