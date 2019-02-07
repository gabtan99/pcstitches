package designchallenge1;

import facebook.FBView;
import sms.SMS;
import sms.SMSView;

import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;

public class NotifAdapter implements NotifInterface{

	@Override
	 public void showNotification(ArrayList<Event> eventsToday) {
		FBView fb = new FBView();
		SMSView sms = new SMSView();

		for (int i=0; i<eventsToday.size(); i++) {

			String name = eventsToday.get(i).getName();
			int month = eventsToday.get(i).getStartMonth();
			int day = eventsToday.get(i).getStartMonth();
			int year = eventsToday.get(i).getStartYear();
			Color color = eventsToday.get(i).getColor();

			fb.showNewEvent(name, month, day, year, color);

			Calendar date = Calendar.getInstance();

			date.set(Calendar.YEAR, eventsToday.get(i).getStartYear());
			date.set(Calendar.MONTH, eventsToday.get(i).getStartMonth());
			date.set(Calendar.DAY_OF_MONTH, eventsToday.get(i).getStartDay());

			SMS text = new SMS(name, date, color);

			sms.sendSMS(text);
		}

	}
}
