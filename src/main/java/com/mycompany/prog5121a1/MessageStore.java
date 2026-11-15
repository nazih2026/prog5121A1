package com.mycompany.prog5121a1;

import java.util.ArrayList;

/**
 * MessageStore class - Part 3 of the Chat App PoE.
 * Holds the five required arrays and provides all query/delete/report
 * operations for stored messages.
 *
 * @author nazih2026
 */
public class MessageStore {

    // ---- The five required arrays ----
    private ArrayList<Message> sentMessages = new ArrayList<>();
    private ArrayList<Message> disregardedMessages = new ArrayList<>();
    private ArrayList<Message> storedMessages = new ArrayList<>();
    private ArrayList<String> messageHashes = new ArrayList<>();
    private ArrayList<String> messageIDs = new ArrayList<>();

    // ---- Adding ----

    /**
     * Adds a message to the correct array(s) based on its Flag.
     * Also records its hash and ID in the parallel arrays.
     */
    public void addMessage(Message m) {
        if (m == null) return;

        // Record hash + ID for every message, regardless of flag
        if (m.getMessageHash() != null && !m.getMessageHash().isEmpty()) {
            messageHashes.add(m.getMessageHash());
        }
        if (m.getMessageID() != null && !m.getMessageID().isEmpty()) {
            messageIDs.add(m.getMessageID());
        }

        // File into the correct flag array
        if (m.getFlag() == Message.Flag.SENT) {
            sentMessages.add(m);
        } else if (m.getFlag() == Message.Flag.DISREGARDED) {
            disregardedMessages.add(m);
        } else if (m.getFlag() == Message.Flag.STORED) {
            storedMessages.add(m);
        }
    }

    /** Loads messages.json into the storedMessages array. */
    public void loadStoredFromJSON(String path) {
        ArrayList<Message> loaded = Message.loadStoredMessages(path);
        for (Message m : loaded) {
            m.setFlag(Message.Flag.STORED);
            if (!containsID(m.getMessageID())) {
                storedMessages.add(m);
                if (m.getMessageHash() != null && !m.getMessageHash().isEmpty()) {
                    messageHashes.add(m.getMessageHash());
                }
                if (m.getMessageID() != null) {
                    messageIDs.add(m.getMessageID());
                }
            }
        }
    }

    private boolean containsID(String id) {
        for (Message m : storedMessages) {
            if (m.getMessageID() != null && m.getMessageID().equals(id)) {
                return true;
            }
        }
        return false;
    }

    // ---- Part 3.2a ----

    /** Returns "Sender -> Recipient" lines for all stored messages. */
    public String getSenderAndRecipientList() {
        if (storedMessages.isEmpty()) {
            return "No stored messages.";
        }
        StringBuilder sb = new StringBuilder();
        for (Message m : storedMessages) {
            // Sender is not tracked in this PoE; use the logged-in user placeholder
            sb.append("Sender: (current user) -> Recipient: ")
              .append(m.getRecipient())
              .append("\n");
        }
        return sb.toString();
    }

    // ---- Part 3.2b ----

    /** Returns the text of the longest stored message. */
    public String getLongestStoredMessage() {
        if (storedMessages.isEmpty()) {
            return "No stored messages.";
        }
        Message longest = storedMessages.get(0);
        for (Message m : storedMessages) {
            if (m.getMessageText() != null
                    && m.getMessageText().length() > longest.getMessageText().length()) {
                longest = m;
            }
        }
        return longest.getMessageText();
    }

    // ---- Part 3.2c ----

    /** Search a message ID across ALL arrays. Returns message text or a not-found string. */
    public String searchByMessageID(String id) {
        if (id == null) return "Message ID not found.";
        for (Message m : sentMessages) {
            if (id.equals(m.getMessageID())) return m.getMessageText();
        }
        for (Message m : storedMessages) {
            if (id.equals(m.getMessageID())) return m.getMessageText();
        }
        for (Message m : disregardedMessages) {
            if (id.equals(m.getMessageID())) return m.getMessageText();
        }
        return "Message ID not found.";
    }

    // ---- Part 3.2d ----

    /**
     * Search all messages (sent OR stored) for a recipient.
     * Returns each matching message text on its own line.
     */
    public String searchByRecipient(String recipient) {
        if (recipient == null) return "No messages found for that recipient.";
        StringBuilder sb = new StringBuilder();
        for (Message m : sentMessages) {
            if (recipient.equals(m.getRecipient())) {
                sb.append(m.getMessageText()).append("\n");
            }
        }
        for (Message m : storedMessages) {
            if (recipient.equals(m.getRecipient())) {
                sb.append(m.getMessageText()).append("\n");
            }
        }
        if (sb.length() == 0) {
            return "No messages found for that recipient.";
        }
        return sb.toString().trim();
    }

    // ---- Part 3.2e ----

    /** Deletes a message by hash. Returns success message or not-found message. */
    public String deleteByHash(String hash) {
        if (hash == null) return "Message hash not found.";

        // Search all three flag arrays
        Message found = removeByHash(sentMessages, hash);
        if (found == null) found = removeByHash(storedMessages, hash);
        if (found == null) found = removeByHash(disregardedMessages, hash);

        if (found == null) {
            return "Message hash not found.";
        }

        // Also remove from the parallel arrays
        messageHashes.remove(found.getMessageHash());
        messageIDs.remove(found.getMessageID());

        return "Message: \"" + found.getMessageText() + "\" successfully deleted.";
    }

    private Message removeByHash(ArrayList<Message> list, String hash) {
        for (int i = 0; i < list.size(); i++) {
            if (hash.equals(list.get(i).getMessageHash())) {
                return list.remove(i);
            }
        }
        return null;
    }

    // ---- Part 3.2f ----

    /** Returns a report of all sent messages with Hash, Recipient, Message. */
    public String generateReport() {
        if (sentMessages.isEmpty()) {
            return "No sent messages to report.";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("===== SENT MESSAGES REPORT =====\n");
        for (Message m : sentMessages) {
            sb.append("Message Hash: ").append(m.getMessageHash()).append("\n");
            sb.append("Recipient:    ").append(m.getRecipient()).append("\n");
            sb.append("Message:      ").append(m.getMessageText()).append("\n");
            sb.append("--------------------------------\n");
        }
        return sb.toString();
    }

    // ---- Deterministic test data (used only by unit tests) ----

    /**
     * Populates all five arrays with the Part 3 marked test data.
     * Fixed IDs are used ONLY here so unit tests are deterministic.
     * The main app continues to generate random IDs.
     */
    public void populateTestData() {
        sentMessages.clear();
        disregardedMessages.clear();
        storedMessages.clear();
        messageHashes.clear();
        messageIDs.clear();

        // Test Message 1 - Sent
        Message m1 = new Message("+27834557896", "Did you get the cake?", 1, "1000000001");
        m1.setFlag(Message.Flag.SENT);
        addMessage(m1);

        // Test Message 2 - Stored
        Message m2 = new Message("+27838884567",
                "Where are you? You are late! I have asked you to be on time.",
                2, "2000000002");
        m2.setFlag(Message.Flag.STORED);
        addMessage(m2);

        // Test Message 3 - Disregarded
        Message m3 = new Message("+27834484567", "Yohoooo, I am at your gate.", 3, "3000000003");
        m3.setFlag(Message.Flag.DISREGARDED);
        addMessage(m3);

        // Test Message 4 - Sent, ID forced to 0838884567 per brief
        Message m4 = new Message("0838884567", "It is dinner time !", 4, "0838884567");
        m4.setFlag(Message.Flag.SENT);
        addMessage(m4);

        // Test Message 5 - Stored
        Message m5 = new Message("+27838884567", "Ok, I am leaving without you.", 5, "5000000005");
        m5.setFlag(Message.Flag.STORED);
        addMessage(m5);
    }

    // ---- Getters for the arrays ----

    public ArrayList<Message> getSentMessages() { return sentMessages; }
    public ArrayList<Message> getDisregardedMessages() { return disregardedMessages; }
    public ArrayList<Message> getStoredMessages() { return storedMessages; }
    public ArrayList<String> getMessageHashes() { return messageHashes; }
    public ArrayList<String> getMessageIDs() { return messageIDs; }
}
