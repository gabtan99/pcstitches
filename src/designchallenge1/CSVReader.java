package designchallenge1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList; 
import java.io.*;
import java.awt.Color;
import java.io.PrintWriter;

public class CSVReader {

    private static final String PIPE_DELIMITER = "\\|";
    private static final String COMMA_DELIMITER = "\\, ";
    private static final String SLASH_DELIMITER = "\\/";
    private static final String CSV_FILE = "Philippine Holidays.csv";
    private static final String PSV_FILE = "DLSU Unicalendar.psv";
    private static final String EVENTS_STORAGE = "My Events.csv";

    public static void main (String args[])  throws FileNotFoundException  {

        ArrayList<Event> temp = readPSVFile(PSV_FILE);

        temp.addAll(readCSVFile(CSV_FILE));

        saveToCSVFile(temp);

    }

    static ArrayList<Event> readCSVFile(String fileName) {

        ArrayList<Event> temp = new ArrayList<Event>();

        try (BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] eventDetails = line.split(COMMA_DELIMITER, 3);

                Event e = new Event();
                int[] date = getMonthDayYear(eventDetails[0]);

                
                e.setMonth(date[0]-1);
                e.setDay(date[1]);
                e.setYear(date[2]);
                e.setName(eventDetails[1]);
                Color c = (Color) Color.class.getField(eventDetails[2].toUpperCase()).get(null);
                e.setColor(c);


                temp.add(e);
            }

        } catch (IOException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException
                | SecurityException e) {
            e.printStackTrace();
        }

        return temp;

    }

    static ArrayList<Event> readPSVFile(String fileName) {

        ArrayList<Event> temp = new ArrayList<Event>();

        try (BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] eventDetails = line.split(PIPE_DELIMITER, 3);

                Event e = new Event();

                int[] date = getMonthDayYear(eventDetails[1]);
                
                e.setMonth(date[0]-1);
                e.setDay(date[1]);
                e.setYear(date[2]);
            
                e.setName(eventDetails[0]);
                Color c = (Color) Color.class.getField(eventDetails[2].toUpperCase()).get(null);
                e.setColor(c);

                temp.add(e);
            }

        } catch (IOException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException
                | SecurityException e) {
            e.printStackTrace();
        }

        return temp;
    }

    static boolean saveToCSVFile(ArrayList<Event> events) {
        try (PrintWriter pw = new PrintWriter(new File(EVENTS_STORAGE))) {

            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < events.size(); i++) {

                int addOne = events.get(i).getMonth() + 1;

                sb.append(events.get(i).getName());
                sb.append(",");
                sb.append(events.get(i).getString(addOne));
                sb.append(",");
                sb.append(events.get(i).getString(events.get(i).getDay()));
                sb.append(",");
                sb.append(events.get(i).getString(events.get(i).getYear()));
                sb.append(",");
                sb.append(events.get(i).getString(events.get(i).getHour()));
                sb.append(","); 
                sb.append(events.get(i).getString(events.get(i).getMinute()));
                sb.append(",");
                sb.append(events.get(i).getColorRGB());
                sb.append("\n");
            }

            pw.write(sb.toString());
            pw.close();
            System.out.println("File saved");

        } catch (IOException | IllegalArgumentException | SecurityException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    static int[] getMonthDayYear (String date) {

        String[] dateString = date.split(SLASH_DELIMITER, 3);
        int[] dateInt = new int[dateString.length];

        for (int i = 0; i < dateInt.length; i++){
            
            dateInt[i] = Integer.parseInt(dateString[i]); 
        }
        return dateInt;
    } 
}