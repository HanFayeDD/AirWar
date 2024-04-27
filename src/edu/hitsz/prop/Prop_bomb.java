package edu.hitsz.prop;

import edu.hitsz.aircraft.*;

public class Prop_bomb extends AbstractProp{
    public Prop_bomb(int X, int Y, int speedX, int speedY){
        super(X, Y, speedX, speedY);
    }
    public void activeProp(){
        System.out.println("Bomb Supply Active!");
        this.vanish();
    }
}
