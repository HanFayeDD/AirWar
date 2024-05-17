package edu.hitsz.aircraft;
import edu.hitsz.prop.*;
import edu.hitsz.shootstrategy.Strategy;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.factory.*;
import java.util.LinkedList;
import java.util.List;
import edu.hitsz.application.*;


public class ElitePlusEnemy extends AbstractBadAircraft{
    private int direction = 1;
    private int shootNum = 3;
    private int power= 20;//每个子弹的力量

    public ElitePlusEnemy(int locationX, int locationY, int speedX, int speedY, int hp, Strategy ashoot_way){
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
        this.decreaseHp(20);
    }

    public List<AbstractProp> dropProp(){
        int which_prop = (int)(Math.random()*100+1)%8;
        List<AbstractProp> res = new LinkedList<>();
        if(which_prop==0){
            PropFactory pf = new BloodFactory();
            res.add(pf.createProp(this));
        }      
        else if(which_prop==1){
            PropFactory pf = new BombFactory();
            res.add(pf.createProp(this));
        }
        else if(which_prop==2){
            PropFactory pf = new BulletFactory();
            res.add(pf.createProp(this));
        }
        else if(which_prop==3){
            PropFactory pf = new BulletPlusFactory();
            res.add(pf.createProp(this));
        }
        return res;
    }

    public List<BaseBullet> shoot(){
        return shoot_way.executeshoot(this, direction, shootNum, power);
    }
}
