
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;

import java.io.IOException;
import java.io.EOFException;

import java.util.ArrayList;

import java.io.ObjectInputStream;
import java.io.FileInputStream;



public class Calendar{
	
	public static void main(String[] args){
		Event party = new Event("Fun Party", "2022-03-03 11:00", "French Quarter", "It's Mardi Gras 2022!");
		Event test = new Event("JavaII Mid-term", "2022-03-11 11:30", "Math 220", "Java II Mid-term 2022 Spring.");
		Event hacathon = new Event("Hacathon 2022", "2022-04-06 10:00", "Math 320", "ACM-W Hacathon 2022!");

		ArrayList<Event> eventList = new ArrayList(); // create an array to add all the events

		//Add event objects to the ArrayList 
		eventList.add(party); eventList.add(test); eventList.add(hacathon);

		//Declare ObjectOutputStreams for writing objects out to disk
		ObjectOutputStream output = null;
		ObjectOutputStream outputList = null;

		// try catch block
		try{
			// Writing objects out, one at a time to the file eventFile.ser
			output = new ObjectOutputStream(new FileOutputStream("eventFile.ser")); // ObjectOutputStream does the conversion, FileOutputSream puses the data
			output.writeObject(party); // Write object party out to eventFile.ser on disk
			output.writeObject(test); // Write test object to eventFile.ser on disk
			output.writeObject(hacathon); // Write hacathon object to eventFile.ser on disk
			output.close(); // Call .close() to flush any remaining bytes in the stream out to disk and free the memory from the stream
			
			// Writing out the while list to the file eventList.ser in one call of writeObject
			outputList = new ObjectOutputStream(new FileOutputStream("eventList.ser"));
			outputList.writeObject(eventList); // Write eventList ArrayList<event> out to eventList.ser on disk
			outputList.close(); // Call .close() to flush any remaining bytes in the stream out to disk and free the memory from the stream
			System.out.println("Done Writing\n");
		} // end of try block
		catch(IOException e){ // must include
			e.printStackTrace();

		} // end of catch block

		// Read these eventFile.ser and eventList.ser and print the objects saved

		// Declare ObjectInputStreams for reading objects from the files on disk
		ObjectInputStream inputOneByOne = null;
		ObjectInputStream inputAll = null;

		Event event = null;
		try{
			// initialize ObjectInputStream to read from the eventFile.ser
			// ObjectInputStream does the conversion, FileInputStream pull the data from the file
			inputOneByOne = new ObjectInputStream(new FileInputStream("eventFile.ser"));
			// Read object one by one until there are none left to read
			while(true){
				event = (Event)inputOneByOne.readObject();
				System.out.println(event);
				System.out.println("\n");
			} // end of while loop
		} // end of try block

		catch(ClassNotFoundException e1){
			e1.printStackTrace();
		} // end of catch block

		catch(IOException e2){
			try{
				inputOneByOne.close();
				if(e2 instanceof EOFException){ // When end of file is reached, print this message
					System.out.println("Reached end of file, " + e2);						
				}else{
					e2.printStackTrace();
				}
			}// end of try block
			catch(IOException e3){
				e3.printStackTrace();
			} // end of catch  block
		}// end of catch block

		eventList = null; // Set our eventList ArrayList equal to null just to demonstrate
		System.out.println(eventList);

		try{
			// initialize ObjectInputStream to read from file eventList.ser
			inputAll = new ObjectInputStream(new FileInputStream("eventList.ser"));
			eventList = (ArrayList<Event>) inputAll.readObject(); // reads the objects and deserialize the arrayList and assigns it to eventList ArrayList object
			System.out.println("\n\nPrinting the whole array below:\n");
			System.out.println(eventList); // prints the content of eventList
			inputAll.close(); // to flush any remaining bytes and free the memory from the stream
		} // end of try block
		catch(ClassNotFoundException e1){ // must include
			e1.printStackTrace();
		} // end of catch block
		catch(IOException e2){ // must include
			e2.printStackTrace();
		}  
		

	} // end of main method
	 

} // end of Calendar class