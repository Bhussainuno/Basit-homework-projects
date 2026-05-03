/*
	In this program, MethodMadness, we give concrete examples on how to do the following:
	1) Import and use a non-static method from the Scanner class.
	2) Import and use a static method from the Math class.
	3) Create your own method.
	4) Use this method repeatedly in your own code!
	
	We will solve the same simple 
*/
import java.util.Scanner;
import java.lang.Math; //Does not need to be imported! I'll still do it here, though.
public class MethodMadness
{
	public static void main(String[] args)
	{
		//1) Import and use a non-static method from the Scanner class.
		
		//Since the methods in the Scanner class are non-static, we must use the Scanner class to or create a Scanner 'object'
		Scanner input = new Scanner(System.in); //Objects are invoked with the 'new' keyword
		//Now we have a Scanner object stored in the variable input.
		
		//Invoke non-static method nextInt() using the Scanner object.
		int num = input.nextInt();
		
		
		
		//2) Import and use a static method from the Math class.
		//Since the methods within the Math class are static, we have no need to create a Math object!
		double max = Math.max(4.5, 16.2); //We simply invoke the Math class directly. 
		//Note, the max method expects two arguments, both arguments must be doubles, and max returns a double (in this case, the max number!).

		System.out.println("Num = " + num + " Max = " + max); // Is println static or non-static?
		
		
		
		//3) Create your own method. How exciting! Check out the method maxInt below the main method.
		int methodMax = maxInt(num, 25); //Because the method is static, it does not require a MethodMadness (the name of the class) 'object'
		System.out.println("methodMax = " + methodMax);
		
		
		
		//4) Use this method repeatedly in your own code! This is a main benefit of using methods. 
		int num1 = 0;
		int num2 = 1;
		int num3 = 2;
		
		methodMax = maxInt(num1, num2);
		methodMax = maxInt(num2, num3);
		System.out.println("methodMax = " + methodMax);
	}

	//maxInt is public which means the method may be accessed outside of this file.
	//maxInt is static which means the method may be ran directly and not through an object.
	//maxInt declares a return type of int which means the compiler expects the keyword return followed by an int.
	public static int maxInt(int x, int y){ //Arguments MUST have a valid datatype (int x, float b, Scanner input,...). 
		//If x is greater than y, return the value in x else return the value in y. 
		if(x>y){
			return x;
		}
		else{
			return y;
		}
		//Logically equivalent to...
	}
	//Second maxInt method with only one return type.
	public static int maxInt2(int x, int y){
		int max = x;
		if(y>x){
			max=y;
		}
		return max;
	}
}