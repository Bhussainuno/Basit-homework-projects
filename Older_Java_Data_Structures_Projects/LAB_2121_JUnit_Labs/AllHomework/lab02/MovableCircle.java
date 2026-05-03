/**
public class named MovableCircle which implements the interface Movable
*/
/** it initializes radius of the circle and give command to move in the circle
 *
 * <p>
 * 
 * </p>
 *
 *
 * @author Basit Hussain
 * @since 1.0.0
 */
public class MovableCircle implements Movable {
    private final int radius;
    private final MovablePoint center;
    

    public MovableCircle(int x, int y, int radius) {
        this.radius = radius;
        this.center = new MovablePoint(x, y);
    }
    /**
    * Overriding all functions of interface 
    * this method is commanding to move up from center  
    */

    @Override
    public void moveUp() {
        center.moveUp();
    }
    /**
    * Overriding all functions of interface 
    * this method is commanding to move down from center  
    */

    @Override
    public void moveDown() {
        center.moveDown();
    }
    /**
    * Overriding all functions of interface 
    * this method is commanding to move left from center  
    */

    @Override
    public void moveLeft() {
        center.moveLeft();
    }
    /**
    * Overriding all functions of interface 
    * this method is commanding to move right from center  
    */

    @Override
    public void moveRight() {
        center.moveRight();
    }
    /** output the value to the user with integers such as radius and center  */
    @Override
    public String toString() {
        return "MovableCircle [radius=" + radius + ", center=" + center + "]";
    }

}
