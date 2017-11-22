package nyc.insideout.weathervane.ui.model;

/**
 * Data object used to display forecast details for a single day.
 * Data doesn't need to be manipulated so fields are public and final.
 */
public class ForecastDetailViewModel extends ForecastViewModel {

    public final int humidity;
    public final String descDetail;

    public ForecastDetailViewModel(final String date, final String tempMax, final String tempMin, final int forecastId,
                                   final String desc, int humidity, String descDetail, final String iconUrl, final long dateUnixTime){
        super(date, tempMax, tempMin, forecastId, desc, iconUrl, dateUnixTime);
        this.humidity = humidity;
        this.descDetail = descDetail;
    }
}
