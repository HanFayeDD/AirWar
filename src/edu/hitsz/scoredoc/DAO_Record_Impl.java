package edu.hitsz.scoredoc;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

public class DAO_Record_Impl implements DAO_Record {
    private LinkedList<Record> table;

    public String path;

    public DAO_Record_Impl(String apath){
        this.path = apath;
        DAO_init();
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
            if(element.getName().equals(aname)){
                table.remove();
            }
        }
        sortByScores();
        for(int i=0; i<table.size(); i++){
            table.get(i).setRanking(i+1);
        }
    }

    public void doDeletebyIndex(int aindex){
        table.remove(aindex);
        sortByScores();
        for(int i=0; i<table.size(); i++){
            table.get(i).setRanking(i+1);
        }
    }

    @Override
    public void findByName(String aneme){
        for(Record element :table){
            if(element.getName().equals(aneme)){
                System.out.println(element);
            }
        }
    }

    @Override
    public LinkedList<Record> getAllRecords() {
        return table;
    }

    @Override 
    public String toString(){
        String res = "";
        for (Record record : table) {
            res = res + record.toString() + '\n';
        }
        return res;
    }

    public void showAllRecords(){
        String star = "********************************************";
        System.out.println(star+ "\n                  得分排行榜\n"+ star);
        for(Record element : table){
            System.out.println(element);
        }
    }

    // public void writetoTxt(){
    //     try{
    //         FileWriter fileWriter = new FileWriter("src/edu/hitsz/scoredoc/score_documents.txt");
    //         PrintWriter PrintWriter = new PrintWriter(fileWriter);
    //         for(Record ele : table){
    //             PrintWriter.println(ele.getRanking()+";"+
    //                                 ele.getName()+";"+
    //                                 ele.getScore()+";"+
    //                                 ele.getTime());                                                                      
    //         }
    //         PrintWriter.close();
    //         System.out.println("Write to txt successfully!");
    //     }catch(IOException e){
    //         e.printStackTrace();
    //     }finally{
    //     }
    // }

    public void sortByScores(){
        Collections.sort(table, new RecordComparator());
    }

    //TODO:对象序列化来存储
    @SuppressWarnings("unchecked")
    public void DAO_init(){
        try{
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
            Object obj = ois.readObject();
            if(obj instanceof LinkedList){
                table = (LinkedList<Record>) obj;                
            }
            else{
                System.out.println(" read object is not of type LinkedList<Record>");
            }
            // System.out.println("read from .txt successfully");
            ois.close();
        }catch(IOException e){
            // System.out.println("catch io problem");
            table = new LinkedList<Record>();
            // e.printStackTrace();
        }catch(ClassNotFoundException e){
            System.out.println("catch classnotfound problem");
            e.printStackTrace();
        }finally{
            // System.out.println("init_finally");
        }
    }

    public void writeToDat(){
        try{
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream((path)));
            oos.writeObject(table);
            // System.out.println("write to .dat successfully!");
            oos.close();
        }catch(IOException e){
            // System.out.println("catch io problem");
            e.printStackTrace();
        }
        finally{
            // System.out.println("out_finally");
            
        }
    }

    public static void main(String[] args) throws IOException{
        var data = new DAO_Record_Impl("src/edu/hitsz/scoredoc/score_rank1.dat");
        data.showAllRecords();
        System.out.println(data);
  
    }
}
   
class RecordComparator implements Comparator<Record>{
    @Override
    public int compare(Record o1, Record o2) {
        return -1*(o1.getScore()-o2.getScore());
    }
}

