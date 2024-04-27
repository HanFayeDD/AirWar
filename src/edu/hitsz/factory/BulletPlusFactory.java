package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractBadAircraft;
import edu.hitsz.prop.AbstractProp;
import edu.hitsz.aircraft.*;
import edu.hitsz.prop.*;
import edu.hitsz.application.*;

public class BulletPlusFactory implements PropFactory{
    public AbstractProp createProp(AbstractBadAircraft efly) {
        if(!(efly instanceof Boss)){
            return new Prop_bulletplus(efly.getLocationX(), efly.getLocationY(), 0, efly.getSpeedY());
        }
        else{
            int width  = ImageManager.BOSS_IMAGE.getWidth();
            int height = ImageManager.BOSS_IMAGE.getHeight();
            int symx = Math.random() >= 0.5 ? 1 : -1;
            int symy = Math.random() >= 0.5 ? 1 : -1;
            return new Prop_bulletplus(         
                       (int)(efly.getLocationX()+symx*((width/2)*0.4)),
                       (int)(efly.getLocationY()+symy*((height/2)*0.4)),
                       0,
                       3);
        }
    }
    
}
