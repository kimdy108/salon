package com.project.salon.main.api.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.spec.AlgorithmParameterSpec;

@Slf4j
public class Common {

    public static final String AUTHCHAR = "🅂🄰🄻🄾🄽🄲" ; // 🄰🄱🄲🄳🄴🄵🄶🄷🄸🄹🄺🄻🄼🄽🄾🄿🅀🅁🅂🅃🅄🅅🅆🅇🅈🅉
    public static final String DEFINEAUTHKEY = "BKroUrRPzJokrDeFfkkBQnVEeQshnQLz";
    public static final String DEFINEAUTHIV = "KIMDY@SALON";

    public static String encryptString(String data) {
        try {
            return encryptStringPrivate(data);
        }
        catch(Exception e) {
            log.debug(e.getMessage());
            return "";
        }
    }

    public static String encryptStringSalt(String data) {
        try {
            return encryptStringPrivate(RandomStringUtils.random(10, true, true) + AUTHCHAR + data + AUTHCHAR + RandomStringUtils.random(10, true, true));
        } catch (Exception e) {
            log.debug(e.getMessage());
            return "";
        }
    }

    private static String encryptStringPrivate(String ciphertext) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(DEFINEAUTHKEY.getBytes(StandardCharsets.UTF_8), "AES");

        String ivString = DEFINEAUTHIV.substring(0,16);
        AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivString.getBytes(StandardCharsets.UTF_8));

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        byte[] encrypted = cipher.doFinal(ciphertext.getBytes(StandardCharsets.UTF_8));

        return new String(Base64.encodeBase64(encrypted));
    }

    public static String decryptString(String data) {
        try {
            return decryptStringPrivate(data);
        }
        catch(Exception e) {
            log.debug(e.getMessage());
            return "";
        }
    }

    public static String decryptStringSalt(String data) {
        try {
            return decryptStringPrivate(data).split(AUTHCHAR)[1];
        } catch (Exception e) {
            log.debug(e.getMessage());
            return "";
        }
    }

    private static String decryptStringPrivate(String encrypttext) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(DEFINEAUTHKEY.getBytes("UTF-8"), "AES");

        String ivString = DEFINEAUTHIV.substring(0,16);
        AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivString.getBytes(StandardCharsets.UTF_8));

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        byte[] decrypted = Base64.decodeBase64(encrypttext.getBytes(StandardCharsets.UTF_8));

        return new String(cipher.doFinal(decrypted), StandardCharsets.UTF_8);
    }
}
