package com.javarush;

public class Runner {

    public static void main(String[] args) {
        if (args.length > 0) {
            CLI.runFromArgs(args);
        } else {
            CLI.runFromCLI();
        }
    }
}
