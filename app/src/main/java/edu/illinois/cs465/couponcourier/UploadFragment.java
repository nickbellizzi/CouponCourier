package edu.illinois.cs465.couponcourier;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.sql.Array;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UploadFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
// TODO: ADD CUSTOM SPINNER for multiple selections
// TODO: ADD MISSING COUPON FIELDS
public class UploadFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UploadFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UploadFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UploadFragment newInstance(String param1, String param2) {
        UploadFragment fragment = new UploadFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

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
        View view = inflater.inflate(R.layout.fragment_upload, container, false);

        Spinner brandSpinner = (Spinner) view.findViewById(R.id.brandSpinner);
        Spinner categorySpinner = (Spinner) view.findViewById(R.id.categorySpinner);
        Spinner typeSpinner = (Spinner) view.findViewById(R.id.typeSpinner);
        EditText expDatePicker = (EditText) view.findViewById(R.id.expDatePicker);
        Button uploadButton = (Button) view.findViewById(R.id.upload_button);

        Calendar calendar = Calendar.getInstance();


        Set<String> categories = new HashSet<>();
        Set<String> brands = new HashSet<>();
        Set<String> types = new HashSet<>();


        categories.addAll(SearchFragment.category_names);
        categories.remove("All Categories");
        types.addAll(SearchFragment.coupon_types);
        brands.addAll(SearchQuery.getUniqueBrands());
        brands.remove("All Brands");


        ArrayList<String> category = new ArrayList<>();
        category.addAll(categories);
        Collections.sort(category);
        category.add(0, "Select Category");

        ArrayList<String> brand = new ArrayList<>();
        brand.addAll(brands);
        Collections.sort(brand);
        brand.add(0, "Select Brand");


        ArrayList<String> type = new ArrayList<>();
        type.addAll(types);
        Collections.sort(type);
        type.add(0, "Select Type");


        String categoriesArray[] = new String[category.size()];
        category.toArray(categoriesArray);
        String brandsArray[] = new String[brand.size()];
        brand.toArray(brandsArray);
        String typesArray[] = new String[type.size()];
        type.toArray(typesArray);

        uploadButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                confirmUpload(view);
            }
        });

        DatePickerDialog.OnDateSetListener datePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateText(calendar, expDatePicker);
            }
        };

        expDatePicker.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(view.getContext(), datePicker, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        ArrayAdapter<String> brandArrayAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item, brandsArray);
        ArrayAdapter<String> categoryArrayAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item, categoriesArray);
        ArrayAdapter<String> typeArrayAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item, typesArray);

        brandArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        brandSpinner.setAdapter(brandArrayAdapter);
        categorySpinner.setAdapter(categoryArrayAdapter);
        typeSpinner.setAdapter(typeArrayAdapter);

        return view;
    }

    private void updateText(Calendar calendar, EditText expDatePicker) {
        String myFormat = "yyyy-MM-d"; // 2021-11-14
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        expDatePicker.setText(sdf.format(calendar.getTime()));
    }

    public void confirmUpload(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle("Have you verified all the information is correct?");
        builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Spinner brandSpinner = (Spinner) view.findViewById(R.id.brandSpinner);
                        Spinner categorySpinner = (Spinner) view.findViewById(R.id.categorySpinner);
                        Spinner typeSpinner = (Spinner) view.findViewById(R.id.typeSpinner);
                        EditText codePicker = (EditText) view.findViewById(R.id.codeInput);
                        EditText expDatePicker = (EditText) view.findViewById(R.id.expDatePicker);
                        ToggleButton inStoreCheckbox = (ToggleButton) view.findViewById(R.id.inStoreCheckbox);
                        ToggleButton onlineCheckbox = (ToggleButton) view.findViewById(R.id.onlineCheckbox);
                        ToggleButton militaryIdCheckbox = (ToggleButton) view.findViewById(R.id.militaryIdCheckbox);
                        ToggleButton stackedCheckbox = (ToggleButton) view.findViewById(R.id.stackedCheckbox);
                        EditText additionalInfoPicker = (EditText) view.findViewById(R.id.addInfoInput);
                        EditText productPicker = (EditText) view.findViewById(R.id.productInput);
                        EditText dealPicker = (EditText) view.findViewById(R.id.dealInput);


                        String selectedBrand = brandSpinner.getSelectedItem().toString();
                        String selectedCategory = categorySpinner.getSelectedItem().toString();
                        String selectedType = typeSpinner.getSelectedItem().toString();
                        String selectedCode = codePicker.getText().toString().trim();
                        String selectedExpDate = expDatePicker.getText().toString().trim();
                        Boolean selectedInStore = inStoreCheckbox.isChecked();
                        Boolean selectedOnline = onlineCheckbox.isChecked();
                        Boolean selectedMilitaryId = militaryIdCheckbox.isChecked();
                        Boolean selectedStacked = stackedCheckbox.isChecked();
                        String selectedAdditionalInformation = additionalInfoPicker.getText().toString().trim();
                        String selectedProduct = productPicker.getText().toString().trim();
                        String selectedDeal = dealPicker.getText().toString().trim();
                        Activity mActivity = getActivity();

                        ScrollView scrollView = getView().findViewById(R.id.submission_scroll);

                        if(selectedBrand.equalsIgnoreCase("Select Brand")){
                            Toast.makeText(mActivity, HtmlCompat.fromHtml("<font color='red'>Please select a brand!</font>", HtmlCompat.FROM_HTML_MODE_LEGACY), Toast.LENGTH_LONG).show();
                            dialog.cancel();
                            scrollView.post(new Runnable() {

                                @Override
                                public void run() {
                                    View incompleteView = getView().findViewById(R.id.brandSpinner);
                                    scrollView.smoothScrollTo(0, incompleteView.getTop());
                                    incompleteView.setFocusableInTouchMode(true);
                                    incompleteView.requestFocus();
                                }
                            });
                            return;
                        }

                        if(selectedCategory.equalsIgnoreCase("Select Category")){
                            Toast.makeText(mActivity, HtmlCompat.fromHtml("<font color='red'>Please select a category!</font>", HtmlCompat.FROM_HTML_MODE_LEGACY), Toast.LENGTH_LONG).show();
                            dialog.cancel();
                            scrollView.post(new Runnable() {

                                @Override
                                public void run() {
                                    View incompleteView = getView().findViewById(R.id.categorySpinner);
                                    scrollView.smoothScrollTo(0, incompleteView.getTop());
                                    incompleteView.setFocusableInTouchMode(true);
                                    incompleteView.requestFocus();
                                }
                            });
                            return;
                        }

                        if(selectedType.equalsIgnoreCase("Select Type")){
                            Toast.makeText(mActivity, HtmlCompat.fromHtml("<font color='red'>Please select a type!</font>", HtmlCompat.FROM_HTML_MODE_LEGACY), Toast.LENGTH_LONG).show();
                            dialog.cancel();
                            scrollView.post(new Runnable() {

                                @Override
                                public void run() {
                                    View incompleteView = getView().findViewById(R.id.typeSpinner);
                                    scrollView.smoothScrollTo(0, incompleteView.getTop());
                                    incompleteView.setFocusableInTouchMode(true);
                                    incompleteView.requestFocus();
                                }
                            });
                            return;
                        }

                        if(selectedDeal.isEmpty()){
                            Toast.makeText(mActivity, HtmlCompat.fromHtml("<font color='red'>Please enter a valid deal!</font>", HtmlCompat.FROM_HTML_MODE_LEGACY), Toast.LENGTH_LONG).show();
                            dialog.cancel();
                            scrollView.post(new Runnable() {

                                @Override
                                public void run() {
                                    View incompleteView = getView().findViewById(R.id.dealInput);
                                    scrollView.smoothScrollTo(0, incompleteView.getTop());
                                    incompleteView.setFocusableInTouchMode(true);
                                    incompleteView.requestFocus();
                                }
                            });
                            return;
                        }

                        if(selectedCode.isEmpty()){
                            Toast.makeText(mActivity, HtmlCompat.fromHtml("<font color='red'>Please enter a valid code!</font>", HtmlCompat.FROM_HTML_MODE_LEGACY), Toast.LENGTH_LONG).show();
                            dialog.cancel();
                            scrollView.post(new Runnable() {

                                @Override
                                public void run() {
                                    View incompleteView = getView().findViewById(R.id.codeInput);
                                    scrollView.smoothScrollTo(0, incompleteView.getTop());
                                    incompleteView.setFocusableInTouchMode(true);
                                    incompleteView.requestFocus();
                                }
                            });
                            return;
                        }

                        if(selectedExpDate.isEmpty()){
                            Toast.makeText(mActivity, HtmlCompat.fromHtml("<font color='red'>Please enter an expiration date!</font>", HtmlCompat.FROM_HTML_MODE_LEGACY), Toast.LENGTH_LONG).show();
                            dialog.cancel();
                            scrollView.post(new Runnable() {

                                @Override
                                public void run() {
                                    View incompleteView = getView().findViewById(R.id.expDatePicker);
                                    scrollView.smoothScrollTo(0, incompleteView.getTop());
                                    incompleteView.setFocusableInTouchMode(true);
                                    incompleteView.requestFocus();
                                }
                            });
                            return;
                        }

                        if(!(selectedInStore || selectedOnline)){
                            Toast.makeText(mActivity, HtmlCompat.fromHtml("<font color='red'>Please select if the coupon can be used in-store, online, or both!</font>", HtmlCompat.FROM_HTML_MODE_LEGACY), Toast.LENGTH_LONG).show();
                            dialog.cancel();
                            scrollView.post(new Runnable() {
                                @RequiresApi(api = Build.VERSION_CODES.Q)
                                @Override
                                public void run() {
                                    scrollView.scrollToDescendant(getView().findViewById(R.id.inStoreCheckbox));
                                }
                            });
                            return;
                        }

                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        LocalDateTime uploadDate = LocalDateTime.now();

                        Map<String, Boolean> attrs = new HashMap<>();

                        attrs.put("In-Store", selectedInStore);
                        attrs.put("Online", selectedOnline);
                        attrs.put("MilitaryID", selectedMilitaryId);
                        attrs.put("Stackable", selectedStacked);


                        Coupon newCoupon = new Coupon(selectedBrand, selectedProduct, new ArrayList<String>(Arrays.asList(selectedCategory)), selectedType,
                                                        selectedCode, selectedDeal, selectedExpDate, dtf.format(uploadDate), selectedAdditionalInformation, attrs);


                        try{
                            MainActivity.couponCollection.add(newCoupon);
                        }catch(Exception e){
                            Toast.makeText(mActivity, HtmlCompat.fromHtml("<font color='red'>Failed to upload coupon!</font>", HtmlCompat.FROM_HTML_MODE_LEGACY), Toast.LENGTH_LONG).show();
//                            Toast.makeText(getContext(), "Failed to upload coupon!", Toast.LENGTH_LONG).show();
                            return;
                        }

                        Toast.makeText(mActivity, HtmlCompat.fromHtml("<font color='green'>Successfully uploaded coupon!</font>", HtmlCompat.FROM_HTML_MODE_LEGACY), Toast.LENGTH_LONG).show();
//                        Toast.makeText(getContext(), "Successfully uploaded coupon!", Toast.LENGTH_LONG).show();

                        brandSpinner.setSelection(0);
                        categorySpinner.setSelection(0);
                        typeSpinner.setSelection(0);
                        codePicker.setText("");
                        expDatePicker.setText("");
                        inStoreCheckbox.setChecked(false);
                        onlineCheckbox.setChecked(false);
                        militaryIdCheckbox.setChecked(false);
                        stackedCheckbox.setChecked(false);
                        additionalInfoPicker.setText("");
                        dealPicker.setText("");
                        productPicker.setText("");
                    }
                });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}