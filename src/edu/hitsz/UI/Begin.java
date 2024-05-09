package edu.hitsz.UI;

import edu.hitsz.application.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Begin {
    private JPanel mainPanel;
    private JButton easyPattern;
    private JButton normalPattern;
    private JButton toughPattern;
    private JComboBox musicSelect;
    private JLabel musicText;

    public Begin() {
        musicSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("hh");
            }
        });
        easyPattern.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game game = new Game_Easy();
                Main.cardPanel.add(game);
                Main.cardLayout.last(Main.cardPanel);
                game.setPattern(1);
                game.action();
            }
        });
        normalPattern.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game game = new Game_Normal();
                Main.cardPanel.add(game);
                Main.cardLayout.last(Main.cardPanel);
                game.setPattern(2);
                game.action();
            }
        });
        toughPattern.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game game = new Game_Tough();
                Main.cardPanel.add(game);
                Main.cardLayout.last(Main.cardPanel);
                game.setPattern(3);
                game.action();
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

}
