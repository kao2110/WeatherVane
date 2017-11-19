package nyc.insideout.weathervane.ui.model;

/**
 * Data object used to display forecast for a single day.
 * Data doesn't need to be manipulated so fields are public and final.
 */
public class ForecastViewModel {

    public final long date;
    public final double tempMax;
    public final double tempMin;
    public final int forecastId;
    public final String desc;

    public ForecastViewModel(final long date, final double tempMax, final double tempMin, final int forecastId,
                    final String desc){
        this.date = date;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
        this.forecastId = forecastId;
        this.desc = desc;
    }
}
