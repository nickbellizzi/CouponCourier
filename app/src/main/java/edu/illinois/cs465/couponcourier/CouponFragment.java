package edu.illinois.cs465.couponcourier;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CouponFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class CouponFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CouponFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CouponFragment newInstance(String param1, String param2) {
        CouponFragment fragment = new CouponFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public CouponFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        //ActionBar ab = getActivity().getActionBar();
        //ab.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String brand = "No brand listed";
        String discount = "No discount listed";
        String expiration = "None listed";
        String restrictions = "No additional restrictions listed!";
        Boolean in_store_bool = Boolean.FALSE;
        Boolean online_bool = Boolean.FALSE;
        Boolean military_bool = Boolean.FALSE;
        Boolean stackable_bool = Boolean.FALSE;

        try {
            Coupon coupon = MainActivity.couponCollection.get(MainActivity.coupon_index);
            in_store_bool = coupon.attributes.get("In-Store");
            online_bool = coupon.attributes.get("Online");
            military_bool = coupon.attributes.get("MilitaryID");
            stackable_bool = coupon.attributes.get("Stackable");
            if (!coupon.expDate.isEmpty()) expiration = coupon.expDate;
            if (!coupon.brand.isEmpty()) brand = coupon.brand;
            if (!coupon.deal.isEmpty()) discount = coupon.deal;
            if (!coupon.additionalInfo.isEmpty()) restrictions = coupon.additionalInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }



        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_coupon, container, false);

        Button redeem_button = (Button) root.findViewById(R.id.redeem_button);
        ImageButton screenshot_button = (ImageButton) root.findViewById(R.id.screenshot_button);
        ImageButton copy_button = (ImageButton) root.findViewById(R.id.copy_button);

        redeem_button.setOnClickListener(v -> confirmRedemption(root));

        screenshot_button.setOnClickListener(view -> {
            Bitmap bitmap = takeScreenshot();
            saveBitmap(bitmap);
        });

        copy_button.setOnClickListener(v -> {
            TextView coupon_code_text = (TextView) root.findViewById(R.id.coupon_code_text);
            copyCode(coupon_code_text.getText().toString());
        });

        ImageView couponLogo =(ImageView) root.findViewById(R.id.couponLogo);
        String img = (String) MainActivity.logos.get(brand);
        couponLogo.setImageResource(getResources().getIdentifier(img, null, getActivity().getPackageName()));

        LinearLayout instore_block =(LinearLayout) root.findViewById(R.id.instore_block);
        LinearLayout online_block =(LinearLayout) root.findViewById(R.id.online_block);
        LinearLayout military_block =(LinearLayout) root.findViewById(R.id.military_block);
        LinearLayout stackable_block =(LinearLayout) root.findViewById(R.id.stackable_block);

        TextView brand_text = (TextView) root.findViewById(R.id.brand_text);
        TextView discount_text = (TextView) root.findViewById(R.id.discount_text);
        TextView exp_text = (TextView) root.findViewById(R.id.exp_text);
        brand_text.setText(brand);
        discount_text.setText(discount);
        exp_text.setText("Exp. " + expiration);

        if (in_store_bool) instore_block.setVisibility(View.VISIBLE);
        if (online_bool) online_block.setVisibility(View.VISIBLE);
        if (military_bool) military_block.setVisibility(View.VISIBLE);
        if (stackable_bool) stackable_block.setVisibility(View.VISIBLE);

        TextView additional_restrictions = (TextView) root.findViewById(R.id.additional_restrictions);
        additional_restrictions.setText(restrictions);

        return root;
    }

    public void confirmRedemption(ViewGroup root) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle("ARE YOU SURE YOU'D LIKE TO REDEEM THIS COUPON?");
//        builder.setMessage("Message");
        builder.setPositiveButton("Confirm",
                (dialog, which) -> {
                    System.out.println("CONFIRM");
                    String coupon_code = "";

                    try {
                        Coupon c = MainActivity.couponCollection.get(MainActivity.coupon_index);
                        coupon_code = c.code;
                        System.out.println(c);
                        MainActivity.couponCollection.remove(MainActivity.coupon_index);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    Button redeem_button = (Button) root.findViewById(R.id.redeem_button);
                    ImageButton screenshot_button = (ImageButton) root.findViewById(R.id.screenshot_button);
                    ImageButton copy_button = (ImageButton) root.findViewById(R.id.copy_button);
                    TextView coupon_code_title = (TextView) root.findViewById(R.id.coupon_code_title);
                    TextView coupon_code_text = (TextView) root.findViewById(R.id.coupon_code_text);
                    LinearLayout coupon_code_container = (LinearLayout) root.findViewById(R.id.coupon_code_container);

                    redeem_button.setVisibility(View.GONE);
                    screenshot_button.setVisibility(View.VISIBLE);
                    copy_button.setVisibility(View.VISIBLE);
                    coupon_code_title.setVisibility(View.VISIBLE);
                    coupon_code_text.setVisibility(View.VISIBLE);
                    coupon_code_text.setText(coupon_code);
                    coupon_code_container.setVisibility(View.VISIBLE);
                });
        builder.setNegativeButton(android.R.string.cancel, (dialog, which) -> System.out.println("REJECT"));

        AlertDialog dialog = builder.create();
        dialog.show();
    }


//    public Bitmap takeScreenshot(){
//        try {
//            Runtime.getRuntime().exec("input keyevent 120");
//            Toast.makeText(getActivity(), Html.fromHtml("You can review your code <b>here</b>"), Toast.LENGTH_LONG).show();
//        } catch (IOException e) {
//            e.printStackTrace();
//            Toast.makeText(getActivity(), Html.fromHtml("Error saving: <b>" + e + "</b>"), Toast.LENGTH_LONG).show();
//        }
//        return null;
//    }

    public Bitmap takeScreenshot() {
        View rootView = getView().getRootView();
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();
    }

    public void saveBitmap(Bitmap bitmap) {
        File imagePath = new File(Environment.getExternalStorageDirectory() + "/screenshot.png");
        FileOutputStream fos;

        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            Toast.makeText(getActivity(), Html.fromHtml("You can review your code here <b>" + imagePath.getPath() + "</b>"), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
            Toast.makeText(getActivity(), Html.fromHtml("Error saving: <b>" + e + "</b>"), Toast.LENGTH_LONG).show();
        }
    }

    public void copyCode(String text){
        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Coupon Code", text);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(getActivity(), "Copied code to clipboard!", Toast.LENGTH_SHORT).show();
    }
}
