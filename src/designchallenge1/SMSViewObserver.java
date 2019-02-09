package designchallenge1;

import sms.SMS;
import sms.SMSView;

import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;

public class SMSViewObserver extends ViewObserver {
    private SMSView view;

    public SMSViewObserver (EventSubject s){
        this.subject = s;
    }

    @Override
    public void update() {
        ArrayList<Event> events = subject.getEvents();
        if (events.size() > 0){
            view = new SMSView();
            for (Event e: events){
                Calendar date = Calendar.getInstance();

                String name = e.getName();
                date.set(Calendar.YEAR, e.getStartYear());
                date.set(Calendar.MONTH, e.getStartMonth());
                date.set(Calendar.DAY_OF_MONTH, e.getStartDay());
                Color color = e.getColor();

                SMS text = new SMS(name, date, color);

                view.sendSMS(text);
                String number = view.getTitle();
                String regex = "\\D";
                number  = number.replaceAll(regex, "");
                view.setTitle("SMS App #" + number);
            }
        }
    }
}
