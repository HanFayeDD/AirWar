package edu.hitsz.application;

import edu.hitsz.factory.BadAircraftFactory;
import edu.hitsz.factory.ElietePlusFactory;
import edu.hitsz.factory.EliteFactory;
import edu.hitsz.factory.MobFactory;

public class Game_Easy extends Game{
    public Game_Easy(){
        super();
        bg_pic = ImageManager.BACKGROUND_IMAGE;
        enemyMaxNumber = 4;
        having_boss = false;

    }

    @Override
    public void generate_BOSS() {

    }

    @Override
    public void generate_Other_Bad() {
        // 新敌机产生
        int which_enemy = (int)(Math.random()*100+1)%10;
        if (enemyAircrafts.size() < enemyMaxNumber && which_enemy<6) {
            BadAircraftFactory badfactory = new MobFactory();
            enemyAircrafts.add(badfactory.createBad());
        }
        if(enemyAircrafts.size() < enemyMaxNumber && 6<=which_enemy && which_enemy<=8){
            BadAircraftFactory badfactory = new EliteFactory();
            enemyAircrafts.add(badfactory.createBad());
        }
        if(enemyAircrafts.size() < enemyMaxNumber && which_enemy==9){
            BadAircraftFactory badfactory = new ElietePlusFactory();
            enemyAircrafts.add(badfactory.createBad());
        }
    }

    public static void main(String[] args) {
        var pp = new Game_Easy();
        System.out.println(pp.enemyMaxNumber);
    }
}
