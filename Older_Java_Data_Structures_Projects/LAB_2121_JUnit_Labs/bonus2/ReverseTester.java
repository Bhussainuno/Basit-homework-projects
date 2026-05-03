import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ReverseTester {
    @Test
    public void Reverse() {
        assertEquals("aaaa aaa aa a", Reverse.reverseSort("a aaaa aa aaa"));
        assertEquals("string this is a", Reverse.reverseSort("string a is this"));
        assertEquals("applee string", Reverse.reverseSort("string applee"));

    }
}
