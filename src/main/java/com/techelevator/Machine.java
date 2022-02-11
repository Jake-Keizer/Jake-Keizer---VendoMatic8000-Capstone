package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Machine {
    //balance (starts at 0) (for each user session)
    private double balance = 0;


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
                    //this is if they feed money
                    //launch some feed money ui
                    //update balance or do nothing
                    //show purchase menu again if they exit this feed money menu
                }

                while (purchaseMenuSelection.equals("2")) {

                    if (balance == 0) {
                        System.out.println("You have a balance of $0. Please feed more money.");
                        break;
                    }


                    ui.displayMachineItems(stock);
                    System.out.println("Enter item code");
                    String itemCode = userInput.nextLine();
                    boolean productSelection = ui.selectProductDisplay(stock, balance, itemCode);
                    if (productSelection) {
                        stock.getStockMap().get(itemCode).decreaseCount();
                        balance -= stock.getStockMap().get(itemCode).getPrice();
                    }

                    purchaseMenuSelection = ui.purchaseMenu(balance);
                    if (purchaseMenuSelection != "2") {
                        break;
                    }

                }

                if (purchaseMenuSelection.equals("3")) {
                    ui.finishTransactionDisplay(quarters, dimes, nickels);
                    mainMenuSelection = ui.mainMenu();
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
