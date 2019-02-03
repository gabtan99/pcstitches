package designchallenge1;

import java.sql.*;
import java.util.ArrayList;

public class DBDataParser extends DataParser {

	private static String driver = "com.mysql.jdbc.Driver";
	private static String db = "calendardb";
	private static String url = "jdbc:mysql://localhost/" + db + "?useSSL=false";
	private static String user = "root";
	private static String pass = "password";
	private static Connection conn = null;
	private static Statement stmt;

	ArrayList<Event> readData() {
		return new ArrayList<Event>();
	}

	boolean writeData(ArrayList<Event> events) {

		String clearData = "DELETE FROM myevents WHERE event_id > 0";

		//Connects to DB
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pass);
			System.out.println("Connected to database : " + db);
			stmt = conn.createStatement();
			stmt.executeUpdate(clearData);
		} catch (Exception e) {
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}

		for (int i=0; i<events.size(); i++) {

			PreparedStatement addEvent = null;

			String insertEvent = "INSERT INTO myevents (event_id, name, start_month, start_day, start_year, start_hour, start_minutes, end_month, end_day, end_year, end_hour, end_minutes, rgbid) VALUES ("+ getNewEventID() +", ?, '"
					+ (events.get(i).getStartMonth()+1) +"', '"+ events.get(i).getStartDay() +"', '"+ events.get(i).getStartYear() +"', '"+ events.get(i).getStartHour() +"', '"
					+ events.get(i).getStartMinute()  +"', '"+ (events.get(i).getEndMonth()+1) +"', '"+ events.get(i).getEndDay()+"', '"+ events.get(i).getEndYear() +"', '"+ events.get(i).getEndHour()
					+"', '"+ events.get(i).getEndMinute() +"', '"+ events.get(i).getColorRGB() +"')";

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

	int getNewEventID() {
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
