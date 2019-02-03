package designchallenge1;

import java.util.ArrayList;

public class DataManager {

    public static void main (String args[])  {

        CSVDataParser csvread = new CSVDataParser();
        ArrayList<Event> temp = csvread.readData();
        PSVDataParser psvread = new PSVDataParser();
        DBDataParser dbsave = new DBDataParser();

        temp.addAll(psvread.readData());

        csvread.writeData(temp);
        psvread.writeData(temp);
        dbsave.writeData(temp);

    }
}