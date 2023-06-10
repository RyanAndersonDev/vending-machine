package com.techelevator.view;

import com.techelevator.Item;
import com.techelevator.Log;
import com.techelevator.VendingMachineInventory;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PurchaseMenu extends Menu {

    private BigDecimal moneyProvided = new BigDecimal("0");


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

        while(!isUserInputValid) {
            try {
                System.out.print("\nPlease enter a dollar amount.\n$");

                BigDecimal userInput = new BigDecimal(scanner.next());
                moneyProvided = moneyProvided.add(userInput);
                Log changeLog = new Log("FEED MONEY:", userInput, userInput);
                changeLog.writeLog();

                isUserInputValid = true;
            } catch (NumberFormatException | FileNotFoundException e) {
                System.out.println("Number not valid.\n");
            }
        }
    }

    public void selectProduct() throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        Map<String, Item> inventoryMap = VendingMachineInventory.INSTANCE.getInventoryMap();

        System.out.println(VendingMachineInventory.INSTANCE + "\nSelect Item to Purchase:");

        String userIdInput = scanner.nextLine();
        Item selectedItem = inventoryMap.get(userIdInput);

        if (inventoryMap.containsKey(userIdInput)) {
            if(moneyProvided.compareTo(selectedItem.getPrice()) >= 0) {
                if (selectedItem.getStock() > 0) {
                    selectedItem.decrementStock();
                    moneyProvided = moneyProvided.subtract(selectedItem.getPrice());
                    System.out.println("\n" + selectedItem.getTypeMessage() + "\nDispensed " + selectedItem.getName() +
                            " ($" + selectedItem.getPrice() + ")\nRemaining Balance: " + moneyProvided);

                    // log purchase
                    Log transactionLog = new Log(selectedItem.getName() + " " + selectedItem.getId(),
                            selectedItem.getPrice(), moneyProvided);
                    transactionLog.writeLog();

                } else {
                    System.out.println("\nItem out of stock.");
                }
            } else {
                System.out.println("\nInvalid funds. Please feed more money to make purchase.");
            }
        } else {
            System.out.println("\nInvalid Slot ID.");
        }
    }

    public String finalizeTransaction() throws FileNotFoundException {
        String change = getChange(moneyProvided);
        BigDecimal returnAmount = moneyProvided;
        moneyProvided = moneyProvided.subtract(moneyProvided);
        Log finalizedLog = new Log("GIVE CHANGE:", returnAmount, new BigDecimal("0.00"));
        finalizedLog.writeLog();
        return change;
    }

    private String getChange(BigDecimal moneyRemaining) {
        BigDecimal quarter = new BigDecimal("0.25");
        BigDecimal dime = new BigDecimal("0.10");
        BigDecimal nickel = new BigDecimal("0.05");
        BigDecimal penny = new BigDecimal("0.01");
        BigDecimal[] coinValues = {quarter, dime, nickel, penny};

        Map<BigDecimal, BigDecimal> changeMap = new HashMap<>();

        for(BigDecimal value : coinValues) {
            if(moneyRemaining.compareTo(value) >= 0) {

                BigDecimal coinCounter = (moneyRemaining.divide(value));
                coinCounter = coinCounter.setScale(0, RoundingMode.DOWN);

                changeMap.put(value, coinCounter);

                BigDecimal amountToSubtract = coinCounter.multiply(value);
                moneyRemaining = moneyRemaining.subtract(amountToSubtract);
            }
        }
        return "Here's your change! Quarters: " + changeMap.get(quarter) + ". Dimes: " + changeMap.get(dime)
                + ". Nickels:" + changeMap.get(nickel) + ". Pennies: " + changeMap.get(penny);
    }

}
