package com.android.onlineshop_castanheirofreno.ui.category;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


import com.android.onlineshop_castanheirofreno.ItemsListActivity;
import com.android.onlineshop_castanheirofreno.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {

    private CategoryViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_category, container, false);

                //Category List
                List<CategoryViewModel> categoryList = new ArrayList<>();

                categoryList.add(new CategoryViewModel("Audio","audio"));
                categoryList.add(new CategoryViewModel("Camera", "camera"));
                categoryList.add(new CategoryViewModel("Computer", "computer"));
                categoryList.add(new CategoryViewModel("Gaming","gaming"));
                categoryList.add(new CategoryViewModel("Smartphone","smartphone"));
                categoryList.add(new CategoryViewModel("TV","tv"));


                //get listView
                ListView categoryListView = root.findViewById(R.id.category_list);
                categoryListView.setAdapter(new CategoryListAdapter(getContext(), categoryList));


        categoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                seeItemsList(view);
            }
        });


        return root;


        }
    public void seeItemsList (View view){
        Intent intent = new Intent(getContext(), ItemsListActivity.class);
        startActivity(intent);
    }

    }

