public class Player 
{
	/*Attributes: Instance Variables*/
	private String image;
	private int width;
	private int height;
	private double x;
	private double y;
	private int speed;



	/* Constructor */
	public Player(double x, double y)   // constructer are just like methods but they use 
	{
		this.x = x; //set x position
		this.y = y; //set y position
		this.width = 32; //set width
		this.height = 32; //set height
		this.image = "assets/spaceman.png"; //set image filename
		this.speed = 10; //set speed

	}
		public void draw() 
	{
		StdDraw.picture(x, y, image, width, height); // spaceship is spawning
	}

		public void move(double x, double y)  // moving horizontally 
	{
		this.y = y;
		this.x = x;
	}

		public double getX() 
	{
		return this.x;
	}
		public double getY() 
	{
		return this.y;
	}



		public double getSpeed() 
	{
		return this.speed;
	}


	public boolean isTouchingX( Enemy gameObject )  // its telling the position of the enemy 
	{
		int hitzone = 14; // hitzone is basicelly a boundry around the stars if I increase 
		//the number then the spaceship will hit the stars from far away and if I decrease the number
		// then the spaceship will hit the stars when they are closer.
		return this.x <= gameObject.getX()+hitzone && gameObject.getX() <= this.x+hitzone; // if enemy and the players horizontal position is less then
		//  the hitzone then the game will exit
		
	}


	public boolean isTouchingY( Enemy gameObject ) // its telling the position of the enemy 
	{
		int hitzone = 24;
		return this.y <= gameObject.getY()+hitzone && gameObject.getY() <= this.y+hitzone;
		// if enemy and the players vertically position is less then
		//  the vertically, then the game will exit
		
	}
	public boolean isTouching( Enemy gameObject ) 
	{
		return this.isTouchingX(gameObject) && this.isTouchingY(gameObject);
	}

	public boolean isTouchingGemX( Gems gameObject ) 
	{
		int hitzone = 24;
		return this.x <= gameObject.getX()+hitzone && gameObject.getX() <= this.x+hitzone;
	}


	public boolean isTouchingGemY( Gems gameObject ) 
	{
		int hitzone = 24;
		return this.y <= gameObject.getY()+hitzone && gameObject.getY() <= this.y+hitzone;
	}


	public boolean isTouchingGem( Gems gameObject ) 
	{
		return this.isTouchingGemX(gameObject) && this.isTouchingGemY(gameObject);
	}



	public boolean isTouchingTankX( Tank gameObject ) 
	{
		int hitzone = 24;
		return this.x <= gameObject.getX()+hitzone && gameObject.getX() <= this.x+hitzone;
	}


	public boolean isTouchingTankY( Tank gameObject ) 
	{
		int hitzone = 24;
		return this.y <= gameObject.getY()+hitzone && gameObject.getY() <= this.y+hitzone;
	}


	public boolean isTouchingTank( Tank gameObject ) 
	{
		return this.isTouchingTankX(gameObject) && this.isTouchingTankY(gameObject);
	}

}

