package com.android.onlineshop_castanheirofreno.ui.home;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
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
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {


    private ViewPager viewPager;
    private NewItemAdapter newItemsAdapter;
    private List<ItemModel> models;
    private Timer timer;
    private int current_position;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        //Adding products to the List containing the new products
        models = new ArrayList<>();
        models.add(new ItemModel(1, R.drawable.acer_aspire_5_599, "Acer Aspire 5", 599.00));
        models.add(new ItemModel(2, R.drawable.apple_imac_1499, "Apple iMac", 1499.00));
        models.add(new ItemModel(3, R.drawable.acer_aspire_5_599, "Acer Aspire 5", 599.00));
        models.add(new ItemModel(4, R.drawable.apple_imac_1499, "Apple iMac", 1499.00));

        newItemsAdapter = new NewItemAdapter(models, getContext());

        viewPager = root.findViewById(R.id.newProducts_viewPager);
        viewPager.setAdapter(newItemsAdapter);
        viewPager.setPadding(150,0,150,0);

        autoScroll();
        return root;
    }

    private void autoScroll(){

        //Scroll automatically the cards
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if(current_position==models.size()){
                    current_position = 0 ;
                }
                viewPager.setCurrentItem(current_position++, true);
            }
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        }, 3500, 3500);
    }
}
