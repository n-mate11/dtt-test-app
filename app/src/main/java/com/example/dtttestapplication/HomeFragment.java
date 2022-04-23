package com.example.dtttestapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class HomeFragment extends Fragment {

    private static final String api_url = "https://intern.development.d-tt.dev/api/house";
    private static final String api_key = "98bww4ezuzfePCYFxJEWyszbUXc7dxRx";

    private RecyclerView recyclerView;
    private ArrayList<HouseCardModel> houseCardModelArrayList;

    public HomeFragment(){
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.overview_list);

        new MyAsyncTask().execute("");

        houseCardModelArrayList = new ArrayList<>();

        houseCardModelArrayList.add(new HouseCardModel(45000, "Amsterdam", R.drawable.ic_launcher_background, 2, 1, 1," 45.2"));
        houseCardModelArrayList.add(new HouseCardModel(12000, "Hilversum", R.drawable.ic_launcher_background, 5, 3, 12, "14.7"));

        OverviewAdapter overviewAdapter = new OverviewAdapter(view.getContext(), houseCardModelArrayList);

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(overviewAdapter);

        return view;
    }

    class MyAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            JSONArray response_body = getDataFromAPI();
            try {
                for(int i=0; i < response_body.length(); i++)
                {
                    JSONObject object = response_body.getJSONObject(i);
                    System.out.println(object.getString("id"));
                    System.out.println(object.getString("image"));
                    System.out.println(object.getString("description"));
                }
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
}









