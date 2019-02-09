package designchallenge1;

import facebook.FBView;

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
        }

    }
}
