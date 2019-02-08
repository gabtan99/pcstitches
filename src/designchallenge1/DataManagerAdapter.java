package designchallenge1;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;

public class DataManagerAdapter implements DataManager{
    private static final String CSV_FILE = "IOFiles/Philippine Holidays.csv";
    private static final String CSV_STORAGE = "IOFiles/My Events.csv";
    private static final String PSV_FILE = "IOFiles/DLSU Unicalendar.psv";
    private static final String PSV_STORAGE = "IOFiles/My Events.psv";
    private static final String DATABASE = "calendardb";

    private ArrayList<Event> defaultList;
    private DataParser fileReader;

    @Override
    public ArrayList<Event> get_events() {
        ArrayList<Event> eventList;
        eventList = new ArrayList<>();
        defaultList = new ArrayList<>();
        fileReader = new CSVDataParser();
        defaultList = fileReader.readData(CSV_FILE);
        eventList.addAll(fileReader.readData(CSV_STORAGE));
        fileReader = new PSVDataParser();
        defaultList.addAll(fileReader.readData(PSV_FILE));
        eventList.addAll(defaultList);
        return eventList;
    }

    @Override
    public boolean save_events(ArrayList<Event> events_to_save) {
        events_to_save.removeAll(defaultList);
        fileReader = new CSVDataParser();
        return fileReader.writeData(events_to_save, CSV_STORAGE);
    }
}
