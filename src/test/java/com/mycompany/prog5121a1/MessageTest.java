package com.mycompany.prog5121a1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Message class.
 *
 * @author nazih2026
 */
public class MessageTest {

    // ---------------- Message length tests ----------------

    @Test
    public void testCheckMessageLength_Success() {
        Message msg = new Message("+27718693002",
                "Hi Mike, can you join us for dinner tonight?", 0);
        assertEquals("Message ready to send.", msg.checkMessageLength());
    }

    @Test
    public void testCheckMessageLength_Failure() {
        // Build a 260-character message (10 over the limit)
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 260; i++) {
            sb.append("a");
        }
        Message msg = new Message("+27718693002", sb.toString(), 0);
        assertEquals("Message exceeds 250 characters by 10; please reduce the size.",
                msg.checkMessageLength());
    }

    // ---------------- Recipient tests ----------------

    @Test
    public void testCheckRecipientCell_Valid() {
        Message msg = new Message("+27718693002", "Test", 0);
        assertEquals("Cell phone number successfully captured.",
                msg.checkRecipientCell());
    }

    @Test
    public void testCheckRecipientCell_Invalid() {
        Message msg = new Message("08575975889", "Test", 0);
        assertEquals("Cell phone number is incorrectly formatted or does not contain "
                + "an international code. Please correct the number and try again.",
                msg.checkRecipientCell());
    }

    // ---------------- Message hash tests ----------------

    @Test
    public void testCreateMessageHash_Format() {
        Message msg = new Message("+27718693002",
                "Hi Mike, can you join us for dinner tonight?", 0);
        String hash = msg.getMessageHash();

        // The hash must end with :HITONIGHT (first + last word, uppercased)
        assertTrue(hash.endsWith(":HITONIGHT"),
                "Hash should end with :HITONIGHT, but was: " + hash);

        // Format must be XX:N:FIRSTLAST
        assertTrue(hash.matches("^\\d{2}:\\d+:HITONIGHT$"),
                "Hash format invalid: " + hash);
    }

    @Test
    public void testCreateMessageHash_Loop() {
        // Test multiple messages in a loop
        String[] messages = {
            "Hi Mike, can you join us for dinner tonight?",
            "Hello there, see you later",
            "Good morning team"
        };
        String[] expectedEnds = {":HITONIGHT", ":HELLOLATER", ":GOODMORNING"};

        for (int i = 0; i < messages.length; i++) {
            Message msg = new Message("+27718693002", messages[i], i);
            String hash = msg.getMessageHash();
            assertTrue(hash.endsWith(expectedEnds[i]),
                    "Hash " + i + " expected to end with " + expectedEnds[i]
                    + " but was: " + hash);
        }
    }

    // ---------------- Message ID tests ----------------

    @Test
    public void testCheckMessageID_Generated() {
        Message msg = new Message("+27718693002", "Test", 0);
        assertTrue(msg.checkMessageID(),
                "Message ID must not be more than 10 characters");
        assertNotNull(msg.getMessageID());
        System.out.println("Message ID generated: " + msg.getMessageID());
    }

    // ---------------- SentMessage action tests ----------------

    @Test
    public void testSentMessage_Send() {
        Message msg = new Message("+27718693002", "Test message", 0);
        assertEquals("Message successfully sent.", msg.SentMessage(1));
    }

    @Test
    public void testSentMessage_Disregard() {
        Message msg = new Message("+27718693002", "Test message", 0);
        assertEquals("Press 0 to delete the message.", msg.SentMessage(2));
    }

    @Test
    public void testSentMessage_Store() {
        Message msg = new Message("+27718693002", "Test message", 0);
        assertEquals("Message successfully stored.", msg.SentMessage(3));
    }

    // ---------------- Total counter test ----------------

    @Test
    public void testReturnTotalMessages() {
        Message msg = new Message("+27718693002", "Test", 0);
        int before = msg.returnTotalMessages();
        msg.SentMessage(1);
        int after = msg.returnTotalMessages();
        assertEquals(before + 1, after,
                "Total messages sent should increase by 1 after sending");
    }
}