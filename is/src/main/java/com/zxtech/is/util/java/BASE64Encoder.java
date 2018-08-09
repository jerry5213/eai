package com.zxtech.is.util.java;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.io.IOException;
import java.io.OutputStream;

public class BASE64Encoder extends CharacterEncoder {
    private static final char[] pem_array = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};

    public BASE64Encoder() {
    }

    protected int bytesPerAtom() {
        return 3;
    }

    protected int bytesPerLine() {
        return 57;
    }

    protected void encodeAtom(OutputStream outputstream, byte[] abyte0, int i, int j) throws IOException {
        byte byte1;
        if (j == 1) {
            byte1 = abyte0[i];
            int k = 0;
            boolean flag = false;
            outputstream.write(pem_array[byte1 >>> 2 & 63]);
            outputstream.write(pem_array[(byte1 << 4 & 48) + (k >>> 4 & 15)]);
            outputstream.write(61);
            outputstream.write(61);
        } else {
            byte byte3;
            if (j == 2) {
                byte1 = abyte0[i];
                byte3 = abyte0[i + 1];
                int l = 0;
                outputstream.write(pem_array[byte1 >>> 2 & 63]);
                outputstream.write(pem_array[(byte1 << 4 & 48) + (byte3 >>> 4 & 15)]);
                outputstream.write(pem_array[(byte3 << 2 & 60) + (l >>> 6 & 3)]);
                outputstream.write(61);
            } else {
                byte1 = abyte0[i];
                byte3 = abyte0[i + 1];
                byte byte5 = abyte0[i + 2];
                outputstream.write(pem_array[byte1 >>> 2 & 63]);
                outputstream.write(pem_array[(byte1 << 4 & 48) + (byte3 >>> 4 & 15)]);
                outputstream.write(pem_array[(byte3 << 2 & 60) + (byte5 >>> 6 & 3)]);
                outputstream.write(pem_array[byte5 & 63]);
            }
        }

    }
}
