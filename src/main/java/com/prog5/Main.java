package com.prog5;

import com.prog5.menu.engine.USSDEngine;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        USSDEngine engine = new USSDEngine();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            engine.displayCurrentMenu();
            System.out.print("Votre choix : ");
            String input = scanner.nextLine().trim();
            engine.processInput(input);
        }
    }
}
