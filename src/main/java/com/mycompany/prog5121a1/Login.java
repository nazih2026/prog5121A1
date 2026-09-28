package com.mycompany.prog5121a1;

/*
 * Login class - Part 1 of the Chat App PoE.
 * Handles user registration validation and login authentication.
 */

/**
 * Login class handles user registration and login validation.
 *
 * @author nazih2026
 */
public class Login {

    private String storedUsername;
    private String storedPassword;
    private String storedCellPhone;
    private String firstName;
    private String lastName;

    public Login() {
    }

    public Login(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Username must contain an underscore and be no more than 5 characters.
     */
    public boolean checkUserName(String username) {
        if (username == null) {
            return false;
        }
        return username.contains("_") && username.length() <= 5;
    }

    /**
     * Password must be >= 8 chars, contain a capital, a number, a special char.
     */
    public boolean checkPasswordComplexity(String password) {
        if (password == null) {
            return false;
        }
        return password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,}$");
    }

    /**
     * SA cell number: +27 followed by 9 digits.
     *
     * Regex pattern adapted from:
     * https://stackoverflow.com/questions/123559/how-to-validate-phone-numbers-using-regex
     */
    public boolean checkCellPhoneNumber(String cellPhone) {
        if (cellPhone == null) {
            return false;
        }
        return cellPhone.matches("^\\+27\\d{9}$");
    }

    public String registerUser(String username, String password, String cellPhone) {
        if (!checkUserName(username)) {
            return "Username is not correctly formatted; please ensure that your username "
                    + "contains an underscore and is no more than five characters in length.";
        }
        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the password "
                    + "contains at least eight characters, a capital letter, a number, "
                    + "and a special character.";
        }
        if (!checkCellPhoneNumber(cellPhone)) {
            return "Cell number is incorrectly formatted or does not contain an international "
                    + "code; please correct the number and try again.";
        }

        this.storedUsername = username;
        this.storedPassword = password;
        this.storedCellPhone = cellPhone;

        return "Username successfully captured.\n"
                + "Password successfully captured.\n"
                + "Cell number successfully captured.";
    }

    public boolean loginUser(String username, String password) {
        if (storedUsername == null || storedPassword == null) {
            return false;
        }
        return storedUsername.equals(username) && storedPassword.equals(password);
    }

    public String returnLoginStatus(boolean loginSuccess) {
        if (loginSuccess) {
            return "Welcome " + firstName + "," + lastName + " it is great to see you again.";
        }
        return "Username or password incorrect, please try again.";
    }

