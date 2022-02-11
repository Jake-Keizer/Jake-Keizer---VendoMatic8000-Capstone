package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;

public class Machine {
    //balance (starts at 0) (for each user session)
    //private double balance = 0;
    private BigDecimal balance = new BigDecimal("0.00");
    private Object LocalDateTime;


    public void run(File inputFile) {
        Stock stock = readFile(inputFile);

        if (stock.getStockMap().isEmpty()) {
            System.out.println("Stock failed to load.");
            System.exit(1);
        }

        UserInterface ui = new UserInterface();

        String mainMenuSelection;

        mainMenuSelection = ui.mainMenu();

        Scanner userInput = new Scanner(System.in);

        boolean running = true;

        String activity;

        BigDecimal oldBalance;

        while (running) {

            //-----------------------------------------------------------------
            //This is for displaying items
            while (mainMenuSelection.equals("1")) {
                ui.displayMachineItems(stock);
                mainMenuSelection = ui.mainMenu();
            }
            //-----------------------------------------------------------------





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
                    int money = Integer.parseInt(moneyFed);
                   if (money == 1 || money == 2 || money == 5 || money == 10){
                       activity = "FEED MONEY";
                       oldBalance = balance;
                       balance = balance.add(new BigDecimal(moneyFed));
                       printToLog(activity, oldBalance, balance);
                       break;
                   }
                    System.out.println("Invalid input");
                   break;
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
                        //PRINT TO LOG
                        oldBalance = balance;
                        activity =  stock.getStockMap().get(itemCode).getName() + " " + itemCode;
                        balance = balance.subtract(itemPrice);
                        printToLog(activity, oldBalance, balance);
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
                    printToLog(activity, oldBalance, balance);
                    ui.finishTransactionDisplay(quarters, dimes, nickels);
                    mainMenuSelection = ui.mainMenu();
                    //USE PRINT TO LOG
                }


            }
            //-------------------------------------------------------------------





            //--------------------------------------------------------------------
            //This is for exiting the program
            if (mainMenuSelection.equals("3")) {
                System.out.println("Thank you for your patronage!");
                System.exit(0);
            }
            //---------------------------------------------------------------------


        }
    }
    public boolean printToLog(String activity, BigDecimal oldBalance, BigDecimal newBalance){
        File logFile = new File("log.txt");
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
        //private void restockMap(String location, String name, double price, String itemType)
        //"A1|Potato Crisps|3.05|Chip"
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






    //balance (starts at 0)
    //reads csv file
    //creates an instance of Stock class,
    //add each key, value to Stock map (example --> A1  :  Wonka Bar )
    //loads UI options pages

    //print to log file each time user puts money in or vends an item






}
