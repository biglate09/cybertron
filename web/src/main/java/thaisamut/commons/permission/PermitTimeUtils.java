package thaisamut.commons.permission;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;
import static org.apache.commons.lang3.SystemUtils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormatSymbols;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * @author <a href="mailto:chalermpong.ch@ocean.co.th">Chalermpong Chaiyawan</a>
 */
public final class PermitTimeUtils
{
    private static final Logger LOG = LoggerFactory.getLogger(PermitTimeUtils.class);

    private static final int WEEKDAY_OFFSET = Calendar.SUNDAY;

    private static final long HOUR_MASK = 0x00ffffffL;

    private static final long DAY_MASK = 0xff000000L;

    private PermitTimeUtils() { }

    public static boolean isPermitted(String perms)
    {
        return isPermitted(perms, Calendar.getInstance(Locale.US));
    }

    public static boolean isPermitted(String perms, Calendar time)
    {
        return isPermitted(normalize(perms), time);
    }

    public static boolean isPermitted(List<Long> perms, Calendar time)
    {
        return check(perms, time.get(Calendar.DAY_OF_WEEK),
            time.get(Calendar.HOUR_OF_DAY));
    }

    private static boolean check(List<Long> perms, int day, int hour)
    {
        long time_mask = (1 << (day - WEEKDAY_OFFSET + 24)) | (1 << hour);

        for (Long l : perms)
        {
            if ((time_mask & l.longValue()) == time_mask)
            {
                return true;
            }
        }

        return false;
    }

    public static String drawGraph(String perms)
    {
        StringBuilder buffer = new StringBuilder();
        DateFormatSymbols symb = new DateFormatSymbols(Locale.US);
        String[] weekdays = symb.getWeekdays();
        List<Long> normalized = normalize(perms);

        buffer.append(LINE_SEPARATOR)
              .append("=================================================================================")
              .append(LINE_SEPARATOR)
              .append("DAYS/HRS. 00 01 02 03 04 05 06 07 08 09 10 11 12 13 14 15 16 17 18 19 20 21 22 23")
              .append(LINE_SEPARATOR)
              .append("---------------------------------------------------------------------------------");

        for (int day = Calendar.SUNDAY; day <= Calendar.SATURDAY; ++day)
        {
            char[] permissions = new char[71];

            Arrays.fill(permissions, ' ');
            buffer.append(LINE_SEPARATOR)
                  .append(StringUtils.rightPad(weekdays[day], 10));

            for (int hr = 0; hr < 24; ++hr)
                permissions[hr * 3] = check(normalized, day, hr) ? '✔' : '✗';
                /*
                if (check(normalized, day, hr))
                    permissions[hr * 3] = '✔';
                */

            buffer.append(permissions);
        }

        buffer.append(LINE_SEPARATOR)
              .append("=================================================================================");

        return buffer.toString();
    }

    public static String normalizeAsString(String perms)
    {
        return StringUtils.join(normalize(perms), ", ");
    }

    public static List<Long> normalize(String perms)
    {
        List<Long> normalized = new ArrayList<Long>();
        Map<Long, Long> daymap = new HashMap<Long, Long>();
        Map<Long, Long> permmap = new HashMap<Long, Long>();

        for (Long perm : convert(perms))
        {
            long l = perm.longValue();

            for (int i = Calendar.SUNDAY; i <= Calendar.SATURDAY; ++i)
            {
                long day_mask = (1 << ((i + 24) - Calendar.SUNDAY));

                if ((day_mask & l) == day_mask)
                {
                    Long day = new Long(day_mask);
                    Long day_perm = daymap.get(day);

                    if (null == day_perm)
                    {
                        daymap.put(day, day_perm = new Long(l & HOUR_MASK));
                    }
                    else
                    {
                        daymap.put(day,
                            new Long(day_perm.longValue() | (l & HOUR_MASK)));
                    }
                }
            }
        }

        for (Long day : daymap.keySet())
        {
            Long perm = daymap.get(day);

            if (null != perm)
            {
                Long a = permmap.get(perm);

                if (null == a)
                {
                    permmap.put(perm,
                        new Long(day.longValue() | perm.longValue()));

                    continue;
                }

                permmap.put(perm, new Long(day.longValue() | a.longValue()));
            }
        }

        normalized.addAll(permmap.values());

        return normalized;
    }

    private static List<Long> convert(String perms)
    {
        List<Long> results = new ArrayList<Long>();
        String stripped = StringUtils.stripToEmpty(perms);

        for (String s : stripped.split("\\s*,\\s*"))
        {
            try { results.add(new Long(s)); }
            catch (NumberFormatException ignored) { }
        }

        return results;
    }
}
