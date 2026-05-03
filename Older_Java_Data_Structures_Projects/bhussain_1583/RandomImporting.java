import java.util.Scanner;
//This example class, RandomImporting, was designed to show three ways which we may generate a random number.
//Use this as a resoure guide and notes whenever you are confused about the best way to generate a number. 
//To facilitate DRY (Do not Repeat Yourself) principles, we will practice creating methods as well.
//We also answer the question from class.. What happens when concatonating an object? 

//Three methods for random... There are more as well. These are just popular or in our book.
import java.security.SecureRandom; //1) Secure random. The most random but the most recource intensive.
import java.lang.Math; 	           //2) Generate numbers with the Math class. Always returns a double between [0,1). We can choose the range we want with a little math.
import java.util.Random;           //3) Can produce random numbers in an intuitive way with many methods to choose from. 
public class RandomImporting
{
	public static void main(String[] args)
	{
		
		//To answer a question in class.. What happens when you concatonate a variable containing an object?
		Scanner canConcat = new Scanner(System.in);
		String out = "Hello! What does the Scanner say?: ";
		
		System.out.println(out+canConcat); //This implicitly envokes the .toString() method for the Scanner.
		//Run the code and check the output! We will see a lot of meta-options. 
		
		//Let's run our random methods 5 times each and print the output.
		for(int i=0; i<5; i++){
			int random = randomClassMethod(5,10);
			int math = mathClassMethod(5,10);
			int secure = secureRandomClassMethod(5,10);
			System.out.println("Random Class: " + random + " Math Class: " + math + " Secure Class: " + secure);
		}
	}
	
	
	//We want each method to return an int between a range of numbers INCLUDING those numbers. 
	//https://stackoverflow.com/questions/363681/how-do-i-generate-random-integers-within-a-specific-range-in-java
	//Here is a great stack overflow resource where the pros and cons of many random methods are discussed. 
	public static int randomClassMethod(int min, int max){
		Random randy = new Random(); //This is the least secure and random method. 
		return randy.nextInt(max - min + 1) + min;
	}
	
	public static int mathClassMethod(int min, int max){
		//Math.random() gives a double between [0,1) and the rest scales the random number to be between max and min!
		return (int)((Math.random() * ((max - min) + 1))+ min); 
	}
	
	public static int secureRandomClassMethod(int min, int max){
		SecureRandom randal = new SecureRandom();
		return randal.nextInt(max - min + 1) + min;
	}

}