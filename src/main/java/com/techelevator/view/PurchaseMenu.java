package com.techelevator.view;

import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
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

}
