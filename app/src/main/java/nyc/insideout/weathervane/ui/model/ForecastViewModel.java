package nyc.insideout.weathervane.ui.model;

/**
 * Data object used to display forecast for a single day.
 * Data doesn't need to be manipulated so fields are public and final.
 */
public class ForecastViewModel {

    public final String date;
    public final long dateUnixTime;
    public final String tempMax;
    public final String tempMin;
    public final int forecastId;
    public final String desc;
    public final String forecastIconUrl;

    public ForecastViewModel(final String date, final String tempMax, final String tempMin, final int forecastId,
                    final String desc, final String iconId, final long dateUnixTime){
        this.date = date;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
        this.forecastId = forecastId;
        this.desc = desc;
        this.forecastIconUrl = iconId;
        this.dateUnixTime = dateUnixTime;
    }
}
