public class Controller 
{
	private Player player;
	private SecondPlayer player2;

	public Controller( Player player, SecondPlayer player2) 
	{
		this.player = player;
		this.player2 = player2;


	}
	public void onMousePress() 
	{
		if ( StdDraw.mousePressed() ) 
		{
			double mouseX = StdDraw.mouseX();
			double mouseY = StdDraw.mouseY();
			double playerX = player.getX();
			double playerY = player.getY();
			if (mouseY < playerY ) playerY -= player.getSpeed();
			if (mouseY > playerY ) playerY += player.getSpeed();
			if (mouseX < playerX ) playerX -= player.getSpeed();
			if (mouseX > playerX ) playerX += player.getSpeed();
			player.move( playerX, playerY );
		}
	}
	public void onKeyboardPressed() 
	{
		if ( StdDraw.hasNextKeyTyped() == true ) 
		{
			double playerX = player2.getX();
			double playerY = player2.getY();

			char key = StdDraw.nextKeyTyped();
			
			if ( key == 'w' )
			{
				playerY-= player2.getSpeed();
			}
			else if ( key == 's') 
			{
				playerY+= player2.getSpeed();
			}
			else if (key == 'a') 
			{
				playerX-= player2.getSpeed();
			}
			else if (key == 'd' ) 
			{
				playerX+= player2.getSpeed();
			}
			player2.move( playerX, playerY );
		}
	}


		public void update() 
		{
			onMousePress();
			onKeyboardPressed();
		}

		
}