package com.mycompany.prog5121a1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for MessageStore - Part 3 of the Chat App PoE.
 *
 * @author nazih2026
 */
public class MessageStoreTest {

    private MessageStore store;

    @BeforeEach
    public void setUp() {
        store = new MessageStore();
        store.populateTestData();
    }

    // ---------------- Test 1: Sent Messages array ----------------

    @Test
    public void testSentMessagesArrayCorrectlyPopulated() {
        // Message 1 and Message 4 have Flag SENT
        String expected = "Did you get the cake?";
        assertEquals(expected, store.getSentMessages().get(0).getMessageText());

        String expected2 = "It is dinner time !";
        assertEquals(expected2, store.getSentMessages().get(1).getMessageText());

        // Sanity: exactly 2 sent messages
        assertEquals(2, store.getSentMessages().size());
    }

    // ---------------- Test 2: Longest message ----------------

    @Test
    public void testLongestMessage() {
        String expected = "Where are you? You are late! I have asked you to be on time.";
        assertEquals(expected, store.getLongestStoredMessage());
    }

    // ---------------- Test 3: Search by message ID ----------------

    @Test
    public void testSearchByMessageID() {
        assertEquals("It is dinner time !", store.searchByMessageID("0838884567"));
    }

    @Test
    public void testSearchByMessageIDNotFound() {
        assertEquals("Message ID not found.", store.searchByMessageID("9999999999"));
    }

    // ---------------- Test 4: Search by recipient ----------------

    @Test
    public void testSearchByRecipient() {
        String expected = "Where are you? You are late! I have asked you to be on time.\n"
                + "Ok, I am leaving without you.";
        assertEquals(expected, store.searchByRecipient("+27838884567"));
    }

    // ---------------- Test 5: Delete by hash ----------------

    @Test
    public void testDeleteByHash() {
        // Get the hash of the second stored message first
        Message msg2 = store.getStoredMessages().get(0);
        String hash = msg2.getMessageHash();
        String text = msg2.getMessageText();

        String expected = "Message: \"" + text + "\" successfully deleted.";
        assertEquals(expected, store.deleteByHash(hash));

        // It should no longer be found
        assertEquals("Message ID not found.", store.searchByMessageID(msg2.getMessageID()));
    }

    // ---------------- Test 6: Display report ----------------

    @Test
    public void testGenerateReport() {
        String report = store.generateReport();

        // Report must contain hash, recipient, and message for every sent message
        assertTrue(report.contains("Message Hash:"));
        assertTrue(report.contains("Recipient:"));
        assertTrue(report.contains("Message:"));
        assertTrue(report.contains("+27834557896"));
        assertTrue(report.contains("Did you get the cake?"));
        assertTrue(report.contains("0838884567"));
        assertTrue(report.contains("It is dinner time !"));
    }

    // ---------------- Extra: five arrays populated ----------------

    @Test
    public void testFiveArraysPopulated() {
        assertEquals(2, store.getSentMessages().size());          // messages 1 & 4
        assertEquals(1, store.getDisregardedMessages().size());   // message 3
        assertEquals(2, store.getStoredMessages().size());        // messages 2 & 5
        assertEquals(5, store.getMessageHashes().size());         // all 5
        assertEquals(5, store.getMessageIDs().size());            // all 5
    }

    // ---------------- Extra: sender/recipient listing ----------------

    @Test
    public void testSenderAndRecipientList() {
        String list = store.getSenderAndRecipientList();
        assertTrue(list.contains("+27838884567"));
        assertTrue(list.contains("Recipient"));
    }
}