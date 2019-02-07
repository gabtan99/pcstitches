package designchallenge1;

import java.util.ArrayList;

public class DataManager {

    private static final String CSV_FILE = "IOFiles/Philippine Holidays.csv";
    private static final String CSV_STORAGE = "IOFiles/My Events.csv";
    private static final String PSV_FILE = "IOFiles/DLSU Unicalendar.psv";
    private static final String PSV_STORAGE = "IOFiles/My Events.psv";

    public static void main (String args[])  {

        CSVDataParser csvread = new CSVDataParser();
        PSVDataParser psvread = new PSVDataParser();
        DBDataParser dbsave = new DBDataParser();

        ArrayList<Event> temp = dbsave.readData(CSV_FILE);

        csvread.writeData(temp, CSV_STORAGE);
        psvread.writeData(temp, PSV_STORAGE);

    }
}