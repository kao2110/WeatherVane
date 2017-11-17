package nyc.insidout.weathervane.data.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiWeatherData {

    @SerializedName("city")
    @Expose
    private ApiCity apiCity;

    @SerializedName("cod")
    @Expose
    private String cod;

    @SerializedName("message")
    @Expose
    private Double message;

    @SerializedName("cnt")
    @Expose
    private Integer cnt;

    @SerializedName("list")
    @Expose
    private List<ApiForecastItem> apiForecastList = null;

    public ApiCity getApiCity() {
        return apiCity;
    }

    public void setApiCity(ApiCity city) {
        this.apiCity = city;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public Double getMessage() {
        return message;
    }

    public void setMessage(Double message) {
        this.message = message;
    }

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public java.util.List<ApiForecastItem> getApiForecastList() {
        return apiForecastList;
    }

    public void setApiForecastList(List<ApiForecastItem> list) {
        this.apiForecastList = list;
    }
}
