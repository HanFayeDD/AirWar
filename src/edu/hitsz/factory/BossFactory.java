package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractBadAircraft;
import edu.hitsz.aircraft.*;
import edu.hitsz.application.*;
import edu.hitsz.shootstrategy.CircleShoot;;


public class BossFactory implements BadAircraftFactory{
    public AbstractBadAircraft createBad(){
        double rand = Math.random();
        int dir = (rand>=0.5) ? 1 : -1;
        return new Boss(
            (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.BOSS_IMAGE.getWidth())),
            (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
            dir,
            3,
            100,
            new CircleShoot()
            );
    }
}
