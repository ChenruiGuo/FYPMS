 package boundaries;

import java.util.ArrayList;

import controllers.AuthController;
import utils.InputOutput;

public abstract class BasePageBoundary implements IPageBoundary {
	
	protected ArrayList<UIOption> options;
	protected String menuWelcomeMessage;
	
	protected String getMenuWelcomeMessage() { return this.menuWelcomeMessage; }
	protected void setMenuWelcomeMessage(String m) { this.menuWelcomeMessage = m; }
	
	protected void renderMenuOptions() {
		InputOutput.printSpacer();
		InputOutput.printDivider();
		System.out.println(this.getMenuWelcomeMessage());
		InputOutput.printSpacer();
		for (UIOption option : this.options) {
			System.out.println("(" + (this.options.indexOf(option) + 1) + ") " + option.getOptionMessage());
		}
		InputOutput.printDivider();
		System.out.print("Please enter your choice: ");
	}
	
	public void display() {
		int choice = 0;
		while (AuthController.isUserLoggedIn()) {
			this.renderMenuOptions();
			choice = InputOutput.getInt();
			if (choice < 1 || choice > options.size()) {
				System.out.println("Invalid choice. Please try again.");
			} else {
				options.get(choice - 1).getOptionHandler().run();
			}
		}
	}	
}
