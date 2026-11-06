import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for BaseConverter.
 *
 * These starter tests (with these exact names) should all pass by the
 * time you're done. They are NOT enough on their own - add your own
 * tests (see the TODO list at the bottom and the lab handout).
 */
public class BaseConverterTest {

    // ---- starter tests ----

    @Test
    void toDecimalLectureExamples() {
        assertEquals(45, BaseConverter.toDecimal("101101", 2));
        assertEquals(45, BaseConverter.toDecimal("55", 8));
        assertEquals(45, BaseConverter.toDecimal("2D", 16));
        assertEquals(45, BaseConverter.toDecimal("1200", 3));
        assertEquals(4725, BaseConverter.toDecimal("4725", 10));
    }

    @Test
    void toBaseLectureExamples() {
        assertEquals("101101", BaseConverter.toBase(45, 2));
        assertEquals("55", BaseConverter.toBase(45, 8));
        assertEquals("2D", BaseConverter.toBase(45, 16));
        assertEquals("1200", BaseConverter.toBase(45, 3));
        assertEquals("45", BaseConverter.toBase(45, 10));
    }

    @Test
    void countDigitsLectureExamples() {
        assertEquals(4, BaseConverter.countDigits(4725, 10));
        assertEquals(6, BaseConverter.countDigits(45, 2));
        assertEquals(2, BaseConverter.countDigits(45, 16));
    }

    @Test
    void testDigitValue() {
        assertEquals(0,BaseConverter.digitValue('0'));
        assertEquals(1,BaseConverter.digitValue('1'));
        assertEquals(9,BaseConverter.digitValue('9'));
        assertEquals(10,BaseConverter.digitValue('A'));
        assertEquals(22,BaseConverter.digitValue('M'));
        assertEquals(35,BaseConverter.digitValue('Z'));
        assertEquals(10,BaseConverter.digitValue('a'));
        assertEquals(22,BaseConverter.digitValue('m'));
        assertEquals(35,BaseConverter.digitValue('z'));
        assertEquals(-1,BaseConverter.digitValue(' '));
        assertEquals(-1,BaseConverter.digitValue((char) 32));
        assertEquals(-1,BaseConverter.digitValue((char) 10));
        assertEquals(-1,BaseConverter.digitValue((char) 96));
        assertEquals(-1,BaseConverter.digitValue((char) (97+28)));

    }

    @Test
    void testToDecimal() {
        assertEquals(2,BaseConverter.toDecimal("10",2));
        assertEquals(6,BaseConverter.toDecimal("110",2));
        assertEquals(0,BaseConverter.toDecimal("0",2));
        assertThrows(IllegalArgumentException.class, () -> BaseConverter.toDecimal("2", 2));
        assertEquals(45,BaseConverter.toDecimal("2D",16));
        assertEquals(31, BaseConverter.toDecimal("1f", 16));
        assertEquals(35, BaseConverter.toDecimal("z", 36));
        assertEquals(0, BaseConverter.toDecimal("0", 10));
        assertEquals(0, BaseConverter.toDecimal("0000", 2));
        assertEquals(42, BaseConverter.toDecimal("0042", 10));
        assertEquals(31, BaseConverter.toDecimal("001f", 16));
        assertEquals(1, BaseConverter.toDecimal("1", 2));
        assertEquals(10, BaseConverter.toDecimal("1010", 2));
        assertEquals(1, BaseConverter.toDecimal("1", 36));
        assertEquals(56, BaseConverter.toDecimal("1k", 36));
        assertEquals(2147483647, BaseConverter.toDecimal("7fffffff", 16));
        assertThrows(IllegalArgumentException.class, () -> BaseConverter.toDecimal("129", 8));
        assertThrows(IllegalArgumentException.class, () -> BaseConverter.toDecimal("4 5", 10));
        assertThrows(IllegalArgumentException.class, () -> BaseConverter.toDecimal(null, 10));
        assertThrows(IllegalArgumentException.class, () -> BaseConverter.toDecimal("", 10));
        assertThrows(IllegalArgumentException.class, () -> BaseConverter.toDecimal("10", 37));
        assertThrows(IllegalArgumentException.class, () -> BaseConverter.toDecimal("2147483648", 10));
        assertThrows(IllegalArgumentException.class, () -> BaseConverter.toDecimal("/", 10));
        assertThrows(IllegalArgumentException.class, () -> BaseConverter.toDecimal((""+Integer.MAX_VALUE+1), 10));
    }

    @Test
    void testDigitChar() {
        assertThrows(IllegalArgumentException.class, () -> BaseConverter.toDecimal("129", 8));
        assertThrows(IllegalArgumentException.class, () -> BaseConverter.toDecimal("4 5", 10));
        assertThrows(IllegalArgumentException.class, () -> BaseConverter.toDecimal(null, 10));
        assertThrows(IllegalArgumentException.class, () -> BaseConverter.toDecimal("", 10));
        assertThrows(IllegalArgumentException.class, () -> BaseConverter.toDecimal("10", 37));
        assertThrows(IllegalArgumentException.class, () -> BaseConverter.toDecimal("2147483648", 10));
        assertThrows(IllegalArgumentException.class, () -> BaseConverter.digitChar(-1));
        assertThrows(IllegalArgumentException.class, () -> BaseConverter.digitChar(36));
    }

    @Test
    void testToBase() {
        assertEquals("101101", BaseConverter.toBase(45,2));
        assertEquals("2D", BaseConverter.toBase(45,16));
        assertEquals("0", BaseConverter.toBase(0,16));
        assertEquals("1", BaseConverter.toBase(1,2));
        assertEquals("10", BaseConverter.toBase(16,16));
        assertEquals("10", BaseConverter.toBase(36,36));
        assertEquals("Z", BaseConverter.toBase(35,36));
        assertThrows(IllegalArgumentException.class, () -> BaseConverter.toBase(45,1));
        assertThrows(IllegalArgumentException.class, () -> BaseConverter.toBase(45,-1));
        assertThrows(IllegalArgumentException.class, () -> BaseConverter.toBase(45,38));
    }

    @Test
    void testToBaseRec() {
        assertEquals("101101", BaseConverter.toBaseRec(45,2));
        assertEquals("2D", BaseConverter.toBaseRec(45,16));
        assertEquals("0", BaseConverter.toBaseRec(0,16));
        assertEquals("1", BaseConverter.toBaseRec(1,2));
        assertEquals("10", BaseConverter.toBaseRec(16,16));
        assertEquals("10", BaseConverter.toBaseRec(36,36));
        assertEquals("Z", BaseConverter.toBaseRec(35,36));
        assertThrows(IllegalArgumentException.class, () -> BaseConverter.toBaseRec(45,1));
        assertThrows(IllegalArgumentException.class, () -> BaseConverter.toBaseRec(45,-1));
        assertThrows(IllegalArgumentException.class, () -> BaseConverter.toBaseRec(45,38));
    }

    @Test
    void testCountDigits()
    {
        assertEquals(2, BaseConverter.countDigits(20,10));
        assertEquals(2, BaseConverter.countDigits(10,10));
        assertEquals(1, BaseConverter.countDigits(9,10));
        assertEquals(11, BaseConverter.countDigits(1024,2));
        assertThrows(IllegalArgumentException.class, () -> BaseConverter.countDigits(45,-1));
        assertThrows(IllegalArgumentException.class, () -> BaseConverter.countDigits(45,1));
        assertThrows(IllegalArgumentException.class, () -> BaseConverter.countDigits(45,38));
    }

    @Test
    void loopToDecimal()
    {
        for(int n=0; n<100; n+= (int) (Math.random() * 2))
        {
            for(int base=2; base<=36; base++)
            {
                assertEquals(n, BaseConverter.toDecimal(BaseConverter.toBase(n,base),base));
                assertEquals(Integer.toString(n, base).toUpperCase(), BaseConverter.toBase(n,base));
            }
        }
    }


    // TODO: add your own tests. At a minimum, cover:
    //   - digitValue: digits, upper- and lowercase letters, and characters that
    //     are not digits (including the ones just before/after '0'-'9', 'A'-'Z', 'a'-'z')
    //   - toDecimal: lowercase letters, "0" and leading zeros, base 2 and base 36,
    //     Integer.MAX_VALUE in a few bases
    //   - toDecimal throws IllegalArgumentException for: a digit too big for the
    //     base ("129" in base 8), a non-digit character ("-45", "4 5"), null,
    //     "", a bad base (1 or 37), and a value larger than Integer.MAX_VALUE
    //   - digitChar: 0, 9, 10, 35, and -1 / 36 throwing
    //   - toBase and toBaseRec: 0, single digits, powers of the base,
    //     Integer.MAX_VALUE, uppercase letters, negative n and bad bases throwing
    //   - countDigits: 0, boundaries like 9/10 and 99/100, 1024 in base 2,
    //     negative n and bad bases throwing
    //   - a loop over many values of n and every base 2-36 checking that
    //     toDecimal(toBase(n, base), base) == n, and that your answers match
    //     Integer.toString(n, base).toUpperCase()

    void main()
    {
        IO.println(BaseConverter.digitValue((char) 1));
        IO.println(BaseConverter.digitValue('A'));
        IO.println(BaseConverter.digitValue('a'));



    }
}
