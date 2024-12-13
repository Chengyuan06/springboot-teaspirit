package com.teaspiritspringboot.teaspiritspringboot.utils;

import java.util.regex.Pattern;

public class Validator {

    final static String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    final static String PHONE_REGEX = "^(\\+\\d{1,3}[- ]?)?\\d{10}$";
    final static String NAME_REGEX = "^[a-zA-Zà-ÿÀ-Ÿ\\s'-]+$";

    public static boolean isValidEmail(String email) {
        if(email == null || email.isEmpty()){
            return false;
        }
        return Pattern.matches(EMAIL_REGEX, email); // Retourne true si l'email correspond au format
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        if(phoneNumber == null || phoneNumber.isEmpty()){
            return false;
        }
        return Pattern.matches(PHONE_REGEX, phoneNumber);
    }

    public static boolean isValidName(String name) {
        // Vérifier si le nom n'est pas nul et correspond à l'expression régulière
        if (name == null || name.isEmpty()) {
            return false; // Le nom est vide ou nul
        }
        return Pattern.matches(NAME_REGEX, name); // Valider avec l'expression régulière
    }



}
