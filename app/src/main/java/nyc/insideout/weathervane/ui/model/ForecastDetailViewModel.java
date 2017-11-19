package nyc.insideout.weathervane.ui.model;

/**
 * Data object used to display forecast details for a single day.
 * Data doesn't need to be manipulated so fields are public and final.
 */
public class ForecastDetailViewModel extends ForecastViewModel {

    public final int humidity;
    public final String descDetail;

    public ForecastDetailViewModel(final long date, final double tempMax, final double tempMin, final int forecastId,
                                   final String desc, int humidity, String descDetail){
        super(date, tempMax, tempMin, forecastId, desc);
        this.humidity = humidity;
        this.descDetail = descDetail;
    }
}
