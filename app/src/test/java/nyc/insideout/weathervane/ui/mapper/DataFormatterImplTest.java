package nyc.insideout.weathervane.ui.mapper;


import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class DataFormatterImplTest {

    private DataFormatterImpl dataFormatter;

    @Before
    public void setup(){
        dataFormatter = new DataFormatterImpl();

    }

    @Test
    public void givenUnixTimestamp_ThenReturnFormattedDateString(){
        String expected = "Sun, Apr 9";
        long date = 261006474; //April 9, 1978
        String actual = dataFormatter.formatDate(date);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void givenNumericTemperature_ThenReturnFormattedStringValue(){
        String expected = "78\u00b0";
        double tempValue = 78;
        String actual = dataFormatter.formatTemperature(tempValue);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void givenString_TheReturnFormattedUrl(){
        String expected = "http://openweathermap.org/img/w/78k.png";
        String iconId = "78k";
        String actual = dataFormatter.formatImgUrl(iconId);
        Assert.assertEquals(expected, actual);
    }
}
