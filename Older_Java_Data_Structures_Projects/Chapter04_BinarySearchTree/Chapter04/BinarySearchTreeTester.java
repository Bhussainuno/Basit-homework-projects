import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BinarySearchTreeTester {

    private BinarySearchTree<Integer> theBST;

    @Before
    public void setup() {
        theBST = new BinarySearchTree<>();
    }

    @Test
    public void testThatEmptyBSTIsEmpty() {
        assertEquals(true, theBST.isEmpty());
    }

    @Test
    public void testThatFullyDeletedBSTIsEmpty() {
        theBST.insert(20);
        theBST.insert(10);
        theBST.insert(30);
        theBST.remove(20);
        theBST.remove(10);
        theBST.remove(30);
        assertEquals(true, theBST.isEmpty());
    }

    @Test
    public void testThatClearedBSTIsEmpty() {
        theBST.insert(20);
        theBST.insert(10);
        theBST.insert(30);
        theBST.makeEmpty();
        assertEquals(true, theBST.isEmpty());
    }

    @Test
    public void testFindMin() {
        theBST.insert(30);
        theBST.insert(10);
        theBST.insert(5);
        theBST.insert(20);
        theBST.insert(60);
        theBST.insert(100);
        theBST.insert(40);
        theBST.insert(5);
        try {
            assertEquals(5, (int) (theBST.findMin()));
        } catch (UnderflowException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = UnderflowException.class)
    public void testFindMinOnEmptyBST() throws UnderflowException {
        theBST.insert(30);
        theBST.insert(10);
        theBST.insert(5);
        theBST.insert(20);
        theBST.insert(60);
        theBST.insert(100);
        theBST.insert(40);
        theBST.insert(5);
        theBST.makeEmpty();
        theBST.findMin();
    }

    @Test
    public void testFindMinOnEmptyBST1() {
        theBST.insert(30);
        theBST.insert(10);
        theBST.insert(5);
        theBST.insert(20);
        theBST.insert(60);
        theBST.insert(100);
        theBST.insert(40);
        theBST.insert(5);
        theBST.remove(10);
        try {
            assertEquals(5, (int) (theBST.findMin()));
        } catch (UnderflowException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFindMinOnModifiedBST1() {
        theBST.insert(30);
        theBST.insert(10);
        theBST.insert(5);
        theBST.insert(20);
        theBST.insert(60);
        theBST.insert(100);
        theBST.insert(40);
        theBST.remove(10);
        try {
            assertEquals(5, (int) (theBST.findMin()));
        } catch (UnderflowException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFindMinOnModifiedBST2() {
        theBST.insert(30);
        theBST.insert(10);
        theBST.insert(5);
        theBST.insert(20);
        theBST.insert(60);
        theBST.insert(100);
        theBST.insert(40);
        theBST.remove(5);
        try {
            assertEquals(10, (int) (theBST.findMin()));
        } catch (UnderflowException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFindMinOnModifiedBST3() {
        theBST.insert(30);
        theBST.insert(10);
        theBST.insert(5);
        theBST.insert(20);
        theBST.insert(60);
        theBST.insert(100);
        theBST.insert(40);
        theBST.remove(30);
        try {
            assertEquals(5, (int) (theBST.findMin()));
        } catch (UnderflowException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFindMinOnModifiedBST4() {
        theBST.insert(30);
        theBST.insert(10);
        theBST.insert(5);
        theBST.insert(20);
        theBST.insert(60);
        theBST.insert(100);
        theBST.insert(40);
        theBST.remove(10);
        theBST.remove(5);
        try {
            assertEquals(20, (int) (theBST.findMin()));
        } catch (UnderflowException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFindMax() {
        theBST.insert(30);
        theBST.insert(10);
        theBST.insert(5);
        theBST.insert(20);
        theBST.insert(60);
        theBST.insert(100);
        theBST.insert(40);
        try {
            assertEquals(100, (int) (theBST.findMax()));
        } catch (UnderflowException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFindMaxOnEmptyBST() {
        theBST.insert(30);
        theBST.insert(20);
        theBST.insert(10);
        theBST.insert(100);
        theBST.insert(40);
        theBST.insert(60);
        theBST.insert(5);
        theBST.makeEmpty();
        try {
            assertEquals(100, (int) (theBST.findMax()));
        } catch (UnderflowException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testFindMaxOnModifiedBST1() {
        theBST.insert(30);
        theBST.insert(10);
        theBST.insert(5);
        theBST.insert(20);
        theBST.insert(60);
        theBST.insert(100);
        theBST.insert(40);
        theBST.remove(60);
        try {
            assertEquals(100, (int) (theBST.findMax()));
        } catch (UnderflowException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFindMaxOnModifiedBST2() {
        theBST.insert(30);
        theBST.insert(10);
        theBST.insert(5);
        theBST.insert(20);
        theBST.insert(60);
        theBST.insert(100);
        theBST.insert(40);
        theBST.remove(100);
        try {
            assertEquals(60, (int) (theBST.findMax()));
        } catch (UnderflowException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFindMaxOnModifiedBST3() {
        theBST.insert(30);
        theBST.insert(10);
        theBST.insert(5);
        theBST.insert(20);
        theBST.insert(60);
        theBST.insert(100);
        theBST.insert(40);
        theBST.remove(30);
        try {
            assertEquals(100, (int) (theBST.findMax()));
        } catch (UnderflowException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFindMaxOnModifiedBST4() {
        theBST.insert(30);
        theBST.insert(10);
        theBST.insert(5);
        theBST.insert(20);
        theBST.insert(60);
        theBST.insert(100);
        theBST.insert(40);
        theBST.remove(60);
        theBST.remove(100);
        try {
            assertEquals(40, (int) (theBST.findMax()));
        } catch (UnderflowException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testContainsOnExistingElement() {
        theBST.insert(30);
        theBST.insert(10);
        theBST.insert(5);
        theBST.insert(20);
        theBST.insert(60);
        theBST.insert(100);
        theBST.insert(40);
        assertTrue(theBST.contains(40));
    }

    @Test
    public void testContainsOnNonExistentElement() {
        theBST.insert(30);
        theBST.insert(10);
        theBST.insert(5);
        theBST.insert(20);
        theBST.insert(60);
        theBST.insert(100);
        theBST.insert(40);
        assertFalse(theBST.contains(110));
    }

    @Test
    public void testContainsOnRemovedElement() {
        theBST.insert(30);
        theBST.insert(10);
        theBST.insert(5);
        theBST.insert(20);
        theBST.insert(60);
        theBST.insert(100);
        theBST.insert(40);
        theBST.remove(40);
        assertFalse(theBST.contains(40));
    }

    @Test
    public void testContainsOnRemovedRoot() {
        theBST.insert(30);
        theBST.insert(10);
        theBST.insert(5);
        theBST.insert(20);
        theBST.insert(60);
        theBST.insert(100);
        theBST.insert(40);
        theBST.remove(30);
        assertFalse(theBST.contains(30));
    }

    @After
    public void cleanup() {
        // Cleanup here if necessary
    }
}
