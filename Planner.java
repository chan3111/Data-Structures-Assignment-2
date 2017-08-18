/**   Name: Planner.java
 * 	  Purpose: This class models a planner.
 * 	  Course: CST8130
 * 	  Section: 303
 *	  Author:  Chandler Newman-Reed
 *	  Date: 2/14/2017
 *	  Data fields:  events: ArrayList<Event> - stores the events made by the user 
 *                  numEvents: int - value of events currently in the planner
 *                  MAX_EVENTS: int - maximum events allowed in planner
 *    Methods:  default constructor - initilizes event array with MAX_EVENTS 
 *				inputEvent(Scanner, String): boolean - sets type of event to add based on user input, 
 *              deleteEvent(OurDate, OurTime): boolean - deletes an event at a given date and time
 *              displayOneDay(OurDate): void - display events of a certain date
 *				displaySevenDays(OurDate): void - displays events for a week starting at a certain date
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Planner {
	
	private ArrayList<Event> events;
	
	private int numEvents;
	
	private final int MAX_EVENTS = 1000;
	
	public Planner(){
		events = new ArrayList<Event>();
		numEvents = 0;
	}
	
	public boolean inputEvent(Scanner input, String prompt){
		int type = 0;
		Event tempEvent = null;
		boolean added = false;
		
		if(numEvents >= MAX_EVENTS){
			System.out.println("Maximum events entered, planner full");
			return false;
		}
		
		do{
			if(prompt.charAt(0) == 'y'){
				System.out.println("\nEnter the event type to add: ");
				System.out.println(" 1 for a meeting event");
				System.out.println(" 2 for a school event");
				System.out.println(" 3 for a work event");
				System.out.print(" 4 for a social event  ");
			}
			
			
			if(input.hasNextInt()){
				type = input.nextInt();
				if(type < 1 || type > 4 && prompt.charAt(0) != 'y'){
					System.out.println("Invalid event type");
					return false;
				}
			}
			else {
				System.out.println("Invalid event type");
				input.next();
				if(prompt.charAt(0) != 'y')
					return false;
			}
		} while (type < 1 || type > 4);
		
		switch(type){
		case 1:
			tempEvent = new Meeting();
			break;
		case 2:
			tempEvent = new School();
			break;
		case 3:
			tempEvent = new Work();
			break;
		case 4:
			tempEvent = new Social();
			break;
		}
			
		if(!tempEvent.inputEvent(input, prompt)){ 
			return false;
		}
		
		if(Collections.binarySearch(events, tempEvent, EventComparator.getInstance()) >= 0){
			System.out.println("You already have an activity for that date and time ... cannot be entered");
			return true; // true only necessary for adding from a file with duplicates
		}
		
		for(int i = 0; i < numEvents; i++){
			if(tempEvent.isLess(events.get(i))){
				events.add(i, tempEvent);
				added = true;
				break;
			}
		}
		
		if(!added)
			events.add(numEvents, tempEvent);
		
		++numEvents;
		return true;
	}
	
	public boolean deleteEvent(OurDate date, OurTime time){
		int index = Collections.binarySearch(events, new Event(date, time), EventComparator.getInstance());
		if(index >= 0){
			events.remove(index);
			System.out.println("\nEvent deleted.");
			return true;
		} else {
			System.out.println("No event at that date/time.");
		}
		return false;
	}
	
	public int displayOneDay(OurDate date, int startIndex){
		int i;
		System.out.println("Your activities for " + date.toString() + " are: ");
		for(i = startIndex; i < numEvents && !events.get(i).getDate().isGreater(date); i++){
				if(events.get(i).getDate().isEqual(date))
					System.out.println(events.get(i));
		}
		return i;
	}
	
	public void displaySevenDays(OurDate date){
		int index = 0;
		System.out.println("Your activities for the week starting " + date.toString() + " are: ");
		for(int j = 0; j < 7; j++){
			index = displayOneDay(date, index);
			date.addOne();
			System.out.println();
		}
	}
}
