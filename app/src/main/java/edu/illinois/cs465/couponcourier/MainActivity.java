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
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static protected ArrayList<Coupon> couponCollection = new ArrayList<>();
    static protected JSONArray jsonArr = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize Bottom Navigation View.

        BottomNavigationView navView = (BottomNavigationView) findViewById(R.id.bottomNav_view);


        //Pass the ID's of Different destinations
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_search, R.id.navigation_coupon, R.id.navigation_upload)
                .build();

        //Initialize NavController.
        NavController navController = Navigation.findNavController(this, R.id.navHostFragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        // Load the JSON file and fill local databases.
        loadJSONFromAsset();
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
        Log.d("MainActivity", "Initialization success");
    }

    protected String loadJSONFromAsset() {
        String jsonStr = "";
        try {
            InputStream is = getAssets().open("coupcour_data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonStr = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        Log.d("JSON_Load_Please", jsonStr);
        // JSONArray jArr = new JSONArray(json);
        try {
            jsonArr = new JSONArray(jsonStr);
            int len = jsonArr.length();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return jsonStr;
    }

}