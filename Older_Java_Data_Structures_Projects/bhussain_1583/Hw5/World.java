	import java.util.Scanner;
	
	public class World 

	{


		private static String[][][] levels;




		public static void start() 
		{


			// get all map data and save it for later
			
			Scanner input = new Scanner(System.in);

			int count = input.nextInt();

			levels = new String[count][][];

			for (int lvl=0; lvl<count; lvl++) 
			{

				int rows = input.nextInt();
				
				int cols = input.nextInt();
				
				setLevel(lvl, rows, cols, input);

		
			}
		}


		public static void setLevel(int lvl,int rows, int cols, Scanner input) 
		{
			levels[lvl] = new String[rows][cols];

				for (int y=0; y < rows; y++)
			{
					for (int x=0; x < cols; x++) 
				{
					String tile = input.next();
					levels[lvl][y][x] = tile;
				}
			
			}
		}
		public static String[][] getLevel(int level) 
		{
			return levels[level];
		}

		public static int getLength() 
		{
			return levels.length;
		}

			
	}
		
	