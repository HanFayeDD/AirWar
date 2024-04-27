package edu.hitsz.scoredoc;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class DAO_Record_Impl implements DAO_Record {
    private LinkedList<Record> table;

    public DAO_Record_Impl(){
        table = new LinkedList<Record>();
        try{
            BufferedReader reader = new BufferedReader(new FileReader("src/edu/hitsz/scoredoc/score_documents.txt"));
            String line = reader.readLine();
            while (line != null) {//排除空串串
                line = line.trim();
                if(line.length()!=0){
                    String[] lineparts = line.split(";");
                    table.add(new Record(Integer.parseInt(lineparts[0]), 
                                        lineparts[1], 
                                        Integer.parseInt(lineparts[2]), 
                                        lineparts[3]));  
                }          
                line = reader.readLine();
            }
        reader.close();
        System.out.println("read from txt successfully");
        }catch(IOException e){
            e.printStackTrace();
        }finally{
        }
    }

    @Override
    public void doADD(Record newRecord) {
        table.add(newRecord);
        sortByScores();
        for(int i=0; i<table.size(); i++){
            table.get(i).setRanking(i+1);
        }
    }

    @Override
    public void doDelete(String aname) {
        for(Record element : table){
            if(element.getName()==aname){
                table.remove();
            }
        }
        sortByScores();
        for(int i=0; i<table.size(); i++){
            table.get(i).setRanking(i+1);
        }
    }

    @Override
    public void findByName(String aneme){
        for(Record element :table){
            if(element.getName() == aneme){
                System.out.println(element);
            }
        }
    }

    @Override
    public LinkedList<Record> getAllRecords() {
        showAllRecords();
        return table;
    }

    @Override 
    public String toString(){
        String res = "";
        for(int i=0; i<table.size(); i++){
            res = res + table.get(i).toString() + '\n';
        }
        return res;
    }

    public void showAllRecords(){
        String star = "********************************************";
        System.out.println(star+ "\n                  得分排行榜\n"+ star);
        for(Record element : table){
            System.out.println(element);
        }
        writetoTxt();
    }

    public void writetoTxt(){
        try{
            FileWriter fileWriter = new FileWriter("src/edu/hitsz/scoredoc/score_documents.txt");
            PrintWriter PrintWriter = new PrintWriter(fileWriter);
            for(Record ele : table){
                PrintWriter.println(ele.getRanking()+";"+
                                    ele.getName()+";"+
                                    ele.getScore()+";"+
                                    ele.getTime());                                                                      
            }
            PrintWriter.close();
            System.out.println("Write to txt successfully!");
        }catch(IOException e){
            e.printStackTrace();
        }finally{
        }
    }

    private void sortByScores(){
        Collections.sort(table, new RecordComparator());
    }

    public static void main(String[] args) {
        var pp = new DAO_Record_Impl();
        pp.doADD(new Record(-1, "ddea", 89, "889"));
        pp.doADD(new Record(-1, "dhhe", 199, "dwdc"));
        pp.showAllRecords();
    }
}
   
class RecordComparator implements Comparator<Record>{
    @Override
    public int compare(Record o1, Record o2) {
        return -1*(o1.getScore()-o2.getScore());
    }
}

