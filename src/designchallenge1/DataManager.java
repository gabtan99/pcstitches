package designchallenge1;

import java.util.ArrayList;

public class DataManager {

    public static void main (String args[])  {

        CSVDataParser csvread = new CSVDataParser();
        PSVDataParser psvread = new PSVDataParser();
        DBDataParser dbsave = new DBDataParser();

        ArrayList<Event> temp = dbsave.readData();

        csvread.writeData(temp);
        psvread.writeData(temp);
        dbsave.writeData(temp);

    }
}