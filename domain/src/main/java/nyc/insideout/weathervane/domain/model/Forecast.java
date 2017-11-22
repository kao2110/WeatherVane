package nyc.insideout.weathervane.domain.model;


public class Forecast {

    public final long date;
    public final double tempMax;
    public final double tempMin;
    public final int forecastId;
    public final String desc;
    public final String forecastIconId;

    public Forecast(final long date, final double tempMax, final double tempMin, final int forecastId,
                    final String desc, final String iconId){
        this.date = date;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
        this.forecastId = forecastId;
        this.desc = desc;
        this.forecastIconId = iconId;
    }

}
