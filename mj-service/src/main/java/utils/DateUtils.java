package utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by renhui on 2017/3/3.
 */
public class DateUtils {
    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static Calendar getTodayZero(){
        Calendar calendar =  Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        return calendar;
    }
    public static Calendar dayOffset(Calendar date,int dayOffset){
        Calendar calendar = Calendar.getInstance();
        date.add(Calendar.DAY_OF_YEAR,dayOffset);
        return calendar;
    }
}
