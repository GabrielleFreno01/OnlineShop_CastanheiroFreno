package com.android.onlineshop_castanheirofreno.ui.orders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.onlineshop_castanheirofreno.R;

import java.util.ArrayList;
import java.util.List;


public class OrderFragment extends Fragment {

    private List<OrderViewModel> ordersList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.order_list, container, false);

        ordersList.add(new OrderViewModel(1, 599.00, "03.03.2020", 1, "In Progress", null));
        ordersList.add(new OrderViewModel(2, 599.00, "03.03.2020", 1, "In Progress", null));
        ordersList.add(new OrderViewModel(3, 599.00, "03.03.2020", 1, "In Progress", null));
        ordersList.add(new OrderViewModel(4, 599.00, "03.03.2020", 1, "In Progress", null));
        ordersList.add(new OrderViewModel(5, 599.00, "03.03.2020", 1, "In Progress", null));
        ordersList.add(new OrderViewModel(6, 599.00, "03.03.2020", 1, "In Progress", null));
        ordersList.add(new OrderViewModel(7, 599.00, "03.03.2020", 1, "In Progress", null));

        ListView listView = root.findViewById(R.id.listView_orders);

        listView.setAdapter(new OrderListAdapter(ordersList, this.getContext()));

        return root;

    }
}