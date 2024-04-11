package utils;

import fypms.App;

/**
 * A utility class for handling input/output operations.
 */
public class InputOutput {

	 /**
      * Reads an integer input from the user.
      *
      * @return The integer value entered by the user.
      */
	public static int getInt() {
        int input = 0;
        boolean isValidInput = false;

        while (!isValidInput) {
            if (App.scanner.hasNextInt()) {
                input = App.scanner.nextInt();
                isValidInput = true;
            } else {
                System.out.println("Invalid input. Please try again.");
                App.scanner.next();
            }
        }

        return input;
	}
	
	
	/**
      * Displays a message to prompt the user to enter any text to exit the current loop.
      * Waits for the user to enter text.
      */
	public static void pressToExit() {
		System.out.println("Enter any text to exit...");
		
		
		while (true) {
			if (App.scanner.hasNext()) {
				break;
			}
		}
		
		App.scanner.nextLine();
		App.scanner.nextLine();
		
	}
	
	 /**
      * Displays a divider consisting of a line of equal signs.
      */
	public static void printDivider() {
		System.out.println("=====================================================");
	}
	
	/**
     * Displays text padded with an empty line on either side.
     *
     * @param text The text to be displayed.
     */
	public static void printPaddedText(String text) {
		System.out.println("");
		System.out.println(text);
		System.out.println("");
	}
	
	/**
     * Displays an empty line.
     */
	public static void printSpacer() {
		System.out.println("");
	}
	
	/**
     * Displays an error message surrounded by asterisks to draw attention to it.
     *
     * @param message The error message to be displayed.
     */
	public static void printError(String message) {
		char ch = '*';
		int length = message.length() + 15;

		String repeatedChars = Character.toString(ch).repeat(length);

		System.out.println("");
		System.out.println(repeatedChars);
		System.out.println("* Error: " + message);
		System.out.println(repeatedChars);
		System.out.println("");
	}
	
	/**
     * Reads a string input from the user.
     *
     * @return The string value entered by the user.
     */
	public static String getString() {
        String input = "";
        
        while(input.isEmpty()) {
        	input = App.scanner.nextLine();
        }

        return input;
	}
}
