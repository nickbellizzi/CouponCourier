package edu.illinois.cs465.couponcourier;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

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
import android.widget.Spinner;
import android.widget.TextView;

import java.sql.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UploadFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
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
        CheckBox inStoreCheckbox = (CheckBox) view.findViewById(R.id.inStoreCheckbox);
        CheckBox onlineCheckbox = (CheckBox) view.findViewById(R.id.onlineCheckbox);
        CheckBox militaryIdCheckbox = (CheckBox) view.findViewById(R.id.militaryIdCheckbox);
        CheckBox stackedCheckbox = (CheckBox) view.findViewById(R.id.stackedCheckbox);

        Calendar calendar = Calendar.getInstance();


        Set<String> categories = new HashSet<>();
        Set<String> brands = new HashSet<>();
        Set<String> types = new HashSet<>();


        for(Coupon c : MainActivity.couponCollection){
            brands.add(c.brand);
            types.add(c.type);
            for(String s : c.category){
                categories.add(s);
            }

        }


        ArrayList<String> category = new ArrayList<>();
        category.addAll(categories);
        category.add(0, "-");

        ArrayList<String> brand = new ArrayList<>();
        brand.addAll(brands);
        brand.add(0, "-");


        ArrayList<String> type = new ArrayList<>();
        type.addAll(types);
        type.add(0, "-");


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
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Spinner brandSpinner = (Spinner) view.findViewById(R.id.brandSpinner);
                        Spinner categorySpinner = (Spinner) view.findViewById(R.id.categorySpinner);
                        Spinner typeSpinner = (Spinner) view.findViewById(R.id.typeSpinner);
                        EditText expDatePicker = (EditText) view.findViewById(R.id.expDatePicker);
                        CheckBox inStoreCheckbox = (CheckBox) view.findViewById(R.id.inStoreCheckbox);
                        CheckBox onlineCheckbox = (CheckBox) view.findViewById(R.id.onlineCheckbox);
                        CheckBox militaryIdCheckbox = (CheckBox) view.findViewById(R.id.militaryIdCheckbox);
                        CheckBox stackedCheckbox = (CheckBox) view.findViewById(R.id.stackedCheckbox);


                        String selectedBrand = brandSpinner.getSelectedItem().toString();
                        String selectedCategory = categorySpinner.getSelectedItem().toString();
                        String selectedType = typeSpinner.getSelectedItem().toString();
                        String selectedExpDate = expDatePicker.getText().toString().trim();
                        Boolean selectedInStore = inStoreCheckbox.isSelected();
                        Boolean selectedOnline = onlineCheckbox.isSelected();
                        Boolean selectedMilitaryId = militaryIdCheckbox.isSelected();
                        Boolean selectedStacked = stackedCheckbox.isSelected();








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