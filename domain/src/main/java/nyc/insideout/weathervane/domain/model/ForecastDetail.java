package nyc.insideout.weathervane.domain.model;


public class ForecastDetail extends Forecast {

    public final int humidity;
    public final String descDetail;

    public ForecastDetail(final long date, final double tempMax, final double tempMin, final int forecastId,
                          final String desc, int humidity, final String descDetail, final String iconId ){
        super(date, tempMax, tempMin, forecastId, desc, iconId);
        this.humidity = humidity;
        this.descDetail = descDetail;
    }
}
