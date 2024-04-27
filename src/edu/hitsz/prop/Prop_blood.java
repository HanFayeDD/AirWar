package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;

public class Prop_blood extends AbstractProp{
    public Prop_blood(int X, int Y, int speedX, int speedY){
        super(X, Y, speedX, speedY);
    }
    public void activeProp(){
        HeroAircraft.getInstance().IncreasingHp(30);
        this.vanish();
    }
    
    /**
     * 确认撞击后自动使道具生效
     */
}
