package thaisamut.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Read properties and provide the development text (mainly to be shown in login and/or main page)
 */
public class DevelopmentTextUtil {

    public static final String DEVELOPMENT_TEXT;
    public static final String TOPBAR_DEVELOPMENT_STYLE;

    static {
        String s = "";
        try {
            InputStream res = DevelopmentTextUtil.class.getResourceAsStream("/development.txt");
            BufferedReader buf = new BufferedReader(new InputStreamReader(res));
            s = buf.readLine();
            if (s == null) {
                s = "";
            }
        } catch (Throwable ignored) {
            //intentional
        }

        DEVELOPMENT_TEXT = s;
    }

    static {
        String s = "";
        try {
            InputStream res = DevelopmentTextUtil.class.getResourceAsStream("/topbardevelopmentstyle.txt");
            BufferedReader buf = new BufferedReader(new InputStreamReader(res));
            s = buf.readLine();
            if (s == null) {
                s = "";
            }
        } catch (Throwable ignored) {
            //intentional
        }

        TOPBAR_DEVELOPMENT_STYLE = s;
    }
}

