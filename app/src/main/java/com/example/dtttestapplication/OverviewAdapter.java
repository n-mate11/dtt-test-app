package com.example.dtttestapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

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

            Locale usa = new Locale("en", "US");
            NumberFormat dollarFormat = NumberFormat.getCurrencyInstance(usa);
            dollarFormat.setMaximumFractionDigits(0);

            new DownloadImageTask(holder.house_image).execute(model.getImage());;


            holder.house_price.setText(dollarFormat.format(model.getPrice()));
            String address = model.getZip() + " " + model.getCity();
            holder.house_address.setText(address);
            holder.house_bedroom.setText(String.valueOf(model.getBedroom()));
            holder.house_bathroom.setText(String.valueOf(model.getBathroom()));
            holder.house_layers.setText(String.valueOf(model.getLayers()));
            holder.house_distance.setText(String.valueOf(model.getDistance()));
            holder.card_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailsActivity.class);
                    intent.putExtra("model", model);
                    context.startActivity(intent);
                }
            });
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
        private ConstraintLayout card_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            house_image = itemView.findViewById(R.id.card_image);
            house_price = itemView.findViewById(R.id.card_cost);
            house_address = itemView.findViewById(R.id.card_location);
            house_bedroom = itemView.findViewById(R.id.card_bedroom);
            house_bathroom = itemView.findViewById(R.id.card_bathroom);
            house_layers = itemView.findViewById(R.id.card_layers);
            house_distance = itemView.findViewById(R.id.card_distance);
            card_layout = itemView.findViewById(R.id.card_layout);
        }
    }
}
