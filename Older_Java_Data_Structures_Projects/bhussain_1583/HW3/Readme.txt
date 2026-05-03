There are four methods :-


1. draw():
	This is a void method and it takes couple of arguments. The perous of this method is to display the scene of the game which includes tiles, zombies, playes, obstacles, Key and the exit door.
	


2. playerMovement(): 
	This is a return boolean method which takes four arguments. 
	This method contains if else statments which is checking if the playes has grabed the key or not. This is done by returning by getKey boolean variable.


3. loseCondition():
	This is a return boolean method which takes couple of arguments. This method determents the lose Condition of the game which means it has if else condition and that is trigger if zombie meets the player or not. Once the zombie meets the the player it returns the boolean variable called gameOver. 



4.  zombieMovement():
	 
	 This is a return int method which takes two arguments.
	 This method calculates the zombie movement on the grid and returns that movemnt using variable called zombieMovement.