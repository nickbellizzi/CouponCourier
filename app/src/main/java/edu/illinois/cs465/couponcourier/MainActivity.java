package edu.illinois.cs465.couponcourier;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    // Global collection of Coupon objects.
    static protected ArrayList<Coupon> couponCollection = new ArrayList<>();

    // JSON array for whatever reason
    static protected JSONArray jsonArr = null;

    //
    static protected Map<String, String> logos = new HashMap<>();

    // This object handles search parameters across fragments.
    static protected SearchQuery currentQuery = new SearchQuery();
    static protected int coupon_index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize Bottom Navigation View.

        BottomNavigationView navView = (BottomNavigationView) findViewById(R.id.bottomNav_view);


        //Pass the ID's of Different destinations
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_search, R.id.navigation_upload)
                .build();

        //Initialize NavController.
        NavController navController = Navigation.findNavController(this, R.id.navHostFragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }
    protected void populateLogoMap() {
        logos.put("Adidas", "@drawable/adidas_logo");
        logos.put("Crocs", "@drawable/crocs_logo");
        logos.put("Jordans", "@drawable/jordan_logo");
        logos.put("New Balance", "@drawable/new_balance_logo");
        logos.put("Nike", "@drawable/nike_logo");
        logos.put("Asics", "@drawable/asics_logo");
        logos.put("Under Armor", "@drawable/under_armour_logo");
        logos.put("Vans", "@drawable/vans_logo");
    }
    protected String loadJSONFromAsset() {
        String jsonStr;
        try {
            InputStream is = getAssets().open("coupcour_data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonStr = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        try {
            jsonArr = new JSONArray(jsonStr);
            //int len = jsonArr.length();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return jsonStr;
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Load the JSON file and fill local databases.
        loadJSONFromAsset();
        populateLogoMap();
        for (int i = 0; i < jsonArr.length(); ++i) {
            try {
                JSONObject jsonCoupon = jsonArr.getJSONObject(i);
                Coupon c = new Coupon(jsonCoupon);
                couponCollection.add(c);
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
        Log.d("MainActivity", "Initialization success, onResume()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Stopped", "The app stopped");
        couponCollection.clear();
    }

}