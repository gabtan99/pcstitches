package designchallenge1;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class PSVDataParser extends DataParser{

	private static final String PIPE_DELIMITER = "\\|";
	private static final String SLASH_DELIMITER = "\\/";
	private ArrayList<String[]> rawData;

	void readData(String readURL) {

		try (BufferedReader reader = new BufferedReader(new FileReader(new File(readURL)))) {

			String line;
			rawData = new ArrayList<String[]>();
			while ((line = reader.readLine()) != null) {

				String[] eventDetails = line.split(PIPE_DELIMITER);
				rawData.add(eventDetails);

			}
		} catch (IOException | IllegalArgumentException | SecurityException e) {
			e.printStackTrace();
		}

	}

	ArrayList<Event> processData () {
		ArrayList<Event> temp = new ArrayList<Event>();
		for (String[] rawDatum : rawData) {
			if (rawDatum.length == 3) {
				Event e = new Event();
				int[] date = getMonthDayYear(rawDatum[1]);

				e.setStartMonth(date[0]);
				e.setStartDay(date[1]);
				e.setStartYear(date[2]);

				e.setEndMonth(date[0]);
				e.setEndDay(date[1]);
				e.setEndYear(date[2]);

				e.setName(rawDatum[0]);
				Color c = null;
				try {
					c = (Color) Color.class.getField(rawDatum[2].toUpperCase()).get(null);
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
				} catch (NoSuchFieldException e1) {
					e1.printStackTrace();
				}
				e.setColor(c);

				temp.add(e);
			} else if (rawDatum.length == 12) {
				Event e = new Event();

				e.setName(rawDatum[0]);

				int n = Integer.parseInt(rawDatum[1]);
				e.setStartMonth(n);

				n = Integer.parseInt(rawDatum[2]);
				e.setStartDay(n);

				n = Integer.parseInt(rawDatum[3]);
				e.setStartYear(n);

				n = Integer.parseInt(rawDatum[4]);
				e.setStartHour(n);

				n = Integer.parseInt(rawDatum[5]);
				e.setStartMinute(n);

				n = Integer.parseInt(rawDatum[6]);
				e.setEndMonth(n);

				n = Integer.parseInt(rawDatum[7]);
				e.setEndDay(n);

				n = Integer.parseInt(rawDatum[8]);
				e.setEndYear(n);

				n = Integer.parseInt(rawDatum[9]);
				e.setEndHour(n);

				n = Integer.parseInt(rawDatum[10]);
				e.setEndMinute(n);

				Color c = null;
				try {
					c = (Color) Color.class.getField(rawDatum[11].toUpperCase()).get(null);
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
				} catch (NoSuchFieldException e1) {
					e1.printStackTrace();
				}
				e.setColor(c);

				temp.add(e);
			}
		}
		return temp;
	}

	boolean writeData(ArrayList<Event> events, String saveURL) {

		try (PrintWriter pw = new PrintWriter(new File(saveURL))) {

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
