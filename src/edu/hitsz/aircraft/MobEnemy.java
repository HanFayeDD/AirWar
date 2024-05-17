package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.prop.AbstractProp;
import edu.hitsz.shootstrategy.Strategy;

import java.util.LinkedList;
import java.util.List;

/**
 * 普通敌机
 * 不可射击
 *
 * @author hitsz
 */
public class MobEnemy extends AbstractBadAircraft {

    public MobEnemy(int locationX, int locationY, int speedX, int speedY, int hp, Strategy ashoot_way) {
        super(locationX, locationY, speedX, speedY, hp, ashoot_way);
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }

    @Override
    public void update(){
        this.decreaseHp(this.getHp());
    }

    @Override
    public List<BaseBullet> shoot() {
        return new LinkedList<>();//返回空的列表
    }

    public List<AbstractProp> dropProp(){
        return new LinkedList<AbstractProp>();
    }
}
