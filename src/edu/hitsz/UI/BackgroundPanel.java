package edu.hitsz.UI;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class BackgroundPanel extends JPanel{
    private final BufferedImage begin_background;
    private int backGroundTop = 0;
    protected int timeInterval = 30;
    private final ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,
            new BasicThreadFactory.Builder().namingPattern("game-action-%d").daemon(true).build());
    private HeroAircraft heroAircraft;

    public BackgroundPanel() {
        double choice = Math.random();
        if(choice<0.2){
            begin_background = ImageManager.BACKGROUND_IMAGE;
        }
        else if(choice<0.4){
            begin_background = ImageManager.BACKGROUND_IMAGE2;
        }
        else if(choice<0.6) {
            begin_background = ImageManager.BACKGROUND_IMAGE3;
        }
        else if(choice<0.8) {
            begin_background = ImageManager.BACKGROUND_IMAGE4;
        }
        else {
            begin_background = ImageManager.BACKGROUND_IMAGE5;
        }
        heroAircraft = HeroAircraft.getInstance();
        action();
    }

    public final void action(){
        Runnable task = this::repaint;
        executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(begin_background, 0, this.backGroundTop- Main.WINDOW_HEIGHT, begin_background.getWidth(), begin_background.getHeight(), null);
        g.drawImage(begin_background, 0, this.backGroundTop, begin_background.getWidth(), begin_background.getHeight(), null);
        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }
    }

    public void stopTask(){
        executorService.shutdown();
    }

}
