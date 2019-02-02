package designchallenge1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
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

    private static Statement stmt;

    private static String driver = "com.mysql.jdbc.Driver";
    private static String db = "calendardb";
    private static String url = "jdbc:mysql://localhost/" + db + "?useSSL=false";
    private static String user = "root";
    private static String pass = "password";
    private static Connection conn = null;

    public static void main (String args[])  {

        //Connection attempt
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, pass);
            System.out.println("Connected to database : " + db);
            stmt = conn.createStatement();

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }



        ArrayList<Event> temp = readPSVFile(PSV_FILE);
        temp.addAll(readCSVFile(CSV_FILE));

        saveToCSVFile(temp);

        saveToDatabase(temp);

    }

    static ArrayList<Event> readCSVFile(String fileName) {

        ArrayList<Event> temp = new ArrayList<Event>();

        try (BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] eventDetails = line.split(COMMA_DELIMITER, 3);

                Event e = new Event();
                int[] date = getMonthDayYear(eventDetails[0]);

                
                e.setStartMonth(date[0]+1);
                e.setStartDay(date[1]);
                e.setStartYear(date[2]);
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
                
                e.setStartMonth(date[0]+1);
                e.setStartDay(date[1]);
                e.setStartYear(date[2]);
            
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


                sb.append(events.get(i).getName());
                sb.append(",");
                sb.append(events.get(i).getString(events.get(i).getStartMonth()));
                sb.append(",");
                sb.append(events.get(i).getString(events.get(i).getStartDay()));
                sb.append(",");
                sb.append(events.get(i).getString(events.get(i).getStartYear()));
                sb.append(",");
                sb.append(events.get(i).getString(events.get(i).getStartHour()));
                sb.append(","); 
                sb.append(events.get(i).getString(events.get(i).getStartMinute()));
                sb.append(",");
                sb.append(events.get(i).getColorRGB());
                sb.append("\n");
            }

            pw.write(sb.toString());
            pw.close();
            System.out.println("File saved to file: " + EVENTS_STORAGE);

        } catch (IOException | IllegalArgumentException | SecurityException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    static boolean saveToDatabase (ArrayList<Event> events)  {

        for (int i=0; i<events.size(); i++) {

            PreparedStatement addEvent = null;

            String insertEvent = "INSERT INTO myevents (event_id, name, start_month, start_day, start_year, start_hour, start_minutes, rgbid) VALUES ("+ getNewEventID() +", ?, '"+ events.get(i).getStartMonth() +"', '"+ events.get(i).getStartDay() +"', '"+ events.get(i).getStartYear() +"', '"+ events.get(i).getStartHour() +"', '"+ events.get(i).getStartMinute()  +"', '"+ events.get(i).getColorRGB() +"')";

            try {
                addEvent = conn.prepareStatement(insertEvent);
                addEvent.setString(1, events.get(i).getName());
                addEvent.executeUpdate();
            } catch (SQLException e) {
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
                return false;
            } finally {
                if (addEvent != null) {
                    try {
                        addEvent.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        System.out.println("File saved to database: " + db);
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

    static int getNewEventID() {
        String returnMaxEventID = "SELECT COUNT(event_id) as 'max' FROM myevents";

        int newID = 0;

        try {
            ResultSet rs = stmt.executeQuery(returnMaxEventID);
            while (rs.next()) {
                newID = rs.getInt("max");
                newID++;
            }

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }

        return newID;
    }
}