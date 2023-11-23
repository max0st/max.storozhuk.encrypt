package com.javarush;

import java.util.Scanner;

public class Runner {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        if (args.length > 0) {
            CLI.runFromArgs(args);
        } else {
            System.out.println("Select the operating mode:");
            System.out.println("1 - CLI");
            System.out.println("2 - GUI");
            System.out.print("Enter the number of the selected mode: ");
            String choice = scanner.nextLine();
            if (choice.equals("1")) {
                CLI.runFromCLI();
            } else if (choice.equals("2")) {
                GUI.launchGUI();
            } else {
                System.out.println("Incorrect command");
            }

        }
    }
}
