import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class BackspaceTester {
    @Test
    public void Backspace() {
        assertEquals("hello", Backspace.erase("he##l#hel#llo"));
        assertEquals("majo spks", Backspace.erase("major# spar##ks"));
        assertEquals("t boy", Backspace.erase("si###t boy"));
        assertEquals("motionsick", Backspace.erase("motion #sick"));
        assertEquals("otn sin", Backspace.erase("m#oti#o#n sick##ne#ss##"));
        assertEquals("courage", Backspace.erase("courz#i#age"));
        assertEquals(" tc", Backspace.erase("aris##### c#r#ti#c"));
        assertEquals("beau", Backspace.erase("beauty##"));
        assertEquals("beaut", Backspace.erase("beauty#"));
        assertEquals("", Backspace.erase("b#"));
        assertEquals("", Backspace.erase("####"));
    }
}
