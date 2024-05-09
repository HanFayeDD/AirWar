package edu.hitsz.application;

import edu.hitsz.UI.Scorerank;
import edu.hitsz.aircraft.*;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.prop.AbstractProp;
import edu.hitsz.musiccontrol.*;
import edu.hitsz.prop.Prop_blood;
import edu.hitsz.prop.Prop_bomb;
import edu.hitsz.prop.Prop_bullet;
import edu.hitsz.basic.AbstractFlyingObject;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import edu.hitsz.factory.*; 
import edu.hitsz.scoredoc.*;
import edu.hitsz.scoredoc.Record;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

/**
 * 游戏主面板，游戏启动
 *
 * @author hitsz
 */
public class Game extends JPanel {

    private int backGroundTop = 0;
    //模式选择
    private static int pattern = -1;
    /**
     * Scheduled 线程池，用于任务调度
     */
    private final ScheduledExecutorService executorService;

    /**
     * 时间间隔(ms)，控制刷新频率
     */
    private int timeInterval = 40;

    private final HeroAircraft heroAircraft;
    private final List<AbstractBadAircraft> enemyAircrafts;
    private final List<BaseBullet> heroBullets;
    private final List<BaseBullet> enemyBullets;
    private final List<AbstractProp> props;

    /**
     * 屏幕中出现的敌机最大数量
     */
    private int enemyMaxNumber = 5;

    /**
     * 当前得分
     */
    private static int score = 0;
    /**
     * 当前时刻
     */
    private int time = 0;
    /**
     * Boss是否被消灭，一个时间内只能有一个boss机
     */
    private boolean boss_destroyed = true;
    /**
     * Boss被消灭后累积的得分
     */
    private int add_score = 0;
    /**
     * 产生Boss机的累积得分上界，只在boss机消灭的时候计数
     */
    private int boss_score = 100;
    /**
     * 周期（ms)
     * 指示子弹的发射、敌机的产生频率
     */
    private int cycleDuration = 600;
    private int cycleTime = 0;

    /**
     * 游戏结束标志
     */
    private boolean gameOverFlag = false;

    protected BufferedImage bg_pic;

    public Game() {
        bg_pic = ImageManager.BACKGROUND_IMAGE;
        heroAircraft = HeroAircraft.getInstance();
        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        props = new LinkedList<>();

        /**
         * Scheduled 线程池，用于定时任务调度
         * 关于alibaba code guide：可命名的 ThreadFactory 一般需要第三方包
         * apache 第三方库： org.apache.commons.lang3.concurrent.BasicThreadFactory
         */
        this.executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("game-action-%d").daemon(true).build());

        //启动英雄机鼠标监听
        new HeroController(this, heroAircraft);

    }

    public static int getScore(){
        return Game.score;
    }

    public void setPattern(int i){
        Game.pattern = i;
    }
    public static int getPattern(){
        return Game.pattern;
    }

    /**
     * 游戏启动入口，执行游戏逻辑
     */
    public void action() {
        boolean bgm_on = true;

        MusicThreadBgm bgm = new MusicThreadBgm("src/videos/bgm.wav");
        bgm.start();

        // 定时任务：绘制、对象产生、碰撞判定、击毁及结束判定
        Runnable task = () -> {
            time += timeInterval;
            //Boss机的产生
            if(add_score>=boss_score & boss_destroyed){
                BadAircraftFactory badfactory = new BossFactory();
                enemyAircrafts.add(badfactory.createBad());
                add_score = 0;
                boss_destroyed = false;
            }

            // 周期性执行（控制频率）
            if (timeCountAndNewCycleJudge()) {
                System.out.println(time);
                // 新敌机产生
                int which_enemy = (int)(Math.random()*10+1)%5;             
                if (enemyAircrafts.size() < enemyMaxNumber && which_enemy<3) {
                    BadAircraftFactory badfactory = new MobFactory();
                    enemyAircrafts.add(badfactory.createBad());
                }
                if(enemyAircrafts.size() < enemyMaxNumber && which_enemy==3){
                    BadAircraftFactory badfactory = new EliteFactory();
                    enemyAircrafts.add(badfactory.createBad());
                }
                if(enemyAircrafts.size() < enemyMaxNumber && which_enemy==4){
                    BadAircraftFactory badfactory = new ElietePlusFactory();
                    enemyAircrafts.add(badfactory.createBad());
                }
                //飞机射出子弹
                shootAction();
            }

            // 子弹移动
            bulletsMoveAction();

            // 飞机移动
            aircraftsMoveAction();

            //工具移动
            propsMoveAction();
            // 撞击检测
            crashCheckAction();
            // 后处理
            postProcessAction();

            //每个时刻重绘界面
            repaint();

            // 游戏结束检查英雄机是否存活
            if (heroAircraft.getHp() <= 0) {
                // 游戏jiesu
                bgm.stopRunning();
                new MusicThread("src/videos/game_over.wav").start();
                executorService.shutdown();
                gameOverFlag = true;
                System.out.println("Game Over!");
//                //打印
//                DATA.doADD(new Record(-1, "testUserName", score,
//                            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
//                DATA.showAllRecords();
//                DATA.writeToDat();
//                System.out.println(Game.pattern);
                Main.cardPanel.add(new Scorerank().getMainPanel());
                Main.cardLayout.last(Main.cardPanel);
            }



        };

        /**
         * 以固定延迟时间进行执行
         * 本次任务执行完成后，需要延迟设定的延迟时间，才会执行新的任务
         */
        executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);
        
    }

    //***********************
    //      Action 各部分
    //***********************

    private boolean timeCountAndNewCycleJudge() {
        cycleTime += timeInterval;
        if (cycleTime >= cycleDuration && cycleTime - timeInterval < cycleTime) {
            // 跨越到新的周期
            cycleTime %= cycleDuration;
            return true;
        } else {
            return false;
        }
    }

    private void shootAction() {
        // TODO 敌机射击
        for(AbstractAircraft el : enemyAircrafts){
            enemyBullets.addAll(el.shoot());
        }
        // 英雄射击
        heroBullets.addAll(heroAircraft.shoot());
    }

    private void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    private void aircraftsMoveAction() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }
    private void propsMoveAction(){
        for(AbstractFlyingObject el:props){
            el.forward();
        }
    }


    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    private void crashCheckAction() {
        // TODO 敌机子弹攻击英雄
        for(BaseBullet el : enemyBullets){
            if(el.notValid()){
                continue;
            }
            if(el.crash(heroAircraft)){
                heroAircraft.decreaseHp(el.getPower());
                el.vanish();
            }
        }
        // 英雄子弹攻击敌机
        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (AbstractBadAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    enemyAircraft.decreaseHp(bullet.getPower());
                    new MusicThread("src/videos/bullet_hit.wav").start();
                    bullet.vanish();
                    if (enemyAircraft.notValid()) {
                        // TODO 获得分数，产生道具补给
                        score += 10;
                        if(boss_destroyed){
                            add_score += 10;
                        }
                        if(enemyAircraft instanceof EliteEnemy || enemyAircraft instanceof ElitePlusEnemy || enemyAircraft instanceof Boss){
                            props.addAll(enemyAircraft.dropProp());
                            if(enemyAircraft instanceof Boss){
                                boss_destroyed = true;
                                add_score = 0;
                                System.out.println("BOSS被消灭");
                            }
                        }
                    }
                }
                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }
        // Todo: 我方获得道具，如果碰撞道具自动生效
        for(AbstractProp el : props){
            if(heroAircraft.crash(el)){
                new MusicThread("src/videos/get_supply.wav").start();
                el.activeProp();
            }
        }
    }

    /**
     * 后处理：
     * 2. 删除无效的敌机
     * <p>
     * 无效的原因可能是撞击或者飞出边界
     */
    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        props.removeIf(AbstractFlyingObject::notValid);
    }


    //***********************
    //      Paint 各部分
    //***********************

    /**
     * 重写paint方法
     * 通过重复调用paint方法，实现游戏动画
     *
     * @param  g
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(bg_pic, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(bg_pic, 0, this.backGroundTop, null);
        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }

        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, heroBullets);
        
        paintImageWithPositionRevised(g, enemyAircrafts);
        paintImageWithPositionRevised(g, props);

        g.drawImage(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);

        //绘制得分和生命值
        paintScoreAndLife(g);

    }

    private void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.size() == 0) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            g.drawImage(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, null);
        }
    }

    private void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;
        g.setColor(new Color(16711680));
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString("SCORE:" + this.score, x, y);
        y = y + 20;
        g.drawString("LIFE:" + this.heroAircraft.getHp(), x, y);
    }


}
