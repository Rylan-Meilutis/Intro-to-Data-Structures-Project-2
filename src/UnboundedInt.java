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
        //add the two unbounded ints together going node by node and handling the carry
        if (addend == null) {
            throw new IllegalArgumentException("Addend cannot be null");
        }
        UnboundedInt sum = new UnboundedInt("0");
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

    UnboundedInt multiply(UnboundedInt factor) {
        // Multiply the two unbounded ints together going node by node and handling the carry
        UnboundedInt product = new UnboundedInt("0");
        product.head = null;
        product.tail = null;
        IntNode current = head;
        IntNode otherCurrent = factor.head;
        int carry = 0;
        int count = 0;
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
            count++;
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
        if (obj == null) {
            throw new IllegalArgumentException("Other cannot be null");
        }
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
     * @throws CloneNotSupportedException if the object cannot be cloned
     */
    public UnboundedInt clone() throws CloneNotSupportedException {
        UnboundedInt clone = (UnboundedInt) super.clone();
        IntNode current = head;
        while (current != null) {
            clone.addEnd(current.getData());
            current = current.getLink();
        }
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
        sb.deleteCharAt(0);
        return sb.toString();
    }
}