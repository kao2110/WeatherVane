package nyc.insidout.weathervane.domain.model;


public class ForecastDetail extends Forecast {

    public final int humidity;
    public final String descDetail;

    public ForecastDetail(final int date, final int tempMax, final int tempMin, final int forecastId,
                          final String desc, int humidity, String descDetail){
        super(date, tempMax, tempMin, forecastId, desc);
        this.humidity = humidity;
        this.descDetail = descDetail;
    }
}
