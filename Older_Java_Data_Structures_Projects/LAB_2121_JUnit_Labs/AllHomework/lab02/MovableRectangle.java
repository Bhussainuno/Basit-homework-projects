
/**
 * public class name MovableRectangle which implements interface Movable
*/
/**
 * public class name MovableRectangle which implements interface Movable
 * <p>
 * this object of class MovablePoint as data variables
 * <p>
 * This code implements and give the cammand 
 * </p>
 *
 *
 * @author Basit Hussain
 * @since 1.0.0
 */

public class MovableRectangle implements Movable {
    private final MovablePoint topLeft;
    private final MovablePoint bottomRight;
    /**
     * 
     * @param x1 -x coordinate of the top left of the ractangular
     * @param y1 -y coordinate of the top left of the ractangular
     * @param x2 -x coordinate of the top right of the ractangular
     * @param y2 -y coordinate of the top right of the ractangular
     
     */


    public MovableRectangle(int x1, int y1, int x2, int y2) {
        this.topLeft = new MovablePoint(x1, y1);
        this.bottomRight = new MovablePoint(x2, y2);
    }
    /**
     * this method moves to the y coordenate from the starting points upward
     *  which is top left and right 
     */

    @Override   
    public void moveUp() {
        topLeft.moveUp();
        bottomRight.moveUp();
    }

    /**
     * this method moves to the y coordenate from the starting points downward
     * which is top left and right 
     */
    @Override
    public void moveDown() {
        topLeft.moveDown();
        bottomRight.moveDown();
    }
    /** 
     * this method moves the x coordenate of top left and bottom right left by 
     */

    @Override
    public void moveLeft() {
        topLeft.moveLeft();
        bottomRight.moveLeft();
    }
    /** 
     * this method moves the x coordenate to top left and bottom right by right
     */

    @Override
    public void moveRight() {
        topLeft.moveRight();
        bottomRight.moveRight();
    }
    /**
     * @return String value that has top left and botm left coordination
     */

    @Override
    public String toString() {
        return "MovableRectangle [topLeft=" + topLeft + ", bottomRight=" + bottomRight + "]";
    }
}
