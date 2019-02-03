package designchallenge1;

import java.awt.*;
import java.util.Calendar;

public class Event implements EventsInterface {
    private String eventName;
    private Color eventColor;
    private Calendar startDate, endDate;


    public Event(){
        startDate = Calendar.getInstance();
        endDate = Calendar.getInstance();
        setStartHour(0);
        setStartMinute(0);
        setEndHour(23);
        setEndMinute(59);
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

    // Start Date Setters
    public void setStartYear(int year){
        this.startDate.set(Calendar.YEAR, year);
    }

    public void setStartMonth(int month){
        this.startDate.set(Calendar.MONTH, month-1);
    }

    public void setStartDay(int day){
        this.startDate.set(Calendar.DAY_OF_MONTH, day);
    }

    public void setStartHour(int hour){
        this.startDate.set(Calendar.HOUR_OF_DAY, hour);
    }

    public void setStartMinute(int minute){
        this.startDate.set(Calendar.MINUTE, minute);
    }

    // Start Date Getters
    public int getStartYear(){
        return this.startDate.get(Calendar.YEAR);
    }

    public int getStartMonth(){
        return this.startDate.get(Calendar.MONTH);
    }

    public int getStartDay(){
        return this.startDate.get(Calendar.DAY_OF_MONTH);
    }

    public int getStartHour(){
        return this.startDate.get(Calendar.HOUR_OF_DAY);
    }

    public int getStartMinute(){
        return this.startDate.get(Calendar.MINUTE);
    }

    // End Date Setters
    public void setEndYear(int year){
        this.endDate.set(Calendar.YEAR, year);
    }

    public void setEndMonth(int month){
        this.endDate.set(Calendar.MONTH, month-1);
    }

    public void setEndDay(int day){
        this.endDate.set(Calendar.DAY_OF_MONTH, day);
    }

    public void setEndHour(int hour){
        this.endDate.set(Calendar.HOUR_OF_DAY, hour);
    }

    public void setEndMinute(int minute){
        this.endDate.set(Calendar.MINUTE, minute);
    }

    // End Date Getters
    public int getEndYear(){
        return this.endDate.get(Calendar.YEAR);
    }

    public int getEndMonth(){
        return this.endDate.get(Calendar.MONTH);
    }

    public int getEndDay(){
        return this.endDate.get(Calendar.DAY_OF_MONTH);
    }

    public int getEndHour(){
        return this.endDate.get(Calendar.HOUR_OF_DAY);
    }

    public int getEndMinute(){
        return this.endDate.get(Calendar.MINUTE);
    }

    public String getString(int num) {
        return Integer.toString(num);
    }
}

