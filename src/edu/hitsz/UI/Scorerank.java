package edu.hitsz.UI;
import edu.hitsz.application.*;
import edu.hitsz.scoredoc.*;
import edu.hitsz.scoredoc.Record;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.LinkedList;

public class Scorerank {
    private JPanel mainPanel;
    private JScrollPane tableScrollPanel;
    private JPanel topPanel;
    private JTable scoreTable;
    private JLabel title;
    private JPanel buttomPanel;
    private JButton delete;





    public Scorerank() {
        int choice = Game.getPattern();
        DAO_Record_Impl DATA;
        if(choice==1){
            DATA = new DAO_Record_Impl("src/edu/hitsz/scoredoc/score_rank1.dat");
        } else if (choice==2) {
            DATA = new DAO_Record_Impl("src/edu/hitsz/scoredoc/score_rank2.dat");
        } else if (choice==3) {
            DATA = new DAO_Record_Impl("src/edu/hitsz/scoredoc/score_rank3.dat");
        }else {
            DATA = new DAO_Record_Impl("src/edu/hitsz/scoredoc/score_rank1.dat");
        }
        //添加元素:
        String userName = JOptionPane.showInputDialog(Main.cardPanel, "游戏已结束，请输入你的姓名：", "输入", JOptionPane.INFORMATION_MESSAGE);
        if(userName!=null && !userName.isBlank()){//按下取消，未输入或者输入空格都不能正确添加
            DATA.doADD(new Record(-1, userName, Game.getScore(),
                            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
            DATA.writeToDat();
        }
        LinkedList<Record> head = DATA.getAllRecords();
        String[] columnName = {"排名", "姓名", "成绩", "时间"};
        int size = head.size();
        String[][] tabledata = new String[size][4];
        int i = 0;
        for (Record temp : head) {
            tabledata[i][0] = Integer.toString(temp.getRanking());
            tabledata[i][1] = temp.getName();
            tabledata[i][2] = Integer.toString(temp.getScore());
            tabledata[i][3] = temp.getTime();
            i++;
        }

        DefaultTableModel model = new DefaultTableModel(tabledata, columnName) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        scoreTable.setModel(model);
        tableScrollPanel.setViewportView(scoreTable);

        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = scoreTable.getSelectedRow();
                System.out.println(row);
                int result = JOptionPane.showConfirmDialog(delete,
                        "是否确定中删除？");
                if (JOptionPane.YES_OPTION == result && row != -1) {
                    model.removeRow(row);
                    //更新视图上的排名
                    updateView(row, model);
                    //删除原数据中的项目（内置了排序）
                    DATA.doDeletebyIndex(row);
                    //控制台打印输出
                    DATA.showAllRecords();
                    //重新写入数据
                    DATA.writeToDat();
                }
            }
        });
    }

    public JPanel getMainPanel(){
        return  mainPanel;
    }

    public void updateView(int row, DefaultTableModel model){
        for(int i=row; i<model.getRowCount(); i++){
            model.setValueAt(Integer.toString(i+1), i, 0);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Scorerank");
        frame.setContentPane(new Scorerank().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
