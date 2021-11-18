package edu.illinois.cs465.couponcourier;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize listeners
        setupListeners(view);
        return view;
    }

    SearchView.OnQueryTextListener sbListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextChange(String newText) {
            return true;
        }

        @Override
        public boolean onQueryTextSubmit(String query) {
            View v = getView();
            Log.d("Searchbar TextSubmit", query);
            SearchFragment.hideKeyboard(getParentFragment());
            MainActivity.currentQuery = new SearchQuery(query);
            NavController nc = Navigation.findNavController(v);
            nc.navigate(R.id.action_navigation_home_to_navigation_search);
            Log.d("home_search bar",query);
            return true;
        }
    };


    private void setupListeners(View v) {
        Log.d("setup", "yay");

        SearchView sv = v.findViewById(R.id.search_bar_home);
        sv.setOnQueryTextListener(sbListener);

        Button all_button = v.findViewById(R.id.all_button);
        all_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.currentQuery = new SearchQuery();
                NavController nc = Navigation.findNavController(view);
                nc.navigate(R.id.action_navigation_home_to_navigation_search);
            }
        });

        Button clothing_button = v.findViewById(R.id.clothing_button);
        clothing_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchQuery sq = new SearchQuery();
                sq.categories.add("clothing");
                MainActivity.currentQuery = sq;
                NavController nc = Navigation.findNavController(view);
                nc.navigate(R.id.action_navigation_home_to_navigation_search);
            }
        });

        Button electronics_button = v.findViewById(R.id.electronics_button);
        electronics_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchQuery sq = new SearchQuery();
                sq.categories.add("electronics");
                MainActivity.currentQuery = sq;
                NavController nc = Navigation.findNavController(view);
                nc.navigate(R.id.action_navigation_home_to_navigation_search);
            }
        });

        Button shoes_button = v.findViewById(R.id.shoes_button);
        shoes_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchQuery sq = new SearchQuery();
                sq.categories.add("footwear");
                MainActivity.currentQuery = sq;
                NavController nc = Navigation.findNavController(view);
                nc.navigate(R.id.action_navigation_home_to_navigation_search);
            }
        });

        Button groceries_button = v.findViewById(R.id.groceries_button);
        groceries_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchQuery sq = new SearchQuery();
                sq.categories.add("groceries");
                MainActivity.currentQuery = sq;
                NavController nc = Navigation.findNavController(view);
                nc.navigate(R.id.action_navigation_home_to_navigation_search);
            }
        });

        Button food_button = v.findViewById(R.id.food_button);
        food_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchQuery sq = new SearchQuery();
                sq.categories.add("food");
                MainActivity.currentQuery = sq;
                NavController nc = Navigation.findNavController(view);
                nc.navigate(R.id.action_navigation_home_to_navigation_search);
            }
        });

        Button personalcare_button = v.findViewById(R.id.personalcare_button);
        personalcare_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchQuery sq = new SearchQuery();
                sq.categories.add("personal_care");
                MainActivity.currentQuery = sq;
                NavController nc = Navigation.findNavController(view);
                nc.navigate(R.id.action_navigation_home_to_navigation_search);
            }
        });

        Button toys_button = v.findViewById(R.id.toys_button);
        toys_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchQuery sq = new SearchQuery();
                sq.categories.add("toys");
                MainActivity.currentQuery = sq;
                NavController nc = Navigation.findNavController(view);
                nc.navigate(R.id.action_navigation_home_to_navigation_search);
            }
        });

        Button homekitchen_button = v.findViewById(R.id.homekitchen_button);
        homekitchen_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchQuery sq = new SearchQuery();
                sq.categories.add("home_kitchen");
                MainActivity.currentQuery = sq;
                NavController nc = Navigation.findNavController(view);
                nc.navigate(R.id.action_navigation_home_to_navigation_search);
            }
        });

        Button travel_button = v.findViewById(R.id.travel_button);
        travel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchQuery sq = new SearchQuery();
                sq.categories.add("travel");
                MainActivity.currentQuery = sq;
                NavController nc = Navigation.findNavController(view);
                nc.navigate(R.id.action_navigation_home_to_navigation_search);
            }
        });
    }
}