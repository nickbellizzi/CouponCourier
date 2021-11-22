package edu.illinois.cs465.couponcourier;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


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

        TextView title = (TextView) rowView.findViewById(R.id.title);
        TextView brand = (TextView) rowView.findViewById(R.id.brand);
        ImageView icon = (ImageView) rowView.findViewById(R.id.coupon_icon);
        TextView exp = (TextView) rowView.findViewById(R.id.exp);

        title.setText(c.deal);
        brand.setText(c.brand);
        String img = (String) MainActivity.logos.get(c.brand);
        //int iconId = view.getResources().getIdentifier(img);
        icon.setImageResource(rowView.getResources().getIdentifier(img, null, this.context.getPackageName()));
        String expStr = "Exp: " + c.expDate;
        exp.setText(expStr);

        return rowView;
    }
}
