package net.smourad.signcts.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SimpleUtils {

    public static String getActualTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(cal.getTime());
    }

}
