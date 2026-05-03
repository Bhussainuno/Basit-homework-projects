public class DodgerGame
{
	private Scene scene;
	private boolean isOver;
	private long startTime;
	private Controller controller;
	private int timer;
	private int gemCounter;
	private int oxygenCounter;
	private boolean nextLevel;





	/*Create a new Dodger Game*/
	public DodgerGame() 
	{
		isOver = false;
		scene = new Scene();
		startTime = System.currentTimeMillis();
		Player player = new Player(250, 187.5);
		this.scene.setPlayer( player );


		SecondPlayer player2 = new SecondPlayer(300, 187.5);
		this.scene.setPlayer( player );

		controller = new Controller( player, player2 );

		this.timer = 0;
		this.gemCounter = 0;
		this.oxygenCounter=200;
		this.nextLevel = false;




	}


	public void update() 
	{
		controller.update();
		long now = System.currentTimeMillis();


	if (now - this.startTime > 200 ) 
		{	 // 1/5th sec duration //
			
			this.timer++;
			this.oxygenCounter--;
			// this.startTime = now;



		}
		if (now - this.startTime > 3000 ) 
		{	 // 1/5th sec duration //
			scene.addMonster(); //game update code
			
			
			// this.startTime = now;


		}

		if (now - this.startTime > 7000 ) 
		{	 // 1/5th sec duration //
			scene.addGem();
			

		}

		if (now - this.startTime > 7000 ) 
		{	 // 1/5th sec duration //
			scene.addTank();
			this.startTime = now;
		}

		scene.update();
		Player player = scene.getPlayer();
		// SecondPlayer player2 = scene.getSPlayer();   remove second played from seen 

		for (Enemy monster : scene.getMonsters() ) 
		{
				if ( player.isTouching(monster) ) // || player2.isTouching(monster) ) remove monster  for second player 
			{
				isOver = true;
			}
			
		}

		for (Gems gem : scene.getGems() ) 
		{
				if ( player.isTouchingGem(gem) )//||  player2.isTouchingGem(gem) ) remove gems for second player 
			{
				
					gemCounter++;
					
				}

		}

		for (Tank tank : scene.getTanks() ) 
		{
				if ( player.isTouchingTank(tank) )//||  player2.isTouchingTank(tank) ) remove tank for second player 
			{
				
					oxygenCounter=oxygenCounter+10;
					

				}

			}
		if(oxygenCounter ==0){
			isOver=true;
		}
		
		
		



	}
	

	public void render() 
	{
		scene.draw(); //draw scene
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.text(64,32,"Time: " + timer/5 );
		StdDraw.text(194,32,"Gems: " + gemCounter );
		StdDraw.text(294,32,"Oxygen: " + oxygenCounter/5 );


		StdDraw.show(100);
	}

	/* The main game loop*/
	public static void main(String[] args) 
	{
	DodgerGame game = new DodgerGame();
		while (game.isOver == false) 
		{
			game.update();
			game.render();
		}
	}




}





