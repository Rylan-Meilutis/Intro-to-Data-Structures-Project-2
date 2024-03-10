/**
 * The UnboundedInt class represents an unbounded integer, which is a positive integer that can be of any size. The
 * UnboundedInt class uses a linked list to store the digits of the unbounded integer, with each node containing 3
 * digits of the integer.
 *
 * @author Rylan Meilutis
 * @author Vassily Dudkin
 */
public class UnboundedInt implements Cloneable {
    private IntNode head;
    private int size;
    private IntNode tail;


    /**
     * Constructor for the UnboundedInt class
     *
     * @param unboundedInt The unbounded int as a string
     *
     * @throws IllegalArgumentException If the unbounded int is not a positive number
     * <dt><b>Precondition</b>unboundedInt is a positive number</dt>
     * @throws IllegalArgumentException If the input string is empty
     */
    public UnboundedInt(String unboundedInt) {
        head = null;
        tail = null;
        size = 0;

        if (unboundedInt.isEmpty()) {
            throw new IllegalArgumentException("UnboundedInt cannot be empty");
        }

        //guard clause to check if the unboundedInt is only digits
        if (!unboundedInt.matches(
                "\\d+")) { // regex patterns found at https://stackoverflow.com/questions/4463867/java-regular-expression-match
            throw new IllegalArgumentException("UnboundedInt must only contain digits");
        }
        if (unboundedInt.charAt(0) == '-') {
            throw new IllegalArgumentException("UnboundedInt must be a positive number");
        }
        // Iterate over every 3 characters in the string
        for (int i = unboundedInt.length(); i > 0; i -= 3) {
            int start = Math.max(i - 3, 0); // Calculate start index for substring
            String sub = unboundedInt.substring(start, i);
            int value = Integer.parseInt(sub);
            addEnd(value); // Insert the parsed value at the tail of the linked list
        }
    }

    /**
     * Default constructor for the UnboundedInt class, creates an empty unbounded int initialized to 0
     */
    public UnboundedInt() {
        head = null;
        tail = null;
        size = 0;

        addEnd(0);

    }

    /**
     * Method to insert a value at the tail of the linked list
     *
     * @param value The value to insert
     *
     * @throws IllegalArgumentException If the value is not between 0 and 999
     * <dt><b>Precondition</b>Value is a positive value less than 1000</dt>
     */
    private void addEnd(int value) {
        if (value < 0 || value > 999) {
            throw new IllegalArgumentException("Value must be between 0 and 999");
        }
        IntNode newNode = new IntNode(value, null);
        if (head == null) {
            head = newNode;
        }
        else {
            tail.setLink(newNode);
        }
        tail = newNode;
        size++;
    }


    /**
     * Method to add two unbounded ints together
     *
     * @param addend The other unbounded int to add
     *
     * @return A new unbounded int containing sum of the two unbounded ints
     *
     * @throws IllegalArgumentException If the other unbounded int is null
     * <dt><b>Precondition</b>Other is not null</dt>
     * <dt><b>Postcondition</b>The original unbounded ints are unchanged</dt>
     */
    public UnboundedInt add(UnboundedInt addend) {
        //guard clause to check if the addend is null
        if (addend == null) {
            throw new IllegalArgumentException("Addend cannot be null");
        }
        //add the two unbounded ints together going node by node and handling the carry
        UnboundedInt sum = new UnboundedInt();
        sum.head = null;
        sum.tail = null;
        IntNode current = head;
        IntNode otherCurrent = addend.head;
        int carry = 0;
        while (current != null || otherCurrent != null) {
            int value = carry;
            if (current != null) {
                value += current.getData();
                current = current.getLink();
            }
            if (otherCurrent != null) {
                value += otherCurrent.getData();
                otherCurrent = otherCurrent.getLink();
            }
            carry = value / 1000;
            value = value % 1000;
            sum.addEnd(value);
        }
        if (carry > 0) {
            sum.addEnd(carry);
        }
        return sum;
    }

    /**
     * Method to multiply two unbounded ints together
     *
     * @param factor The other unbounded int to multiply
     *
     * @return A new unbounded int containing the product of the two unbounded ints
     *
     * @throws IllegalArgumentException If the other unbounded int is null
     * <dt><b>Precondition</b>Factor is not null</dt>
     * <dt><b>Postcondition</b>The original unbounded ints are unchanged</dt>
     */
    UnboundedInt multiply(UnboundedInt factor) {
        //guard clause to check if the factor is null
        if (factor == null) {
            throw new IllegalArgumentException("Factor cannot be null");
        }
        // Multiply the two unbounded ints together going node by node and handling the carry
        UnboundedInt product = new UnboundedInt();
        product.head = null;
        product.tail = null;
        IntNode current = head;
        IntNode otherCurrent;
        int carry = 0;
        while (current != null) {
            int value = carry;
            otherCurrent = factor.head; // Reset otherCurrent for each iteration
            while (otherCurrent != null) {
                value += current.getData() * otherCurrent.getData();
                otherCurrent = otherCurrent.getLink();
            }
            carry = value / 1000;
            value = value % 1000;
            product.addEnd(value);
            current = current.getLink();
        }
        if (carry > 0) {
            product.addEnd(carry);
        }
        return product;
    }


    /**
     * Method to add two unbounded ints together
     *
     * @param obj The other unbounded int to add
     *
     * @return The sum of the two unbounded ints
     *
     * @throws IllegalArgumentException If the other unbounded int is null
     * <dt><b>Precondition</b>Other is not null</dt>
     */
    public boolean equals(Object obj) {
        //guard clause to check if the obj is null
        if (obj == null) {
            throw new IllegalArgumentException("Other cannot be null");
        }
        // Check if the other object is an instance of UnboundedInt and if the size of the two unbounded ints are the
        // same
        if (obj instanceof UnboundedInt other) {
            if (size != other.size) {
                return false;
            }
            IntNode current = head;
            IntNode otherCurrent = other.head;
            while (current != null && otherCurrent != null) {
                if (current.getData() != otherCurrent.getData()) {
                    return false;
                }
                current = current.getLink();
                otherCurrent = otherCurrent.getLink();
            }
            return current == null && otherCurrent == null;
        }
        else {
            return false;
        }
    }

    /**
     * Clone the UnboundedInt object
     *
     * @return a clone of the UnboundedInt object
     *
     * @throws RuntimeException if the class does not implement Cloneable
     */
    public UnboundedInt clone() {
        UnboundedInt clone;
        try {
            clone = (UnboundedInt) super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException("This class does not implement Cloneable.");
        }

        clone.head = IntNode.listCopy(this.head);
        clone.size = this.size;

        clone.tail = size > 1 ? IntNode.listPosition(clone.head, clone.size - 1) : clone.head;
        return clone;
    }

    /**
     * Outputs the unbounded int as a string, with leading zeros and commas
     *
     * @return The unbounded int as a string, with leading zeros and commas
     */
    public String toString() {
        // Iterate over the linked list and create a string with the value of each node, adding a comma between each
        // group of 3
        StringBuilder sb = new StringBuilder();
        IntNode current = head;
        while (current != null) {
            sb.insert(0, String.format("%03d", current.getData()));
            sb.insert(0, ",");
            current = current.getLink();
        }
        if (!sb.isEmpty()) {
            sb.deleteCharAt(0);
        }
        return sb.toString();
    }
}