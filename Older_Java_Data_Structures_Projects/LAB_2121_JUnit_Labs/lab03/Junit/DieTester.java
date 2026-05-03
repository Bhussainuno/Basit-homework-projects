
import org.junit.Assert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
    
import org.junit.Before;
import org.junit.Test;

public class DieTester {
   Die die = new Die();
   Die die5 = new Die(5);

   @Test
   public void testDefaultRoll() {
       int roll = die.roll();
       assertEquals(true, roll >= 1 && roll <= 6);
   }

   @Test
   public void test5SideRoll() {
       int roll = die5.roll();
       assertEquals(true, roll >= 1 && roll <= 5);
   }

   @Test
   public void testDefaultNumSides() {
       assertEquals(6, die.getNumSides());
   }

   @Test
   public void test5NumSides() {
       assertEquals(5, die5.getNumSides());
   }

   @Test
   public void testGetDefaultResult() {
       int y = die.roll();
       assertEquals(y, die.getResult());
   }

   @Test
   public void testGet5SideResult() {
       int y = die5.roll();
       assertEquals(y, die5.getResult());
   }

   @Test
   public void testEqualsTrue() {
       assertEquals(true, die.equals(die));
   }

   @Test
   public void testEqualsFalse() {
       assertEquals(false, die.equals(die5));
   }

   @Test
   public void testDefaultToString() {
       assertEquals("Num sides 6 result 1", die.toString());
   }

   @Test
   public void test5SideToString() {
       assertEquals("Num sides 5 result 1", die5.toString());
   }

}