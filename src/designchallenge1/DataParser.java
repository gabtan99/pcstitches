package designchallenge1;
import java.util.ArrayList;

abstract class DataParser {

	ArrayList<Event> readFromLocation (String location) {
		readData(location);
		return processData();
	}

	boolean saveToLocation(ArrayList<Event> events, String location) {
		return writeData(events, location);
	}


	abstract void readData(String location);
	abstract ArrayList<Event> processData();
	abstract boolean writeData(ArrayList<Event> events, String location);
}
