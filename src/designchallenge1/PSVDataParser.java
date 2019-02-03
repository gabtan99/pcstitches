package designchallenge1;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class PSVDataParser extends DataParser{

	private static final String PIPE_DELIMITER = "\\|";
	private static final String SLASH_DELIMITER = "\\/";
	private static final String PSV_FILE = "IOFiles/DLSU Unicalendar.psv";
	private static final String EVENTS_STORAGE = "IOFiles/My Events.psv";


	ArrayList<Event> readData() {
		ArrayList<Event> temp = new ArrayList<Event>();

		try (BufferedReader reader = new BufferedReader(new FileReader(new File(PSV_FILE)))) {

			String line;

			while ((line = reader.readLine()) != null) {

				String[] eventDetails = line.split(PIPE_DELIMITER, 3);

				Event e = new Event();

				int[] date = getMonthDayYear(eventDetails[1]);

				e.setStartMonth(date[0]);
				e.setStartDay(date[1]);
				e.setStartYear(date[2]);

				e.setEndMonth(date[0]);
				e.setEndDay(date[1]);
				e.setEndYear(date[2]);

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

	boolean writeData(ArrayList<Event> events) {

		try (PrintWriter pw = new PrintWriter(new File(EVENTS_STORAGE))) {

			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < events.size(); i++) {


				sb.append(events.get(i).getName());
				sb.append("|");
				sb.append(events.get(i).getString(events.get(i).getStartMonth()+1));
				sb.append("|");
				sb.append(events.get(i).getString(events.get(i).getStartDay()));
				sb.append("|");
				sb.append(events.get(i).getString(events.get(i).getStartYear()));
				sb.append("|");
				sb.append(events.get(i).getString(events.get(i).getStartHour()));
				sb.append("|");
				sb.append(events.get(i).getString(events.get(i).getStartMinute()));
				sb.append("|");
				sb.append(events.get(i).getString(events.get(i).getEndMonth()+1));
				sb.append("|");
				sb.append(events.get(i).getString(events.get(i).getEndDay()));
				sb.append("|");
				sb.append(events.get(i).getString(events.get(i).getEndYear()));
				sb.append("|");
				sb.append(events.get(i).getString(events.get(i).getEndHour()));
				sb.append("|");
				sb.append(events.get(i).getString(events.get(i).getEndMinute()));
				sb.append("|");
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

	int[] getMonthDayYear (String date) {

		String[] dateString = date.split(SLASH_DELIMITER, 3);
		int[] dateInt = new int[dateString.length];

		for (int i = 0; i < dateInt.length; i++){
			dateInt[i] = Integer.parseInt(dateString[i]);
		}
		return dateInt;
	}
}
