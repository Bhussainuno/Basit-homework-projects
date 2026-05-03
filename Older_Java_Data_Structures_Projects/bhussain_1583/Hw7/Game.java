public class Game {
private boolean isOver;

	/*Create a new Platform Game*/
	public Game() 
	{
		this.isOver = false;
	}
	public void update() 
	{
		//game update code
	}

	public void render() 
	{
		//game draw code
	}
	/* The main game loop*/
	public static void main(String[] args) 
	{
	Game game = new Game();
		while (game.isOver == false) 
		{
			game.update();
			game.render();
		}
	}

}
