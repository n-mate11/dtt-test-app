package com.example.dtttestapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OverviewAdapter extends RecyclerView.Adapter<OverviewAdapter.ViewHolder> {

    private Context context;
    private ArrayList<HouseCardModel> houseCardModelArrayList;

    public OverviewAdapter(Context context, ArrayList<HouseCardModel> houseCardModelArrayList) {
        this.context = context;
        this.houseCardModelArrayList = houseCardModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_house, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            HouseCardModel model = houseCardModelArrayList.get(position);
            holder.house_image.setImageResource(model.getImage());
            holder.house_price.setText(String.valueOf(model.getPrice()));
            holder.house_address.setText(model.getAddress());
            holder.house_bedroom.setText(String.valueOf(model.getBedroom()));
            holder.house_bathroom.setText(String.valueOf(model.getBathroom()));
            holder.house_layers.setText(String.valueOf(model.getLayers()));
            holder.house_distance.setText(model.getDistance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return houseCardModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView house_image;
        private TextView house_price, house_address, house_bedroom, house_bathroom, house_layers, house_distance;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            house_image = itemView.findViewById(R.id.card_image);
            house_price = itemView.findViewById(R.id.card_cost);
            house_address = itemView.findViewById(R.id.card_location);
            house_bedroom = itemView.findViewById(R.id.card_bedroom);
            house_bathroom = itemView.findViewById(R.id.card_bathroom);
            house_layers = itemView.findViewById(R.id.card_layers);
            house_distance = itemView.findViewById(R.id.card_distance);
        }
    }
}
