import org.junit.Assert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
    
import org.junit.Before;
import org.junit.Test;

public class MyLinkedListTester {
 
    @Test
    public void testNewLinkedList(){

        MyLinkedList singly = new MyLinkedList<String>();

        singly.add("1");
        singly.add("2");
        singly.add("3");
        singly.add("4");
    
        assertEquals("1", singly.get(0));
        assertEquals("2", singly.get(1));
        assertEquals("3", singly.get(2));
        assertEquals("4", singly.get(3));

        singly.removeLazyDelete(2);
        assertEquals("4", singly.get(2));

    }
}


