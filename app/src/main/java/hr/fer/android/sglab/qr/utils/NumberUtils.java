package hr.fer.android.sglab.qr.utils;

import java.util.Locale;

public class NumberUtils {
    public static String formatDouble(Double number) {
        return String.format(Locale.getDefault(), "%.2f", number);
    }

    public static String formatInteger(Integer number) {
        return String.format(Locale.getDefault(), "%d", number);
    }
}
