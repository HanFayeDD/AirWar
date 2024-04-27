package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractBadAircraft;
import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.Prop_blood;
import edu.hitsz.application.*;
import edu.hitsz.aircraft.Boss;


public class BloodFactory implements PropFactory{
    public AbstractProp createProp(AbstractBadAircraft efly){
        if(!(efly instanceof Boss)){
            return new Prop_blood(efly.getLocationX(), efly.getLocationY(), 0, efly.getSpeedY());
        }
        else{
            int width  = ImageManager.BOSS_IMAGE.getWidth();
            int height = ImageManager.BOSS_IMAGE.getHeight();
            int symx = Math.random() >= 0.5 ? 1 : -1;
            int symy = Math.random() >= 0.5 ? 1 : -1;
            return new Prop_blood(         
                       (int)(efly.getLocationX()+symx*((width/2)*0.4)),
                       (int)(efly.getLocationY()+symy*((height/2)*0.4)),
                       0,
                       3);
        }
    }
}
