package com.android.onlineshop_castanheirofreno.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.onlineshop_castanheirofreno.R;


public class ItemListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private String[][] itemsList;
    private int[] imagesList;

    public ItemListAdapter(Context context, int[] imagesList, String[][] itemsList){
        this.context = context;
        this.itemsList = itemsList;
        this.imagesList = imagesList;
    }

    @Override
    public int getCount() {
        return itemsList.length ;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null){
            convertView = inflater.inflate(R.layout.product_item, null);
        }

        ImageView imageView = convertView.findViewById(R.id.item_image);
        TextView name_textView = convertView.findViewById(R.id.item_name);
        TextView price_textView = convertView.findViewById(R.id.item_price);

        imageView.setImageResource(imagesList[position]);
        name_textView.setText(itemsList[position][0]);
        price_textView.setText(itemsList[position][1]);

        return convertView;
    }
}
