package nyc.insidout.weathervane.domain.model;


public class Forecast {

    public final int date;
    public final int tempMax;
    public final int tempMin;
    public final int forecastId;
    public final String desc;

    public Forecast(final int date, final int tempMax, final int tempMin, final int forecastId,
                    final String desc){
        this.date = date;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
        this.forecastId = forecastId;
        this.desc = desc;
    }

}
