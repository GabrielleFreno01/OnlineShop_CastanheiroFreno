package com.android.onlineshop_castanheirofreno.ui.cart;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.android.onlineshop_castanheirofreno.ui.ConfirmationActivity;
import com.android.onlineshop_castanheirofreno.R;


public class CartFragment extends Fragment {

    private CartViewModel cartViewModel;
    private Button buy;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cartViewModel =
                ViewModelProviders.of(this).get(CartViewModel.class);
        View root = inflater.inflate(R.layout.fragment_cart, container, false);

        buy = (Button) root.findViewById(R.id.btn_buy);
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seeConfirmation(v);
            }
        });

        return root;
    }

    public void seeConfirmation (View view) {
        Intent intent = new Intent(this.getContext(), ConfirmationActivity.class);
        startActivity(intent);
    }
}
