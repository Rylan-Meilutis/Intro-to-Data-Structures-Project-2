import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UnboundedIntTests {

    @Test
    void unboundedIntConstructorWithValidString() {
        UnboundedInt unboundedInt = new UnboundedInt("123456");
        assertEquals("123,456", unboundedInt.toString());
    }

    @Test
    void unboundedIntConstructorWithEmptyString() {
        assertThrows(IllegalArgumentException.class, () -> new UnboundedInt(""));
    }

    @Test
    void unboundedIntConstructorWithNonDigitString() {
        assertThrows(IllegalArgumentException.class, () -> new UnboundedInt("123abc"));
    }

    @Test
    void unboundedIntConstructorWithNegativeNumber() {
        assertThrows(IllegalArgumentException.class, () -> new UnboundedInt("-123456"));
    }

    @Test
    void addEndWithValidValue() {
        UnboundedInt unboundedInt = new UnboundedInt();
        unboundedInt.addEnd(123);
        assertEquals("123,000", unboundedInt.toString());
    }

    @Test
    void addEndWithInvalidValue() {
        UnboundedInt unboundedInt = new UnboundedInt();
        assertThrows(IllegalArgumentException.class, () -> unboundedInt.addEnd(1000));
    }

    @Test
    void addWithValidUnboundedInt() {
        UnboundedInt unboundedInt1 = new UnboundedInt("123456");
        UnboundedInt unboundedInt2 = new UnboundedInt("789012");
        UnboundedInt sum = unboundedInt1.add(unboundedInt2);
        assertEquals("912,468", sum.toString());
    }

    @Test
    void addWithLargeUnboundedInt() {
        UnboundedInt unboundedInt1 = new UnboundedInt("123456457328956473256495234532454354232");
        UnboundedInt unboundedInt2 = new UnboundedInt("7890124352554325325423");
        UnboundedInt sum = unboundedInt1.add(unboundedInt2);
        assertEquals("123,456,457,328,956,481,146,619,587,086,779,679,655", sum.toString());
    }

    @Test
    void addWithNullUnboundedInt() {
        UnboundedInt unboundedInt = new UnboundedInt("123456");
        assertThrows(IllegalArgumentException.class, () -> unboundedInt.add(null));
    }

    @Test
    void multiplyWithValidUnboundedInt() {
        UnboundedInt unboundedInt1 = new UnboundedInt("123");
        UnboundedInt unboundedInt2 = new UnboundedInt("456");
        UnboundedInt product = unboundedInt1.multiply(unboundedInt2);
        assertEquals("056,088", product.toString());
    }


    @Test
    void multiplyWithLargeUnboundedInt() {
        UnboundedInt unboundedInt1 = new UnboundedInt("1234372859234525634256783249");
        UnboundedInt unboundedInt2 = new UnboundedInt("4565435742357423952343");
        UnboundedInt product = unboundedInt1.multiply(unboundedInt2);
        assertEquals("005,635,449,970,945,232,517,063,671,588,690,294,102,215,056,702,407", product.toString());
    }

    @Test
    void multiplyWithNullUnboundedInt() {
        UnboundedInt unboundedInt = new UnboundedInt("123456");
        assertThrows(IllegalArgumentException.class, () -> unboundedInt.multiply(null));
    }

    @Test
    void equalsWithEqualUnboundedInt() {
        UnboundedInt unboundedInt1 = new UnboundedInt("123456");
        UnboundedInt unboundedInt2 = new UnboundedInt("123456");
        assertEquals(unboundedInt1, unboundedInt2);
    }

    @Test
    void equalsWithUnequalUnboundedInt() {
        UnboundedInt unboundedInt1 = new UnboundedInt("123456");
        UnboundedInt unboundedInt2 = new UnboundedInt("789012");
        assertNotEquals(unboundedInt1, unboundedInt2);
    }

    @Test
    void equalsWithNullUnboundedInt() {
        UnboundedInt unboundedInt = new UnboundedInt("123456");
        assertThrows(IllegalArgumentException.class, () -> unboundedInt.equals(null));
    }

    @Test
    void cloneUnboundedInt() {
        UnboundedInt unboundedInt = new UnboundedInt("123456");
        UnboundedInt clone = unboundedInt.clone();
        assertEquals(unboundedInt.toString(), clone.toString());
    }

    @Test
    void toStringUnboundedInt() {
        UnboundedInt unboundedInt = new UnboundedInt("123456");
        assertEquals("123,456", unboundedInt.toString());
    }

    @Test
    void toStringWithLeading0sUnboundedInt() {
        UnboundedInt unboundedInt = new UnboundedInt("1123456");
        assertEquals("001,123,456", unboundedInt.toString());
    }
}