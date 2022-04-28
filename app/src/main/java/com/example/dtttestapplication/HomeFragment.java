package com.example.dtttestapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.net.ssl.HttpsURLConnection;

public class HomeFragment extends Fragment {
    @Override
    public void onStart() {
        super.onStart();
    }

    private static final String api_url = "https://intern.development.d-tt.dev/api/house";
    private static final String api_key = "98bww4ezuzfePCYFxJEWyszbUXc7dxRx";

    private CircularProgressIndicator circularProgressIndicator;
    private RecyclerView recyclerView;
    private EditText editText;
    private ImageButton clearSearch;
    private ImageView searchIcon, noResultsImage;
    private TextView noResultsText;
    private ArrayList<HouseCardModel> houseCardModelArrayList = new ArrayList<>();
    private ArrayList<HouseCardModel> filteredList = new ArrayList<>();
    OverviewAdapter overviewAdapter;

    LocationRequest mLocationRequest;
    LocationCallback mLocationCallback;
    FusedLocationProviderClient fusedLocationProviderClient;
    Location currentLocation = new Location("currentLocation");

    public HomeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        circularProgressIndicator = view.findViewById(R.id.progress_circular);
        recyclerView = view.findViewById(R.id.overview_list);
        editText = view.findViewById(R.id.search_field);
        searchIcon = view.findViewById(R.id.search_icon);
        clearSearch = view.findViewById(R.id.search_clear_button);
        noResultsImage = view.findViewById(R.id.no_results_image);
        noResultsText = view.findViewById(R.id.no_results_text);

        new MyAsyncTask().execute("");

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filteredList.clear();

                if (s.toString().isEmpty()) {
                    noResultsImage.setVisibility(View.GONE);
                    noResultsText.setVisibility(View.GONE);
                    clearSearch.setVisibility(View.GONE);
                    searchIcon.setVisibility(View.VISIBLE);
                    recyclerView.setAdapter(new OverviewAdapter(requireContext(), houseCardModelArrayList));
                    overviewAdapter.notifyDataSetChanged();
                } else {
                    clearSearch.setVisibility(View.VISIBLE);
                    searchIcon.setVisibility(View.GONE);
                    Filter(s.toString());
                }
            }
        });

        clearSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
                clearSearch.setVisibility(View.GONE);
            }
        });

        overviewAdapter = new OverviewAdapter(view.getContext(), houseCardModelArrayList);

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(overviewAdapter);

        return view;
    }

    private void Filter(String text) {
        for (HouseCardModel house : houseCardModelArrayList) {
            if (house.getCity().toLowerCase().startsWith(text.toLowerCase())) {
                filteredList.add(house);
            } else if (house.getZip().toLowerCase().startsWith(text.toLowerCase())) {
                filteredList.add(house);
            }
        }

        if (filteredList.isEmpty() ) {
            noResultsImage.setVisibility(View.VISIBLE);
            noResultsText.setVisibility(View.VISIBLE);
        } else {
            noResultsImage.setVisibility(View.GONE);
            noResultsText.setVisibility(View.GONE);
        }

        recyclerView.setAdapter(new OverviewAdapter(requireContext(), filteredList));
        overviewAdapter.notifyDataSetChanged();
    }

    class MyAsyncTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            circularProgressIndicator.setProgress(values[0]);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            LocationRequest mLocationRequest = LocationRequest.create();
            mLocationRequest.setInterval(60000);
            mLocationRequest.setFastestInterval(5000);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            mLocationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    if (locationResult == null) {
                        System.out.println("locationResult == null");
                        return;
                    }
                    for (Location location : locationResult.getLocations()) {
                        if (location != null) {
                            //TODO: UI updates.
                            System.out.println(location.getLatitude());
                            System.out.println(location.getLongitude());
                            currentLocation = location;
                            overviewAdapter.setLocation(location);
                        }
                    }
                }
            };

            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext());
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                LocationServices.getFusedLocationProviderClient(requireContext()).requestLocationUpdates(mLocationRequest, mLocationCallback, null);
                getLastLocation();
            } else {
                askLocationPermission();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            overviewAdapter.notifyDataSetChanged();
        }

        @Override
        protected String doInBackground(String... strings) {
            JSONArray response_body = getDataFromAPI();
            try {
                for(int i = 0; i < response_body.length(); i++) {
                    JSONObject object = response_body.getJSONObject(i);

                    Location houseLocation = new Location("houseLocation");
                    houseLocation.setLatitude(object.getDouble("latitude"));
                    houseLocation.setLatitude(object.getDouble("longitude"));

                    double distance = currentLocation.distanceTo(houseLocation) / 1000;
                    distance = Math.round(distance * 10.0) / 10.0;

                    houseCardModelArrayList.add(new HouseCardModel(
                                    object.getInt("id"),
                                    object.getInt("price"),
                                    object.getString("zip"),
                                    object.getString("city"),
                                    object.getString("image"),
                                    object.getInt("bedrooms"),
                                    object.getInt("bathrooms"),
                                    object.getInt("size"),
                                    object.getString("description"),
                                    object.getDouble("latitude"),
                                    object.getDouble("longitude"),
                                    object.getString("createdDate"),
                                    distance));
                }

                Collections.sort(houseCardModelArrayList, new SortByPrice());
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            }
            return null;
        }
    }

    private JSONArray getDataFromAPI() {
        JSONArray jsonArray = null;
        try {
            URL url = new URL(api_url);
            URLConnection urlConnection = url.openConnection();
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) urlConnection;
            httpsURLConnection.setRequestMethod("GET");
            httpsURLConnection.setAllowUserInteraction(false);
            httpsURLConnection.setInstanceFollowRedirects(true);
            httpsURLConnection.setRequestProperty("Access-Key", api_key);
            httpsURLConnection.connect();

            int response_code = httpsURLConnection.getResponseCode();

            BufferedReader br;
            if (response_code == 200) {
                br = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));
                String response;
                while ((response = br.readLine()) != null) {
                    jsonArray = new JSONArray(response);
                }
            } else {
                br = new BufferedReader(new InputStreamReader(httpsURLConnection.getErrorStream()));
                String strCurrentLine;
                while ((strCurrentLine = br.readLine()) != null) {
                    System.out.println(strCurrentLine);
                }
                System.out.println("Error: " + response_code);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException jsonException) {
            Log.e("MYAPP", "unexpected JSON exception", jsonException);
        }
        return jsonArray;
    }

    class SortByPrice implements Comparator<HouseCardModel> {
        public int compare(HouseCardModel a, HouseCardModel b) {
            return a.getPrice() - b.getPrice();
        }
    }

    private void askLocationPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                Log.d("LOCATION", "askLocationPermission: you should show an alert dialog...");
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 10001);
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 10001);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 10001) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                // access denied
            }
        }
    }

    private void getLastLocation() {
        Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();
        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    System.out.println(location.getLatitude());
                    System.out.println(location.getLongitude());
                    currentLocation = location;
                } else {
                    System.out.println("location is null...");
                }
            }
        });

        locationTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("failure occurred with locationTask...");
            }
        });
    }
}









