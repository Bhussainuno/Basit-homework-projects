// A simple JUnit test for StringMaker.

import org.junit.*;
import static org.junit.Assert.*;

public class StringMakerTest {
	
	@Test 
	public void testConcatenate() {
        StringMaker myStringMaker = new StringMaker();

        String result = myStringMaker.concatenate("Hello", " World!");

        assertEquals(result, "Hello World!");
    }
}