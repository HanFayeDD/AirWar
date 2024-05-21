package edu.hitsz.UI;

import edu.hitsz.application.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Begin{
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
                String select = (String) musicSelect.getSelectedItem();
                if (select != null) {
                    if(select.equals("开")){
                        System.out.println("音效开");
                        Game.setMusic_on(true);
                    } else if (select.equals("关")) {
                        System.out.println("音效关");
                        Game.setMusic_on(false);
                    }
                }
                else {
                    System.out.println("having null selection");
                }
            }
        });
        easyPattern.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((BackgroundPanel) mainPanel).stopTask();
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
                ((BackgroundPanel) mainPanel).stopTask();
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
                ((BackgroundPanel) mainPanel).stopTask();
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
        mainPanel = new BackgroundPanel();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("mainPanel");
        frame.setContentPane(new Begin().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }



}
