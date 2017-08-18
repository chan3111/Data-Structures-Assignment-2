/**   Name: EventComparator.java
 * 	  Purpose: This class models an EventComparator used for BinarySearch methods using a singleton design pattern.
 * 	  Course: CST8130
 * 	  Section: 303
 *	  Author:  Chandler Newman-Reed
 *	  Date: 2/14/2017
 *	  Data fields:  instance: EventComparator - a new instance of this class
 *    Methods:  default constructor - private in order for one instance only of class to be created
 *    			getInstance : EventComparator - returns a single instance of class
 *    			compare(Event, Event) : int - compares two events for greater than, less than or equal
 */
import java.util.Comparator;

public class EventComparator implements Comparator<Event>{
	
	private static EventComparator instance = new EventComparator();
	
	private EventComparator(){}

	public static EventComparator getInstance() {
		return instance;
	}
	
	@Override
	public int compare(Event event1, Event event2) {
		if(event1.isGreater(event2))
			return 1;
		if(event1.isLess(event2))
			return -1;
		return 0;
	}

}
