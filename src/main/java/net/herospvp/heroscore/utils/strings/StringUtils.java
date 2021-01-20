package net.herospvp.heroscore.utils.strings;

import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.ChatColor;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class StringUtils {

    public static String fromMinutesToHHmm(int minutes) {
        long hours = TimeUnit.MINUTES.toHours((long) minutes);
        long remainMinutes = minutes - TimeUnit.HOURS.toMinutes(hours);
        return String.format("%02dh %02dm", hours, remainMinutes);
    }

    public static long extractNumber(String string) {
        try {
            return Integer.parseInt(ChatColor.stripColor(string).replaceAll("[^0-9]", ""));
        }catch (NumberFormatException e) {
            return 0;
        }
    }

    public static String toRoman(int input) {
        String s = "";

        while (input >= 1000) {
            s += "M";
            input -= 1000;
        } while (input >= 900) {
        s += "CM";
        input -= 900;
    } while (input >= 500) {
        s += "D";
        input -= 500;
    } while (input >= 400) {
        s += "CD";
        input -= 400;
    } while (input >= 100) {
        s += "C";
        input -= 100;
    } while (input >= 90) {
        s += "XC";
        input -= 90;
    } while (input >= 50) {
        s += "L";
        input -= 50;
    } while (input >= 40) {
        s += "XL";
        input -= 40;
    } while (input >= 10) {
        s += "X";
        input -= 10;
    } while (input >= 9) {
        s += "IX";
        input -= 9;
    } while (input >= 5) {
        s += "V";
        input -= 5;
    } while (input >= 4) {
        s += "IV";
        input -= 4;
    } while (input >= 1) {
        s += "I";
        input -= 1;
    }

        return s;
    }

    public static String c(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    /**
     * @param formatString "yyyy MM dd HH:mm:ss"
     */
    public static String convertTime(long time, String formatString){
        Date date = new Date(time);
        Format format = new SimpleDateFormat(formatString);
        return format.format(date);
    }

    public static String capitalize(String str) {
        str = str.toLowerCase();
        StringBuilder titleCase = new StringBuilder(str.length());
        boolean nextTitleCase = true;

        for (char c : str.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true;
            } else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            }

            titleCase.append(c);
        }

        return titleCase.toString();
    }

    public static boolean isNumber(String string) {
        return NumberUtils.isNumber(string);
    }

    public static String formatNumber(double count) {
        if (count < 1000) return "" + count;
        int exp = (int) (Math.log(count) / Math.log(1000));
        return String.format("%.1f%c",
                count / Math.pow(1000, exp),
                "kMBTPE".charAt(exp-1));
    }
}
