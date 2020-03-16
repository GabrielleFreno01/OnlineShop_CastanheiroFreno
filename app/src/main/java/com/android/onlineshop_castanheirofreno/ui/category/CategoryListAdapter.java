package com.android.onlineshop_castanheirofreno.ui.category;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.android.onlineshop_castanheirofreno.R;

import java.util.List;

public class CategoryListAdapter extends BaseAdapter {

    private Context context;
    private List<CategoryViewModel> categoryList;
    private LayoutInflater inflater;

    public CategoryListAdapter(Context context, List<CategoryViewModel> categoryList){
        this.context = context;
        this.categoryList = categoryList ;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public CategoryViewModel getItem(int position) {
        return categoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(R.layout.adapter_list_item, null);
        CategoryViewModel currentCategory = getItem(position);
        String categoryName = currentCategory.getCategoryName();
        String tag = currentCategory.getTag();

        ImageView categoryImageView = convertView.findViewById(R.id.category_image);
        String ressourceName = "ic_" + tag ;
        int resId = context.getResources().getIdentifier(ressourceName, "drawable", context.getPackageName());
        categoryImageView.setImageResource(resId);

        TextView categoryNameView = convertView.findViewById(R.id.category_name);
        categoryNameView.setText(categoryName);


        return convertView;
    }
}
