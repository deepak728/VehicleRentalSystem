package com.carrental;

import com.carrental.driver.Driver;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            String filePath = args[0];
            FileInputStream fis = new FileInputStream(filePath);
            Scanner scanner = new Scanner(fis);
            Driver driver = new Driver();

            while (scanner.hasNextLine()) {
                driver.StartProgram(scanner.nextLine());
            }

            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
