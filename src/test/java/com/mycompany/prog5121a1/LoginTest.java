package com.mycompany.prog5121a1;
/*
 * LoginTest class - Part 1 of the Chat App PoE.
 * Contains 16 JUnit tests covering registration validation,
 * login authentication, and username/password/cell phone checks.
 */

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Login class.
 *
 * @author nazih2026
 */
public class LoginTest {

    Login login = new Login("Kyle", "Smith");

    // ---------------- assertEquals tests ----------------

    @Test
    public void testRegisterUser_UsernameCorrect() {
        String expected = "Username successfully captured.\n"
                + "Password successfully captured.\n"
                + "Cell number successfully captured.";
        String actual = login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertEquals(expected, actual);
    }

    @Test
    public void testRegisterUser_UsernameIncorrect() {
        String expected = "Username is not correctly formatted; please ensure that your username "
                + "contains an underscore and is no more than five characters in length.";
        String actual = login.registerUser("kyle!!!!!!!", "Ch&&sec@ke99!", "+27838968976");
        assertEquals(expected, actual);
    }

    @Test
    public void testRegisterUser_PasswordCorrect() {
        String expected = "Username successfully captured.\n"
                + "Password successfully captured.\n"
                + "Cell number successfully captured.";
        String actual = login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertEquals(expected, actual);
    }

    @Test
    public void testRegisterUser_PasswordIncorrect() {
        String expected = "Password is not correctly formatted; please ensure that the password "
                + "contains at least eight characters, a capital letter, a number, "
                + "and a special character.";
        String actual = login.registerUser("kyl_1", "password", "+27838968976");
        assertEquals(expected, actual);
    }

    @Test
    public void testRegisterUser_CellCorrect() {
        String expected = "Username successfully captured.\n"
                + "Password successfully captured.\n"
                + "Cell number successfully captured.";
        String actual = login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertEquals(expected, actual);
    }

    @Test
    public void testRegisterUser_CellIncorrect() {
        String expected = "Cell number is incorrectly formatted or does not contain an international "
                + "code; please correct the number and try again.";
        String actual = login.registerUser("kyl_1", "Ch&&sec@ke99!", "08966553");
        assertEquals(expected, actual);
    }

    @Test
    public void testReturnLoginStatus_Success() {
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        boolean success = login.loginUser("kyl_1", "Ch&&sec@ke99!");
        assertEquals("Welcome Kyle,Smith it is great to see you again.",
                login.returnLoginStatus(success));
    }

    @Test
    public void testReturnLoginStatus_Failure() {
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        boolean success = login.loginUser("wrong", "wrong");
        assertEquals("Username or password incorrect, please try again.",
                login.returnLoginStatus(success));
    }

    // ---------------- assertTrue / assertFalse tests ----------------

    @Test
    public void testLoginUser_Successful() {
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertTrue(login.loginUser("kyl_1", "Ch&&sec@ke99!"));
    }

    @Test
    public void testLoginUser_Failed() {
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertFalse(login.loginUser("kyl_1", "wrongpass"));
    }

    @Test
    public void testCheckUserName_Correct() {
        assertTrue(login.checkUserName("kyl_1"));
    }

    @Test
    public void testCheckUserName_Incorrect() {
        assertFalse(login.checkUserName("kyle!!!!!!!"));
    }

    @Test
    public void testCheckPasswordComplexity_Correct() {
        assertTrue(login.checkPasswordComplexity("Ch&&sec@ke99!"));
    }

    @Test
    public void testCheckPasswordComplexity_Incorrect() {
        assertFalse(login.checkPasswordComplexity("password"));
    }

    @Test
    public void testCheckCellPhoneNumber_Correct() {
        assertTrue(login.checkCellPhoneNumber("+27838968976"));
    }

    @Test
    public void testCheckCellPhoneNumber_Incorrect() {
        assertFalse(login.checkCellPhoneNumber("08966553"));
    }
}