package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.HeroBullet;
import edu.hitsz.application.Main;
import edu.hitsz.application.ImageManager;
import java.util.LinkedList;
import java.util.List;
import edu.hitsz.shootstrategy.*;
import edu.hitsz.shootstrategy.*;
/**
 * 英雄飞机，游戏玩家操控
 * @author hitsz
 */
public class HeroAircraft extends AbstractAircraft {

    /**饿汉模式*/
    private static HeroAircraft instance;
    /**
     * 子弹一次发射数量
     */
    private int shootNum = 1;

    /**
     * 子弹伤害
     */
    private int power = 30;

    /**
     * 子弹射击方向 (向上发射：1，向下发射：-1)
     */
    private int direction = -1;

    /**
     * @param locationX 英雄机位置x坐标
     * @param locationY 英雄机位置y坐标
     * @param speedX 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param speedY 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param hp    初始生命值
     */
    private HeroAircraft(int locationX, int locationY, int speedX, int speedY, int hp, Strategy ashoot_way) {
        super(locationX, locationY, speedX, speedY, hp, ashoot_way);
    }


    public static HeroAircraft getInstance(){
        if(HeroAircraft.instance==null){
            HeroAircraft.instance = new HeroAircraft(                                          
                Main.WINDOW_WIDTH / 2,
                Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight() ,
                0, 0, 10000, new DefaultShoot());    
            return HeroAircraft.instance;
        }
        else{
            return HeroAircraft.instance;
        }
    }

    @Override
    public void forward() {
        // 英雄机由鼠标控制，不通过forward函数移动
    }

    @Override
    /**
     * 通过射击产生子弹
     * @return 射击出的子弹List
     */
    public List<BaseBullet> shoot() {
        if(shoot_way instanceof ScatterShoot){
            return shoot_way.executeshoot(instance, direction, 5, power);
        }
        if(shoot_way instanceof CircleShoot){
            return shoot_way.executeshoot(instance, direction, 20, power);
        }
        return shoot_way.executeshoot(instance, direction, shootNum, power);
    }

    public void IncreasingHp(int num){
        this.hp = Math.min(hp+num, this.maxHp);
    }

}
