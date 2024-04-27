package edu.hitsz.shootstrategy;

import java.util.List;
import java.util.LinkedList;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.*;

public class DefaultShoot implements Strategy{
    public DefaultShoot(){
        
    }
    @Override
    public List<BaseBullet> executeshoot(AbstractAircraft fly, int direction, int shootNum, int power) {
        // TODO Auto-generated method stub
        List<BaseBullet> res = new LinkedList<>();
        int x = fly.getLocationX();
        int y = fly.getLocationY() + direction*2;
        int speedX = 0;
        int speedY = fly.getSpeedY() + direction*5;
        boolean ishero = (fly instanceof HeroAircraft);
        BaseBullet bullet;
        for(int i=0; i<shootNum; i++){
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            if(ishero){
                bullet = new HeroBullet(x + (i*2 - shootNum + 1)*10, y, speedX, speedY, power);
            }
            else{
                bullet = new EnemyBullet(x + (i*2 - shootNum + 1)*10, y, speedX, speedY, power);
            }
            res.add(bullet);
        }
        return res;
        
    }
    
}
