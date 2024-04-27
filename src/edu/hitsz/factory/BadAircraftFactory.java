package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractBadAircraft;

public interface BadAircraftFactory {
    public abstract AbstractBadAircraft createBad(); 
}
