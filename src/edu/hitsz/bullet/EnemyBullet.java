package edu.hitsz.bullet;

import edu.hitsz.application.Badthing;

/**
 * @Author hitsz
 */
public class EnemyBullet extends BaseBullet  {

    public EnemyBullet(int locationX, int locationY, int speedX, int speedY, int power) {
        super(locationX, locationY, speedX, speedY, power);
    }

    @Override
    public void update() {
        this.vanish();
    }
}
