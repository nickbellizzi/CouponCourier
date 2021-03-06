package edu.illinois.cs465.couponcourier;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // Are the search filters visible? Default is false.
    public static boolean filtersVisible = false;

    // Array of different category types
    public static final ArrayList<String> category_names = new ArrayList<> (Arrays.asList("All Categories", "Clothing", "Electronics", "Footwear", "Groceries", "Food", "Personal Care", "Toys", "Home & Kitchen", "Travel"));

    // Array of coupon types
    public static final ArrayList<String> coupon_types = new ArrayList<>(Arrays.asList("BXGX", "Freebie", "%off", "$off"));

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        Log.d("PreL", "Pre-parade");
        setListeners(v);
        Log.d("PostL", "Post-parade");
        populateResults();
        return v;
    }

    public void toggleFilters() {
        Log.d("Filter button", "Toggling!");
        ListView results = getActivity().findViewById(R.id.search_results);
        LinearLayout params = getActivity().findViewById(R.id.search_param_layout);
        TextView endResults = getActivity().findViewById(R.id.search_end);
        hideKeyboard(getParentFragment());
        populateResults();
        if (!SearchFragment.filtersVisible) {
            results.setVisibility(View.GONE);
            endResults.setVisibility(View.GONE);
            params.setVisibility(View.VISIBLE);

            SearchFragment.filtersVisible = true;
            return;
        }
        params.setVisibility(View.GONE);
        results.setVisibility(View.VISIBLE);
        endResults.setVisibility(View.VISIBLE);
        SearchFragment.filtersVisible = false;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        populateResults();
    }

    public void populateResults() {
        try {
            SearchQuery sq = MainActivity.currentQuery;
            ArrayList<Coupon> coupons = SearchQuery.search(sq);

            int len = coupons.size();
            List<String> listContents = new ArrayList<>(len);
            for (int i = 0; i < len; ++i) {
                Coupon coupon = coupons.get(i);
                String product = "";
                if (!coupon.product.isEmpty()) product = " (" + coupon.product + ')';
                String brand = coupon.brand + ": " + coupon.deal + product;
                listContents.add(brand);
            }
            Log.d("a", String.valueOf(listContents.size()));
            ListView lv = getView().findViewById(R.id.search_results);
            if (lv != null) {
                CouponAdapter adapter = new CouponAdapter(getActivity(), SearchQuery.search(MainActivity.currentQuery));
                //ArrayAdapter<Coupon> adapter = new ArrayAdapter<Coupon>(getActivity(), android.R.layout.simple_list_item_1, coupons);
                lv.setAdapter(adapter);
                Log.d("Yay", "I set the adapter.");
            } else {
                Log.d("ONO", "We couldn't get the list view wtf");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // https://stackoverflow.com/questions/1109022/how-do-you-close-hide-the-android-soft-keyboard-programmatically
    public static void hideKeyboard(Fragment fragment) {
        Activity activity = fragment.getActivity();
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void setListeners(View v) {
        SearchView.OnQueryTextListener sbListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                // your text view here
                MainActivity.currentQuery.query = newText;
                populateResults();
                System.out.println("HERE");
                Log.d("Searchbar TextChange", newText);
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("Searchbar TextSubmit", query);
                hideKeyboard(getParentFragment());
                MainActivity.currentQuery.query = query;
                checkFilters();
                populateResults();
                return true;
            }
        };

        ImageButton filterButton = v.findViewById(R.id.filter_button);
        Button filterApplyButton = v.findViewById(R.id.filter_apply_button);
        SearchView searchBar = v.findViewById(R.id.search_bar);
        ListView lv = v.findViewById(R.id.search_results);

        View.OnClickListener filterListener = new View.OnClickListener() {
            public void onClick(View v) {
                checkFilters();
                populateResults();
                toggleFilters();
            }
        };

        filterButton.setOnClickListener(filterListener);
        filterApplyButton.setOnClickListener(filterListener);

        searchBar.setOnQueryTextListener(sbListener);
        searchBar.setQuery(MainActivity.currentQuery.query, false);

        ArrayAdapter<String> catAdapt = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, category_names);
        Spinner catSpinner = v.findViewById(R.id.paramcb_category_spinner);
        catSpinner.setAdapter(catAdapt);

        ArrayAdapter<String> brandAdapt = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, SearchQuery.getUniqueBrands());
        Spinner brandSpinner = v.findViewById(R.id.paramcb_brand_spinner);
        brandSpinner.setAdapter(brandAdapt);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Search Item Click", String.valueOf(position));
                Coupon c = (Coupon) parent.getAdapter().getItem(position);
                MainActivity.coupon_index = MainActivity.couponCollection.indexOf(c);
                Log.d("Search Item Click", String.valueOf(MainActivity.coupon_index));

                NavController nc = Navigation.findNavController(v);
                nc.navigate(R.id.action_navigation_search_to_navigation_coupon);
            }
        });
    }

    public void checkFilters() {
        View v = getView();
        SearchView sv = v.findViewById(R.id.search_bar);
        String query = sv.getQuery().toString();
        MainActivity.currentQuery = new SearchQuery(query);

        SearchQuery sq = MainActivity.currentQuery;

        boolean bxgx = ((CheckBox) v.findViewById(R.id.paramcb_bxgx)).isChecked();
        boolean freebie = ((CheckBox) v.findViewById(R.id.paramcb_freebie)).isChecked();
        boolean dollar = ((CheckBox) v.findViewById(R.id.paramcb_dollar)).isChecked();
        boolean percent = ((CheckBox) v.findViewById(R.id.paramcb_percent)).isChecked();

        if (bxgx) {
            sq.types.add("BXGX");
        }
        if (freebie) {
            sq.types.add("Freebie");
        }
        if (dollar) {
            sq.types.add("$off");
        }
        if (percent) {
            sq.types.add("%off");
        }

        Spinner brandSpinner = v.findViewById(R.id.paramcb_brand_spinner);
        sq.brands.add(brandSpinner.getSelectedItem().toString());

        Spinner catSpinner = v.findViewById(R.id.paramcb_category_spinner);
        sq.categories.add(catSpinner.getSelectedItem().toString());
    }
}

