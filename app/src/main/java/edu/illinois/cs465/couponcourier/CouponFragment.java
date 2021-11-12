package edu.illinois.cs465.couponcourier;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.w3c.dom.Text;

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

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_coupon, container, false);
//    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_coupon, container, false);

        Button redeem_button = (Button) root.findViewById(R.id.redeem_button);
        ImageButton screenshot_button = (ImageButton) root.findViewById(R.id.screenshot_button);
        ImageButton copy_button = (ImageButton) root.findViewById(R.id.copy_button);

        redeem_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                confirmRedemption(root);
            }
        });

        screenshot_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                takeScreenshot();
            }
        });

        copy_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView coupon_code_text = (TextView) root.findViewById(R.id.coupon_code_text);
                copyCode(coupon_code_text.getText().toString());
            }
        });

        ImageView couponLogo =(ImageView) root.findViewById(R.id.couponLogo);
        couponLogo.setImageResource(getResources().getIdentifier("@drawable/nike_logo", null, getActivity().getPackageName()));

        LinearLayout instore_block =(LinearLayout) root.findViewById(R.id.instore_block);
        LinearLayout online_block =(LinearLayout) root.findViewById(R.id.online_block);
        LinearLayout military_block =(LinearLayout) root.findViewById(R.id.military_block);
        LinearLayout stackable_block =(LinearLayout) root.findViewById(R.id.stackable_block);

        TextView brand_text = (TextView) root.findViewById(R.id.brand_text);
        TextView discount_text = (TextView) root.findViewById(R.id.discount_text);
        TextView exp_text = (TextView) root.findViewById(R.id.exp_text);
        brand_text.setText("Nike");
        discount_text.setText("20% Off");
        exp_text.setText("Exp. 12/31/2021");

        instore_block.setVisibility(View.VISIBLE);
        online_block.setVisibility(View.VISIBLE);
        military_block.setVisibility(View.VISIBLE);
        stackable_block.setVisibility(View.VISIBLE);

        TextView additional_restrictions = (TextView) root.findViewById(R.id.additional_restrictions);
        additional_restrictions.setText("blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah ");

        return root;
    }

    public void confirmRedemption(ViewGroup root) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle("ARE YOU SURE YOU'D LIKE TO REDEEM THIS COUPON?");
//        builder.setMessage("Message");
        builder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println("CONFIRM");
                        Button redeem_button = (Button) root.findViewById(R.id.redeem_button);
                        ImageButton screenshot_button = (ImageButton) root.findViewById(R.id.screenshot_button);
                        ImageButton copy_button = (ImageButton) root.findViewById(R.id.copy_button);
                        TextView coupon_code_title = (TextView) root.findViewById(R.id.coupon_code_title);
                        TextView coupon_code_text = (TextView) root.findViewById(R.id.coupon_code_text);

                        redeem_button.setVisibility(View.GONE);
                        screenshot_button.setVisibility(View.VISIBLE);
                        copy_button.setVisibility(View.VISIBLE);
                        coupon_code_title.setVisibility(View.VISIBLE);
                        coupon_code_text.setVisibility(View.VISIBLE);
                        coupon_code_text.setText("CS465ISAWESOME");
                    }
                });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println("REJECT");
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }



    protected static File screenshot(View view, String filename) {
        Date date = new Date();

        // Here we are initialising the format of our image name
        CharSequence format = android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", date);
        try {
            // Initialising the directory of storage
            String dirpath = Environment.getExternalStorageDirectory() + "";
            File file = new File(dirpath);
            if (!file.exists()) {
                boolean mkdir = file.mkdir();
            }

            // File name
            String path = dirpath + "/" + filename + "-" + format + ".jpeg";
            view.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);
            File imageurl = new File(path);
            FileOutputStream outputStream = new FileOutputStream(imageurl);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
            outputStream.flush();
            outputStream.close();
            return imageurl;

        } catch (FileNotFoundException io) {
            io.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void takeScreenshot(){
        Toast.makeText(getActivity(), "You just Captured a Screenshot," +
                " Open Gallery/ File Storage to view your captured Screenshot", Toast.LENGTH_SHORT).show();
        screenshot(getActivity().getWindow().getDecorView().getRootView(), "result");
    }

    public void copyCode(String text){
        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Coupon Code", text);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(getActivity(), "Copied code to clipboard!", Toast.LENGTH_SHORT).show();
    }
}
