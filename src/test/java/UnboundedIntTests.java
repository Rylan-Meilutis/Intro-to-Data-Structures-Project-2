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
}