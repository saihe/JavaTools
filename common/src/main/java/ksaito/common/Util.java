package ksaito.common;

import java.text.MessageFormat;

public class Util {
    public static void exit(int code) {
        System.exit(code);
    }

    public static void println(Object obj) {
        System.out.println(obj);
    }

    public static void println(String msg, Object ...args) {
        System.out.println(MessageFormat.format(msg, args));
    }
}
