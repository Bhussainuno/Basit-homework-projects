
/**
 * This tester was written for junit 4, but you can create your own tester equivalent to this in junit 5.
 */
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class Tester {

    @Test
    public void TestReverse() {
        assertEquals("rac", Recursion.reverse("car"));
        assertEquals("", Recursion.reverse(""));
        assertEquals("apple", Recursion.reverse("elppa"));
    }

    @Test
    public void TestIsPalindrome() {
        assertTrue(Recursion.isPalindrome("madam"));
        assertFalse(Recursion.isPalindrome("car"));
        assertTrue(Recursion.isPalindrome(""));
        assertTrue(Recursion.isPalindrome("aa"));
    }

    @Test
    public void TestLength() {
        assertEquals(5, Recursion.length("madam"));
        assertEquals(0, Recursion.length(""));
        assertNotEquals(1, Recursion.length("aa"));
    }
}
