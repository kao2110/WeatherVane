package nyc.insideout.weathervane.domain.model;


public class Forecast {

    public final long date;
    public final double tempMax;
    public final double tempMin;
    public final int forecastId;
    public final String desc;

    public Forecast(final long date, final double tempMax, final double tempMin, final int forecastId,
                    final String desc){
        this.date = date;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
        this.forecastId = forecastId;
        this.desc = desc;
    }

}
