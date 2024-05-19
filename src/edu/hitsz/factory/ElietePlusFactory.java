package edu.hitsz.factory;
import edu.hitsz.aircraft.*;
import edu.hitsz.application.*;
import edu.hitsz.shootstrategy.ScatterShoot;
public class ElietePlusFactory implements BadAircraftFactory{
    private static double rate_bad = 1;
    private static double step = 0.005;
    public AbstractBadAircraft createBad(){
        double rand = Math.random();
        int dir = (rand>=0.5) ? 1 : -1;
        return new ElitePlusEnemy(
            (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELI_PLUS_ENEMY_IMAGE.getWidth())),
            (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                (int)(rate_bad*dir),
                (int)(3*rate_bad),
                (int)(35*rate_bad),
            new ScatterShoot()
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
