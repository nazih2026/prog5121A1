package com.mycompany.prog5121a1;

import java.util.Scanner;

/**
 * Console entry point for the Chat App.
 *
 * @author nazih2026
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login login = new Login();

        System.out.println("=== REGISTRATION ===");

        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

        login.setFirstName(firstName);
        login.setLastName(lastName);

        System.out.print("Enter username (must contain _ and be <= 5 chars): ");
        String username = scanner.nextLine();

        System.out.print("Enter password (>=8 chars, capital, number, special): ");
        String password = scanner.nextLine();

        System.out.print("Enter SA cell phone (e.g. +27838968976): ");
        String cellPhone = scanner.nextLine();

        String result = login.registerUser(username, password, cellPhone);
        System.out.println(result);

        if (result.contains("successfully")) {
            System.out.println("\n=== LOGIN ===");

            System.out.print("Enter username: ");
            String loginUser = scanner.nextLine();

            System.out.print("Enter password: ");
            String loginPass = scanner.nextLine();

            boolean success = login.loginUser(loginUser, loginPass);
            System.out.println(login.returnLoginStatus(success));
        }

        scanner.close();
    }
}
