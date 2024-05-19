package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractBadAircraft;
import edu.hitsz.application.Main;
import edu.hitsz.application.ImageManager;
import edu.hitsz.aircraft.*;

public class MobFactory implements BadAircraftFactory{
    private static double rate_bad = 1;
    private static double step = 0.2;
    public AbstractBadAircraft createBad(){
        return new MobEnemy(
                            (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELI_ENEMY_IMAGE.getWidth())),
                            (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                            0,
                            (int)(5*rate_bad),
                            (int)(20*rate_bad),
                            null);
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
