package edu.hitsz.shootstrategy;
import java.util.*;
import edu.hitsz.bullet.*;
import edu.hitsz.aircraft.*;

/**
 * strategy
 */
public interface Strategy {
    List<BaseBullet> executeshoot(AbstractAircraft fly, int direction, int shootNum, int power);
}