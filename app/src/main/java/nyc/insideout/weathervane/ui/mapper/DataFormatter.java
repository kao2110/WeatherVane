package nyc.insideout.weathervane.ui.mapper;

public interface DataFormatter {

    String formatDate(long time);

    String formatTemperature(double tempValue);

    String formatImgUrl(String iconId);

}
