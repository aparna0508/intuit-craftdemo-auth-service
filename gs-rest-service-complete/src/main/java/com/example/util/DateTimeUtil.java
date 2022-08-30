package com.example.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtil {
	/* Returns a random salt */
	public static String getRandomSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
	
	/* Returns hashed string of input with salt */
	public static String getHashed(String input, String salt) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-512");
		md.update(salt.getBytes());
		byte[] hashedBytes = md.digest(input.getBytes(StandardCharsets.UTF_8));
		String hashed = Base64.getEncoder().encodeToString(hashedBytes); 
		return hashed;
	}
	
	public static Date getCurrentTime() {
		Calendar date = Calendar.getInstance();
		return date.getTime();
	}
	
	public static Date getExpiryTime(Integer addInMinutes) {
		Calendar date = Calendar.getInstance();
		long timeInSecs = date.getTimeInMillis();
		Date advanceTime = new Date(timeInSecs + (addInMinutes * 60 * 1000));
		return advanceTime;
	}

	public static boolean expired(Date expiresAt) {
		return (getCurrentTime().compareTo(expiresAt) >= 0);
	}
}
