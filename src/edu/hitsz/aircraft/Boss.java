package edu.hitsz.aircraft;
import edu.hitsz.prop.*;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.shootstrategy.*;
import java.util.LinkedList;
import java.util.List;
import edu.hitsz.factory.*;
import edu.hitsz.application.*;

public class Boss extends AbstractBadAircraft{
    private int direction = 1;
    private int shootNum = 20;
    private int power = 20;

    public Boss(int locationX, int locationY, int speedX, int speedY, int hp, Strategy ashoot_way){
        super(locationX, locationY, speedX, speedY, hp, ashoot_way);
    }

    @Override
    public void forward(){
        if(this.locationY>=(int)(0.18*Main.WINDOW_HEIGHT)){
            this.speedY=0;
        }
        super.forward();
    }

    @Override
    public void update(){
    }

    public List<BaseBullet> shoot(){
       return shoot_way.executeshoot(this, direction, shootNum, power);
    }
    
    public List<AbstractProp> dropProp(){
        int sumProp = (int)(Math.random()*100+1)%3+1;//产生[1,3]个道具
        List<AbstractProp> res = new LinkedList<>();
        for(int i=0; i<sumProp; i++){
            int which_prop = (int)(Math.random()*100+1)%4;
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
                res.add((pf.createProp(this)));
            }
        }
        return res;
    }

}
