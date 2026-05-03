import java.util.Scanner;
public class hw2
{
	public static void main(String[] args)
	{

		int min = 0 ;
		int max = 100;
		int userInput;
		int userInputOption;
		int randomNumber;
		int numberOfTries = 1;
		int guessedNumber; 
		boolean correctGuess = false;




		Scanner scanner = new Scanner(System.in);


		System.out.println("Please enter a number");

		userInput = scanner.nextInt();


		


		while (correctGuess == false)
		{
			

			randomNumber = (int)Math.floor(Math.random()*(max-min + 1)+min);
			System.out.println("\nIs this your number? "+randomNumber);
			System.out.println("\nPlease select if the guessed number is: \n 1. Correct \n 2. Low \n 3. High");

			userInputOption = scanner.nextInt();

			if(userInputOption == 1)
			{
				System.out.print("Awesome!");
				correctGuess = true;
			}

			if(userInputOption == 2)
			{
				max = randomNumber - 1;
				numberOfTries = numberOfTries + 1;

			}

			if(userInputOption == 3)
			{
				min = randomNumber + 1;
				numberOfTries = numberOfTries + 1;

			}
		}
		System.out.println("\nSteps it took to guess the number: "+numberOfTries);


	}






}