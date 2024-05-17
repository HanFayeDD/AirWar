package edu.hitsz.aircraft;

import java.util.List;

import edu.hitsz.application.Badthing;
import edu.hitsz.prop.*;

import edu.hitsz.shootstrategy.*;

public abstract class AbstractBadAircraft extends AbstractAircraft implements Badthing {
    public AbstractBadAircraft(int locationX, int locationY, int speedX, int speedY, int hp, Strategy ashoot_way){
        super(locationX, locationY, speedX, speedY, hp, ashoot_way);
    }
    public abstract List<AbstractProp> dropProp();
}