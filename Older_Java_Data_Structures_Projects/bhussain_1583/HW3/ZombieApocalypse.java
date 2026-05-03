import java.util.Scanner;
// 1ST GOAL 
public class ZombieApocalypse
{
	
	public static void main(String[] args)

	{
			
			Scanner input = new Scanner(System.in);

			boolean gameOver = false;
			boolean getKey = false;

			String floorTile = ". ";
			String playerTile = "@ ";
			String exitTile = "# ";
			String zombieTile = "* ";
			String obstacle = "| ";
			String key = "$ ";

			int obstacleX = 5;
			int obstacleY = 7;

			int min = 2 ;
			int max = 8;

			int keyX = (int)Math.floor(Math.random()*(max-min + 1)+min); 
			int keyY = (int)Math.floor(Math.random()*(max-min + 1)+min);


			int gameLevel = 0;
			int colSize = 10;
			int rowSize = 10;
			
			int playerX = 0;
			int playerY = 0;

			int exitX = colSize - 1;
			int exitY = rowSize - 1;

			int playerMoveY = 0;
			int playerMoveX = 0;

			int zombieX = 5;
			int zombieY = 5;
			int zombieX1 = 6; // showing zombie1 on the X exes 
			int zombieY1 = 7;  // showing zombie1 on the Y axes
			int zombieX2 = 8; // showing zombie2 on the X exes 
			int zombieY2 = 8; // showing zombie2 on the Y exes


				while (gameOver ==false &&  gameLevel<=2)
				 {
				 	draw(floorTile, rowSize, colSize, playerX, playerY,playerTile, zombieX, zombieX1,zombieX2, zombieY, zombieY1, zombieY2,zombieTile,key,keyX, keyY, obstacle,obstacleX,obstacleY,exitTile,exitX, exitY, getKey);


					String choice = input.nextLine();

					if (choice.equals("w") && playerY>0 && (playerY!=obstacleY+1 || playerX!=obstacleX))
					{
						getKey = playerMovement(playerX, keyX, playerY, keyY+1, getKey);
						System.out.println(playerY);
						playerY--;

					}
					else if (choice.equals("s") && playerY<=rowSize-2 && (playerY!=obstacleY-1|| playerX!=obstacleX) ){
						getKey = playerMovement(playerX, keyX, playerY, keyY-1,getKey);
						System.out.println(playerY);
						playerY++;
					}
					else if (choice.equals("d") && playerX<=colSize-2 && (playerY!=obstacleY|| playerX!=obstacleX-1)){
						getKey = playerMovement(playerX, keyX-1, playerY, keyY,getKey);	if(playerX == keyX-1 && playerY==keyY)
						System.out.println(playerX);
						playerX++;
					}
					else if (choice.equals("a")&& playerX>0 && (playerY!=obstacleY || playerX!=obstacleX+1)){
					getKey = playerMovement(playerX, keyX+1, playerY, keyY,getKey);	if(playerX == keyX+1 && playerY==keyY)
						System.out.println(playerX);
				

						playerX--;
					}
			

			//Check Win Condition ======

			if (playerX == exitX && playerY == exitY && getKey == true){

				gameLevel++;

				if(gameLevel==1 )
				{
				// System.out.println("if1");
					colSize=15;
					colSize=15;

					max = 11;

					keyX = (int)Math.floor(Math.random()*(max-min + 1)+min); 
					keyY = (int)Math.floor(Math.random()*(max-min + 1)+min);

					exitX = colSize - 1;
					exitY = rowSize - 1;

					playerX = 0;
					playerY = 0;


					playerMoveY = 0;
					playerMoveX = 0;

					getKey = false;
				

				}

				else if(gameLevel==2 )
				{

					colSize=8;
					rowSize=8;

					exitX = colSize - 1;
					exitY = rowSize - 1;

					max = 5	;
					keyX = (int)Math.floor(Math.random()*(max-min)+min); 
					keyY = (int)Math.floor(Math.random()*(max-min)+min);

					playerX = 0;
					playerY = 0;

					getKey = false;
					
					
				}
				System.out.println("LEVEL "+gameLevel+"!");
			}


			//Execute Monster Action ======

			int zombieChoice = (int) (Math.random()*4 );
			if (zombieChoice == 0 ) {
				zombieX = zombieMovement(zombieX, colSize);
				zombieX1 = zombieMovement(zombieX1, colSize);  // moving zombie1 into coloum Size
				zombieX2 = zombieMovement(zombieX2, colSize);  // moving zombie2 in coloum size			
			}
			else if (zombieChoice == 1) {
				zombieX = --zombieX >= 0 ? zombieX : (colSize - 1);
			}
			else if (zombieChoice == 2) {
				zombieY = --zombieY >= 0 ? zombieY : (rowSize - 1);
			}
			else if (zombieChoice == 3) {
				zombieY = zombieMovement(zombieY, rowSize);  // moving zombie in rowSize
				zombieY1 =zombieMovement(zombieY1, rowSize) ; // moving zombie1 in rowSize
				zombieY1 = zombieMovement(zombieY1, rowSize); //moving zombie2 in row Size
			}

			gameOver = loseCondition(   zombieX, zombieX1, zombieX2, zombieY, zombieY1, zombieY2,  playerX,  playerY, gameOver );

			


	}
}

	public static void draw(String floorTile,int  rowSize, int colSize, int playerX, int playerY,String playerTile, int zombieX, int zombieX1,int zombieX2, int zombieY, int zombieY1,int  zombieY2,String zombieTile,String key,int keyX,int  keyY, String obstacle,int obstacleX,int obstacleY,String exitTile,int exitX, int exitY, boolean getKey)
			{

				for( int y=0; y < rowSize; y++ )
				{ //for number of rows

					for( int x=0; x < colSize; x++ )
					{ //for number of columns

						if ( x == playerX && y == playerY)
						{ //if this grid coord is player's position

							System.out.print(playerTile); //...print player tile
						}
						else if ( x == exitX && y == exitY)
						{ //if this grid coord is exit's position
							System.out.print(exitTile);    //...print exit tile	

						}
						else if (( x == zombieX && y == zombieY) || ( x == zombieX1 && y == zombieY1)) // aponing zombie and zombie1

						{
							System.out.print(zombieTile);

						}
						else if (x == zombieX2 && y == zombieY2){ // spawing my zombie2
							System.out.print(zombieTile);
						}

						else if(x== obstacleX && y ==obstacleY)
						{
							System.out.print(obstacle);
						}	
					
						else if(x== keyX && y ==keyY)
						{
							if(getKey==false)
							{
								System.out.print(key);

							}
							else
							{
								System.out.print(floorTile);
							}
						}

						else
						{
							System.out.print(floorTile); //...print floor tile
						}
					}
				System.out.print("\n");
				}
			}
				
	public static boolean loseCondition(  int zombieX, int zombieX1,int zombieX2, int zombieY, int zombieY1,int zombieY2,  int playerX, int playerY, boolean gameOver )
	{
		if (zombieX == playerX && zombieY == playerY){
			gameOver = true;
			System.out.println("Your brains were eaten by the zombie");
		}
		else if(zombieX1 == playerX && zombieY1 == playerY){
			gameOver = true;

			System.out.println("Your brains were eaten by the zombie1"); // eaten by zombie1
		}

		else if(zombieX2 == playerX && zombieY2 == playerY){
			gameOver = true;

			System.out.println("Your brains were eaten by the zombie2"); // eaten by zombie2
		}
		return gameOver;
			 
	}	


	public static int zombieMovement(int zombieAxis, int gridSize){
		int zombieMovement = (zombieAxis +1) % gridSize;
		return zombieMovement;

	}

	public static boolean playerMovement(int playerX,int keyX,int playerY,int keyY, boolean getKey){
		if(playerX == keyX && playerY==keyY)
		{
			getKey = true;
		}
		return getKey;



	}
}


