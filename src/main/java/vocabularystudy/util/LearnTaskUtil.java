package vocabularystudy.util;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class LearnTaskUtil
{
    public static Long getTodayTaskAmount(Date endDate, Long restNum)
    {
        Long dayNum = (endDate.getTime() - System.currentTimeMillis()) / (1000*3600*24);

        if(dayNum <= 0)
            return restNum;

        return Math.round(restNum * 1.0 / dayNum);
    }
}
