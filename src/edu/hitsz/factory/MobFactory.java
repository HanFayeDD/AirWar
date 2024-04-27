package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractBadAircraft;
import edu.hitsz.application.Main;
import edu.hitsz.application.ImageManager;
import edu.hitsz.aircraft.*;

public class MobFactory implements BadAircraftFactory{
    public AbstractBadAircraft createBad(){
        return new MobEnemy(
                            (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELI_ENEMY_IMAGE.getWidth())),
                            (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                            0,
                            5,
                            30,
                            null);
    }
}
