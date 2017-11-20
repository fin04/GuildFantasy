package com.epriest.game.guildfantasy.util;

/**
 * Created by darka on 2017-11-17.
 */

public class StringUtils {
    public static boolean isEmpty(CharSequence text) {
        return text == null || text.length() == 0;
    }

    public static String nullToEmpty(CharSequence text) {
        return text == null ? "" : text.toString();
    }
}
