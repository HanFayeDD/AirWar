package edu.hitsz.application;

import edu.hitsz.factory.*;

import java.awt.image.BufferedImage;

public class Game_Normal extends Game{
    protected int boss_hp;
    //BOSS外的敌机的属性倍率
    public final double step = 0.005;
    public final double P_rate = 0.995;
    public  Game_Normal(){
        super();
        bg_pic = ImageManager.BACKGROUND_IMAGE2;
        enemyMaxNumber = 7;
        having_boss = true;
        boss_score = 150;//产生BOSS的得分阈值
        boss_hp = 150;
        MobFactory.setStep(step);
        EliteFactory.setStep(step);
        ElietePlusFactory.setStep(step);
        enemy_1_2 = 0.70;
        enemy_2_3 = 0.9;
        cycleDuration = 400;
        cycleDurationHero = 400;
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
        //更新倍率(倍率小于1.8才更新)
        if (MobFactory.rate_bad <= 1.8){
            EliteFactory.UpRate_bad();
            ElietePlusFactory.UpRate_bad();
            MobFactory.UpRate_bad();
        }
        String result = String.format("%.3f", MobFactory.getRate_bad());
        System.out.println("提高难度！敌机属性(速度、hp)提高倍率"+ result);
        //依据倍率产生相关敌机
        if(enemy_1_2>=0.2){
            enemy_1_2 = enemy_1_2*(P_rate-0.02);
            enemy_2_3 = enemy_2_3*P_rate;
        }
        double rand = Math.random();
        String p1 = String.format("%.2f", enemy_1_2);
        String p2 = String.format("%.2f", enemy_2_3-enemy_1_2);
        String p3 = String.format("%.2f", 1-enemy_2_3);
        System.out.println("提高难度！Mob概率"+p1+" Elite概率"+p2+" ElitePlus概率"+p3);
        if (enemyAircrafts.size() < enemyMaxNumber && rand<enemy_1_2) {
            BadAircraftFactory badfactory = new MobFactory();
            enemyAircrafts.add(badfactory.createBad());
        }
        else if(enemyAircrafts.size() < enemyMaxNumber && rand<enemy_2_3){
            BadAircraftFactory badfactory = new EliteFactory();
            enemyAircrafts.add(badfactory.createBad());
        }
        else if(enemyAircrafts.size() < enemyMaxNumber){
            BadAircraftFactory badfactory = new ElietePlusFactory();
            enemyAircrafts.add(badfactory.createBad());
        }

    }
}
