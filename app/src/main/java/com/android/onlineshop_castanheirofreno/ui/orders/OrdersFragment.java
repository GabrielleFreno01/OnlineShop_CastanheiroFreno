package com.android.onlineshop_castanheirofreno.ui.orders;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.onlineshop_castanheirofreno.ui.OrderDetailsActivity;
import com.android.onlineshop_castanheirofreno.R;

import java.util.ArrayList;
import java.util.List;


public class OrdersFragment extends Fragment {

    private List<OrderViewModel> ordersList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.order_list_item, container, false);

        ordersList.add(new OrderViewModel(1, 599.00, "03.03.2020", 1, "In Progress", null));
        ordersList.add(new OrderViewModel(2, 599.00, "03.03.2020", 1, "In Progress", null));
        ordersList.add(new OrderViewModel(3, 599.00, "03.03.2020", 1, "In Progress", null));
        ordersList.add(new OrderViewModel(4, 599.00, "03.03.2020", 1, "In Progress", null));
        ordersList.add(new OrderViewModel(5, 599.00, "03.03.2020", 1, "In Progress", null));
        ordersList.add(new OrderViewModel(6, 599.00, "03.03.2020", 1, "In Progress", null));
        ordersList.add(new OrderViewModel(7, 599.00, "03.03.2020", 1, "In Progress", null));
        ordersList.add(new OrderViewModel(8, 599.00, "03.03.2020", 1, "In Progress", null));
        ordersList.add(new OrderViewModel(9, 599.00, "03.03.2020", 1, "In Progress", null));
        ordersList.add(new OrderViewModel(10, 599.00, "03.03.2020", 1, "In Progress", null));
        ordersList.add(new OrderViewModel(11, 599.00, "03.03.2020", 1, "In Progress", null));
        ordersList.add(new OrderViewModel(12, 599.00, "03.03.2020", 1, "In Progress", null));
        ordersList.add(new OrderViewModel(13, 599.00, "03.03.2020", 1, "In Progress", null));
        ordersList.add(new OrderViewModel(14, 599.00, "03.03.2020", 1, "In Progress", null));
        ordersList.add(new OrderViewModel(15, 599.00, "03.03.2020", 1, "In Progress", null));
        ordersList.add(new OrderViewModel(16, 599.00, "03.03.2020", 1, "In Progress", null));
        ordersList.add(new OrderViewModel(18, 599.00, "03.03.2020", 1, "In Progress", null));
        ordersList.add(new OrderViewModel(19, 599.00, "03.03.2020", 1, "In Progress", null));

        final ListView listView = root.findViewById(R.id.listView_orders);

        listView.setAdapter(new OrdersListAdapter(ordersList, getContext()));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                seeOrderDetails(view, ordersList.get(position).getIdOrder());
            }
        });

        return root;

    }

    private void seeOrderDetails(View view, int idOrder){
        Intent intent = new Intent(getContext(), OrderDetailsActivity.class);
        intent.putExtra("ID_ORDER", idOrder);
        startActivity(intent);
    }
}