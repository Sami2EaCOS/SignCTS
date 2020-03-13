package net.smourad.signcts.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SimpleUtils {

    public static String getActualTime() {
        return new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
    }

}
