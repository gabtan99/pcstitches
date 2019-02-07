package designchallenge1;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class CSVDataParser extends DataParser {

	private static final String COMMA_DELIMITER = "\\, ";
	private static final String SLASH_DELIMITER = "\\/";

	ArrayList<Event> readData(String readURL) {



		ArrayList<Event> temp = new ArrayList<Event>();

		try (BufferedReader reader = new BufferedReader(new FileReader(new File(readURL)))) {
			String line;
			while ((line = reader.readLine()) != null) {

				String[] eventDetails = line.split(COMMA_DELIMITER);

				if (eventDetails.length == 3) {
					Event e = new Event();
					int[] date = getMonthDayYear(eventDetails[0]);

					e.setStartMonth(date[0]);
					e.setStartDay(date[1]);
					e.setStartYear(date[2]);

					e.setEndMonth(date[0]);
					e.setEndDay(date[1]);
					e.setEndYear(date[2]);

					e.setName(eventDetails[1]);
					Color c = (Color) Color.class.getField(eventDetails[2].toUpperCase()).get(null);
					e.setColor(c);

					temp.add(e);
				}

				else {

					Event e = new Event();

					e.setName(eventDetails[0]);

					int n = Integer.parseInt(eventDetails[1]);
					e.setStartMonth(n);

					n = Integer.parseInt(eventDetails[2]);
					e.setStartDay(n);

					n = Integer.parseInt(eventDetails[3]);
					e.setStartYear(n);

					n = Integer.parseInt(eventDetails[4]);
					e.setStartHour(n);

					n = Integer.parseInt(eventDetails[5]);
					e.setStartMinute(n);

					n = Integer.parseInt(eventDetails[6]);
					e.setEndMonth(n);

					n = Integer.parseInt(eventDetails[7]);
					e.setEndDay(n);

					n = Integer.parseInt(eventDetails[8]);
					e.setEndYear(n);

					n = Integer.parseInt(eventDetails[9]);
					e.setEndHour(n);

					n = Integer.parseInt(eventDetails[10]);
					e.setEndMinute(n);

					n = Integer.parseInt(eventDetails[11]);
					Color c = new Color(n);
					e.setColor(c);

					temp.add(e);
				}

			}

		} catch (IOException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException
				| SecurityException e) {
			e.printStackTrace();
		}
		return temp;
	}

	boolean writeData(ArrayList<Event> events, String saveURL) {
		try (PrintWriter pw = new PrintWriter(new File(saveURL))) {

			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < events.size(); i++) {

				sb.append(events.get(i).getName());
				sb.append(", ");
				sb.append(events.get(i).getString(events.get(i).getStartMonth()+1));
				sb.append(", ");
				sb.append(events.get(i).getString(events.get(i).getStartDay()));
				sb.append(", ");
				sb.append(events.get(i).getString(events.get(i).getStartYear()));
				sb.append(", ");
				sb.append(events.get(i).getString(events.get(i).getStartHour()));
				sb.append(", ");
				sb.append(events.get(i).getString(events.get(i).getStartMinute()));
				sb.append(", ");
				sb.append(events.get(i).getString(events.get(i).getEndMonth()+1));
				sb.append(", ");
				sb.append(events.get(i).getString(events.get(i).getEndDay()));
				sb.append(", ");
				sb.append(events.get(i).getString(events.get(i).getEndYear()));
				sb.append(", ");
				sb.append(events.get(i).getString(events.get(i).getEndHour()));
				sb.append(", ");
				sb.append(events.get(i).getString(events.get(i).getEndMinute()));
				sb.append(", ");
				sb.append(events.get(i).getColorRGB());
				sb.append("\n");
			}

			pw.write(sb.toString());

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
