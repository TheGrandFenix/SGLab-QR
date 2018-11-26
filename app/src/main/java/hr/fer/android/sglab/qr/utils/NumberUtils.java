package hr.fer.android.sglab.qr.utils;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class NumberUtils {
    public static String formatDouble(Double number) {
        return String.format(Locale.getDefault(), "%.2f", number);
    }

    public static String formatInteger(Integer number) {
        return String.format(Locale.getDefault(), "%d", number);
    }

    public static String[] makeStringSequenceOfFloats(float begin, float end, float step) {
        List<String> ret = new LinkedList<>();

        for(float i = begin; i <= end; i += step, ret.add(String.format("%.2f", i)));

        String[] array = new String[ret.size()];
        ret.toArray(array);

        return array;
    }
}
