package designchallenge1;

import facebook.FBView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
                int month = e.getStartMonth() + 1;
                int day = e.getStartDay();
                int year = e.getStartYear();
                Color color = e.getColor();
                view.showNewEvent(name, month, day, year, color);
            }
            view.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            view.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent event) {
                    view.dispose();
                }
            });
        }

    }
}
