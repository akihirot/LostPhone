package com.akihirot.lostphone;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class toMD5 {
    public static String encodePassdigiest(String password){  
        byte[] enclyptedHash=null;  
        // get MD5 in Byte
                MessageDigest md5;  
                try {  
                    md5 = MessageDigest.getInstance("MD5");  
                    md5.update(password.getBytes());  
                    enclyptedHash = md5.digest();  
                } catch (NoSuchAlgorithmException e) {  
                    // TODO Auto-generated catch block  
                    e.printStackTrace();  
                }  
                
                // to Hex
                return bytesToHexString(enclyptedHash);  
    }  
      
      
    private static String bytesToHexString(byte[] fromByte) {  
      
        StringBuilder hexStrBuilder = new StringBuilder();  
        for (int i = 0; i < fromByte.length; i++) {   
            if ((fromByte[i] & 0xff) < 0x10) {  
                hexStrBuilder.append("0");  
            }  
            hexStrBuilder.append(Integer.toHexString(0xff & fromByte[i]));  
        }  
      
        return hexStrBuilder.toString();  
    }  
}
