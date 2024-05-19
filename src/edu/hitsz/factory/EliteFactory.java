package edu.hitsz.factory;

import edu.hitsz.application.Main;
import edu.hitsz.application.ImageManager;
import edu.hitsz.aircraft.*;
import edu.hitsz.shootstrategy.*;

public class EliteFactory implements BadAircraftFactory{
    private static double rate_bad = 1;
    private static double step=0.005;
    public AbstractBadAircraft createBad(){
        double rand = Math.random();
        int dir = (rand>=0.5) ? 1 : -1;
        return new EliteEnemy(
            (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
            (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                (int)(dir*2*rate_bad),
                (int)(5*rate_bad),
                (int)(30*rate_bad),
            new DefaultShoot()
            );
    }
    public static void setStep(double num){
        step = num;
    }
    public static void UpRate_bad(){
        rate_bad = rate_bad + step;
    }
    public static double getRate_bad(){
        return rate_bad;
    }
}
