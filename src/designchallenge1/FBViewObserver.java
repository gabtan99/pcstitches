package designchallenge1;

import facebook.FBView;

import java.awt.*;
import java.util.ArrayList;

public class FBViewObserver extends ViewObserver{
    private FBView view;

    public FBViewObserver (EventSubject s){
        this.subject = s;
        this.subject.attach(this);
    }

    @Override
    public void update() {
        ArrayList<Event> events = subject.getEvents();
        if (events.size() > 0){
            view = new FBView();
            for (Event e: events){
                String name = e.getName();
                int month = e.getStartMonth();
                int day = e.getStartMonth();
                int year = e.getStartYear();
                Color color = e.getColor();
                view.showNewEvent(name, month, day, year, color);
            }
        }

    }
}
