//package Lab7Student;

//#TODO
//Use appropriate imports
//hint: there are a lot!


//all the import statements needed
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
//package Lab7Student;

//#TODO
//Use appropriate imports
//hint: there are a lot!

public class CartoonCharacterSerializer 
{
	
	private static ObjectOutputStream outputCartoon;
	private static ObjectInputStream inputCartoon;
	private static PrintWriter writer;
	
	//Method that writes one CartoonCharacter object out to a file
	public static void serializeCharacter(CartoonCharacter character) 

        {
		//#TODO
                ObjectOutputStream outputCartoon = null;
                try
                {
		//Initialize outputCartoon to serialize objects to a file called Cartoon.ser
                        outputCartoon = new ObjectOutputStream(new FileOutputStream("Cartoon.ser"));  
                        outputCartoon.writeObject(character);
                        outputCartoon.close(); 



                }
                catch(IOException e){

                }

		//Write the character object out to the file
		//Close the stream
	}
	
	public static CartoonCharacter deserializeCharacter() {
		CartoonCharacter cartoon = null;
                try{
		
		//#TODO
		//Initialize inputCartoon to read objects from a file called Cartoon.ser
                        inputCartoon = new ObjectInputStream(new FileInputStream("Cartoon.ser"));

                        //Read one CartoonCharacter object and store it in variable cartoon
                        cartoon = (CartoonCharacter)inputCartoon.readObject();
                        inputCartoon.close();
                        


                } catch(IOException e){
                        e.printStackTrace();
                }
                catch(ClassNotFoundException e){
                        e.printStackTrace();
                }
		//Read one CartoonCharacter object and store it in variable cartoon
		
		return cartoon;
	}
	
	// NOTE: This is optional and you don't have to do this if you choose not to
	// public static void printToFile(String stringToFile) throws FileNotFoundException {
		
	// 	//#TODO
	// 	//Initialize writer to print characters to a file called C-137.txt
	// 	//Print stringToFile to that file
	// 	//Print the string "PrintWriter makes printing 50 times easier!" followed by a newline character out to the file
	// 	//Close the PrintWriter stream
	// }

}