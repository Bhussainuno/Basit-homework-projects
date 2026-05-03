public class Game 

{
	private static boolean gameOver = false;
	private static int score = 0;


	public static void main(String[] args)

	{
		start(); //setup game
		while (gameOver == false){

			update(); //update game
			render(); //render game
		}
		
	}
	public static void render() {
		
		Scene.draw(); //draw scene
		Enemy.draw(); //draw enemy
		Player.draw(); //draw player
		StdDraw.text(64,32,"Score: " + score); //draw score
		StdDraw.show(100); //show graphics

	}
	public static void update() {
		Player.update(); //update player
		Enemy.update(); // update enemy data

			//check for input
			//update player
			
	}
	public static void start() {
		Scene.start(); // setup scene data
		Enemy.start(); // setup enemy data
		Player.start(); //setup player data

	

	}

	public static void addScore(){
		score++; //increment score
		}




}

	