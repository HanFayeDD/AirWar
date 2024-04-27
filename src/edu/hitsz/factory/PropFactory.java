package edu.hitsz.factory;

import edu.hitsz.prop.AbstractProp;
import edu.hitsz.aircraft.*;

public interface PropFactory {
    public abstract AbstractProp createProp(AbstractBadAircraft efly);
}
