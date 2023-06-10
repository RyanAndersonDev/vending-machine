package com.techelevator;

import com.techelevator.view.Menu;
import com.techelevator.view.PurchaseMenu;

import javax.imageio.IIOException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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

	public void run() throws IOException {
		Item.createItemsFromFolder();
		Log.populateSalesReport(true);
		while (true) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				System.out.println(VendingMachineInventory.INSTANCE);

			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				PurchaseMenu purchaseMenuInstance = new PurchaseMenu(System.in, System.out);

				while(true) {
					System.out.println("\nCurrent Money Provided: $" + purchaseMenuInstance.getMoneyProvided());

					choice = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);

					if(choice.equals(PURCHASE_MENU_OPTION_FEED_MONEY)) {
						purchaseMenuInstance.feedMoney();

					} else if(choice.equals(PURCHASE_MENU_OPTION_SELECT_PRODUCT)) {
						purchaseMenuInstance.selectProduct();

					} else if(choice.equals(PURCHASE_MENU_OPTION_FINISH_TRANSACTION)) {
						String changeString = purchaseMenuInstance.finalizeTransaction();
						System.out.println(changeString);
						break;
					}
				}

			} else if (choice.equals(MAIN_MENU_OPTION_EXIT)) {
				break;
			} else if (choice.equals(MAIN_MENU_SECRET_OPTION)) {
				Log.populateSalesReport(false);
			}
		}
	}

	public static void main(String[] args) throws IOException {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
	}
}
