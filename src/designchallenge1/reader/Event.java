import java.awt.*;
import java.util.Calendar;

public class Event implements EventsInterface {
    private String eventName;
    private Color eventColor;
    private Calendar date;

    public Event(){
        date = Calendar.getInstance();
        this.setHour(0);
        this.setMinute(0);
    }

    // Getters
    public String getName() {
        return eventName;
    }

    public Color getColor() {
        return eventColor;
    }

    public int getColorRGB(){
        return eventColor.getRGB();
    }

    // Setters
    public void setColor(Color eventColor) {
        this.eventColor = eventColor;
    }

    public void setName(String eventName) {
        this.eventName = eventName;
    }

    // Date Setters
    public void setYear(int year){
        this.date.set(Calendar.YEAR, year);
    }

    public void setMonth(int month){
        this.date.set(Calendar.MONTH, month);
    }

    public void setDay(int day){
        this.date.set(Calendar.DAY_OF_MONTH, day);
    }

    public void setHour(int hour){
        this.date.set(Calendar.HOUR_OF_DAY, hour);
    }

    public void setMinute(int minute){
        this.date.set(Calendar.MINUTE, minute);
    }

    //Date Getters
    public int getYear(){
        return this.date.get(Calendar.YEAR);
    }

    public int getMonth(){
        return this.date.get(Calendar.MONTH);
    }

    public int getDay(){
        return this.date.get(Calendar.DAY_OF_MONTH);
    }

    public int getHour(){
        return this.date.get(Calendar.HOUR_OF_DAY);
    }

    public int getMinute(){
        return this.date.get(Calendar.MINUTE);
    }

    public String getString(int num){
        return Integer.toString(num);
    }



}

