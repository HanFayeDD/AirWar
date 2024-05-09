package edu.hitsz.prop;

import edu.hitsz.aircraft.*;
import edu.hitsz.musiccontrol.MusicThread;

public class Prop_bomb extends AbstractProp{
    public Prop_bomb(int X, int Y, int speedX, int speedY){
        super(X, Y, speedX, speedY);
    }
    public void activeProp(){
        new MusicThread("src/videos/bomb_explosion.wav").start();
        System.out.println("Bomb Supply Active!");
        this.vanish();
    }
}
