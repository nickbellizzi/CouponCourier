package edu.illinois.cs465.couponcourier;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class Coupon {
    public int couponId = 0;
    public String brand = "";
    public String product = "";
    public List<String> category = new ArrayList<>();
    public String type = "";
    public String deal = "";
    public String code = "";
    public String expDate = "";
    public String uploadDate = "";
    public HashMap<String, Boolean> attributes = new HashMap<>();
    public String additionalInfo = "";

    Coupon(JSONObject jsonCoupon) {
        try {
            this.couponId = jsonCoupon.getInt("CouponID");
            this.brand = jsonCoupon.getString("Brand");
            this.product = jsonCoupon.getString("ProductName");
            this.category = getCategoryList(jsonCoupon.getJSONArray("Category"));
            this.type = jsonCoupon.getString("Type");
            this.code = jsonCoupon.getString("Code");
            this.deal = jsonCoupon.getString("Deal");
            this.expDate = jsonCoupon.getString("ExpDate");
            this.uploadDate = jsonCoupon.getString("UploadDate");
            this.additionalInfo = jsonCoupon.getJSONObject("Attributes").optString("Additional");

            JSONObject attr = jsonCoupon.getJSONObject("Attributes");
            this.attributes.put("In-Store", attr.getBoolean("In-Store"));
            this.attributes.put("Online", attr.getBoolean("Online"));
            this.attributes.put("MilitaryID", attr.getBoolean("MilitaryID"));
            this.attributes.put("Stackable", attr.getBoolean("Stackable"));

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("Coupon Object", "Unable to create a coupon object!");
        }
    }

    Coupon() {}

    Coupon(String brand, String product, ArrayList<String> category, String type, String code, String deal, String expDate, String uploadDate, String additionalInfo, Map<String, Boolean> attributes){
        this.couponId = 1; // can make this randomly generated if needed
        this.brand = brand;
        this.product = product;
        this.category = new ArrayList<>(category);
        this.type = type;
        this.code = code;
        this.deal = deal;
        this.expDate = expDate;
        this.uploadDate = uploadDate;
        this.additionalInfo = additionalInfo;
        this.attributes = new HashMap<>(attributes);
    }

    private static List<String> getCategoryList(JSONArray jsonCat) {
        List<String> output = new ArrayList<>();
        for (int i = 0; i < jsonCat.length(); ++i) {
            try {
                output.add(jsonCat.getString(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return output;
    }

    @Override
    public String toString() {
        String out_product = "";
        if (!product.isEmpty()) out_product = " (" + String.valueOf(product) + ')';
        String output = brand + ": " + deal + out_product;
        return output;
    }
}
