package designchallenge1;

import java.util.ArrayList;

public interface DataManager {
    ArrayList<Event> get_events();
    boolean save_events(ArrayList<Event> events_to_save);
}
