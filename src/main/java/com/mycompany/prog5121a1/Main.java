package com.mycompany.prog5121a1;

import java.util.Scanner;

/*
 * Main class - console entry point for the Chat App PoE.
 * Part 1: Registration and Login.
 * Part 2: QuickChat messaging menu.
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login login = new Login();

        // ---- PART 1: REGISTRATION ----
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

        String regResult = login.registerUser(username, password, cellPhone);
        System.out.println(regResult);

        if (!regResult.contains("successfully")) {
            System.out.println("Registration failed. Exiting.");
            scanner.close();
            return;
        }

        // ---- PART 1: LOGIN ----
        System.out.println("\n=== LOGIN ===");
        System.out.print("Enter username: ");
        String loginUser = scanner.nextLine();
        System.out.print("Enter password: ");
        String loginPass = scanner.nextLine();

        boolean success = login.loginUser(loginUser, loginPass);
        System.out.println(login.returnLoginStatus(success));

        if (!success) {
            System.out.println("Login failed. Exiting.");
            scanner.close();
            return;
        }

        // ---- PART 2: QUICKCHAT ----
        System.out.println("\nWelcome to QuickChat.");

        System.out.print("How many messages do you wish to send? ");
        int numMessages = Integer.parseInt(scanner.nextLine());

        int messagesEntered = 0;
        boolean running = true;

        while (running) {
            System.out.println("\n--- QuickChat Menu ---");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.print("Choose an option: ");
            int menuChoice = Integer.parseInt(scanner.nextLine());

            switch (menuChoice) {
                case 1:
                    if (messagesEntered >= numMessages) {
                        System.out.println("You have reached your message limit (" + numMessages + ").");
                        break;
                    }
                    while (messagesEntered < numMessages) {
                        System.out.print("Enter recipient cell (+27...): ");
                        String recipient = scanner.nextLine();

                        System.out.print("Enter message (max 250 chars): ");
                        String messageText = scanner.nextLine();

                        Message msg = new Message(recipient, messageText, messagesEntered);

                        // Validate length
                        String lengthCheck = msg.checkMessageLength();
                        System.out.println(lengthCheck);
                        if (!lengthCheck.equals("Message ready to send.")) {
                            System.out.println("Message rejected. Try again.");
                            continue;
                        }

                        // Validate recipient
                        String recipientCheck = msg.checkRecipientCell();
                        System.out.println(recipientCheck);
                        if (!recipientCheck.equals("Cell phone number successfully captured.")) {
                            System.out.println("Message rejected. Try again.");
                            continue;
                        }

                        // Display message details
                        System.out.println("\n--- Message Details ---");
                        System.out.println("Message ID: " + msg.getMessageID());
                        System.out.println("Message Hash: " + msg.getMessageHash());
                        System.out.println("Recipient: " + msg.getRecipient());
                        System.out.println("Message: " + msg.getMessageText());

                        // Send/Store/Disregard
                        System.out.println("\nChoose action:");
                        System.out.println("1) Send Message");
                        System.out.println("2) Disregard Message");
                        System.out.println("3) Store Message to send later");
                        System.out.print("Choice: ");
                        int action = Integer.parseInt(scanner.nextLine());

                        String actionResult = msg.SentMessage(action);
                        System.out.println(actionResult);

                        messagesEntered++;
                    }
                    System.out.println("All messages processed.");
                    break;

                case 2:
                    System.out.println("Coming Soon.");
                    break;

                case 3:
                    running = false;
                    System.out.println("Goodbye.");
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }

        // Final totals
        Message finalMsg = new Message("+27000000000", "dummy", 0);
        System.out.println("\nTotal messages sent: " + finalMsg.returnTotalMessages());
        System.out.println(finalMsg.printMessages());

        scanner.close();
    }
}