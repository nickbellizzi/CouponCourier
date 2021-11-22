package edu.illinois.cs465.couponcourier;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

//https://www.javatpoint.com/android-custom-listview
public class CouponAdapter extends ArrayAdapter<Coupon> {
    private final ArrayList<Coupon> results;
    private final Activity context;

    public CouponAdapter(Activity context, ArrayList<Coupon> results) {
        super(context, R.layout.coupon_list, results);

        this.results = results;
        this.context = context;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.coupon_list, null, true);

        Coupon c = results.get(position);

        TextView title = rowView.findViewById(R.id.title);
        TextView brand = rowView.findViewById(R.id.brand);
        ImageView icon = rowView.findViewById(R.id.coupon_icon);
        TextView exp = rowView.findViewById(R.id.exp);

        title.setText(c.deal);
        brand.setText(c.brand);
        String img = MainActivity.logos.get(c.brand);
        icon.setImageResource(rowView.getResources().getIdentifier(img, null, this.context.getPackageName()));
        String expStr = "Exp: " + c.expDate;
        exp.setText(expStr);
        setIcons(rowView, c);

        return rowView;
    }

    private static void setIcons(View rowView, Coupon c) {

        boolean inPerson = c.attributes.get("In-Store");
        boolean online = c.attributes.get("Online");
        boolean military = c.attributes.get("MilitaryID");
        boolean stackable = c.attributes.get("Stackable");

        ImageView ip = rowView.findViewById(R.id.instore_image);
        if (inPerson) {
            ip.setVisibility(View.VISIBLE);
        } else {
            ip.setVisibility(View.GONE);
        }

        ImageView ml = rowView.findViewById(R.id.military_image);
        if (military) {
            ml.setVisibility(View.VISIBLE);
        } else {
            ml.setVisibility(View.GONE);
        }

        ImageView ol = rowView.findViewById(R.id.online_image);
        if (online) {
            ol.setVisibility(View.VISIBLE);
        } else {
            ol.setVisibility(View.GONE);
        }

        ImageView st = rowView.findViewById(R.id.stackable_image);
        if (stackable) {
            st.setVisibility(View.VISIBLE);
        } else {
            st.setVisibility(View.GONE);
        }
    }
}
