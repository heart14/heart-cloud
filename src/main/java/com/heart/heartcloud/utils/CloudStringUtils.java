package com.heart.heartcloud.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Random;
import java.util.UUID;

public class CloudStringUtils extends StringUtils {

    private static String[] chars = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    public static Integer getId() {
        Random random = new Random();
        return Math.abs(random.nextInt() + 1);
    }

    public static String getShotUuid() {
        StringBuilder shotUuid = new StringBuilder();
        String uuid = getUuid();
        for (int i = 0; i < 8; i++) {
            String string = uuid.substring(i * 4, i * 4 + 4);
            int parseInt = Integer.parseInt(string, 16);
            shotUuid.append(chars[parseInt % 0x3E]);
        }
        return shotUuid.toString();
    }

    public static String getUuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String getSalt(String userName, String userPass) {
        return userName + "_" + userPass;
    }

}
