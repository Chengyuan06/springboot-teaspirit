package com.teaspiritspringboot.teaspiritspringboot.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12)); 
    }

    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        try {
            return BCrypt.checkpw(plainPassword, hashedPassword);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Mot de passe haché invalide. Vérifiez vos données.");
        }
    }
}

    