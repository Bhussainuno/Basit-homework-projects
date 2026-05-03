/**
 * public class named MovablePoint which implements the interface Movable points 
 * <p>
 * this calss use to give command to move the points of on given command
 * <p>
 * This code implements and give the cammand 
 * </p>
 *
 *
 * @author Basit Hussain
 * @since 1.0.0
 */


public class MovablePoint implements Movable {
    int x;
    int y;

    /**
     * This method divides two number.
     *
     * @param x is used as x axis to initilize the point
     * @param y is used as y axis to initilize the point 
     */

    public MovablePoint(int x, int y) {
        this.x = x;
        this.y = y;
    }
    /**
    * all functions of interface 
    * this method is commanding to move upward y excis 
    */

    @Override
    public void moveUp() {
        y += 1;
    }
    /**
    * all functions of interface 
    * this method is commanding to move down y excis 
    */
    @Override
    public void moveDown() {
        y -= 1;
    }
    /**
    * all functions of interface 
    * this method is commanding to move left x excis 
    */
    @Override
    public void moveLeft() {
        x -= 1;
    }
    /**
    * all functions of interface 
    * this method is commanding to move right x excis 
    */

    @Override
    public void moveRight() {
        x += 1;
    }
    /** output the value to the user with integers on x axcis and y excis points */

    @Override
    public String toString() {
        return "MovablePoint [x=" + x + ", y=" + y + "]";
    }
}
