package edu.hitsz.application;

import edu.hitsz.factory.*;

public class Game_Tough extends Game{
    //BOSS的设置
    protected int boss_hp;//BOSS的血量
    private final double rate_hp;//每次产生BOSS血量的提升比例
    //BOSS机外敌机的设置
    public final double step = 0.01;//敌机属性（血量、速度）提升的倍率步长
    public final double P_rate = 0.987;//各种敌机产生概率的变化
    public Game_Tough(){
        super();
        bg_pic = ImageManager.BACKGROUND_IMAGE3;
        //最大敌机数量
        enemyMaxNumber = 10;
        //控制BOSS属性变化
        having_boss = true;//在该模式难度下有BOSS产生
        boss_score = 180;//BOSS产生的得分阈值
        boss_hp = 200;//BOSS的血量
        rate_hp = 1.2;//BOSS的血量提升比例
        //控制除BOSS机之外敌机的属性（血量、速度）变化
        MobFactory.setStep(step);
        EliteFactory.setStep(step);
        ElietePlusFactory.setStep(step);
        //控制各敌机的概率
        enemy_1_2 = 0.65;
        enemy_2_3 = 0.9;
        //控制敌机的产生和射击频率
        cycleDuration = 300;
        //英雄机的射击频率
        cycleDurationHero = 350;
    }

    @Override
    public void generate_BOSS() {
        BadAircraftFactory badfactory = new BossFactory(boss_hp);
        System.out.println("Generate BOSS with hp "+ boss_hp + ";" + "是上一个产生的BOSS敌机的" + rate_hp + "倍");
        enemyAircrafts.add(badfactory.createBad());
        boss_hp = (int)(boss_hp*rate_hp);
    }
    @Override
    public void generate_Other_Bad() {
        // 新敌机产生
        //更新倍率
        EliteFactory.UpRate_bad();
        ElietePlusFactory.UpRate_bad();
        MobFactory.UpRate_bad();
        String result = String.format("%.2f", MobFactory.getRate_bad());
        System.out.println("提高难度！敌机属性(速度、hp)提高倍率"+ result);
        //更新产生频率：
//        timeInterval = timeInterval*time_circle_rate
        //依据概率产生相应敌机
        if(enemy_1_2>=0.10){
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
