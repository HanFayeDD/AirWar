package edu.hitsz.application;

import edu.hitsz.factory.*;

import java.awt.image.BufferedImage;

public class Game_Normal extends Game{
    protected int boss_hp;
    private double rate_bad = 1.0;
    public final double step = 0.005;

    public  Game_Normal(){
        super();
        bg_pic = ImageManager.BACKGROUND_IMAGE2;
        enemyMaxNumber = 6;
        having_boss = true;
        boss_score = 90;//产生BOSS的得分阈值
        boss_hp = 100;
        MobFactory.setStep(step);
        EliteFactory.setStep(step);
        ElietePlusFactory.setStep(step);
    }

    @Override
    public void generate_BOSS() {
        BadAircraftFactory badfactory = new BossFactory(boss_hp);
        System.out.println("Generate BOSS with hp "+ boss_hp);
        enemyAircrafts.add(badfactory.createBad());
    }

    @Override
    public void generate_Other_Bad() {
        // 新敌机产生
        EliteFactory.UpRate_bad();
        ElietePlusFactory.UpRate_bad();
        MobFactory.UpRate_bad();
        String result = String.format("%.3f", MobFactory.getRate_bad());
        System.out.println("提高难度！敌机属性(速度、hp)提高倍率"+ result);
        int which_enemy = (int)(Math.random()*10+1)%5;
        if (enemyAircrafts.size() < enemyMaxNumber && which_enemy<3) {
            BadAircraftFactory badfactory = new MobFactory();
            enemyAircrafts.add(badfactory.createBad());
        }
        if(enemyAircrafts.size() < enemyMaxNumber && which_enemy==3){
            BadAircraftFactory badfactory = new EliteFactory();
            enemyAircrafts.add(badfactory.createBad());
        }
        if(enemyAircrafts.size() < enemyMaxNumber && which_enemy==4){
            BadAircraftFactory badfactory = new ElietePlusFactory();
            enemyAircrafts.add(badfactory.createBad());
        }

    }
}
