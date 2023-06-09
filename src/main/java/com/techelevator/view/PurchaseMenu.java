package com.techelevator.view;

import com.techelevator.Item;
import com.techelevator.VendingMachineInventory;

import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Scanner;

public class PurchaseMenu extends Menu {

    private BigDecimal moneyProvided = new BigDecimal(0);


    // Constructors
    public PurchaseMenu() {
        super();
    };

    public PurchaseMenu(InputStream input, OutputStream output) {
        super(input, output);
    }


    // Getters
    public BigDecimal getMoneyProvided() {
        return moneyProvided;
    }


    // Methods
    public void feedMoney() {
        Scanner scanner = new Scanner(System.in);
        boolean isUserInputValid = false;

        while(!isUserInputValid)
        try {
            System.out.print("\nPlease enter a dollar amount.\n$");
            BigDecimal userInput = new BigDecimal(scanner.next());
            moneyProvided = moneyProvided.add(userInput);
            isUserInputValid = true;
        } catch (NumberFormatException e) {
            System.out.println("Number not valid.\n");
        }
    }

    public void selectProduct() {
        Scanner scanner = new Scanner(System.in);
        Map<String, Item> inventoryMap = VendingMachineInventory.INSTANCE.getInventoryMap();

        System.out.println(VendingMachineInventory.INSTANCE + "\nSelect Item to Purchase");

        String userIdInput = scanner.nextLine();
        Item selectedItem = inventoryMap.get(userIdInput);

        if (inventoryMap.containsKey(userIdInput)) {
            if (selectedItem.getStock() > 0) {
                selectedItem.decrementStock();
                moneyProvided = moneyProvided.subtract(selectedItem.getPrice());
                System.out.println("\n" + selectedItem.getTypeMessage() + "\nDispensed " + selectedItem.getName() +
                        " ($" + selectedItem.getPrice() + ")\nRemaining Balance: " + moneyProvided);
            } else {
                System.out.println("\nItem out of stock");
            }
        } else {
            System.out.println("\nInvalid Slot ID");
        }
    }

}
