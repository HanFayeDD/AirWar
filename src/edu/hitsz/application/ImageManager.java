package edu.hitsz.application;


import edu.hitsz.aircraft.Boss;
import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.aircraft.MobEnemy;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;
import edu.hitsz.prop.Prop_blood;
import edu.hitsz.prop.Prop_bomb;
import edu.hitsz.prop.Prop_bullet;
import edu.hitsz.prop.*;
import edu.hitsz.aircraft.ElitePlusEnemy;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 综合管理图片的加载，访问
 * 提供图片的静态访问方法
 *
 * @author hitsz
 */
public class ImageManager {

    /**
     * 类名-图片 映射，存储各基类的图片 <br>
     * 可使用 CLASSNAME_IMAGE_MAP.get( obj.getClass().getName() ) 获得 obj 所属基类对应的图片
     */
    private static final Map<String, BufferedImage> CLASSNAME_IMAGE_MAP = new HashMap<>();

    public static BufferedImage BACKGROUND_IMAGE;
    public static BufferedImage BACKGROUND_IMAGE2;
    public static BufferedImage BACKGROUND_IMAGE3;
    public static BufferedImage BACKGROUND_IMAGE4;
    public static BufferedImage BACKGROUND_IMAGE5;

    public static BufferedImage HERO_IMAGE;
    public static BufferedImage HERO_BULLET_IMAGE;
    public static BufferedImage ENEMY_BULLET_IMAGE;
    public static BufferedImage MOB_ENEMY_IMAGE;
    public static BufferedImage ELI_ENEMY_IMAGE;
    public static BufferedImage PROP_BLOOD;
    public static BufferedImage PROP_BOMB;
    public static BufferedImage PROP_BULLET;
    public static BufferedImage PROP_BULLETPLUS;
    public static BufferedImage ELI_PLUS_ENEMY_IMAGE;
    public static BufferedImage BOSS_IMAGE;

    static {
        try {

            BACKGROUND_IMAGE = ImageIO.read(new FileInputStream("src/images/bg.jpg"));
            BACKGROUND_IMAGE2 = ImageIO.read(new FileInputStream("src/images/bg2.jpg"));
            BACKGROUND_IMAGE3 = ImageIO.read(new FileInputStream("src/images/bg3.jpg"));
            BACKGROUND_IMAGE4 = ImageIO.read(new FileInputStream("src/images/bg4.jpg"));
            BACKGROUND_IMAGE5 = ImageIO.read(new FileInputStream("src/images/bg5.jpg"));
            HERO_IMAGE = ImageIO.read(new FileInputStream("src/images/hero.png"));
            MOB_ENEMY_IMAGE = ImageIO.read(new FileInputStream("src/images/mob.png"));
            ELI_ENEMY_IMAGE = ImageIO.read(new FileInputStream("src/images/elite.png"));
            HERO_BULLET_IMAGE = ImageIO.read(new FileInputStream("src/images/bullet_hero.png"));
            ENEMY_BULLET_IMAGE = ImageIO.read(new FileInputStream("src/images/bullet_enemy.png"));
            PROP_BLOOD = ImageIO.read(new FileInputStream("src/images/prop_blood.png"));
            PROP_BOMB = ImageIO.read(new FileInputStream("src/images/prop_bomb.png"));
            PROP_BULLET = ImageIO.read(new FileInputStream("src/images/prop_bullet.png"));
            PROP_BULLETPLUS = ImageIO.read(new FileInputStream("src/images/prop_bulletPlus.png"));
            ELI_PLUS_ENEMY_IMAGE = ImageIO.read(new FileInputStream("src/images/elitePlus.png"));
            BOSS_IMAGE = ImageIO.read(new FileInputStream("src/images/boss.png"));

            CLASSNAME_IMAGE_MAP.put(HeroAircraft.class.getName(), HERO_IMAGE);
            CLASSNAME_IMAGE_MAP.put(MobEnemy.class.getName(), MOB_ENEMY_IMAGE);
            CLASSNAME_IMAGE_MAP.put(EliteEnemy.class.getName(), ELI_ENEMY_IMAGE);
            CLASSNAME_IMAGE_MAP.put(HeroBullet.class.getName(), HERO_BULLET_IMAGE);
            CLASSNAME_IMAGE_MAP.put(EnemyBullet.class.getName(), ENEMY_BULLET_IMAGE);
            CLASSNAME_IMAGE_MAP.put(Prop_blood.class.getName(), PROP_BLOOD);
            CLASSNAME_IMAGE_MAP.put(Prop_bomb.class.getName(), PROP_BOMB);
            CLASSNAME_IMAGE_MAP.put(Prop_bullet.class.getName(), PROP_BULLET);
            CLASSNAME_IMAGE_MAP.put(Prop_bulletplus.class.getName(), PROP_BULLETPLUS);
            CLASSNAME_IMAGE_MAP.put(ElitePlusEnemy.class.getName(), ELI_PLUS_ENEMY_IMAGE);
            CLASSNAME_IMAGE_MAP.put(Boss.class.getName(), BOSS_IMAGE);

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public static BufferedImage get(String className){
        return CLASSNAME_IMAGE_MAP.get(className);
    }

    public static BufferedImage get(Object obj){
        if (obj == null){
            return null;
        }
        return get(obj.getClass().getName());
    }

}
