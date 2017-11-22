package nyc.insideout.weathervane.ui.mapper;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DataFormatterImpl implements DataFormatter {


    private static final String DATE_FORMAT_SHORT = "EEE, MMM d";
    private static final String DATE_FORMAT_LONG = "EEEE, MMMM d";
    private static final String DEGREE_SYMBOL = "\u00B0";
    private static final String BASE_IMG_URL = "http://openweathermap.org/img/w/";
    private static final String IMG_URL_TYPE = ".png";

    /*
    * Returns a formatted String value of a unix timestamp.
    * Ex: "Mon, Jun 9"
    */
    public String formatDate(long time){
        return formatDateHelper(time, DATE_FORMAT_SHORT);
    }

    public String formatDateLong(long time){
        return formatDateHelper(time, DATE_FORMAT_LONG);
    }

    private String formatDateHelper(long time, String format){
        // Convert unix timestamp (seconds) to milliseconds so it can be converted to valid date
        Date date = new Date(time * 1000);

        SimpleDateFormat monthDayFormat = new SimpleDateFormat(format, Locale.getDefault());
        return monthDayFormat.format(date);
    }

    /*
    * Returns String value of temperature with the unicode degree symbol
    */
    public String formatTemperature(double tempValue){
        int value = (int)tempValue;
        return Integer.toString(value) + DEGREE_SYMBOL;
    }

    /*
    * Returns URL of weather icon to be used based on icon id provided by API
    */
    public String formatImgUrl(String iconId){
        return BASE_IMG_URL + iconId + IMG_URL_TYPE;
    }

}
