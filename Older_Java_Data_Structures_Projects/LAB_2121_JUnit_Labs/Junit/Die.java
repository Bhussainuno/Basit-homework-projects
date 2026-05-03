import java.util.Random;

/**
 * Models a playing die with sides numbered 1 to 6. All sides have uniform
 * probablity of being rolled.
 *
 * @author Saroj Duwal
 */
public class Die {
    public static final int DEFAULT_SIDES = 6;
    private static Random ourRandNumGen = new Random();
    private final int iMyNumSides;
    private int iMyResult;

    public Die() {
        this(DEFAULT_SIDES);
    }

    public Die(int numSides) {
        iMyNumSides = numSides;
        iMyResult = 1;
    }

    public int roll() {
        iMyResult = ourRandNumGen.nextInt(iMyNumSides) + 1;

        return iMyResult;
    }

    public int getNumSides() {
        return iMyNumSides;
    }

    public int getResult() {
        return iMyResult;
    }

    public boolean equals(Object otherObj) {
        boolean result = true;
        if (otherObj == null)
            result = false;
        else if (this == otherObj)
            result = true;
        else if (this.getClass() != otherObj.getClass())
            result = false;
        else {
            Die otherDie = (Die) otherObj;
            result = this.iMyResult == otherDie.iMyResult && this.iMyNumSides == otherDie.iMyNumSides;
        }
        return result;
    }

    public String toString() {
        return "Num sides " + getNumSides() + " result " + getResult();
    }

}