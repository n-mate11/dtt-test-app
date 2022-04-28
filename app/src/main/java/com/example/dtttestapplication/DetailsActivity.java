package com.example.dtttestapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.FragmentContainerView;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.NumberFormat;
import java.util.Locale;

public class DetailsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private AppCompatImageButton backButton;
    private ImageView imageView;
    private TextView priceText, bedText, bathText, layersText, distanceText, descriptionText;

    double latitude, longitude;
    double currentLatitude, currentLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        backButton = findViewById(R.id.details_back_button);
        imageView = findViewById(R.id.details_image);
        priceText = findViewById(R.id.details_price);
        bedText = findViewById(R.id.details_bedroom);
        bathText = findViewById(R.id.details_bathroom);
        layersText = findViewById(R.id.details_layers);
        distanceText = findViewById(R.id.details_distance);
        descriptionText = findViewById(R.id.description_text);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailsActivity.super.onBackPressed();
            }
        });

        Intent intent = getIntent();
        HouseCardModel model = (HouseCardModel) intent.getSerializableExtra("model");
        currentLatitude = intent.getDoubleExtra("latitude", -1);
        currentLongitude = intent.getDoubleExtra("longitude", -1);

        Locale usa = new Locale("en", "US");
        NumberFormat dollarFormat = NumberFormat.getCurrencyInstance(usa);
        dollarFormat.setMaximumFractionDigits(0);

        new DownloadImageTask(imageView).execute(model.getImage());

        String distance = model.getDistance() + " km";
        priceText.setText(dollarFormat.format(model.getPrice()));
        bedText.setText(String.valueOf(model.getBedroom()));
        bathText.setText(String.valueOf(model.getBathroom()));
        layersText.setText(String.valueOf(model.getLayers()));
        distanceText.setText(distance);
        descriptionText.setText(String.valueOf(model.getDescription()));

        latitude = model.getLatitude();
        longitude = model.getLongitude();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        System.out.println(latitude + ", " + longitude);
        LatLng latLng = new LatLng(latitude, longitude);
        googleMap.getUiSettings().setScrollGesturesEnabled(false);
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                String url = "https://maps.google.com/?saddr=" + currentLatitude + "," + currentLongitude + "&daddr=" + latitude + "," + longitude;
                Intent intentMaps = new Intent(android.content.Intent.ACTION_VIEW,  Uri.parse(url));
                startActivity(intentMaps);
            }
        });
        googleMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13f));
    }
}