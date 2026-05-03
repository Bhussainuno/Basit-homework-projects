import java.util.Scanner;
public class World 
{

	private String[][][] levels;

	public World() 
	{
		//get all map data and save it for later
		Scanner input = new Scanner(System.in);
		int count = input.nextInt();
		levels = new String[count][][];
		for (int lvl=0; lvl<count; lvl++) 
		{
		int rows = input.nextInt();
		int cols = input.nextInt();
		input.nextLine();
		setLevel(lvl, rows, cols, input);
		}
	}

			private void setLevel(int lvl, int rows, int cols, Scanner input) 
		{
			levels[lvl] = new String[rows][cols];
			input.useDelimiter("");
			for (int y=0; y < rows; y++) 
			{
				for (int x=0; x < cols; x++) 
			{
				String tile = input.next();
				levels[lvl][y][x] = tile;
				System.out.print(tile);
			}
			input.nextLine();
			System.out.print("\n");
			}
			input.reset();
		}












}
