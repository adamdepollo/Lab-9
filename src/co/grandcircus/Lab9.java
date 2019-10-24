/*
 * @Adam DePollo, 10/22/19
*/
package co.grandcircus;

import java.util.HashMap;
import java.util.Scanner;

public class Lab9 {
	public static void main(String[] args) {
		// Create scanner and variables
		Scanner scnr = new Scanner(System.in);
		String cont = "yes";
		String moreFruit = "yes";
		double runningSum = 0.0;
		// Instantiate item menu using a method to clear up code in main method
		HashMap<Integer, HashMap<String, Double>> itemMenu = initializeMenu();
		HashMap<Integer, Integer> itemsOrdered = new HashMap<>();

		while (cont.equalsIgnoreCase("yes")) {
			do {
				// Call printMenu method to display formatted menu to user
				printMenu(itemMenu);

				// Ask user to select an item to purchase from the menu using its item number
				// and validate the selection using Validator.getInt method
				int itemChoice = Validator.getInt(scnr,
						"What item would you like to purchase? (Enter a number 1-10)\n", 1, 10);

				// Ask user to input quantity of that item they want to purchase
				// and validate the input using Validator.getInt method
				int numItems = Validator.getInt(scnr,
						"And how many " + getKeyString(itemChoice, itemMenu).toLowerCase() + "s would you like?\n");

				// Save the item selection to the itemsOrdered HashMap. If the item was already
				// in their cart, add the new quantity to the number that was already saved in
				// the HashMap.
				if (!itemsOrdered.keySet().contains(itemChoice)) {
					itemsOrdered.put(itemChoice, numItems);
				} else {
					itemsOrdered.replace(itemChoice, (itemsOrdered.get(itemChoice) + numItems));
				}

				// Tell user how many of the item they are selecting and at what unit price.
				// GetKeyString and getUnitPrice methods return the name of the item selected
				// and the unit price
				System.out.printf("%s%d%s%.2f%s\n", "Adding ", numItems,
						" " + getKeyString(itemChoice, itemMenu) + "(s) to your cart at $",
						getUnitPrice(itemChoice, itemMenu), " each.");

				// Add the sum of the items selected to the runningSum variable using the
				// sumItems method
				runningSum = sumItems(runningSum, itemChoice, numItems, itemMenu);

				// Ask user whether they'd like to add anything else to their order
				System.out.println("Would you like to order anything else? (yes/no)");
				moreFruit = scnr.nextLine();

				// If they do not enter a valid input, request they re-enter input
				while (!moreFruit.equalsIgnoreCase("yes") && !moreFruit.equalsIgnoreCase("no")) {
					System.out.println("Sorry, please put yes or no:");
					moreFruit = scnr.nextLine();
				}
			} while (moreFruit.equals("yes"));

			// Call printItemsOrdered method to print a formatted list of the items they
			// ordered, the quantity of each, and the sub-total for that item
			printItemsOrdered(itemsOrdered, itemMenu);

			// Print the total cost of their order as saved in the runningSum variable
			System.out.printf("%-22s%s%.2f\n", "Total cost: ", "$", runningSum);

			// Print out the average cost of their items purchased, calculated using the
			// calcAverageCost method
			System.out.printf("%-22s%s%.2f\n", "Avg. item cost: ", "$", calcAverageCost(itemsOrdered, itemMenu));

			// Find and print the most and least expensive fruits purchased using the
			// findHighLowVal method
			findHighLowVal(itemsOrdered, itemMenu);

			// Ask the user whether they want to continue
			System.out.println("Do you want to go again? (yes/no)");
			cont = scnr.nextLine();

			// Prompt for new input if their entry is invalid
			while (!cont.equalsIgnoreCase("yes") && !cont.equalsIgnoreCase("no")) {
				System.out.println("Sorry, please put yes or no:");
				cont = scnr.nextLine();
			}
		}

		// Say bye to user and close scanner
		System.out.println("Bye!");
		scnr.close();
	}

	// Create method to initialize the menu of items the user can choose from.
	public static HashMap<Integer, HashMap<String, Double>> initializeMenu() {
		// Declare HashMap which will store item name and cost in HashMaps numbered 1-10
		HashMap<Integer, HashMap<String, Double>> itemMenu = new HashMap<>();

		// For loop to declare a new HashMap for each menu item rather than printing the
		// same line of code 10 times
		for (int i = 1; i <= 10; i++) {
			itemMenu.put(i, new HashMap<String, Double>());
		}

		// Put item name and cost into the HashMap for each menu item and return the
		// completed itemMenu HashMap
		itemMenu.get(1).put("Banana", 0.50);
		itemMenu.get(2).put("Apple", 0.35);
		itemMenu.get(3).put("Orange", 0.75);
		itemMenu.get(4).put("Guava", 2.50);
		itemMenu.get(5).put("Mango", 1.50);
		itemMenu.get(6).put("Pomegranate", 2.25);
		itemMenu.get(7).put("Star Fruit", 2.75);
		itemMenu.get(8).put("Plum", 1.05);
		itemMenu.get(9).put("Watermelon", 3.50);
		itemMenu.get(10).put("Papaya", 1.25);
		return itemMenu;
	}

	// This method formats the itemMenu HashMap created in the initializeMenu method
	// using output formatting and then prints the formatted menu
	public static void printMenu(HashMap<Integer, HashMap<String, Double>> itemMenu) {
		// Print out title row
		System.out.printf("%-3s|%-12s|%-10s\n", "No.", "Name", "Price");

		// For loop prints 30 * characters as a line separator
		for (int i = 1; i <= 30; i++) {
			System.out.print("*");
		}
		System.out.println();

		// Loop through each menu item in the itemMenu HashMap
		for (Integer i : itemMenu.keySet()) {
			// For each item, print the item number (i), name (s), and price
			// (itemMenu.get(i).get(s))
			for (String s : itemMenu.get(i).keySet()) {
				System.out.printf("%-3s|%-12s|$%-10.2f\n", i + ".", s, itemMenu.get(i).get(s));
			}
		}

		// For loop prints 30 * characters as a line separator
		for (int i = 1; i <= 30; i++) {
			System.out.print("*");
		}
		System.out.println();

	}

	// This method returns each item name in the itemMenu HashMap as a string
	public static String getKeyString(int itemChoice, HashMap<Integer, HashMap<String, Double>> itemMenu) {
		String keyString = "";
		// For each loop iterates through the HashMap saved at itemChoice. There's only
		// 1 key/value pair in that HashMap with the item name serving as the key. The
		// for each loop thus saves the item name as a String.
		for (String s : itemMenu.get(itemChoice).keySet()) {
			keyString = s;
		}
		return keyString;
	}

	// Similar to getKeyString above, this method returns the unit price of a given
	// menu item
	public static double getUnitPrice(int itemChoice, HashMap<Integer, HashMap<String, Double>> itemMenu) {
		double unitPrice = 0.0;
		for (String s : itemMenu.get(itemChoice).keySet()) {
			unitPrice = itemMenu.get(itemChoice).get(s);
		}
		return unitPrice;
	}

	// This method calculates the sum of the quantity of particular menu items
	// selected by the user
	public static double sumItems(double runningSum, int itemChoice, int numItems,
			HashMap<Integer, HashMap<String, Double>> itemMenu) {
		double unitCost = 0.0;
		// For the menu item selected by the user ...
		for (String s : itemMenu.get(itemChoice).keySet()) {
			// Set unit cost equal to the unit cost saved in the itemMenu HashMap
			unitCost = itemMenu.get(itemChoice).get(s);
		}
		// Add the total cost for the number of items selected to the runningSum
		// input in the method call
		runningSum += unitCost * numItems;
		return runningSum;
	}

	// This method prints a formatted list of the items in thee user's order
	public static void printItemsOrdered(HashMap<Integer, Integer> itemsOrdered,
			HashMap<Integer, HashMap<String, Double>> itemMenu) {
		System.out.println("Items Ordered");
		// For loop prints 30 * characters as a spacer
		for (int i = 1; i <= 30; i++) {
			System.out.print("*");
		}
		System.out.println();
		System.out.printf("%-12s|%-10s|%-10s\n", "Name", "Quantity", "Price");
		for (int i = 1; i <= 30; i++) {
			System.out.print("*");
		}
		System.out.println();
		// Loop through the itemsOrdered HashMap, which includes the reference # for
		// every item ordered
		for (Integer i : itemsOrdered.keySet()) {
			// For each of those items, print out the name of the item (stored in
			// itemMenu.get(i).keySet(), the quantity of those items ordered (stored at
			// itemsOrdered.get(i)), and the sum total of those items (quantity of items
			// order x unit cost stored at items itemMenu.get(i).get(s)).
			for (String s : itemMenu.get(i).keySet()) {
				System.out.printf("%-12s|%-10s|$%-10.2f\n", s, itemsOrdered.get(i),
						(itemMenu.get(i).get(s) * itemsOrdered.get(i)));
			}
		}
		for (int i = 1; i <= 30; i++) {
			System.out.print("*");
		}
		System.out.println();
	}

	// This method calculates the average cost of all of the items included in the
	// user's order
	public static double calcAverageCost(HashMap<Integer, Integer> itemsOrdered,
			HashMap<Integer, HashMap<String, Double>> itemMenu) {
		double totalToAverage = 0.0;
		double average = 0.0;
		// For each item saved in the itemsOrdered HashMap ...
		for (Integer i : itemsOrdered.keySet()) {
			// ...iterate into the HashMap saved for that menu item within the itemMenu
			// HashMap ...
			for (String s : itemMenu.get(i).keySet()) {
				// ...and add that item's cost to the totalToAverage variable
				totalToAverage += itemMenu.get(i).get(s);
			}
		}
		// Calculate the average (i.e., the totalToAverage divided by the number of
		// items in the user's order
		average = totalToAverage / itemsOrdered.size();

		return average;
	}

	// This method finds the most and least expensive items in the user's order
	public static void findHighLowVal(HashMap<Integer, Integer> itemsOrdered,
			HashMap<Integer, HashMap<String, Double>> itemMenu) {
		// Declare variables used to find and save the items
		HashMap<String, Double> highVal = new HashMap<>();
		HashMap<String, Double> lowVal = new HashMap<>();
		double highValDouble = 0.0;
		double lowValDouble = 100.0;
		String highestVal = "";
		String lowestVal = "";

		// For each item in the user's order ...
		for (Integer i : itemsOrdered.keySet()) {
			// ...iterate into the HashMap saved for that item in the itemMenu HashMap
			for (String s : itemMenu.get(i).keySet()) {
				// If the value is higher than any previously reviewed item, put it in the
				// highVal HashMap, save its name as the highestVal string, and save its unit
				// price as the highValDouble.
				if (itemMenu.get(i).get(s) > highValDouble) {
					highValDouble = itemMenu.get(i).get(s);
					highestVal = s;
					highVal.put(s, itemMenu.get(i).get(s));
				}
				// If the value is lower than any previously reviewed item, put it in the
				// lowVal HashMap, save its name as the lowestVal string, and save its unit
				// price as the lowValDouble.
				if (itemMenu.get(i).get(s) < lowValDouble) {
					lowValDouble = itemMenu.get(i).get(s);
					lowestVal = s;
					lowVal.put(s, itemMenu.get(i).get(s));
				}
			}
		}

		// Print the high and low values
		System.out.printf("%-22s%s%.2f\n", "Most Expensive Purchase: ", highestVal + ", $", highValDouble);
		System.out.printf("%-22s%s%.2f\n", "Least Expensive Purchase: ", lowestVal + ", $", lowValDouble);
	}

}
