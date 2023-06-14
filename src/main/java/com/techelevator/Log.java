package com.techelevator;

import javax.swing.*;
import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static com.techelevator.VendingMachineInventory.INSTANCE;

public class Log {

    private static final String STOCK_FILE_PATH = "Log.txt";
    private File stockFile = new File(STOCK_FILE_PATH);
    private LocalDate currentDate;
    private LocalTime currentTime;
    private String transactionType;
    private BigDecimal transactionAmount;
    private BigDecimal currentBalance;
    private static Map<String, Integer> salesReport = new HashMap<>();
    private static String salesFileDateTimePrefix = LocalDate.now().format(DateTimeFormatter.ofPattern("MM-dd-yyyy")) + " " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH-mm-ss")) + " ";
    private static final String SALES_REPORT_FILE_PATH = salesFileDateTimePrefix + "Sales_Report.txt";

    private static File SALES_REPORT_FILE = new File(SALES_REPORT_FILE_PATH);

    // Constructor
    public Log(String transactionType, BigDecimal transactionAmount, BigDecimal currentBalance) {
        this.currentDate = LocalDate.now();
        this.currentTime = LocalTime.now();
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
        this.currentBalance = currentBalance;
    }

    public Log(){}


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
        if(validateFile(stockFile)) {
            validateFile(stockFile);
            String loggedString = LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) + " " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + " " + transactionType + " $"
                    + transactionAmount + " $" + currentBalance;

            try (PrintWriter printWriter = new PrintWriter(new FileOutputStream(stockFile, true))){
                printWriter.println(loggedString);
                return true;
            }
        }
        return false;
    }

    private boolean validateFile(File file) {
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

    public static String addSaleToReport(String itemName) {
        salesReport.put(itemName, salesReport.get(itemName) + 1);
        return itemName;
    }

    public static boolean populateSalesReport(boolean isInitializing) throws IOException {
        Log salesLog = new Log();
        salesLog.validateFile(SALES_REPORT_FILE);
        try(PrintWriter printWriter = new PrintWriter(SALES_REPORT_FILE);) {

            for(Item item : INSTANCE.getInventoryMap().values()){
                if(isInitializing) {
                    salesReport.put(item.getName(), 0);
                }
                String salesReportLine = item.getName() + "|" + salesReport.get(item.getName());
                printWriter.println(salesReportLine);
            }

            BigDecimal salesReportTotal = calculateSalesReportTotal(salesReport, INSTANCE.getInventoryMap());
            printWriter.println("\n\n**TOTAL SALES** $" + salesReportTotal);

        } catch(IOException ioException) {
            System.out.println("IO Error");
        }
        return (salesReport.get("Potato Crisps") >= 0);
    }

    private static BigDecimal calculateSalesReportTotal(Map<String, Integer> salesReport, Map<String, Item> inventoryMap) {
        BigDecimal total = new BigDecimal("0.00");

        for(Map.Entry<String, Item> entry : inventoryMap.entrySet()) {
            Item item = entry.getValue();
            String itemName = item.getName();
            BigDecimal itemPrice = item.getPrice();

            if(salesReport.containsKey(itemName)) {
                int quantity = salesReport.get(itemName);
                BigDecimal price = new BigDecimal(String.valueOf(itemPrice));

                BigDecimal amountToAdd = new BigDecimal(String.valueOf(price.multiply(BigDecimal.valueOf(quantity))));
                total = total.add(amountToAdd);
            }
        }
        return total;
    }

}
