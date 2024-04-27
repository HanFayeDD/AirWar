package edu.hitsz.factory;

import edu.hitsz.application.Main;
import edu.hitsz.application.ImageManager;
import edu.hitsz.aircraft.*;
import edu.hitsz.shootstrategy.*;

public class EliteFactory implements BadAircraftFactory{
    public AbstractBadAircraft createBad(){
        double rand = Math.random();
        int dir = (rand>=0.5) ? 1 : -1;
        return new EliteEnemy(
            (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
            (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
            dir*2,
            5,
            30,
            new DefaultShoot()
            );
    }
}
