package edu.hitsz.application;

import edu.hitsz.factory.*;

public class Game_Tough extends Game{
    protected int boss_hp;
    private final double rate_hp;
    public final double step = 0.01;
    public final double P_rate = 0.95;
    public  Game_Tough(){
        super();
        bg_pic = ImageManager.BACKGROUND_IMAGE3;
        //最大敌机数量
        enemyMaxNumber = 8;
        //控制BOSS属性变化
        having_boss = true;
        boss_score = 70;//BOSS产生的得分阈值
        boss_hp = 120;
        rate_hp = 1.2;
        //控制除BOSS机之外的属性
        MobFactory.setStep(step);
        EliteFactory.setStep(step);
        ElietePlusFactory.setStep(step);
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
        //依据概率产生相应敌机
        enemy_1_2 = enemy_1_2*(P_rate-0.02);
        enemy_2_3 = enemy_2_3*P_rate;
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
        else{
            BadAircraftFactory badfactory = new ElietePlusFactory();
            enemyAircrafts.add(badfactory.createBad());
        }
    }


}
