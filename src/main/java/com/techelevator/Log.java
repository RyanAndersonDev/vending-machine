package com.techelevator;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public class Log {

    private static final String FILE_PATH = "Log.txt";
    private File file = new File(FILE_PATH);
    private LocalDate currentDate;
    private LocalTime currentTime;
    private String transactionType;
    private BigDecimal transactionAmount;
    private BigDecimal currentBalance;

    // Constructor
    public Log(String transactionType, BigDecimal transactionAmount, BigDecimal currentBalance) {
        this.currentDate = LocalDate.now();
        this.currentTime = LocalTime.now();
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
        this.currentBalance = currentBalance;
    }


    // Getters
    public LocalDate getCurrentDate() {
        return currentDate;
    }
    public LocalTime getCurrentTime() {
        return currentTime;
    }


    // Setters
    public void setTransactionType(String type) {
        this.transactionType = type;
    }
    public void setTransactionAmount(BigDecimal amount) {
        this.transactionAmount = amount;
    }


    // Methods
    public boolean writeLog() throws FileNotFoundException {
        if(validateFile()) {
            validateFile();
            String loggedString = currentDate.toString() + " " + currentTime.toString() + " " + transactionType + " $"
                    + transactionAmount + " $" + currentBalance;

            try (PrintWriter printWriter = new PrintWriter(new FileOutputStream(file, true))){
                printWriter.println(loggedString);
                return true;
            }
        }
        return false;
    }

    private boolean validateFile() {
        boolean fileExists = file.exists();

        try {
            if(!fileExists) {
                fileExists = file.createNewFile();
            }
        } catch(IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
        return fileExists;
    }

}
