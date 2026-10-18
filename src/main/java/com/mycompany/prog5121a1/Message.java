package com.mycompany.prog5121a1;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/*
 * Message class - Part 2 of the Chat App PoE.
 * Handles message creation, validation, hashing, and storage.
 */
public class Message {

    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;
    private static int totalMessagesSent = 0;
    private static ArrayList<Message> sentMessages = new ArrayList<>();

    // Constructor
    public Message(String recipient, String messageText, int messageNumber) {
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageNumber = messageNumber;
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
    }

    // Generates a random 10-digit message ID as a String
    private String generateMessageID() {
        long id = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
        return String.valueOf(id);
    }

    // Requirement: Message ID not more than 10 characters
    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }

    // Requirement: recipient no more than 10 chars AND starts with international code
    public String checkRecipientCell() {
        if (recipient == null) {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
        if (recipient.matches("^\\+27\\d{9}$") && recipient.length() <= 12) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }

    // Requirement: Hash = first 2 digits of ID : message number : FIRST + LAST word in caps
    public String createMessageHash() {
        if (messageID == null || messageID.length() < 2 || messageText == null || messageText.trim().isEmpty()) {
            return "";
        }
        String firstTwo = messageID.substring(0, 2);
        String[] words = messageText.trim().split("\\s+");
        String firstWord = words[0].replaceAll("[^A-Za-z]", "").toUpperCase();
        String lastWord = words[words.length - 1].replaceAll("[^A-Za-z]", "").toUpperCase();
        return firstTwo + ":" + messageNumber + ":" + firstWord + lastWord;
    }

    // Requirement: returns the message length validation result
    public String checkMessageLength() {
        if (messageText == null) {
            return "Message exceeds 250 characters by 250; please reduce the size.";
        }
        if (messageText.length() <= 250) {
            return "Message ready to send.";
        }
        int excess = messageText.length() - 250;
        return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
    }

    // Requirement: Send / Store / Disregard
    public String SentMessage(int choice) {
        switch (choice) {
            case 1:
                totalMessagesSent++;
                sentMessages.add(this);
                return "Message successfully sent.";
            case 2:
                return "Press 0 to delete the message.";
            case 3:
                storeMessage();
                return "Message successfully stored.";
            default:
                return "Invalid option.";
        }
    }

    // Requirement: returns all sent messages as a formatted String
    public String printMessages() {
        if (sentMessages.isEmpty()) {
            return "No messages sent yet.";
        }
        StringBuilder sb = new StringBuilder();
        for (Message m : sentMessages) {
            sb.append("Message ID: ").append(m.messageID).append("\n");
            sb.append("Message Hash: ").append(m.messageHash).append("\n");
            sb.append("Recipient: ").append(m.recipient).append("\n");
            sb.append("Message: ").append(m.messageText).append("\n\n");
        }
        return sb.toString();
    }

    // Requirement: returns total messages sent
    public int returnTotalMessages() {
        return totalMessagesSent;
    }

    // Research task: store message to JSON using Gson
    public String storeMessage() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter("messages.json", true)) {
            writer.write(gson.toJson(this));
            writer.write("\n");
            return "Message successfully stored.";
        } catch (IOException e) {
            return "Error storing message: " + e.getMessage();
        }
    }

    // Getters for testing
    public String getMessageID() { return messageID; }
    public String getMessageHash() { return messageHash; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
    public int getMessageNumber() { return messageNumber; }
}