package edu.hitsz.shootstrategy;

import java.util.List;

import edu.hitsz.bullet.*;

import edu.hitsz.aircraft.*;

import java.util.LinkedList;

public class ScatterShoot implements Strategy{
    private int[] sx_arr = new int[]{-2, -1, 0, 1, 2};
    private int[] sy_arr = new int[]{2 ,2, 2, 2, 2};

    public ScatterShoot(){
    }

    @Override
    public List<BaseBullet> executeshoot(AbstractAircraft fly, int direction, int shootNum, int power) {
        // TODO Auto-generated method stub
        List<BaseBullet> res = new LinkedList<>();
        int x = fly.getLocationX();
        int y = fly.getLocationY()+direction*2;
        BaseBullet bullet;
        boolean ishero = (fly instanceof HeroAircraft);
        for(int i=0; i<shootNum; i++){
            if(ishero){
                bullet = new HeroBullet(x,y, sx_arr[i]*2, sy_arr[i]*direction*2, power);
            }
            else{
                bullet = new EnemyBullet(x,y, (sx_arr[i+1]+fly.getSpeedX()), (sy_arr[i+1]+fly.getSpeedY()), power);
            }
            res.add(bullet);
        }
        return res;
    }
    
}
