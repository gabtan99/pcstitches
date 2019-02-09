package designchallenge1;

import sms.SMSView;

import java.util.ArrayList;

public class SMSViewObserver extends ViewObserver {
    private SMSView view;

    public SMSViewObserver (EventSubject s){
        this.subject = s;
        this.subject.attach(this);
    }

    @Override
    public void update() {
        ArrayList<Event> events = subject.getEvents();
        if (events.size() > 0){
            view = new SMSView();
        }
    }
}
