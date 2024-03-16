import java.util.Scanner;

// Authors Rylan Meilutis and Vassily Dudkin
public class LargeNumberTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        UnboundedInt[] unboundedIntsArray = getUnboundedInts(scanner);
        UnboundedInt uint1 = unboundedIntsArray[0];
        UnboundedInt uint2 = unboundedIntsArray[1];
        int choice = 0;

        while (choice != 7) {
            System.out.println();
            System.out.println("Unbounded Int Test Menu:");
            System.out.println("1. Display both numbers (with commas)");
            System.out.println("2. Input two new numbers (without commas)");
            System.out.println("3. Check if numbers are equal");
            System.out.println("4. Report the sum of the two numbers");
            System.out.println("5. Report the multiplication of the two numbers");
            System.out.println("6. Create and output the clone of the first number");
            System.out.println("7. Quit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            System.out.println();

            try {
                switch (choice) {
                    case 1:
                        System.out.println("The first number is: " + uint1);
                        System.out.println("The second number is: " + uint2);
                        break;
                    case 2:
                        scanner.nextLine(); // consume the entire buffer
                        unboundedIntsArray = getUnboundedInts(scanner);
                        uint1 = unboundedIntsArray[0];
                        uint2 = unboundedIntsArray[1];
                        break;
                    case 3:
                        System.out.println(uint1.equals(uint2) ? "The numbers are equal" : "The numbers are not equal");
                        break;
                    case 4:
                        System.out.println("The sum of the two numbers is: " + uint1.add(uint2));
                        break;
                    case 5:
                        System.out.println("The product of the two numbers is: " + uint1.multiply(uint2));
                        break;
                    case 6:
                        UnboundedInt uint3 = uint1.clone();
                        System.out.println("The clone of the first number is: " + uint3);
                        break;
                    case 7:
                        System.out.println("Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice");
                        break;
                }
                System.out.println();
            }
            catch (IllegalStateException | IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        scanner.close();
    }

    /**
     * Get two unbounded ints from the user
     *
     * @param scanner The scanner to use for input
     *
     * @return An array of two unbounded ints
     */
    static UnboundedInt[] getUnboundedInts(Scanner scanner) {
        UnboundedInt[] unboundedIntsArray = {null, null};

        for (int i = 0; i < unboundedIntsArray.length; i++) { // loop through the array and get the unbounded ints from
            // the user only 2 for now
            boolean passed = false; // flag to check if the input is valid
            while (!passed) {
                try {
                    System.out.print("Enter unbounded int " + (i + 1) + ": ");
                    unboundedIntsArray[i] = new UnboundedInt(scanner.nextLine()); // create the unbounded int
                    passed = true; // if the input is valid, set the flag to true
                }
                catch (IllegalArgumentException e) {
                    System.out.println("Error creating unbounded int " + (i + 1) + ": " + e.getMessage()); //notify the
                    // user of the error
                }
            }
        }
        return unboundedIntsArray;
    }
}
