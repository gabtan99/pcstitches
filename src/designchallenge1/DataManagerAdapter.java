package designchallenge1;

import java.util.ArrayList;

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
        defaultList = fileReader.readFromLocation(CSV_FILE);
        eventList.addAll(fileReader.readFromLocation(CSV_STORAGE));
        fileReader = new PSVDataParser();
        defaultList.addAll(fileReader.readFromLocation(PSV_FILE));
        eventList.addAll(defaultList);
        return eventList;
    }

    @Override
    public boolean save_events(ArrayList<Event> events_to_save) {
        events_to_save.removeAll(defaultList);
        fileReader = new CSVDataParser();
        return fileReader.saveToLocation(events_to_save, CSV_STORAGE);
    }
}
