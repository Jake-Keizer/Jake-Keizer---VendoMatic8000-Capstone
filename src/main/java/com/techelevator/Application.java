package com.techelevator;

import java.io.File;

public class Application {

	public static void main(String[] args) {

		//machine constructor
		File filePath = new File();
		Machine vendingMachine = new Machine(filePath);
		vendingMachine.run();

	}
}
