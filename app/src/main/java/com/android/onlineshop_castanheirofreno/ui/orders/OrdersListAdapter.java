package com.android.onlineshop_castanheirofreno.ui.orders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.android.onlineshop_castanheirofreno.R;

import java.util.List;

public class OrdersListAdapter extends BaseAdapter {

    private Context context;
    private List<OrderViewModel> ordersList;
    private LayoutInflater inflater;

    public OrdersListAdapter(List<OrderViewModel> models, Context context){
        this.context = context;
        this.ordersList = models;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return ordersList.size();
    }

    @Override
    public OrderViewModel getItem(int position) {
        return ordersList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return ordersList.get(position).getIdOrder();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.order_list_item, null);

        OrderViewModel orderModel = getItem(position);

        TextView textView_orderId = convertView.findViewById(R.id.textView_orderId);
        TextView textView_creationDate = convertView.findViewById(R.id.textView_creationDate);
        TextView textView_productName = convertView.findViewById(R.id.textView_productName);
        TextView textView_status = convertView.findViewById(R.id.textView_status);


        textView_orderId.setText(Integer.toString(orderModel.getIdOrder()));
        textView_creationDate.setText(orderModel.getCreationDate());
        //textView_productName.setText(ordersList.get(position).get);
        return convertView;
    }
}
