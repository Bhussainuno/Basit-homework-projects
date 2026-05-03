/**
* This calss has 3 meathods and each meathod is doing seprate task
* <p>
*  
* <p>
* @author Basit Hussain	
*
*/
import java.util.Arrays;

public class StringUtility {
    /**
     * in this meathod the string type is printing backward 
     * @param sentence
     * @return
     */

    public static String reverse(String sentence) {
        // the string is being split by using space and being saved inside array veriable called string 
        sentence= sentence.toLowerCase();
        sentence = sentence.trim().replaceAll("\\s{2,}", " ");
        String string [] = sentence.split(" ");

        // reverced string will be saved inside veriable string_reverse 
        String string_reverse = "";
        // 
        for(int i = string.length - 1; i >= 0; i--)
        {
            string_reverse = string_reverse + string[i] + " ";
        }
        // System.out.println(string_reverse);
        return string_reverse; // return the result
   
    }

    public static char maxOccuringCharacter(String sentence) throws IllegalArgumentException {
    
        if (sentence == null || sentence.length() == 0)

        throw new IllegalArgumentException();
        sentence = sentence.toLowerCase();
        
        char[] chars = sentence.toCharArray();
        Arrays.sort(chars);
        String sorted = new String(chars);

        char maxOccuring = ' ';
        int maxOccuringCount = 0;
        for (int i = 0; i < sorted.length(); i++) {
            if (Character.isAlphabetic(sorted.charAt(i))) {
                int count = 0;
                for (int j = 0; j < sorted.length(); j++) {
                    if (sorted.charAt(i) == sorted.charAt(j))
                    ++count;
                }

                if (count > maxOccuringCount) {
                    maxOccuring = sorted.charAt(i);
                    maxOccuringCount = count;
                }
            }
        }
        if (maxOccuring == ' '){
            throw new IllegalArgumentException();
        }

        // System.out.println(maxOccuring);
        
        return maxOccuring;
    }

    public static boolean isPalindrome(String input) {
         // Pointers pointing to the beginning
        // and the end of the string
        int firstPointer = 0, secondPointer = input.length() - 1;
 
        // While there are characters to compare
        while (firstPointer < secondPointer) {
 
            // If there is a mismatch
            if (input.charAt(firstPointer) != input.charAt(secondPointer))
                return false;
 
            // Increment first pointer and
            // decrement the other
            firstPointer++;
            secondPointer--;
        }
 
        // Given string is a palindrome
        return true;
    }
    //     public static void main(String[] args)
    // {
        
    //     Reverse("This SENTENCE");
    //     // maxOccuringCharacter("mmmmiiiiiaaaaa");
    // }
}
