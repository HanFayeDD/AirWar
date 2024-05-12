package edu.hitsz.UI;
import edu.hitsz.application.ImageManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


public class BackgroundPanel extends JPanel{
    private final BufferedImage begin_background;

    public BackgroundPanel() {
        begin_background = ImageManager.BACKGROUND_IMAGE4;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(begin_background, 0, 0, this.getWidth(), this.getHeight(), this);
    }
}
