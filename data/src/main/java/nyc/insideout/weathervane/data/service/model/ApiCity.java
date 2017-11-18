package nyc.insideout.weathervane.data.service.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiCity {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("coord")
    @Expose
    private ApiLatLong apiLatLong;

    @SerializedName("country")
    @Expose
    private String country;

    @SerializedName("population")
    @Expose
    private Integer population;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ApiLatLong getApiLatLong() {
        return apiLatLong;
    }

    public void setApiLatLong(ApiLatLong apiLatLong) {
        this.apiLatLong = apiLatLong;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }
}
