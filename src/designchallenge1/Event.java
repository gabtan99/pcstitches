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
        this.startDate.set(Calendar.MONTH, month);
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
    public void getStartYear(){
        this.startDate.get(Calendar.YEAR);
    }

    public void getStartMonth(){
        this.startDate.get(Calendar.MONTH);
    }

    public void getStartDay(){
        this.startDate.get(Calendar.DAY_OF_MONTH);
    }

    public void getStartHour(){
        this.startDate.get(Calendar.HOUR_OF_DAY);
    }

    public void getStartMinute(){
        this.startDate.get(Calendar.MINUTE);
    }

    // End Date Setters
    public void setEndYear(int year){
        this.endDate.set(Calendar.YEAR, year);
    }

    public void setEndMonth(int month){
        this.endDate.set(Calendar.MONTH, month);
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
    public void getEndYear(){
        this.endDate.get(Calendar.YEAR);
    }

    public void getEndMonth(){
        this.endDate.get(Calendar.MONTH);
    }

    public void getEndDay(){
        this.endDate.get(Calendar.DAY_OF_MONTH);
    }

    public void getEndHour(){
        this.endDate.get(Calendar.HOUR_OF_DAY);
    }

    public void getEndMinute(){
        this.endDate.get(Calendar.MINUTE);
    }

}

