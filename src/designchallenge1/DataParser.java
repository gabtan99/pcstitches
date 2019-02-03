package designchallenge1;
import java.util.ArrayList;

abstract class DataParser {
	abstract ArrayList<Event> readData();
	abstract boolean writeData(ArrayList<Event> events);
}
