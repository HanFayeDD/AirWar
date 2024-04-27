package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractBadAircraft;
import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.prop.*;
import edu.hitsz.application.*;
import edu.hitsz.aircraft.Boss;

public class BombFactory implements PropFactory{
    public AbstractProp createProp(AbstractBadAircraft efly){
        if(!(efly instanceof Boss)){
            return new Prop_bomb(efly.getLocationX(), efly.getLocationY(), 0, efly.getSpeedY());
        }
        else{
            int width  = ImageManager.BOSS_IMAGE.getWidth();
            int height = ImageManager.BOSS_IMAGE.getHeight();
            int symx = Math.random() >= 0.5 ? 1 : -1;
            int symy = Math.random() >= 0.5 ? 1 : -1;
            return new Prop_bomb(         
                       (int)(efly.getLocationX()+symx*((width/2)*0.4)),
                       (int)(efly.getLocationY()+symy*((height/2)*0.4)),
                       0,
                       3);

        }
    }

    // public static void main(String[] args) {
    //     System.out.println(ImageManager.BOSS_IMAGE.getWidth());
    //     System.out.println(ImageManager.BOSS_IMAGE.getHeight());
    //     System.out.println(ImageManager.ELI_PLUS_ENEMY_IMAGE.getWidth());
    // }
}
