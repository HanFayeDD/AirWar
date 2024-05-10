package edu.hitsz.timecontrol;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.shootstrategy.CircleShoot;
import edu.hitsz.shootstrategy.DefaultShoot;
import edu.hitsz.shootstrategy.ScatterShoot;
import edu.hitsz.shootstrategy.Strategy;


//public class TickingThread extends Thread {
//    int loop = 5;
//
//    private  boolean flag;
//
//    public TickingThread(){
//        flag = true;
//    }
//
//    @Override
//    public void run(){
//        while (flag){
//            Strategy current_sgy = HeroAircraft.getInstance().getShoot_way();
//            if(!(current_sgy instanceof DefaultShoot)){
//                for(int i=0; i<loop; i++){
//                    try {
//                        Thread.sleep(1000);
//                        System.out.println(HeroAircraft.getInstance().getShoot_way().getClass().getName()+"道具第 " + (i+1) + 's');
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//                HeroAircraft.getInstance().setStrategy(new DefaultShoot());
//                System.out.println("Change to default shoot_way");
//            }
//        }
//
//    }
//
//    public void stopThread(){
//        this.flag = false;
//    }
//
//    public static void main(String[] args) throws Exception{
//        TickingThread timmer = new TickingThread();
//        timmer.start();
//        HeroAircraft.getInstance().setStrategy(new ScatterShoot());
//        timmer.stopThread();
//    }
//}
public class TickingThread extends Thread {
    int loop = 5;

    private  boolean flag;

    public TickingThread(){
        flag = true;
    }

    @Override
    public void run(){
        while (flag){
            Strategy current_sgy = HeroAircraft.getInstance().getShoot_way();
            if(!(current_sgy instanceof DefaultShoot)){
                for(int i=0; i<loop; i++){
                    try {
                        Thread.sleep(1000);
                        System.out.println(HeroAircraft.getInstance().getShoot_way().getClass().getName()+"道具第 " + (i+1) + 's');
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                HeroAircraft.getInstance().setStrategy(new DefaultShoot());
                System.out.println("Change to default shoot_way");
            }
        }
    }

    public void stopThread(){
        this.flag = false;
    }

    public static void main(String[] args) throws Exception{
        TickingThread timmer = new TickingThread();
        timmer.start();
        HeroAircraft.getInstance().setStrategy(new ScatterShoot());
        timmer.stopThread();
    }
}