import java.util.ArrayDeque;
import java.util.Deque;

/*
 Names: Allison L & Dillon H
*/

/**
 * Converts whole numbers between bases 2 through 36
 *
 * Digit values 0-9 are written '0'-'9', and 10-35 are written with letters:
 * 'A' = 10, 'B' = 11, ..., 'Z' = 35. Methods that read digits accept
 * upper- or lowercase letters; methods that write digits use uppercase.
 *
 * Only non-negative numbers are supported.
 *
 * Do not rename the class, the public methods, or change their
 * parameters/return types. You may add private helper methods.
 * Do not use Integer.parseInt, Integer.toString(n, base), Integer.valueOf,
 * Character.digit, or Character.forDigit in this file - the point is to
 * write the conversions yourself. (You may use them in your tests.)
 */
public class BaseConverter {

    /** The smallest base supported. */
    public static final int MIN_BASE = 2;

    /** The largest base supported (10 digits plus 26 letters). */
    public static final int MAX_BASE = 36;

    /** The digit characters, in order of value: DIGITS.charAt(v) is the digit for value v. */
    public static final String DIGITS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    // ---------------------------------------------------------------
    // Part 1: reading digits
    // ---------------------------------------------------------------

    /**
     * Returns the value of a single digit character: '0'-'9' are 0-9, and
     * 'A'-'Z' (or 'a'-'z') are 10-35. Returns -1 for any other character.
     * Example: digitValue('7') is 7, digitValue('d') is 13, digitValue('?') is -1.
     */
    public static int digitValue(char ch) {
        int ASCIICode = ch;
        if(ASCIICode >= 48 && ASCIICode <= 59) return (ch - 48);    //0-9
        else if(ASCIICode >= 65 && ASCIICode <= (65+25)) return (ch - 55);  //uppercase A-Z
        else if(ASCIICode >= 97 && ASCIICode <= (97+25)) return (ch - 87);  //lowercase A-Z
        return -1;
    }

    /**
     * Returns the value of digits read as a number in the given base
     * Example: toDecimal("101101", 2) and toDecimal("2D", 16) both return 45.
     * throws IllegalArgumentException if base is not 2 to 36, if digits is
     * null or empty, if any character is not a valid digit in that base, or
     * if the value is larger than Integer.MAX_VALUE
     */
    public static int toDecimal(String digits, int base){
        checkBase(base); //using private helper checkBase
        if (digits == null || digits.isEmpty()){
            throw new IllegalArgumentException("digits cannot be null or empty");
        }
        long value = 0;
        for(int i = 0; i < digits.length(); i++){
            int digit = digitValue(digits.charAt(i));
            if (digit >= base || digit < 0){
                throw new IllegalArgumentException("not a valid digit");
            }
            value = value * base + digit;
            if (value > Integer.MAX_VALUE){
                throw new IllegalArgumentException("too big!");
            }
        }
        return (int) value;
    }

    // ---------------------------------------------------------------
    // Part 2: writing digits, using a stack
    // ---------------------------------------------------------------

    /**
     * Returns the digit character for a digit value: 0-9 give '0'-'9', and
     * 10-35 give 'A'-'Z' (uppercase).
     * Example: digitChar(13) is 'D'.
     * throws IllegalArgumentException if value is not 0 to 35
     */
    public static char digitChar(int value) {
        if(value < 0 || value > 35) throw new IllegalArgumentException("invalid parameter");
        if(value <= 9 ){
            return (char) (value + 48);
        } else if(value >= 10 && value <= 35){
            return (char) (value + 48);
        }
        return ((char)(value + 55));
    }

    /**
     * Returns n written in the given base, using repeated division: each
     * remainder n % base is pushed onto a stack, then the digits are popped
     * off in the right order. Letters are uppercase; there are no leading
     * zeros, except that 0 is written "0".
     * Example: toBase(45, 2) is "101101", toBase(45, 16) is "2D".
     * throws IllegalArgumentException if n is negative or base is not 2 to 36
     */
    public static String toBase(int n, int base) {
        checkBase(base); //checking the base first
        checkNonNegative(n);

        Deque<Character> stack = new ArrayDeque<>();
        if (n == 0){
            return "0";
        }
        while (n > 0){
            stack.push(digitChar(n % base));
            n = n / base;
        }
        StringBuilder sb = new StringBuilder();
        while(!stack.isEmpty()){
            sb.append(stack.pop());
        }
        return  sb.toString();
    }

    // ---------------------------------------------------------------
    // Part 3: recursion
    // ---------------------------------------------------------------

    /**
     * Returns n written in the given base, the same as toBase, but recursively
     * and without a stack: the digits of n / base, followed by the digit for
     * n % base. A number smaller than the base is a single digit.
     * Example: toBaseRec(45, 3) is "1200".
     * throws IllegalArgumentException if n is negative or base is not 2 to 36
     */
    public static String toBaseRec(int n, int base) {
        checkBase(base);
        checkNonNegative(n);

        if (n < base) {
            return String.valueOf(digitChar(n));
        }
        return toBaseRec(n / base, base) + digitChar(n % base);
    }

    /**
     * Returns the number of digits needed to write n in the given base,
     * recursively: a number smaller than the base has 1 digit; otherwise it
     * has 1 more digit than n / base. countDigits(0, base) is 1.
     * Example: countDigits(4725, 10) is 4, countDigits(45, 2) is 6.
     * throws IllegalArgumentException if n is negative or base is not 2 to 36
     */
    public static int countDigits(int n, int base) {
        // TODO: no loops - use recursion
        if (n < 0 || base < MIN_BASE || base > MAX_BASE) {
            throw new IllegalArgumentException("Invalid input");
        }
        if (n < base) {
            return 1;
        }
        return 1 + countDigits(n / base, base);
    }

    // ---------------------------------------------------------------
    // private helpers
    // ---------------------------------------------------------------

    /** Throws IllegalArgumentException unless base is 2 to 36. */
    private static void checkBase(int base) {
        if (base < MIN_BASE || base > MAX_BASE){
            throw new IllegalArgumentException("invalid base");
        }
    }

    /** Throws IllegalArgumentException if n is negative. */
    private static void checkNonNegative(int n) {
        if (n < 0){
            throw new IllegalArgumentException("invalid value");
        }
    }

    /*
    1. How many calls does countDigits(n, 2) make (counting the first call) for n = 1, 2, 4, 8, 16, and 1024?
        n:1     1
        n:2     2
        n:4     3
        n:8     4
        n:16    5
        n:1024  11
    2. What is the Big-O of countDigits(n, base) and toBaseRec(n, base), in terms of n? Explain why.
        countDigits is O(logn) because it is continuing to divide by the base stopping at 1
            e.g. calls(n) = log_2(n)+1
        toBaseRec is also O(logn) for similar reasons, it is continuing to divide recursively with a constant
        **For both, the division is logn because it is reducing it by half (log_2)
    3. toBase needs a stack to get the digits in the right order, but toBaseRec does not. Where do the digits wait in the recursive version?
        In the recursive version, the digits wait in the stack of the computer (the memory stack not DS)
        once it gets to the base case of the recursion function, it will go back up and clear out those values.

    */
}
