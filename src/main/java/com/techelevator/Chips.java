package com.techelevator;

public class Chips extends Item {


    public Chips(String name, double price) {
        super(name, price);
    }

    @Override
    public String printMessage() {
        return "Crunch Crunch, Yum!";
    }
}
