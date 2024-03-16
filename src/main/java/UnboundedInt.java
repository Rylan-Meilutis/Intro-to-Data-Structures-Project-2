/**
 * The UnboundedInt class represents an unbounded integer, which is a positive integer that can be of any size. The
 * UnboundedInt class uses a linked list to store the digits of the unbounded integer, with each node containing 3
 * digits of the integer.
 *
 * @author Rylan Meilutis
 * @author Vassily Dudkin
 */

public class UnboundedInt implements Cloneable {
    //Invariant:
    //head is the first node in the linked list
    //tail is the last node in the linked list
    //size is the number of nodes in the linked list
    private IntNode head;
    private int size;
    private IntNode tail;


    /**
     * Constructor for the UnboundedInt class
     *
     * @param unboundedInt The unbounded int as a string
     *
     * @throws IllegalArgumentException If the input string is empty
     * @throws IllegalArgumentException If the unbounded int is not made up of digits
     * @throws IllegalArgumentException If the unbounded int is not a positive number
     * <dt><b>Precondition</b>unboundedInt is a positive number</dt>
     */
    public UnboundedInt(String unboundedInt) {
        head = null;
        tail = null;
        size = 0;

        if (unboundedInt.isEmpty()) {
            throw new IllegalArgumentException("UnboundedInt cannot be empty");
        }

        //guard clause to check if the unboundedInt is only digits
        // regex usage found at https://stackoverflow.com/questions/4463867/java-regular-expression-match
        //regex characters and meanings found at https://www.rexegg.com/regex-quickstart.html
        if (!unboundedInt.matches("-?\\d+")) {
            throw new IllegalArgumentException("UnboundedInt must only contain digits");
        }
        //guard clause to check if the unboundedInt is a positive number
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
    void addEnd(int value) {
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
        //remove the first node so that the new unbounded int isn't shifted 3 digits to the left
        sum.head = null;
        sum.tail = null;

        IntNode current = head;
        IntNode otherCurrent = addend.head;
        int carry = 0;
        while (current != null ||
               otherCurrent != null) { // Iterate over the linked list and add the values of each node
            int value = carry; // Add the carry to the value
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
        if (factor == null) {
            throw new IllegalArgumentException("Factor cannot be null");
        }

        UnboundedInt product = new UnboundedInt();
        //remove the first node so that the new unbounded int isn't shifted 3 digits to the left
        product.head = null;
        product.tail = null;

        IntNode current = head;
        int shift = 0;

        while (current != null) {
            int carry = 0;
            UnboundedInt partialProduct = new UnboundedInt(); // Stores partial product for current digit
            //remove the first node so that the new unbounded int isn't shifted 3 digits to the left
            partialProduct.head = null;
            partialProduct.tail = null;
            for (int i = 0; i < shift; i++) {
                partialProduct.addEnd(0); // Append zeros for shifting
            }

            IntNode factorCurrent = factor.head; // Multiply the current digit with each digit of the factor

            while (factorCurrent != null) {
                int value =
                        (current.getData() * factorCurrent.getData()) + carry; // Multiply the current digit with the
                // factor and add the carry
                carry = value / 1000;
                value %= 1000;
                partialProduct.addEnd(value); // Add the value to the partial product
                factorCurrent = factorCurrent.getLink();
            }

            if (carry > 0) {
                partialProduct.addEnd(carry); // Add any remaining carry
            }

            product = product.add(partialProduct); // Add the partial product to the final product
            shift++;
            current = current.getLink();
        }

        return product;
    }


    /**
     * Method to add two unbounded ints together
     *
     * @param obj The other unbounded int to add
     *
     * @return whether the two unbounded ints are equal
     *
     */
    public boolean equals(Object obj) {
        //guard clause to check if the obj is null
        if (obj == null) {
            return false;
        }
        // Check if the other object is an instance of UnboundedInt and if the size of the two unbounded ints are the
        // same
        if (obj instanceof UnboundedInt other) {
            if (size !=
                other.size) { // If the sizes are different, the unbounded ints are not equal so no need for further
                // checks
                return false;
            }
            IntNode current = head;
            IntNode otherCurrent = other.head;
            while (current != null && otherCurrent != null) { // loop through the linked list and check if the data of
                // each node is the same
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
            clone = (UnboundedInt) super.clone(); // Call the clone method of the Object class
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException("This class does not implement Cloneable.");
        }

        clone.head = IntNode.listCopy(this.head); // Copy the linked list
        clone.size = this.size;

        clone.tail = size > 1 ? IntNode.listPosition(clone.head, clone.size - 1) : clone.head; // Set the tail to the
        // last node in the linked list if the size is greater than 1, otherwise set it to the head
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
        while (current != null) { // loop through the linked list and add the data of each node to the string
            sb.insert(0, String.format("%03d", current.getData())); //format the data to have leading zeros
            sb.insert(0, ","); // Add a comma before every 3 digits value
            current = current.getLink();
        }
        // Remove the leading comma if the unbounded int is not empty
        if (!sb.isEmpty()) {
            sb.deleteCharAt(0);
        }
        //remove leading zeros
        while (sb.charAt(0) == '0' && sb.length() > 1) {
            sb.deleteCharAt(0);
        }
        return sb.toString();
    }
}