package ca.ualberta.cs.habittracker.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ZEKE_XU on 30/09/2016.
 */

public class DateUtil {

    public DateUtil() {}

    public static String getFormatDateString(Date date) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

}
