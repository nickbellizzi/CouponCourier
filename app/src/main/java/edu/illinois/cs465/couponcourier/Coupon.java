package edu.illinois.cs465.couponcourier;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class Coupon {
    public int couponId = 0;
    public String brand;
    public String product;
    public List<String> category = new ArrayList<>();
    public String type;
    public String deal;
    public String code;
    public String expDate;
    public String uploadDate;
    public HashMap<String, Boolean> attributes = new HashMap<>();
    public String additionalInfo;

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
            this.additionalInfo = jsonCoupon.optString("Additional");

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
}
