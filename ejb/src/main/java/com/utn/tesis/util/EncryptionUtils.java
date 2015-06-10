package com.utn.tesis.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created with IntelliJ IDEA.
 * User: enzo
 * Date: 09/06/15
 * Time: 23:23
 */
public class EncryptionUtils {

    public static String encryptMD5A1(String string) throws NoSuchAlgorithmException {
        String encryptedString = null;

        if (string != null && !string.isEmpty()) {
            MessageDigest digest = MessageDigest.getInstance("MD5");

            digest.update(string.getBytes(), 0, string.length());
            encryptedString = new BigInteger(1, digest.digest()).toString(16);
        }

        return encryptedString;
    }

    public static String encryptMD5A2(String string) {
        return DigestUtils.md5Hex(string);
    }
}
