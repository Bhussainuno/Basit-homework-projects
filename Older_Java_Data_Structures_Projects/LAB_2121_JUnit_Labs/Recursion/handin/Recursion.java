

/**
 * If you do not use recursion, you will receive 0 points. You can delete the
 * content of the methods before you start your work. You should not change the
 * method definition.
 */
public class Recursion {
    public static String reverse(String s) {
        if(s.equals (""))
            return "";
        return s.charAt(length(s) - 1) + reverse(s.substring(0, length(s) - 1));
    }

    public static boolean isPalindrome(String s) {
        if (s.equals("") || length(s) == 1)
            return true;
        if (s.charAt(0) != s.charAt(length(s) - 1))
            return false;
        return isPalindrome(s.substring(1, length(s) - 1));

    }

    public static int length(String s) {
        /*
         * Donot return s.length() Use recursion to solve this
         */
        if (s.equals(""))
            return 0;
        return 1 + length(s.substring(1));

    }
}