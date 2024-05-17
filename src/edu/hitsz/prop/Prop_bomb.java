package edu.hitsz.prop;

import edu.hitsz.application.Badthing;
import edu.hitsz.application.MusicManager;
import edu.hitsz.musiccontrol.MusicThread;

import java.util.LinkedList;
import java.util.List;

public class Prop_bomb extends AbstractProp{
    private final List<Badthing> badthings;

    public Prop_bomb(int X, int Y, int speedX, int speedY){
        super(X, Y, speedX, speedY);
        badthings = new LinkedList<>();
    }

    public void activeProp(){
        new MusicThread(MusicManager.BOMB_EXPLOSION).start();
        System.out.println("Bomb Supply Active!");
        notifyAllBad();
        this.vanish();
    }

    public void addBadthing(Badthing thebad){
        badthings.add(thebad);
    }

    public void deleteBadthing(Badthing thebad){
        badthings.remove(thebad);
    }

    public void addAll(List<? extends Badthing> badthing_list){
        badthings.addAll(badthing_list);
    }

    public void notifyAllBad(){
        for(Badthing thebad : badthings){
            thebad.update();
        }
    }

}
