public class Food {
	
	public static final int TILE_SIZE = 32;
	public static int x;
	public static int y; 
	private static String image;
	private static boolean eater = true;


	// start food data

	public static void start(int x, int y){

		Food.x = x;
		Food.y = y;
		image = "Assets/food.png ";

	} 

	public static void draw(){
		int tileX = x * TILE_SIZE + TILE_SIZE/2;
		int tileY = y * TILE_SIZE + TILE_SIZE/2;
		StdDraw.picture(tileX, tileY, image);
	}

	
	public static void update() 
	{
		 if ( Player.getX() == getX() && Player.getY() == getY() ) 

		{

				eater = false;

			System.out.print("Here===");


			if(Scene.canMove(x,y+1)){
			}
			

			// char key = StdDraw.nextKeyTyped();
			
			// if ( key == 'w' && Scene.canMove(x,y-1))
			// {
			// 	y--;
			// }
			// else if ( key == 's'&& Scene.canMove(x,y+1)) 
			// {
			// 	y++;
			// }
			// else if (key == 'a' && Scene.canMove(x-1,y)) 
			// {
			// 	x--;
			// }
			// else if (key == 'd' && Scene.canMove(x+1,y)) 
			// {
			// 	x++;
			// }
		}
		
	}
	public static int getX() 
	{
		return x;
	}
	public static int getY() 
	{
		return y;
	}

	public static boolean getFood() 
	{
		
		return eater ;
	}
		

}






