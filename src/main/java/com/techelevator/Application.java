package com.techelevator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Application {

	public static void main(String[] args) {

		//machine constructor

		File filePath = new File("vendingmachine.csv");
		Machine vendingMachine = new Machine();
		vendingMachine.run(filePath);



	}
}
