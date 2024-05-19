package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractBadAircraft;

public interface BadAircraftFactory {
    abstract AbstractBadAircraft createBad();
}
