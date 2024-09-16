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
import java.util.InputMismatchException;

public class FermatTheoremNearMisses {
    // Initialization and declaration of static Scanner class to get input from the user
    static Scanner input = new Scanner(System.in);
    public static void main(String[] args) {
        int n = 0;
        while (true) {
            // Prompting the user to enter a value of exponent (n) greater than 2 and less than 12
            System.out.println("Enter a value of exponent greater than 2 and less than 12");
            try { // Try catch encase the user input invalid characters
                n = input.nextInt();
                if (n > 2 && n < 12) {
                    break;
                } else {
                    System.out.println("Invalid input. Please enter n between 2 and 12.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number greater than 2 and less than 12.");
                input.next(); // Clear the invalid input
            }
        }
        int k = 0;
        while (true) {
            // Prompting the user to enter the upper limit (k), where k should be greater than or equal to 10
            System.out.println("Enter the value of upper limit k >= 10 and k < pow(2, 32)");
            try { // Try catch encase the user input invalid characters
                k = input.nextInt();  // Reading user input for upper limit (k)
                if (k >= 10 && k < Integer.MAX_VALUE) { //  Upper limit on K so we don't run out memory
                    break;
                } else {
                    System.out.println("Invalid input. Please enter k greater than or equal to 10.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number greater than or equal to 10.");
                input.next(); // Clear the invalid input
            }
        }
        // Calling the static method calculateNearMiss and passing the two arguments, n and k
        calculateNearMiss(n, k);
    }

    // Method which handles the calculation of near misses in Fermat's theorem
    public static void calculateNearMiss(int n, int k) {
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
                // Use Math.floor to avoid rounding issues
                int z = (int) Math.floor(Math.pow(xnPlusYn, 1.0 / n)); // Start z at the approximate nth root of (x^n + y^n)

                // Calculate z^n and (z+1)^n
                double zn = Math.pow(z, n);
                double nextZn = Math.pow(z + 1, n);

                // Check if the value x^n + y^n lies between z^n and (z+1)^n
                // Calculate the miss between x^n + y^n and z^n
                double missA = Math.abs(xnPlusYn - zn);

                // Calculate the miss between (z+1)^n and x^n + y^n
                double missB = Math.abs(nextZn - xnPlusYn);

                // Find the smaller miss (either missA or missB)
                double miss = Math.min(missA, missB);

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
                    System.out.println("New Smallest Relative Miss: " + bestRelativeMiss);
                    System.out.println("Current x: " + bestX + ", y: " + bestY + ", z: " + bestZ);
                    System.out.println("Actual miss: " + miss);
                    System.out.println("Relative Miss: " + relativeMiss + "\n");
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
    }
}