package nyc.insideout.weathervane.data.database.model;

/**
 * Data model used to store weather data for a single day in local database.
 */
public class WeatherData {

    //internal id of city used by API
    private int cityId;

    //text name of city
    private String cityName;

    //time stamp of data in unix time format
    private long date;

    private int humidity;

    //max temp for day
    private double tempMax;

    //min temp for day
    private double tempMin;

    private double pressure;

    private int windDirection;

    private double windSpeed;

    //short form of forecast description
    private String forecastDesc;

    //long form of forecast description
    private String forecastDescDetail;

    //id of forecast, used in display to match icon to forecast description
    private int forecastId;

    private String forecastIconId;

    public WeatherData(){

    }

    //constructor used for mapping data from API and for mapping data to domain
    public WeatherData(final int cityId, final String cityName, final long date, final int humidity,
                       final double tempMax, final double tempMin, final double pressure,
                       final int windDirection, final double windSpeed, final String forecastDesc,
                       final String forecastDescDetail, final int forecastId, final String iconId){

        this.cityId = cityId;
        this.cityName = cityName;
        this.date = date;
        this.humidity = humidity;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
        this.pressure = pressure;
        this.windDirection = windDirection;
        this.windSpeed = windSpeed;
        this.forecastDesc = forecastDesc;
        this.forecastDescDetail = forecastDescDetail;
        this.forecastId = forecastId;
        this.forecastIconId = iconId;
    }


    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public long getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getTempMax() {
        return tempMax;
    }

    public void setTempMax(double tempMax) {
        this.tempMax = tempMax;
    }

    public double getTempMin() {
        return tempMin;
    }

    public void setTempMin(double tempMin) {
        this.tempMin = tempMin;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public int getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(int windDirection) {
        this.windDirection = windDirection;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getForecastDesc() {
        return forecastDesc;
    }

    public void setForecastDesc(String forecastDesc) {
        this.forecastDesc = forecastDesc;
    }

    public String getForecastDescDetail() {
        return forecastDescDetail;
    }

    public void setForecastDescDetail(String forecastDescDetail) {
        this.forecastDescDetail = forecastDescDetail;
    }

    public int getForecastId() {
        return forecastId;
    }

    public void setForecastId(int forecastId) {
        this.forecastId = forecastId;
    }

    public String getForecastIconId() {
        return forecastIconId;
    }

    public void setForecastIconId(String forecastIconId) {
        this.forecastIconId = forecastIconId;
    }
}

