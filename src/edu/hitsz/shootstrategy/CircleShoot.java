package edu.hitsz.shootstrategy;

import java.util.List;

import edu.hitsz.bullet.*;

import java.util.LinkedList;

import edu.hitsz.aircraft.*;

public class CircleShoot implements Strategy{
    public CircleShoot(){
    }
    @Override
    public List<BaseBullet> executeshoot(AbstractAircraft fly, int direction, int shootNum, int power) {
        // TODO Auto-generated method stub
        List<BaseBullet> res = new LinkedList<>();
        int x_center = fly.getLocationX();
        int y_center = fly.getLocationY();
        BaseBullet bullet;
        float angel = 0;
        boolean ishero = (fly instanceof HeroAircraft);
        int speedxy = 10;
        for(int i=0; i<shootNum; i++){
            if(ishero){
                bullet = new HeroBullet(x_center,
                                     y_center,
                                     (int)(fly.getSpeedX()+speedxy*Math.sin(Math.toRadians(angel))),
                                     (int)(fly.getSpeedY()+speedxy*Math.cos(Math.toRadians(angel))),
                                     power);
            }
            else{
                bullet = new EnemyBullet(x_center,
                                     y_center,
                                     (int)(fly.getSpeedX()+speedxy*Math.sin(Math.toRadians(angel))),
                                     (int)(fly.getSpeedY()+speedxy*Math.cos(Math.toRadians(angel))),
                                     power);
            }
            res.add(bullet);
            angel += 18;
        }
        return res;
    }
    
}
