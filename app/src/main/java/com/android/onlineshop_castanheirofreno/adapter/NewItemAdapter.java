package com.android.onlineshop_castanheirofreno.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.android.onlineshop_castanheirofreno.ui.ProductDescriptionActivity;
import com.android.onlineshop_castanheirofreno.R;
import com.android.onlineshop_castanheirofreno.model.ItemModel;
import java.util.List;

public class NewItemAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<ItemModel> models;

    public NewItemAdapter(List<ItemModel> models, Context context){
        this.context = context;
        this.models = models;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container,final int position) {
        inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.new_item, container, false);

        ImageView imageView;
        TextView productName, price;

        imageView = view.findViewById(R.id.new_item_image);
        productName = view.findViewById(R.id.new_item_name);
        price = view.findViewById(R.id.new_item_price);

        imageView.setImageResource(models.get(position).getImage());
        productName.setText(models.get(position).getProductName());
        price.setText("CHF "+models.get(position).getPrice());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seeProductDescription(v);
            }
        });

        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    public void seeProductDescription (View view){
        Intent intent = new Intent(context, ProductDescriptionActivity.class);
        context.startActivity(intent);
    }
}
