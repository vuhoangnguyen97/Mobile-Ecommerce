package com.example.legia.mobileweb.DAO;

import java.security.SecureRandom;

public class generateTokenKey {
    protected static SecureRandom random = new SecureRandom();

    public static synchronized String generateToken( String username ) {
        long longToken = Math.abs( random.nextLong() );
        String random = Long.toString( longToken, 16 );
        return random;
    }

}
