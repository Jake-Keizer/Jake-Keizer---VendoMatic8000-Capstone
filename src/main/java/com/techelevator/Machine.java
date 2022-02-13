package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;

public class Machine {
    private BigDecimal balance = new BigDecimal("0.00");
    private Object LocalDateTime;

    //tracks the item, and a Big Decimal array of the item's total items sold and individual price
    //for instance, "Wonka Bar" : {40, 0.95}
    private static Map<String, BigDecimal[]> sales = new HashMap<>();


    public void run(File inputFile) {
        Stock stock = readFile(inputFile);

        if (stock.getStockMap().isEmpty()) {
            System.out.println("Stock failed to load.");
            System.exit(1);
        }

        initializeSalesMap(stock);

        File mostRecentSalesReport = getLastModified(".");



        UserInterface ui = new UserInterface();

        String mainMenuSelection;

        mainMenuSelection = ui.mainMenu();

        Scanner userInput = new Scanner(System.in);

        boolean running = true;

        String fileName = "log.txt";

        String activity;

        BigDecimal oldBalance;

        String itemName;

        while (running) {

            //-----------------------------------------------------------------
            //This is for displaying items
            while (mainMenuSelection.equals("1")) {
                ui.displayMachineItems(stock);
                System.out.println("\n");
                mainMenuSelection = ui.mainMenu();
                System.out.println("\n");
            }




            //----------------------------------------------------------------
            //This is for the purchase menu
            while (mainMenuSelection.equals("2")) {
                String purchaseMenuSelection;
                purchaseMenuSelection = ui.purchaseMenu(balance);
                int quarters = 0;
                int dimes = 0;
                int nickels = 0;

                while (purchaseMenuSelection.equals("1")) {
                    System.out.println("Please feed money in whole dollar amounts of $1, $2, $5, or $10.");
                    System.out.print(">>> ");
                    String moneyFed = userInput.nextLine();
                    System.out.println("\n");
                    int money;
                    try {
                        money = Integer.parseInt(moneyFed);

                    } catch (NumberFormatException e) {
                        System.out.println("\n\n\nInvalid denomination.");
                        System.out.println("Please try again.");
                        continue;
                    }

                   if (money == 1 || money == 2 || money == 5 || money == 10){
                       activity = "FEED MONEY";
                       oldBalance = balance;
                       balance = balance.add(new BigDecimal(moneyFed));
                       printToLog(fileName, activity, oldBalance, balance);
                       break;
                   }
                    System.out.println("\n\n\nInvalid input");
                    System.out.println("Please try again.");
                }

                while (purchaseMenuSelection.equals("2")) {
                    if (balance.compareTo(new BigDecimal("0")) == 0) {
                        System.out.println("You have a balance of $0. Please feed more money.");
                        break;
                    }
                    ui.displayMachineItems(stock);
                    System.out.println("Enter item code");
                    System.out.print(">>> ");
                    String itemCode = userInput.nextLine();
                    boolean productSelection = ui.selectProductDisplay(stock, itemCode);
                    if (productSelection) {
                        BigDecimal itemPrice = BigDecimal.valueOf(stock.getStockMap().get(itemCode).getPrice());
                        if (balance.compareTo(itemPrice) < 0) {
                            System.out.println("Balance is insufficient. Please feed more money.");
                            break;
                        }
                        System.out.println(stock.getStockMap().get(itemCode).printMessage());
                        stock.getStockMap().get(itemCode).decreaseCount();
                        itemName = stock.getStockMap().get(itemCode).getName();

                        //PRINT TO LOG
                        oldBalance = balance;
                        activity =  stock.getStockMap().get(itemCode).getName() + " " + itemCode;
                        balance = balance.subtract(itemPrice);
                        printToLog(fileName,activity, oldBalance, balance);
                    }
                    break;
                }

                if (purchaseMenuSelection.equals("3")) {
                    quarters = (balance.divide(new BigDecimal("0.25"), 2, RoundingMode.UP)).intValue();
                    BigDecimal quarterRemainder = balance.remainder(new BigDecimal("0.25"));
                    dimes = (quarterRemainder.divide(new BigDecimal("0.10"), 2, RoundingMode.UP)).intValue();                 //(int)(quarterRemainder / .10);
                    BigDecimal dimeremainder = quarterRemainder.remainder(new BigDecimal("0.10"));                                      //quarterRemainder % .10;
                    nickels = (dimeremainder.divide(new BigDecimal("0.05"), 2, RoundingMode.UP)).intValue();                  //(int)(dimeremainder / .05);
                    oldBalance = balance;
                    activity = "GIVE CHANGE";
                    balance = new BigDecimal("0.00");
                    printToLog(fileName, activity, oldBalance, balance);
                    ui.finishTransactionDisplay(quarters, dimes, nickels);
                    mainMenuSelection = ui.mainMenu();
                }


            }





            //--------------------------------------------------------------------
            //This is for exiting the program
            if (mainMenuSelection.equals("3")) {
                System.out.println("Thank you for your patronage!");
                System.exit(0);
            }



            //----------------------------------------------------------------------
            //Hidden sales report menu:

            if (mainMenuSelection.equals("4")) {
                File logFile = new File(fileName);
                updateSalesMap(logFile, sales);

                Date current = new Date();
                String currentDate = current.toString();
                String currentDateModified = currentDate.replaceAll(":", ".");
                String newSalesReport = "Sales_Report_" + currentDateModified + ".txt";
                File salesReport = new File(newSalesReport);
                try(PrintWriter salesWriter = new PrintWriter(salesReport)) {
                    BigDecimal totalSales = new BigDecimal("0");
                    for (Map.Entry<String, BigDecimal[]> item : sales.entrySet()) {
                        salesWriter.println(item.getKey() + "|" + item.getValue()[0]);
                        totalSales = totalSales.add(item.getValue()[0].multiply(item.getValue()[1]));
                    }
                    salesWriter.println("\nTOTAL SALES: $" + totalSales);
                    System.out.println("Sales Report Generated!\n");
                    mainMenuSelection = ui.mainMenu();
                } catch (FileNotFoundException e) {
                    System.out.println("Sales Report could not be generated!");
                    mainMenuSelection = ui.mainMenu();
                }
            }
            //-----------------------------------------------------------------------


        }
    }




    public boolean printToLog( String fileName, String activity, BigDecimal oldBalance, BigDecimal newBalance){
        File logFile = new File(fileName);
        try(PrintWriter logWriter = new PrintWriter(
                new FileOutputStream(logFile, true))){
            logWriter.println(new Date() + " " + activity + ": $" + oldBalance.toString() + " $" + newBalance.toString());
            return true;
        }
        catch (Exception e){
            System.out.println("Could not print to log.");
            return false;
        }
    }



    //read CSV file and create Stock
    public Stock readFile(File inputFile) {
        Stock stock = new Stock();
        try (Scanner fileReader = new Scanner(inputFile)){
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                String[] itemArray = line.split("\\|");
                double price = Double.parseDouble(itemArray[2]);
                stock.restockMap(itemArray[0], itemArray[1], price, itemArray[3]);
            }
            return stock;
        } catch (FileNotFoundException e) {
            System.out.println("Invalid file!");
            return stock;
        }
    }

    public void initializeSalesMap(Stock stock) {

        for (Map.Entry<String, Item> slot : stock.getStockMap().entrySet()) {
            if (!sales.containsKey(slot.getValue().getName())) {
                BigDecimal zero = new BigDecimal("0");
                BigDecimal price = BigDecimal.valueOf(slot.getValue().getPrice());

                sales.put(slot.getValue().getName(), new BigDecimal[]{zero, price});
            }
        }
    }

    public static File getLastModified(String directoryFilePath)
    {
        File directory = new File(directoryFilePath);
        File[] files = directory.listFiles(File::isFile);
        long lastModifiedTime = Long.MIN_VALUE;
        File chosenFile = null;

        if (files != null)
        {
            for (File file : files)
            {
                if (file.lastModified() > lastModifiedTime && file.getAbsolutePath().contains("Sales_Report_"))
                {
                    chosenFile = file;
                    lastModifiedTime = file.lastModified();
                }
            }
        }

        return chosenFile;
    }


    public void updateSalesMap(File logFile, Map<String, BigDecimal[]> sales) {
        try (Scanner dataInput = new Scanner(logFile)) {
            while(dataInput.hasNextLine()) {
                String line = dataInput.nextLine();
                String[] lineArray = line.split(" ");
                if (!line.contains("GIVE CHANGE") && !line.contains("FEED MONEY")) {
                    for (Map.Entry<String, BigDecimal[]> item : sales.entrySet()) {
                        if (line.contains(item.getKey())) {
                            BigDecimal updatedCount = item.getValue()[0].add(new BigDecimal("1"));
                            BigDecimal price = item.getValue()[1];
                            sales.put(item.getKey(), new BigDecimal[]{updatedCount, price});
                        }
                    }
                }

            }
        } catch (FileNotFoundException e) {
            System.out.println("Sales Report file does not exist.");
        }
    }









    }




