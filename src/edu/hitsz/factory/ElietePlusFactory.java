package edu.hitsz.factory;
import edu.hitsz.aircraft.*;
import edu.hitsz.application.*;
import edu.hitsz.shootstrategy.ScatterShoot;
public class ElietePlusFactory implements BadAircraftFactory{
    public AbstractBadAircraft createBad(){
        double rand = Math.random();
        int dir = (rand>=0.5) ? 1 : -1;
        return new ElitePlusEnemy(
            (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELI_PLUS_ENEMY_IMAGE.getWidth())),
            (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
            dir,
            3,
            30,
            new ScatterShoot()
            );
    }
}
