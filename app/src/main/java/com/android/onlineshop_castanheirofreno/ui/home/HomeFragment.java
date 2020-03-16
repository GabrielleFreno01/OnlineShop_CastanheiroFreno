package com.android.onlineshop_castanheirofreno.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.android.onlineshop_castanheirofreno.R;
import com.android.onlineshop_castanheirofreno.adapter.NewItemAdapter;
import com.android.onlineshop_castanheirofreno.model.ItemModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {


    ViewPager viewPager;
    NewItemAdapter newItemsAdapter;
    List<ItemModel> models;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        //Adding products to the List containing the new products
        models = new ArrayList<>();
        models.add(new ItemModel(R.drawable.acer_aspire_5_599, "Acer Aspire 5", "CHF 599.00"));
        models.add(new ItemModel(R.drawable.apple_imac_1499, "Apple iMac", "CHF 1499.00"));
        models.add(new ItemModel(R.drawable.acer_aspire_5_599, "Acer Aspire 5", "CHF 599.00"));
        models.add(new ItemModel(R.drawable.apple_imac_1499, "Apple iMac", "CHF 1499.00"));

        newItemsAdapter = new NewItemAdapter(models, getContext());

        viewPager = root.findViewById(R.id.newProducts_viewPager);
        viewPager.setAdapter(newItemsAdapter);
        viewPager.setPadding(150,0,150,0);


        return root;
    }
}
