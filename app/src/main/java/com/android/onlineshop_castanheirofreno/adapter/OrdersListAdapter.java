package com.android.onlineshop_castanheirofreno.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.android.onlineshop_castanheirofreno.R;
import com.android.onlineshop_castanheirofreno.model.OrderModel;

import java.util.List;

public class OrdersListAdapter extends BaseAdapter {

    private Context context;
    private List<OrderModel> ordersList;
    private LayoutInflater inflater;

    public OrdersListAdapter(List<OrderModel> models, Context context){
        this.context = context;
        this.ordersList = models;
    }

    @Override
    public int getCount() {
        return ordersList.size();
    }

    @Override
    public Object getItem(int position) {
        return ordersList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return ordersList.get(position).getIdOrder();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null){
            convertView = inflater.inflate(R.layout.order, null);
        }

        TextView textView_orderId = convertView.findViewById(R.id.textView_orderId);
        TextView textView_creationDate = convertView.findViewById(R.id.textView_creationDate);
        TextView textView_productName = convertView.findViewById(R.id.textView_productName);
        TextView textView_status = convertView.findViewById(R.id.textView_status);

        textView_orderId.setText(ordersList.get(position).getIdOrder());
        textView_creationDate.setText(ordersList.get(position).getCreationDate().toString());
        //textView_productName.setText(ordersList.get(position).get);


        return convertView;
    }
}
