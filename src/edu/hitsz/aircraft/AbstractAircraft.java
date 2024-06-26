package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.prop.AbstractProp;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.shootstrategy.*;
import java.util.List;

/**
 * 所有种类飞机的抽象父类：
 * 敌机（BOSS, ELITE, MOB），英雄飞机
 *
 * @author hitsz
 */
public abstract class AbstractAircraft extends AbstractFlyingObject {
    /**+=
     * 生命值
     */
    protected int maxHp;
    protected int hp;
    protected Strategy shoot_way;

    public AbstractAircraft(int locationX, int locationY, int speedX, int speedY, int hp, Strategy ashoot_way) {
        super(locationX, locationY, speedX, speedY);
        this.hp = hp;
        this.maxHp = hp;
        shoot_way = ashoot_way;
    }

    public void decreaseHp(int decrease){
        hp -= decrease;
        if(hp <= 0){
            hp=0;
            vanish();
        }
    }

    public int getHp() {
        return hp;
    }
    public synchronized void setStrategy(Strategy ashoot_way){shoot_way = ashoot_way;
    }
    /**
     * 飞机射击方法，可射击对象必须实现
     * @return
     *  可射击对象需实现，返回子弹
     *  非可射击对象空实现，返回null
     */
    public abstract List<BaseBullet> shoot();

    public synchronized Strategy getShoot_way(){
        return shoot_way;
    }

    public int getMaxHp(){
        return maxHp;
    }

}


