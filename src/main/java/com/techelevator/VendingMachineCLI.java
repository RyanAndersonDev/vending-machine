package com.techelevator;

import com.techelevator.view.Menu;
import com.techelevator.view.PurchaseMenu;

public class VendingMachineCLI {
	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String MAIN_MENU_SECRET_OPTION = "*Sales Report";

	private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed Money";
	private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT = "Select Product";
	private static final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION = "Finish Transaction";

	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT, MAIN_MENU_SECRET_OPTION };
	private static final String[] PURCHASE_MENU_OPTIONS = { PURCHASE_MENU_OPTION_FEED_MONEY, PURCHASE_MENU_OPTION_SELECT_PRODUCT, PURCHASE_MENU_OPTION_FINISH_TRANSACTION};


	private Menu menu;

	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}

	public void run() {
		while (true) {
			//restocking vending machine restock(invFile)
			Item.createItemsFromFolder();
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				// display vending machine items
				System.out.println(VendingMachineInventory.INSTANCE);
			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				// do purchase
				PurchaseMenu purchaseMenuInstance = new PurchaseMenu(System.in, System.out);

				while(true) {
					System.out.println("\nCurrent Money Provided: $" + purchaseMenuInstance.getMoneyProvided());

					choice = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);

					if(choice.equals(PURCHASE_MENU_OPTION_FEED_MONEY)) {
						//feed money
						purchaseMenuInstance.feedMoney();
					} else if(choice.equals(PURCHASE_MENU_OPTION_SELECT_PRODUCT)) {
						//select product

					} else if(choice.equals(PURCHASE_MENU_OPTION_FINISH_TRANSACTION)) {
						//finish transaction

					}
				}

			} else if (choice.equals(MAIN_MENU_OPTION_EXIT)) {
				//Exit
				break;
			}
		}
	}

	public static void main(String[] args) {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
	}
}
