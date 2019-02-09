package designchallenge1;

import java.util.ArrayList;

public class EventSubject {
    private ArrayList<Event> eventsToday = new ArrayList<>();
    private ArrayList<ViewObserver> VOs= new ArrayList<>();

    public void setEvents (ArrayList<Event> events){
        eventsToday = events;
        notifyObservers();
    }

    public ArrayList<Event> getEvents() {
        return eventsToday;
    }

    public void attach(ViewObserver vo){
        VOs.add(vo);
    }

    private void notifyObservers(){
        for(ViewObserver vo: VOs)
            vo.update();
    }
}
