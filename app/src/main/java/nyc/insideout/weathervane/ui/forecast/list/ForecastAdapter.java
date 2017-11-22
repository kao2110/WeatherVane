package nyc.insideout.weathervane.ui.forecast.list;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nyc.insideout.weathervane.R;
import nyc.insideout.weathervane.ui.model.ForecastViewModel;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>{


    private List<ForecastViewModel> forecastItems = new ArrayList<>();

    @Override
    public ForecastViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.forecast_row_item_layout, parent, false);

        return new ForecastViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ForecastViewHolder holder, int position){
        ForecastViewModel forecastViewModel = forecastItems.get(position);
        holder.forecastImg.setImageResource(R.mipmap.ic_launcher_round);
        holder.forecastDate.setText(forecastViewModel.date);
        holder.forecastDesc.setText(forecastViewModel.desc);
        holder.forecastTempMax.setText(forecastViewModel.tempMax);
        holder.forecastTempMin.setText(forecastViewModel.tempMin);
    }

    @Override
    public int getItemCount(){
        return forecastItems.size();
    }

    public void setForecastItems(List<ForecastViewModel> newItems){
        this.forecastItems = newItems;
        notifyDataSetChanged();
    }

    public class ForecastViewHolder extends RecyclerView.ViewHolder{

        public ImageView forecastImg;
        public TextView forecastDate;
        public TextView forecastDesc;
        public TextView forecastTempMax;
        public TextView forecastTempMin;

        public ForecastViewHolder(View view){
            super(view);
            forecastImg = view.findViewById(R.id.forecast_img);
            forecastDate = view.findViewById(R.id.forecast_date);
            forecastDesc = view.findViewById(R.id.forecast_desc);
            forecastTempMax = view.findViewById(R.id.forecast_tempHi);
            forecastTempMin = view.findViewById(R.id.forecast_tempLo);
        }
    }
}
