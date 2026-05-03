import java.util.ArrayList;

public class Scene 
{
	/*Attributes: Instance Variables*/
	private String image;
	private int width;
	private int height;
	private ArrayList<Enemy> monsters;
	private ArrayList<Gems> gems;
	private ArrayList<Tank> tanks;

	private Player player;
	//private SecondPlayer player2;
	private double y;
	private int speed;
		

	/* Constructor */
	public Scene() 
	{
		
		width = 500; //set scene width
		height = 350; //set scene height

		
		image = "assets/space-background.png"; //set image filename
		
		monsters = new ArrayList<Enemy>();
		gems = new ArrayList<Gems>();
		tanks = new ArrayList<Tank>();



		// this.y = y; //set y position
		// this.speed = (int) (3 + Math.random() * 10);

		StdDraw.setCanvasSize(width, height); //set canvas size for image
		StdDraw.setXscale(0.0, width); //set x=0 from right to left
		StdDraw.setYscale(height, 0.0); //set y=0 from top to bottom
	}
	/*draw method*/
	public void draw() 
	{
		
		StdDraw.picture(width/2, height/2,image); //draw background image using center point

		for(Enemy monster : monsters) 
		{
			monster.draw();

		}
		for(Gems gem : gems) 
		{
			gem.draw();
		}
		for(Tank tank : tanks) 
		{
			tank.draw();
		}


		player.draw(); //draw player
		//player2.draw();
	}
	public void addMonster() // this is my enemy in the game which are the stars 
	{
		double x = 32 + (Math.random() * (width - 64));// Math.random explains the wide and length 
		double y = -32;
		// double y = Math.random() * height;

		Enemy star = new Enemy(x,y);
		monsters.add(star);
	}

	public void addGem() 
	{
		double x = 32 + (Math.random() * (width - 64));
		double y = -32;
		

		Gems cherry = new Gems(x,y);
		gems.add(cherry);
	}

	public void addTank() // adding tank which is a planet 
	{
		double x = 32 + (Math.random() * (width - 64)); // x and y is defining the size
		double y = -32;
		

		Tank oxygen = new Tank(x,y);
		tanks.add(oxygen);
	}


	public void update() // using for loop to update the scene   
	{
		for (Enemy monster : monsters) 
		{
			monster.move();
		}

		for (Gems gem : gems) 
		{
			gem.move();
		}

		for (Tank tank : tanks) 
		{
			tank.move();
		}

		
		moveImage();
		
	}


	public void moveImage() // move method 
	{
		this.y += this.speed;
	}
	public Player getPlayer() // get method for first player 
	{
		return this.player;
	}

	/*public SecondPlayer getSPlayer()  remove second player get method from seen 
	//{
	return this.player2;
	} */
	public void setPlayer( Player player)  // set method for first player
	{
		this.player = player;
	}

	/*public void setSPlayer( SecondPlayer player2) remove set method from seen 
	{
		this.player2 = player2;
	} */

	public ArrayList<Gems> getGems() 
	{
		return this.gems;
	}


	public ArrayList<Tank> getTanks() 
	{
		return this.tanks;
	}
	public ArrayList<Enemy> getMonsters() 
	{
		return this.monsters;
	}


}