package designchallenge1;
import java.util.ArrayList;

abstract class DataParser {
	abstract ArrayList<Event> readData(String location);
	abstract boolean writeData(ArrayList<Event> events, String location);
}
