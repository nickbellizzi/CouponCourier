package edu.illinois.cs465.couponcourier;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SearchQuery {
    public String query = "";

    public ArrayList<String> brands = new ArrayList<>(); // Brands from a dropdown
    public ArrayList<String> categories = new ArrayList<>(); // Categories from a dropdown
    public ArrayList<String> types = new ArrayList<>(); // Types from toggles

    // Attribute boolean values
    public ArrayList<String> attributes = new ArrayList<>(); // Inside are the keys to check for

    public String orderBy = "";

    SearchQuery(String uquery, ArrayList<String> ubrands, ArrayList<String> ucats, ArrayList<String> utypes, ArrayList<String> uattr) {
        this.query = uquery.trim();
        this.brands = ubrands;
        this.categories = ucats;
        this.types = utypes;
        this.attributes = uattr;
    }

    // Empty constructor
    SearchQuery() {}

    // Query constructor (keyword only)
    SearchQuery(String uquery) {
        this.query = uquery;
    }


    // Given a SearchQuery, go through the couponCollection and lazily iterate through the whole set
    // This method gets called each time a search button is pressed.
    public static ArrayList<Coupon> search(SearchQuery sq) {
        ArrayList<Coupon> coupons = MainActivity.couponCollection;
        ArrayList<Coupon> results = new ArrayList<>();

        String query = sq.query.toLowerCase().trim();

        for (int i = 0; i < coupons.size(); ++i) {
            Coupon c = coupons.get(i);

            // Check product name if not empty
            if (!sq.query.isEmpty()) {
                if (!c.product.toLowerCase().contains(query) && !c.brand.toLowerCase().contains(query) && !c.deal.toLowerCase().contains((query))) {
                    continue;
                }
            }

            // Check brand if not empty
            if (!sq.brands.isEmpty()) {
                if (!sq.brands.contains(c.brand) && !sq.brands.contains("All Brands")) {
                    continue;
                }
            }

            // Check categories if not empty
            if (!sq.categories.isEmpty() && !sq.categories.contains("All Categories")) {
                Set<String> searchCategories = new HashSet<>();
                for (String category : sq.categories) {
                    String toLowerCase = category.toLowerCase();
                    searchCategories.add(toLowerCase);
                }

                Set<String> couponCategories = new HashSet<>();
                for (String s : c.category) {
                    String toLowerCase = s.toLowerCase();
                    couponCategories.add(toLowerCase);
                }

                couponCategories.retainAll(searchCategories);

                if (couponCategories.size() == 0) {
                    continue;
                }
            }

            // Check types if not empty
            if (!sq.types.isEmpty()) {
                if (!sq.types.contains(c.type)) {
                    continue;
                }
            }
            // Check attributes if the SQ list isn't empty.
            // ONLY ADD THE COUPON IF ALL OF THESE PASS
            results.add(c);
        }

        return results;
    }

    public static ArrayList<String> getUniqueBrands() {
        ArrayList<String> output = new ArrayList<>();
        output.add("All Brands");
        for (int i = 0; i < MainActivity.couponCollection.size(); ++i) {
            Coupon c = MainActivity.couponCollection.get(i);
            if (!output.contains(c.brand)) {
                output.add(c.brand);
            }
        }
        return output;
    }
}
