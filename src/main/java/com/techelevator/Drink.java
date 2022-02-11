package com.techelevator;

public class Drink extends Item {


    public Drink(String name, double price) {
        super(name, price);
    }

    @Override
    public String printMessage() {
        return "Glug Glug, Yum!";
    }
}
