package com.zxtech.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by SYP521 on 2017/7/3.
 */

public class Md5 {

    public static String md5s(String plainText) throws NoSuchAlgorithmException {
        MessageDigest md= MessageDigest.getInstance("MD5");
        md.update(plainText.getBytes());
        byte b[]=md.digest();
        int i;
        StringBuffer buf=new StringBuffer();
        for(int j=0;j<b.length;j++){
            i=b[j];
            if(i<0)
                i+=256;
            if(i<16)
                buf.append("0");
            buf.append(Integer.toHexString(i));

        }

        return buf.toString();
    }
}
