public class Enemy 
{
	/*Attributes: Instance Variables*/
	private String image;
	private int width;
	private int height;
	private double x;
	private double y;
	private int speed;


	/* Constructor */
	public Enemy(double x, double y) 
	{
		this.x = x; //set x position
		this.y = y; //set y position
		this.width = 32; //set width
		this.height = 32; //set height
		this.image = "assets/asteroid.png"; //set image filename
		this.speed = (int) (5 + Math.random() * 10);

	}

	public void draw() 
	{
		StdDraw.picture(x, y, image, width, height);
	}

	public void move() 
	{
		this.y += this.speed;
	}

	public double getX() 
	{
		return this.x;
	}
		public double getY() 
	{
		return this.y;
	}




}
