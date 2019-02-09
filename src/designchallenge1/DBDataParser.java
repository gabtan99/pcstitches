package designchallenge1;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class DBDataParser extends DataParser {

	private static Connection conn = null;
	private static Statement stmt;
	private ResultSet rs;

	void readData(String db) {

		String returnAllEvents = "SELECT * FROM myevents";
		connectDB(db);

		try {
			rs = stmt.executeQuery(returnAllEvents);
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
		}
		conn = null;
	}

	ArrayList<Event> processData () {

		ArrayList<Event> temp = new ArrayList<Event>();

		try {
			while (rs.next()) {
				Event e = new Event();

				e.setName(rs.getString("name"));
				e.setStartMonth(rs.getInt("start_month"));
				e.setStartDay(rs.getInt("start_day"));
				e.setStartYear(rs.getInt("start_year"));
				e.setEndMonth(rs.getInt("end_month"));
				e.setEndDay(rs.getInt("end_day"));
				e.setEndYear(rs.getInt("end_year"));
				e.setColor(new Color(rs.getInt("rgbid")));

				temp.add(e);
			}
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
		}
		return temp;
	}

	boolean writeData(ArrayList<Event> events, String db) {

		connectDB(db);

		String clearData = "DELETE FROM myevents WHERE event_id > 0";

		try {
			stmt.executeUpdate(clearData);
		} catch (SQLException e) {
			e.printStackTrace();
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
				try { addEvent.close(); } catch (Exception e){ /* ignored */ }
			}
		}

		conn = null;
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

	void connectDB (String dbName) {

		String db = dbName;
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost/" + db + "?useSSL=false";
		String user = "root";
		String pass = "password";

		//Connects to DB
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pass);
			System.out.println("Connected to database : " + db);
			stmt = conn.createStatement();
		} catch (Exception e) {
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
	}

}
