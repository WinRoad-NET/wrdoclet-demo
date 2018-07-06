package net.winroad.config;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class SimpleDateFormatThreadLocal {
    private static ThreadLocal<Map<String, SimpleDateFormat>> simpleDateFormatThreadLocal = new ThreadLocal<Map<String, SimpleDateFormat>>() {
        protected synchronized Map<String, SimpleDateFormat> initialValue() {
            return new HashMap<>();
        }
    };

    public static SimpleDateFormat get(String dateFormat) {
        return simpleDateFormatThreadLocal.get().computeIfAbsent(dateFormat, SimpleDateFormat::new);
    }

    public static void clear() {
        simpleDateFormatThreadLocal.remove();
    }
}
