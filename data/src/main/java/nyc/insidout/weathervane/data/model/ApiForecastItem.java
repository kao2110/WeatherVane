package nyc.insidout.weathervane.data.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiForecastItem {

    @SerializedName("dt")
    @Expose
    private Integer date;

    @SerializedName("temp")
    @Expose
    private ApiTemperature apiTemperature;

    @SerializedName("pressure")
    @Expose
    private Double pressure;
    @SerializedName("humidity")
    @Expose
    private Integer humidity;
    @SerializedName("weather")
    @Expose
    private List<ApiForecastDescription> descriptions = null;
    @SerializedName("speed")
    @Expose
    private Double speed;
    @SerializedName("deg")
    @Expose
    private Integer deg;
    @SerializedName("clouds")
    @Expose
    private Integer clouds;
    @SerializedName("rain")
    @Expose
    private Double rain;

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer dt) {
        this.date = dt;
    }

    public ApiTemperature getApiTemperature() {
        return apiTemperature;
    }

    public void setApiTemperature(ApiTemperature apiTemperature) {
        this.apiTemperature = apiTemperature;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public java.util.List<ApiForecastDescription> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(List<ApiForecastDescription> descriptions) {
        this.descriptions = descriptions;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Integer getDeg() {
        return deg;
    }

    public void setDeg(Integer deg) {
        this.deg = deg;
    }

    public Integer getClouds() {
        return clouds;
    }

    public void setClouds(Integer clouds) {
        this.clouds = clouds;
    }

    public Double getRain() {
        return rain;
    }

    public void setRain(Double rain) {
        this.rain = rain;
    }

}
