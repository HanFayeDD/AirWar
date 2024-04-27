package edu.hitsz.prop;

import edu.hitsz.aircraft.*;
import edu.hitsz.shootstrategy.ScatterShoot;

public class Prop_bullet extends AbstractProp{
    public Prop_bullet(int X, int Y, int speedX, int speedY){
        super(X, Y, speedX, speedY);
    }
    public void activeProp(){
        System.out.println("Fire Supply Active!");
        HeroAircraft.getInstance().setStrategy(new ScatterShoot());
        this.vanish();
    }
}
