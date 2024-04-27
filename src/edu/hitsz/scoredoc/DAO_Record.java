package edu.hitsz.scoredoc;
import java.util.LinkedList;


public interface DAO_Record {
    void findByName(String aneme);

    LinkedList<Record> getAllRecords();

    void doADD(Record newRecord);

    void doDelete(String aname);
}
