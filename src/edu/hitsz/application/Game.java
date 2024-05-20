package edu.hitsz.application;

import edu.hitsz.UI.Scorerank;
import edu.hitsz.aircraft.*;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.prop.AbstractProp;
import edu.hitsz.musiccontrol.*;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.prop.Prop_bomb;
import edu.hitsz.shootstrategy.DefaultShoot;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import edu.hitsz.factory.*;

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
public abstract class Game extends JPanel {
    private int backGroundTop = 0;
    //模式选择
    private static int pattern = -1;
    //游戏结束后存储输入的玩家姓名
    private static String playername = "";
    /**
     * Scheduled 线程池，用于任务调度
     */
    private final ScheduledExecutorService executorService;

    /**
     * 控制除BOSS外三种敌机的概率
     * 产生普通敌机的概率是enemy_1_2
     * 产生Elite敌机的概率是enemy_2_3 - enemy_1_2
     * 产生Elite_plus的概率是1 - enemy_2_3
     */
    protected double enemy_1_2 = 0.7;//0.7的概率是普通敌机
    protected double enemy_2_3 = 0.9;//0.2的概率是Elite,0.1是Eliteplus
    /**
     * 时间间隔(ms)，控制刷新频率
     */
    protected int timeInterval = 30;
    //该难度是否产生BOSS机
    protected boolean having_boss = true;
    //游戏过程中产生的对象
    protected final HeroAircraft heroAircraft;
    protected final List<AbstractBadAircraft> enemyAircrafts;
    protected final List<BaseBullet> heroBullets;
    protected final List<BaseBullet> enemyBullets;
    protected final List<AbstractProp> props;
    /**
     * 屏幕中出现的敌机最大数量
     */
    protected int enemyMaxNumber = 5;

    /**
     * 当前得分
     */
    protected static int score = 0;
    /**
     * 当前时刻
     */
    private int time = 0;
    /**
     * Boss是否被消灭，一个时间内只能有一个boss机
     */
    private boolean boss_destroyed = true;
    /**
     * Boss被消灭后累积的得分，用此来跟产生BOSS机的得分阈值比较，
     * 来判断是否产生BOSS机
     */
    private int add_score = 0;
    /**
     * 产生Boss机的得分阈值
     */
    protected int boss_score = 50;
    /**
     * 周期（ms)
     * 指示敌机子弹的发射、敌机的产生频率
     */
    protected int cycleDuration = 500;
    /**
     * 周期(ms)
     * 指示英雄机的子弹发射频率
     */
    protected int cycleDurationHero = 500;
    private int cycleTime = 0;
    private int cycleTimeHero = 0;

    /**
     * 游戏结束标志
     */
    private static  boolean gameOverFlag = false;
    /**
     * 游戏背景图片
     */
    protected BufferedImage bg_pic;
    /**
     * 音效开关与否
     */
    private static boolean music_on = true;

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

    public static void setMusic_on(boolean choice){
        Game.music_on = choice;
    }

    public static boolean getgameOverFlag(){
        return gameOverFlag;
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
    public final void action() {
        MusicThread bgm = new MusicThread(MusicManager.BGM);
        bgm.notEXIT();
        MusicThread bgm_boss = new MusicThread(MusicManager.BGM_BOSS);
        bgm_boss.notEXIT();
        if(music_on){
            bgm.start();
        }
        // 定时任务：绘制、对象产生、碰撞判定、击毁及结束判定
        Runnable task = () -> {
            time += timeInterval;
            //Boss机的产生
            if(having_boss && add_score>=boss_score && boss_destroyed){
                //generate_boss
                this.generate_BOSS();
                add_score = 0;
                boss_destroyed = false;
                if(music_on) {
                    if (bgm_boss.isAlive()) {
                        bgm_boss.restartRunning();
                    } else {
                        bgm_boss.start();
                    }
                }
            }

            if(music_on) {
                if (!boss_destroyed) {
                    bgm_boss.restartRunning();
                    bgm.stopRunning();
                } else {
                    bgm_boss.stopRunning();
                    bgm.restartRunning();
                }
            }

            // 周期性执行（控制频率）敌机的射击和产生
            if (timeCountAndNewCycleJudge()) {
                System.out.println(time);
                // 新敌机产生
                this.generate_Other_Bad();
                //飞机射出子弹
                shootAction();
            }

            //英雄机的射击
            if(timeCountAndNewCycleJudgeHero()){
                shootActionHero();
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
            //控制英雄机的射击策略

            //每个时刻重绘界面
            repaint();
            // 游戏结束检查英雄机是否存活
            if (heroAircraft.getHp() <= 0) {
                // 游戏jiesu
                bgm.setEXIT();
                bgm_boss.setEXIT();
                if(music_on) {
                    new MusicThread(MusicManager.GAME_OVER).start();
                }
                executorService.shutdown();
                gameOverFlag = true;
                System.out.println("Game Over!");
                Scorerank page = new Scorerank();
                Main.cardPanel.add(page.getMainPanel());
                Main.cardLayout.last(Main.cardPanel);
                page.addplayerPage();
            }


        };

        /**
         * 以固定延迟时间进行执行
         * 本次任务执行完成后，需要延迟设定的延迟时间，才会执行新的任务
         */
        executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);
        
    }
    //TODO:模板模式
    /*
    模板模式
     */
    public abstract void generate_BOSS();
    public abstract void generate_Other_Bad();

    //***********************
    //      Action 各部分
    //***********************

    public static void addscore(int addnum){
        score = score + addnum;
    }

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

    private boolean timeCountAndNewCycleJudgeHero() {
        cycleTimeHero += timeInterval;
        if (cycleTimeHero >= cycleDurationHero && cycleTimeHero - timeInterval < cycleTimeHero) {
            // 跨越到新的周期
            cycleTimeHero %= cycleDurationHero;
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
    }

    private void shootActionHero() {
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

    public static String getPlayername(){
        return playername;
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
                    if(music_on) {
                        new MusicThread(MusicManager.BULLET_HIT).start();
                    }
                    bullet.vanish();
                    if (enemyAircraft.notValid()) {
                        // TODO 获得分数，产生道具补给
                        score += 10;
                        if(having_boss && boss_destroyed){
                            add_score += 10;
                        }
                        if(enemyAircraft instanceof EliteEnemy || enemyAircraft instanceof ElitePlusEnemy || enemyAircraft instanceof Boss){
                            props.addAll(enemyAircraft.dropProp());
                            if(enemyAircraft instanceof Boss && having_boss){
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
                if(music_on) {
                    new MusicThread(MusicManager.GET_SUPPLY).start();
                }
                if(el instanceof Prop_bomb){
                    if(music_on){
                        new MusicThread(MusicManager.BOMB_EXPLOSION).start();
                    }
                    ((Prop_bomb) el).addAll(enemyAircrafts);
                    ((Prop_bomb) el).addAll(enemyBullets);
                }
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
        //TODO:plot healtbar
        paintImageWithPositionRevised(g, enemyAircrafts);
        paintImageWithPositionRevised(g, props);
        paintHeroBar(g);
        paintEnemyBar(g, enemyAircrafts);

        g.drawImage(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);

        //绘制得分和生命值
        paintScoreAndLife(g);
        paintHeroBar(g);

    }

    private void paintEnemyBar(Graphics g, List<? extends AbstractBadAircraft> objects){
        if(objects.size()==0){
            return;
        }
        for(AbstractBadAircraft object : objects){
            float x;
            float y;
            float width;
            int new_x;
            int new_y;
            int new_width;
            if(object instanceof MobEnemy){
                x = (float) ImageManager.MOB_ENEMY_IMAGE.getWidth() /2;
                y = (float) ImageManager.MOB_ENEMY_IMAGE.getHeight()/2;
                width = (float) (1.6*x);
                new_x = (int)(object.getLocationX()-0.8*x);
                new_y = (int)(object.getLocationY()-1.5*y);//change here
                new_width = (int)(width*object.getHp()/object.getMaxHp());
            }
            else if(object instanceof EliteEnemy){
                x = (float) ImageManager.ELI_ENEMY_IMAGE.getWidth() /2;
                y = (float) ImageManager.ELI_ENEMY_IMAGE.getHeight() /2;
                width = (float) (1.6*x);
                new_x = (int)(object.getLocationX()-0.8*x);
                new_y = (int)(object.getLocationY()-1.5*y);//change here
                new_width = (int)(width*object.getHp()/object.getMaxHp());
            }
            else if(object instanceof ElitePlusEnemy){
                x = (float) ImageManager.ELI_PLUS_ENEMY_IMAGE.getWidth()/2;
                y = (float) ImageManager.ELI_PLUS_ENEMY_IMAGE.getHeight()/2;
                width = (float) (1.6*x);
                new_x = (int)(object.getLocationX()-0.8*x);
                new_y = (int)(object.getLocationY()-1.5*y);//change here
                new_width = (int)(width*object.getHp()/object.getMaxHp());;
            }else{
                x = (float) ImageManager.BOSS_IMAGE.getWidth()/2;
                y = (float) ImageManager.BOSS_IMAGE.getHeight()/2;
                width = (float) (1.6*x);
                new_x = (int)(object.getLocationX()-0.8*x);
                new_y = (int)(object.getLocationY()-1.1*y);//change here
                new_width = (int)(width*object.getHp()/object.getMaxHp());;
            }
            g.setColor(Color.red);
            g.fillRect(new_x, new_y, new_width, 10);
            g.setColor(Color.BLACK);
            g.drawRect(new_x, new_y, (int)(width), 10);
        }

    }

    private void paintHeroBar(Graphics g){
        int x = (int)(HeroAircraft.getInstance().getLocationX()-0.8*(ImageManager.HERO_IMAGE.getWidth()/2));
        int y = (int)(HeroAircraft.getInstance().getLocationY()+1.1*(ImageManager.HERO_IMAGE.getHeight()/2));
        int width = (int)(1.6*(ImageManager.HERO_IMAGE.getWidth()/2)*HeroAircraft.getInstance().getHp()/HeroAircraft.getInstance().getMaxHp());
        g.setColor(Color.GREEN);
        g.fillRect(x, y, width, 10);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, (int)(1.6*(ImageManager.HERO_IMAGE.getWidth()/2)), 10);
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
        g.drawString("SCORE:" + score, x, y);
        y = y + 20;
        g.drawString("LIFE:" + this.heroAircraft.getHp(), x, y);
    }


}
