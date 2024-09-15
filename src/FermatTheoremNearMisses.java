/*
 * Title: Fermat's Last Theorem Near Misses Program
 * File Name: FermatTheoremNearMisses.java
 *
 * External Files Needed: None
 * External Files Created: None
 *
 * Programmers: Prithu Kathet, Travis Lester
 * Email: prithukathet@lewisu.edu , travisllester@lewisu.edu
 *
 * Course: Software Engineering, FA24-CPSC-60500-002
 * Instructor: Fadi Wedyan
 *
 * Date Finished: September 18, 2024
 *
 * Program Description:
 * This program helps the user find "near misses" for Fermat's Last Theorem,
 * by testing combinations of integers x, y, z and checking how close
 * the equation x^n + y^n = z^n is to being true for n > 2.
 *
 * Resources Used:
 * 1. Java Math documentation (for power and rounding functions)
 * 2. Fermat's Last Theorem resources from Wikipedia
 */

import java.util.Scanner;

public class FermatTheoremNearMisses {
    // Initialization and declaration of static Scanner class to get input from the user
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {

        // Prompting the user to enter a value of exponent (n) greater than 2 and less than 12
        System.out.println("Enter a value of exponent greater than 2 and less than 12");
        int n = input.nextInt();  // Reading user input for exponent (n)

        // Prompting the user to enter the upper limit (k), where k should be greater than or equal to 10
        System.out.println("Enter the value of upper limit k >= 10");
        int k = input.nextInt();  // Reading user input for upper limit (k)

        // Calling the static method calculateNearMiss and passing the two arguments, n and k
        calculateNearMiss(n, k);
    }

    // Method which handles the calculation of near misses in Fermat's theorem
    public static void calculateNearMiss(int n, int k) {

        // Check if input values are within the required range, and if not, print an error message and return
        if (n <= 2 || n >= 12 || k < 10) {
            System.out.println("Invalid input. Please enter n between 2 and 12, and k greater than or equal to 10.");
            return;  // Exit the method if inputs are invalid
        }

        // Initializing minRelativeMiss to the maximum possible double value to track the smallest relative miss
        double minRelativeMiss = Double.MAX_VALUE;

        // Variables to track the best values of x, y, z and their corresponding misses
        int bestX = 0, bestY = 0, bestZ = 0;
        double bestMiss = 0, bestRelativeMiss = 0;

        // Loop through all combinations of x and y, starting from 10 up to the upper limit k
        for (int x = 10; x <= k; x++) {
            for (int y = 10; y <= k; y++) {

                // Calculating the sum of x^n and y^n for the current values of x and y
                double xnPlusYn = Math.pow(x, n) + Math.pow(y, n);

                // Initialize z to 1 for each combination of x and y
                int z = 1;

                // Loop to find the closest z such that z^n is less than or equal to x^n + y^n
                while (Math.pow(z, n) <= xnPlusYn) {
                    // Calculate z^n and (z+1)^n
                    double zn = Math.pow(z, n);
                    double nextZn = Math.pow(z + 1, n);

                    // Check if the value x^n + y^n lies between z^n and (z+1)^n
                    if (zn <= xnPlusYn && xnPlusYn < nextZn) {
                        // Calculate the miss between x^n + y^n and z^n
                        double miss_a = xnPlusYn - zn;

                        // Calculate the miss between (z+1)^n and x^n + y^n
                        double miss_b = nextZn - xnPlusYn;

                        // Find the smaller miss (either miss_a or miss_b)
                        double miss = Math.min(miss_a, miss_b);

                        // Calculate the relative miss by dividing the miss by x^n + y^n
                        double relativeMiss = miss / xnPlusYn;

                        // Update the smallest relative miss and corresponding values if a new smaller miss is found
                        if (relativeMiss < minRelativeMiss) {
                            minRelativeMiss = relativeMiss;
                            bestX = x;
                            bestY = y;
                            bestZ = z;
                            bestMiss = miss;
                            bestRelativeMiss = relativeMiss;
                        }
                    }
                    z++;  // Increment z to check the next value
                }
            }
        }

        // After all combinations, check if any near misses were found (bestX, bestY, bestZ should not be 0)
        if (bestX != 0 || bestY != 0 || bestZ != 0) {
            // Print the smallest relative miss and the corresponding values of x, y, and z
            System.out.println("Smallest relative miss: " + bestRelativeMiss);
            System.out.println("Values: x = " + bestX + ", y = " + bestY + ", z = " + bestZ);
            System.out.println("Actual miss: " + bestMiss);
        } else {
            // If no near misses were found, print a message indicating so
            System.out.println("No near misses found.");
        }

        // Prompt the user to press Enter to exit, pausing the execution to allow them to view the results
        System.out.println("Press Enter to exit...");
        input.nextLine();  // Consume the leftover newline character
        input.nextLine();  // Wait for user to press Enter to exit

        // Close the Scanner object to free up resources
        input.close();
    }
}
