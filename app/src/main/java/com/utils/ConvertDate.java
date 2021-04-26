package com.utils;


import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/*
 * Created by Raj on 4/4/2015.
 * this class use for converting Date Format
 */
public class ConvertDate {

    public static String yyTOdd(String dt) {
        try {

            String s = "";
            try {
                s = dt.substring(0, dt.lastIndexOf(" "));

            } catch (Exception e) {
                // TODO: handle exception
                s = dt;
            }
            SimpleDateFormat Oldformat = new SimpleDateFormat("yyyy/MM/dd");
            DateFormat newFormat = new SimpleDateFormat("dd/MM/yyyy");

            try {
                s = newFormat.format(Oldformat.parse(s));

            } catch (ParseException e) {
                // TODO Auto-generated catch block

                s = dt;

            }
            return s;
        } catch (Exception e) {
            // TODO: handle exception
            return "";
        }

    }

    public static String yyTOdd2(String dt) {
        try {

            String s = "";
            try {
                s = dt.substring(0, dt.lastIndexOf(" "));

            } catch (Exception e) {
                // TODO: handle exception
                s = dt;
            }
            SimpleDateFormat Oldformat = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat newFormat = new SimpleDateFormat("dd-MM-yyyy");

            try {
                s = newFormat.format(Oldformat.parse(s));

            } catch (ParseException e) {
                // TODO Auto-generated catch block

                s = dt;

            }
            return s;
        } catch (Exception e) {
            // TODO: handle exception
            return "";
        }

    }

    public static String DTToDD(String dt) {
        try {

            String s = "";
            try {
                s = dt.substring(0, dt.lastIndexOf(" "));

            } catch (Exception e) {
                // TODO: handle exception
                s = dt;
            }
            SimpleDateFormat Oldformat = new SimpleDateFormat("MM/dd/yyyy");
            DateFormat newFormat = new SimpleDateFormat("dd/MM/yyyy");

            try {
                s = newFormat.format(Oldformat.parse(s));

            } catch (ParseException e) {
                // TODO Auto-generated catch block

                s = dt;

            }
            return s;
        } catch (Exception e) {
            // TODO: handle exception
            return "";
        }

    }

    public static String DMY2DM(String dt) {
        try {

            String s = "";
            try {
                s = dt.substring(0, dt.lastIndexOf(" "));

            } catch (Exception e) {
                // TODO: handle exception
                s = dt;
            }
            SimpleDateFormat Oldformat = new SimpleDateFormat("MM/dd/yyyy");
            DateFormat newFormat = new SimpleDateFormat("dd/MM");

            try {
                s = newFormat.format(Oldformat.parse(s));

            } catch (ParseException e) {
                // TODO Auto-generated catch block

                s = dt;

            }
            return s;
        } catch (Exception e) {
            // TODO: handle exception
            return "";
        }

    }

    public static String ddTOyy(String dt) {
        try {
            String s = "";
            try {
                s = dt.substring(0, dt.lastIndexOf(" "));
            } catch (Exception e) {
                // TODO: handle exception
                s = dt;
            }
            SimpleDateFormat oldFormate = new SimpleDateFormat("dd/MM/yyyy");
            DateFormat newFormate = new SimpleDateFormat("yyyy/MM/dd");

            try {
                s = newFormate.format(oldFormate.parse(s));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return s;
        } catch (Exception e) {
            // TODO: handle exception
            return "";
        }

    }

    public static String ddTOyy2(String dt) {
        try {
            String s = "";
            try {
                s = dt.substring(0, dt.lastIndexOf(" "));
            } catch (Exception e) {
                // TODO: handle exception
                s = dt;
            }
            SimpleDateFormat oldFormate = new SimpleDateFormat("dd-MM-yyyy");
            DateFormat newFormate = new SimpleDateFormat("yyyy-MM-dd");

            try {
                s = newFormate.format(oldFormate.parse(s));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return s;
        } catch (Exception e) {
            // TODO: handle exception
            return "";
        }

    }

    public static String sameYMD(String dt) {
        try {
            String s = "";
            try {
                s = dt.substring(0, dt.lastIndexOf(" "));
            } catch (Exception e) {
                // TODO: handle exception
                s = dt;
            }
            SimpleDateFormat oldFormate = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat newFormate = new SimpleDateFormat("yyyy-MM-dd");

            try {
                s = newFormate.format(oldFormate.parse(s));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return s;
        } catch (Exception e) {
            // TODO: handle exception
            return "";
        }

    }

    public static String YMDToYM(String dt) {
        try {
            String s = "";
            try {
                s = dt.substring(0, dt.lastIndexOf(" "));
            } catch (Exception e) {
                // TODO: handle exception
                s = dt;
            }
            SimpleDateFormat oldFormate = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat newFormate = new SimpleDateFormat("yyyy-MM");

            try {
                s = newFormate.format(oldFormate.parse(s));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return s;
        } catch (Exception e) {
            // TODO: handle exception
            return "";
        }

    }

    public static String sameDMY(String dt) {
        try {
            String s = "";
            try {
                s = dt.substring(0, dt.lastIndexOf(" "));
            } catch (Exception e) {
                // TODO: handle exception
                s = dt;
            }
            SimpleDateFormat oldFormate = new SimpleDateFormat("dd-MM-yyyy");
            DateFormat newFormate = new SimpleDateFormat("dd-MM-yyyy");

            try {
                s = newFormate.format(oldFormate.parse(s));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return s;
        } catch (Exception e) {
            // TODO: handle exception
            return "";
        }

    }


    public static String GetTime(String ddmmyyyy) {
        try {
            String s = "";
            s = ddmmyyyy.substring(ddmmyyyy.indexOf(" "), ddmmyyyy.length());
            return s;
        } catch (Exception e) {
            // TODO: handle exception
            return "";
        }

    }

    public static String getDateToYMD(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public static String getDateToYMDHms(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
        return sdf.format(date);
    }

    public static String HH2hh(String HHMMSS) {
        SimpleDateFormat oldFormate = new SimpleDateFormat("HH:mm:ss");
        DateFormat newFormate = new SimpleDateFormat("hh:mm:ss a");
        String s = "";
        try {
            s = newFormate.format(oldFormate.parse(HHMMSS));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return s;
    }

    public static String getDateToDMYhms(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
        return sdf.format(date);
    }

    public static Date getDateToDMYHM(String strDMY) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
        Date date = null;
        try {
            date = sdf.parse(strDMY);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getDateToDMY(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(date);
    }

    public static Date getDateFromDMY(String strDMY) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            date = sdf.parse(strDMY);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;

    }

    public static Date getDateFromDMYhms(String strDMY) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
        Date date = null;
        try {
            date = sdf.parse(strDMY);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;

    }

    public static Date getDateFromYMD(String strYMD) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(strYMD);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;

    }

    public static String MiliToDT(String MiliSec) {
        long milliSeconds = Long.parseLong(MiliSec);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss a");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());

    }

    public static String MiliToDT2(String MiliSec) {
        long milliSeconds = Long.parseLong(MiliSec);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());

    }

    public static String YMD_TO_DMY(String dt) {

        SimpleDateFormat Oldformat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss a");
        DateFormat newFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
        String s = "";
        try {
            s = newFormat.format(Oldformat.parse(s));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            s = dt;
        }
        return s;

    }

    public static String ConvertDuration(int duration) {

        String finalTimerString = "";
        String secondsString = "";

        int hours = (int) (duration / (1000 * 60 * 60));
        int minutes = (int) (duration % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((duration % (1000 * 60 * 60)) % (1000 * 60) / 1000);

        if (hours > 0) {
            finalTimerString = hours + ":";
        }


        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }

        finalTimerString = finalTimerString + minutes + ":" + secondsString;
        return finalTimerString;

    }

    public static boolean isValidFormat(String format, String value) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(value);
            if (!value.equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return date != null;
    }

    public static String calDate(String LastDate) {
        String s = "";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        DateFormat dateFormat3 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
        DateFormat dateFormat4 = new SimpleDateFormat("yyyy-MM-dd hh:mm a");

        //get current date time with Date()
        Date date = new Date();
        System.out.println();
        String dateStart = dateFormat.format(date);
        String dateStop = LastDate;

        //HH converts hour in 24 hours format (0-23), day calculation

        Date d1 = null;
        Date d2 = null;

        try {
            d1 = dateFormat.parse(dateStart);

            boolean isAcc = false;
            if (!isAcc) {
                try {
                    d2 = dateFormat.parse(dateStop);
                    isAcc = true;
                } catch (ParseException e) {
                    isAcc = false;
                    Log.e("Date Error 1", "" + e.toString());

                }
            }
            if (!isAcc) {
                try {
                    d2 = dateFormat2.parse(dateStop);
                    isAcc = true;
                } catch (ParseException e) {
                    isAcc = false;
                    Log.e("Date Error 2", "" + e.toString());

                }
            }
            if (!isAcc) {
                try {
                    d2 = dateFormat3.parse(dateStop);
                    isAcc = true;
                } catch (ParseException e) {
                    isAcc = false;
                    Log.e("Date Error 3", "" + e.toString());

                }
            }
            if (!isAcc) {
                try {
                    d2 = dateFormat4.parse(dateStop);
                    isAcc = true;
                } catch (ParseException e) {
                    isAcc = false;
                    Log.e("Date Error 4", "" + e.toString());

                }
            }
            if (!isAcc) {
                try {
                    d2 = dateFormat.parse(dateStop + " 00:00:00");
                    isAcc = true;
                } catch (ParseException e) {
                    isAcc = false;
                    Log.e("Date Error 5", "" + e.toString());

                }
            }
            if (!isAcc) {
                try {
                    d2 = dateFormat2.parse(dateStop + " 00:00:00");
                    isAcc = true;
                } catch (ParseException e) {
                    isAcc = false;
                    Log.e("Date Error 6", "" + e.toString());

                }
            }

            if (!isAcc) {
                try {
                    d2 = dateFormat3.parse(dateStop + " 00:00:00");
                    isAcc = true;
                } catch (ParseException e) {
                    isAcc = false;
                    Log.e("Date Error 7", "" + e.toString());

                }
            }

            if (!isAcc) {
                try {
                    d2 = dateFormat4.parse(dateStop + " 00:00:00");
                    isAcc = true;
                } catch (ParseException e) {
                    isAcc = false;
                    Log.e("Date Error 8", "" + e.toString());

                }
            }


            //in milliseconds
            long diff = d2.getTime() - d1.getTime();

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);
            if (diffDays > 0) {
                if (s.equals("")) {
                    s = "" + diffDays + "d";
                } else {
                    s = s + " " + diffDays + "d";
                }
            }

            if (diffHours > 0) {
                if (s.equals("")) {
                    s = "" + diffHours + "h";
                } else {
                    s = s + " " + diffHours + "h";
                }
            }
            if (diffMinutes > 0) {
                if (s.equals("")) {
                    s = "" + diffMinutes + "m";
                } else {
                    s = s + " " + diffMinutes + "m";
                }
            }

//            Log.e("diffDays", "-" + diffDays);
//            Log.e("diffHours", "-" + diffHours);
//            Log.e("diffMinutes", "-" + diffMinutes);
//            Log.e("diffSeconds", "-" + diffSeconds);

        } catch (ParseException e) {
            s = "";
            e.printStackTrace();
        }

        return s;
    }

    public static String getCurrendDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return sdf.format(new Date());
    }

    public static String getCurrendDateTime2() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
        return sdf.format(new Date());
    }

    public static String getCurrendDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(new Date());
    }

    public static String getMiliToFullDate(String milliSeconds) {
        if (!milliSeconds.equals("") && !milliSeconds.toLowerCase().equals("null")) {
            SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMMM dd yyyy");
            // Create a calendar object that will convert the date and time value in milliseconds to date.
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(Long.parseLong(milliSeconds));
            return formatter.format(calendar.getTime());
        } else {
            return "";
        }
        // Create a DateFormatter object for displaying date in specified format.

    }
    public static String getMiliToFullDateTime(String milliSeconds) {
        if (!milliSeconds.equals("") && !milliSeconds.toLowerCase().equals("null")) {
            String s = "";
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMM dd yyyy, hh:mm a");
                // Create a calendar object that will convert the date and time value in milliseconds to date.
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(Long.parseLong(milliSeconds));
                s= formatter.format(calendar.getTime());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            return s;
        } else {
            return "";
        }
        // Create a DateFormatter object for displaying date in specified format.

    }

    public static String getMiliToDMYHMSA(String milliSeconds) {
        if (!milliSeconds.equals("") && !milliSeconds.toLowerCase().equals("null")) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
            // Create a calendar object that will convert the date and time value in milliseconds to date.
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(Long.parseLong(milliSeconds));
            return formatter.format(calendar.getTime());
        } else {
            return "";
        }
        // Create a DateFormatter object for displaying date in specified format.

    }

    public static String getCurrentYMD() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }

    public static String getCurrentYear() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        return sdf.format(new Date());
    }

    public static String getCurrentTime() {

        SimpleDateFormat Oldformat = new SimpleDateFormat("hh:mm:ss a");
        return Oldformat.format(new Date());

    }

    public static String getCurrentTime1() {

        SimpleDateFormat Oldformat = new SimpleDateFormat("hh:mm a");
        return Oldformat.format(new Date());

    }

    public static String getCurrentTime24() {

        SimpleDateFormat Oldformat = new SimpleDateFormat("HH:mm");
        return Oldformat.format(new Date());

    }

    public static String MiliToDT3(String MiliSec) {
        long milliSeconds = Long.parseLong(MiliSec);
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy hh_mm_ss_a");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());

    }

    public static String GetDayInString(String yyyymmdd) {
        try {

            String s = "";
            SimpleDateFormat oldFormate = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
            s = dayFormat.format(oldFormate.parseObject(yyyymmdd));
            return s;
        } catch (Exception e) {
            // TODO: handle exception
            return "";
        }

    }

    public static String getDayFromYMD(String dt) {
        try {
            String s = "";
            try {
                s = dt.substring(0, dt.lastIndexOf(" "));
            } catch (Exception e) {
                // TODO: handle exception
                s = dt;
            }
            SimpleDateFormat oldFormate = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat newFormate = new SimpleDateFormat("dd");

            try {
                s = newFormate.format(oldFormate.parse(s));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return s;
        } catch (Exception e) {
            // TODO: handle exception
            return "";
        }

    }

    public static String getMonthFromYMD(String dt) {
        try {
            String s = "";
            try {
                s = dt.substring(0, dt.lastIndexOf(" "));
            } catch (Exception e) {
                // TODO: handle exception
                s = dt;
            }
            SimpleDateFormat oldFormate = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat newFormate = new SimpleDateFormat("MMMM-yyyy");

            try {
                s = newFormate.format(oldFormate.parse(s));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return s;
        } catch (Exception e) {
            // TODO: handle exception
            return "";
        }

    }

    public static Date yesterday() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    public static Date lastWeek() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7);
        return cal.getTime();
    }

    public static Date currentMonth() {
        Calendar cal = Calendar.getInstance();   // this takes current date
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    public static Date currentYear() {
        Calendar cal = Calendar.getInstance();   // this takes current date
        cal.set(Calendar.DAY_OF_YEAR, 1);
        return cal.getTime();
    }

    public static Date lastMonthFirstDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, 1);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date lastDateOfPreviousMonth = cal.getTime();
        cal.set(Calendar.DATE, 1);
        Date firstDateOfPreviousMonth = cal.getTime();
        return firstDateOfPreviousMonth;
    }

    public static Date lastMonthEndDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, 1);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date lastDateOfPreviousMonth = cal.getTime();
        cal.set(Calendar.DATE, 1);
        Date firstDateOfPreviousMonth = cal.getTime();
        return lastDateOfPreviousMonth;
    }

    public static String getCurrendDateYMD2() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }

    public static String getCurrentYearString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        return sdf.format(new Date());
    }

}
