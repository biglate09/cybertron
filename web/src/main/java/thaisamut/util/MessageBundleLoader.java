package thaisamut.util;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.ResourceBundle;

public class MessageBundleLoader {

    public static final String DEFAULT_MESSAGE_PATH = "resources.error_code";
    private static HashMap messageBundles = new HashMap();

    public MessageBundleLoader() {
    }

    public static String getMessage(String key) {
        
        if (key == null) {
            return null;
        }
        
        try {

            ResourceBundle res = ResourceBundle.getBundle(DEFAULT_MESSAGE_PATH);
            if (res == null) {
                res = ResourceBundle.getBundle(DEFAULT_MESSAGE_PATH);
                messageBundles.put(key, res);
            }
            return res.getString(key);
        } catch (Exception e) {
            return null;
        }
        
    }


    public static String getMessageFormat(String key, Object... args) {
        if (key == null) {
            return null;
        }
        
        try {
            ResourceBundle messages = ResourceBundle.getBundle(DEFAULT_MESSAGE_PATH);
            return MessageFormat.format(messages.getString(key), args);
        } catch (Exception e) {
            return "";
        }
        
    }
}
